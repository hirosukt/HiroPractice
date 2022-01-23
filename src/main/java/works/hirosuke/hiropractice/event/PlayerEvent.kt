package works.hirosuke.hiropractice.event

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import works.hirosuke.hiropractice.match.EnumMatch
import works.hirosuke.hiropractice.match.MatchManager
import works.hirosuke.hiropractice.util.ItemUtil
import works.hirosuke.hiropractice.util.MatchUtil

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
        e.player.inventory.setItem(0, ItemUtil.create(Material.IRON_SWORD, "§4§lUnranked Match"))
    }

    @EventHandler
    fun on(e: PlayerDropItemEvent) {

    }

    @EventHandler
    fun on(e: FoodLevelChangeEvent) {
        if (e.entity !is Player) return

        (e.entity as Player).foodLevel = 20
    }

    @EventHandler
    fun on(e: PlayerDeathEvent) {
        e.drops.clear()
    }

    @EventHandler
    fun on(e: PlayerMoveEvent) {
        val player = e.player
        if (MatchManager.isMatching(player) && MatchManager.findMatch(player)?.type == EnumMatch.SUMO) {
            if (MatchUtil.isInWater(player)) {
                MatchManager.findMatch(player)?.onDeath(player)
            }
        }
    }
}