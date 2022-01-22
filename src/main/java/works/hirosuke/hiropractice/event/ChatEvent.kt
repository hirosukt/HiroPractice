package works.hirosuke.hiropractice.event

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class ChatEvent: Listener {

    @EventHandler
    fun on(e: AsyncPlayerChatEvent) {
        val team = e.player.server.scoreboardManager.mainScoreboard.teams.firstOrNull { it.hasPlayer(e.player) }
        e.format = "${if (team != null) team.prefix else ""}${e.player.name}§7: ${e.message}"
    }
}