package works.hirosuke.hiropractice.event

import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent

class PlayerEvent: Listener {
    
    @EventHandler
    fun on(e: PlayerInteractEvent) {
        if (e.action == Action.PHYSICAL) e.isCancelled = true
    }

    @EventHandler
    fun on(e: BlockBreakEvent) {
        if (e.player.gameMode != GameMode.CREATIVE) e.isCancelled = true
    }

    @EventHandler
    fun on(e: BlockPlaceEvent) {
        if (e.player.gameMode != GameMode.CREATIVE) e.isCancelled = true
    }
}