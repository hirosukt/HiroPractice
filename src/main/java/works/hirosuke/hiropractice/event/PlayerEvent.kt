package works.hirosuke.hiropractice.event

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class PlayerEvent: Listener {
    
    @EventHandler
    fun on(e: PlayerInteractEvent) {
        if (e.action == Action.PHYSICAL) e.isCancelled = true
    }
}