package works.hirosuke.hiropractice.event

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.*
import org.bukkit.util.Vector
import works.hirosuke.hiropractice.HiroPractice.Companion.hiro
import works.hirosuke.hiropractice.match.EnumMatch
import works.hirosuke.hiropractice.match.MatchManager
import works.hirosuke.hiropractice.util.ItemUtil
import works.hirosuke.hiropractice.util.MatchUtil
import works.hirosuke.hiropractice.util.SchedularUtil.runTaskLater
import works.hirosuke.hiropractice.util.isGround

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
        e.player.inventory.setItem(0, ItemUtil.UNRANKED_SELECTOR)
    }

    @EventHandler
    fun on(e: PlayerDropItemEvent) {

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

        if (!MatchUtil.isMovable(player)) player.teleport(e.from)

        if (MatchManager.findMatch(player)?.type == EnumMatch.SUMO) {
            if (MatchUtil.isInWater(player)) {
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

        val x = 0.6
        val y = 0.35
        val z = 0.6

        hiro.runTaskLater(1) {
            player.velocity = player.velocity.setX(attacker.location.direction.x * x).setY(if (player.isGround()) y else 0.1).setZ(attacker.location.direction.z * z)
        }
    }
}