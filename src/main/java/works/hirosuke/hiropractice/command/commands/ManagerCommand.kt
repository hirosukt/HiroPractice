package works.hirosuke.hiropractice.command.commands

import org.bukkit.command.CommandSender
import works.hirosuke.hiropractice.command.Command

object ManagerCommand: Command("hiro-practice") {

    override fun onCommand(sender: CommandSender, label: String, args: Array<out String>) {
        TODO("Not yet implemented")
    }

    override fun onTabComplete(sender: CommandSender, label: String, args: Array<out String>): List<String>? {
        TODO("Not yet implemented")
    }
}