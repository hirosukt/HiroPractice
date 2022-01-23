package works.hirosuke.hiropractice.queue

import org.bukkit.entity.Player
import works.hirosuke.hiropractice.match.EnumMatch

object QueueData {

    val queues = mutableMapOf<Player, EnumMatch>()
}