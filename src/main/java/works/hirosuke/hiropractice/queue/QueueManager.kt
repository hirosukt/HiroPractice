package works.hirosuke.hiropractice.queue

import org.bukkit.entity.Player
import works.hirosuke.hiropractice.match.EnumMatch
import works.hirosuke.hiropractice.match.Match
import works.hirosuke.hiropractice.match.MatchManager
import works.hirosuke.hiropractice.match.Team

object QueueManager {

    /**
     * Search a match queueing players are above 1.
     * If match not found, return null.
     */
    private fun searchStartableQueue(): Match? {
        EnumMatch.values().forEach { match ->
            val players = QueueData.queues.filter { it.value == match }.map { it.key }
            if (players.size > 1) return MatchManager.getInstance(match, listOf(Team(listOf(players[0])), Team(listOf(players[1]))))
        }

        return null
    }

    fun enqueue(match: EnumMatch, player: Player) {
        QueueData.queues[player] = match

        searchStartableQueue()?.start()
    }

    fun dequeue(player: Player) {
        QueueData.queues.remove(player)
    }
}