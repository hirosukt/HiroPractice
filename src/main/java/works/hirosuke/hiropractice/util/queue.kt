package works.hirosuke.hiropractice.util

import org.bukkit.entity.Player
import works.hirosuke.hiropractice.match.EnumMatch
import works.hirosuke.hiropractice.queue.QueueData
import works.hirosuke.hiropractice.queue.QueueManager

fun Player.enqueue(match: EnumMatch) {
    dequeue()
    QueueData.queues[this] = match

    val found = QueueManager.searchStartableQueue(match)

    found?.startOriginal(found.teams)
}

fun Player.dequeue() {
    QueueData.queues.remove(this)
}