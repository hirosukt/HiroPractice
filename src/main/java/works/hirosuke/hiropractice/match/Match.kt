package works.hirosuke.hiropractice.match

import works.hirosuke.hiropractice.HiroPractice.Companion.hiro
import works.hirosuke.hiropractice.queue.Team
import works.hirosuke.hiropractice.util.SchedularUtil.runTaskTimer

abstract class Match(val teams: List<Team>) {

    val size = teams.size

    abstract val type: EnumMatch
    abstract val aliveTeams: MutableList<Team>

    abstract fun onSeconds()
    abstract fun onStart()
    abstract fun onEnd()
    abstract fun scoreboard()

    private fun isEnded(): Boolean {
        return aliveTeams.size <= 1
    }

    fun start() {
        initTeam()
        onStart()

        hiro.runTaskTimer(0, 20) {
            onSeconds()
            scoreboard()

            if (isEnded()) {
                onEnd()
                reset()
                cancel()
            }
        }
    }

    private fun initTeam() {
        teams.forEach { aliveTeams.add(it) }
    }

    fun reset() {

    }
}