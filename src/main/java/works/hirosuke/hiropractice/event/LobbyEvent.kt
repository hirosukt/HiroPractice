package works.hirosuke.hiropractice.event

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockFadeEvent
import org.bukkit.event.block.LeavesDecayEvent
import org.bukkit.event.weather.WeatherChangeEvent

object LobbyEvent: Listener {

    @EventHandler
    fun on(e: LeavesDecayEvent) {
        e.isCancelled = true
    }

    @EventHandler
    fun on(e: WeatherChangeEvent) {
        e.isCancelled = true
    }

    @EventHandler
    fun on(e: BlockFadeEvent) {
        if (e.block.type == Material.SOIL) e.isCancelled = true
    }
}