package works.hirosuke.hiropractice.command.commands

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import works.hirosuke.hiropractice.command.Command

object ManagerCommand: Command("hiropractice") {

    override fun onCommand(sender: CommandSender, label: String, args: Array<out String>) {
        if (sender !is Player || !sender.hasPermission("hiropractice.command.manager")) return
    }

    override fun onTabComplete(sender: CommandSender, label: String, args: Array<out String>): List<String>? {
        TODO("Not yet implemented")
    }
}