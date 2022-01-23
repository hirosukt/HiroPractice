package works.hirosuke.hiropractice.command.commands

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import works.hirosuke.hiropractice.command.Command
import works.hirosuke.hiropractice.match.EnumMatch
import works.hirosuke.hiropractice.match.MatchManager

object ManagerCommand: Command("hiropractice") {

    override fun onCommand(sender: CommandSender, label: String, args: Array<out String>) {
        if (sender !is Player || !sender.hasPermission("hiropractice.command.manager") || args.isEmpty()) return

        when (args[0]) {
            "kit" -> {
                if (args.size <= 2) return

                val match = MatchManager.getInstanceByName(args[1]) ?: return

                when (args[2]) {
                    "inventory" -> {
                        
                    }
                }
            }
        }
    }

    override fun onTabComplete(sender: CommandSender, label: String, args: Array<out String>): List<String>? {
        TODO("Not yet implemented")
    }
}