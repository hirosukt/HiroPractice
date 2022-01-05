package works.hirosuke.hiropractice.util

import org.bukkit.ChatColor
import org.bukkit.entity.Player

object MessageUtil {

    fun Player.sendPrefixedMessage(text: String) {
        sendMessage(ChatColor.DARK_RED.toString() + "[Hiro]" + text)
    }
}