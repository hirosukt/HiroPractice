package works.hirosuke.hiropractice.match.matches

import works.hirosuke.hiropractice.match.EnumMatch
import works.hirosuke.hiropractice.match.Match

class Sumo : Match() {

    override val type: EnumMatch = EnumMatch.SUMO
    override val countdownSeconds: Int = 5

    override fun onDeath() {
        TODO("Not yet implemented")
    }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun end() {
        TODO("Not yet implemented")
    }
}