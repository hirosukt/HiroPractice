package works.hirosuke.hiropractice.match

import works.hirosuke.hiropractice.HiroPractice.Companion.hiro
import works.hirosuke.hiropractice.queue.Team
import works.hirosuke.hiropractice.util.SchedularUtil.runTaskTimer

abstract class Match(val teams: List<Team>) {

    val size = teams.size

    abstract val type: EnumMatch
    abstract val aliveTeams: MutableList<Team>
    abstract val countdownSeconds: Int

    abstract fun onCountdown()
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

        var countdown = 0

        hiro.runTaskTimer(0, 20) {
            countdown++

            onCountdown()

            if (countdown == countdownSeconds) cancel()
        }

        hiro.runTaskTimer(countdownSeconds + 1, 20) {
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

    private fun reset() {

    }
}