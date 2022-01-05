package works.hirosuke.hiropractice.queue

import works.hirosuke.hiropractice.match.EnumMatch

object QueueManager {

    fun searchStartableQueue() {
        EnumMatch.values().forEach {
            if (QueueData.queues.filter { queue -> queue.key.type == it }.size > 1) {

            }
        }
    }
}