package works.hirosuke.hiropractice.event

import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.*
import works.hirosuke.hiropractice.HiroPractice.Companion.hiro
import works.hirosuke.hiropractice.match.EnumMatch
import works.hirosuke.hiropractice.match.matches.Boxing
import works.hirosuke.hiropractice.util.*

object PlayerEvent: Listener {

    @EventHandler
    fun on(e: BlockBreakEvent) {
        if (e.player.gameMode != GameMode.CREATIVE) e.isCancelled = true
    }

    @EventHandler
    fun on(e: BlockPlaceEvent) {
        if (e.player.gameMode != GameMode.CREATIVE) e.isCancelled = true
    }

    @EventHandler
    fun on(e: PlayerJoinEvent) {
        e.player.teleport(Location(e.player.world, 0.5, 10.0, 0.5))
        ItemUtil.setLobbyItem(e.player)

        hiro.server.onlinePlayers.forEach { player ->
            hiro.server.onlinePlayers.forEach { member ->
                player.showPlayer(member)
                member.showPlayer(player)
            }
        }
    }

    @EventHandler
    fun on(e: PlayerInteractEvent) {
        if (e.action == Action.PHYSICAL && e.clickedBlock.type == Material.SOIL) {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun on(e: PlayerQuitEvent) {
        e.player.dequeue()

        if (e.player.isMatching()) {
            e.player.findMatch()?.onDeath(e.player)
        }
    }

    @EventHandler
    fun on(e: PlayerDropItemEvent) {
        e.player
    }

    @EventHandler
    fun on(e: FoodLevelChangeEvent) {
        val player = e.entity
        if (player !is Player) return

        e.isCancelled = player.findMatch()?.noFood == true || !player.isMatching()
    }

    @EventHandler
    fun on(e: PlayerDeathEvent) {
        e.drops.clear()
    }

    @EventHandler
    fun on(e: PlayerMoveEvent) {
        val player = e.player

        if (player.findMatch()?.type == EnumMatch.SUMO) {
            if (player.isInWater()) {
                player.findMatch()?.onDeath(player)
            }
        }
    }

    @EventHandler
    fun on(e: EntityDamageEvent) {
        val player = e.entity as? Player

        if (player?.isInvulnerable == true) {
            e.isCancelled = true
            return
        }
    }

    @EventHandler
    fun on(e: EntityDamageByEntityEvent) {
        val player = e.entity
        val attacker = e.damager

        if (player !is Player || attacker !is Player) return

        if (player.findMatch()?.zeroDamage == true) {
            e.damage = 0.0
        }

        when (player.findMatch()?.type) {
            EnumMatch.BOXING -> {
                val match = player.findMatch()
                (match as Boxing).hits[match.findTeam(player)] = (match.hits[match.findTeam(player)] ?: return).inc()
            }
            else -> return
        }
    }

    @EventHandler
    fun on(e: PlayerVelocityEvent) {
        if (e.player.lastDamageCause is EntityDamageByEntityEvent) {

            val damager = (e.player.lastDamageCause as? EntityDamageByEntityEvent)?.damager ?: return

            val x = damager.location.direction.x * .73
            val y = .33
            val z = damager.location.direction.z * .73
            val airx = damager.location.direction.x * .67
            val airy = .25
            val airz = damager.location.direction.z * .67

            e.player.velocity = if (e.player.isGround()) {
                e.velocity.setX(x).setY(y).setZ(z)
            } else {
                e.velocity.setX(airx).setY(airy).setZ(airz)
            }
        }
    }
}