package works.hirosuke.hiropractice.event

import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.*
import works.hirosuke.hiropractice.HiroPractice.Companion.hiro
import works.hirosuke.hiropractice.match.EnumMatch
import works.hirosuke.hiropractice.match.MatchManager
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
        e.player.teleport(Location(e.player.world, 0.5, 6.0, 0.5))
        ItemUtil.setLobbyItem(e.player)

        hiro.server.onlinePlayers.forEach { player ->
            hiro.server.onlinePlayers.forEach { member ->
                player.showPlayer(member)
                member.showPlayer(player)
            }
        }
    }

    @EventHandler
    fun on(e: PlayerQuitEvent) {
        e.player.dequeue()
    }

    @EventHandler
    fun on(e: PlayerDropItemEvent) {
        e.player
    }

    @EventHandler
    fun on(e: FoodLevelChangeEvent) {
        val player = e.entity
        if (player !is Player) return

        e.isCancelled = MatchManager.findMatch(player)?.noFood == true || !MatchManager.isMatching(player)
    }

    @EventHandler
    fun on(e: PlayerDeathEvent) {
        e.drops.clear()
    }

    @EventHandler
    fun on(e: PlayerMoveEvent) {
        val player = e.player

        if (!player.isMovable()) player.teleport(e.from)

        if (MatchManager.findMatch(player)?.type == EnumMatch.SUMO) {
            if (player.isInWater()) {
                MatchManager.findMatch(player)?.onDeath(player)
            }
        }
    }

    @EventHandler
    fun on(e: EntityDamageByEntityEvent) {
        val player = e.entity
        val attacker = e.damager

        if (player !is Player || attacker !is Player) return

        if (MatchManager.findMatch(player)?.noDamage == true) {
            e.damage = 0.0
        }

        val x = 0.5
        val y = 0.28
        val z = 0.5
        val airx = 0.2
        val airy = 0.1
        val airz = 0.2

        hiro.runTaskLater(1) {
            player.velocity = if (!player.isGround()) {
                player.velocity.setX(attacker.location.direction.x * x).setY(y).setZ(attacker.location.direction.z * z)
            } else {
                player.velocity.setX(attacker.location.direction.x * airx).setY(airy).setZ(attacker.location.direction.z * airz)
            }
        }
    }
}