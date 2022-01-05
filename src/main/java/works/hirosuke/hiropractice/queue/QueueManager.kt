package works.hirosuke.hiropractice.queue

import works.hirosuke.hiropractice.match.EnumMatch

object QueueManager {

    /**
     * Search a match queueing players are above 1.
     * If match not found, return null.
     */
    fun searchStartableQueue(): EnumMatch? {
        EnumMatch.values().forEach {
            if (QueueData.queues.filter { queue -> queue.key.type == it }.size > 1) {
                return it
            }
        }
        return null
    }
}