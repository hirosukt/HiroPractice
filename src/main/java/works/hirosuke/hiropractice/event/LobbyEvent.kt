package works.hirosuke.hiropractice.event

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.LeavesDecayEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.weather.WeatherChangeEvent

class LobbyEvent: Listener {

    @EventHandler
    fun on(e: LeavesDecayEvent) {
        e.isCancelled = true
    }

    @EventHandler
    fun on(e: WeatherChangeEvent) {
        e.isCancelled = true
    }
}