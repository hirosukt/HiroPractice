package works.hirosuke.hiropractice.queue

import org.bukkit.entity.Player
import works.hirosuke.hiropractice.match.EnumMatch
import works.hirosuke.hiropractice.match.Match
import works.hirosuke.hiropractice.match.MatchManager

object QueueManager {

    /**
     * Search a match queueing players are above 1.
     * If match not found, return null.
     */
    private fun searchStartableQueue(): Match? {
        EnumMatch.values().forEach { if (QueueData.queues.filter { queue -> queue.type == it }.size > 1) return MatchManager.getInstance(it) }
        return null
    }

    fun enqueue(match: EnumMatch, player: Player) {
        val queue = Queue(match, player)
        if (queue !in QueueData.queues) QueueData.queues.add(queue)

        searchStartableQueue()?.start()
    }

    fun dequeue(match: EnumMatch, player: Player) {
        QueueData.queues.remove(Queue(match, player))
    }
}