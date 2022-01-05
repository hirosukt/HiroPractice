package works.hirosuke.hiropractice.queue

import org.bukkit.entity.Player
import works.hirosuke.hiropractice.match.Match

object QueueData {

    val queues = LinkedHashMap<Match, Player>()
}