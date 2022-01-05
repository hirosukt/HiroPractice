package works.hirosuke.hiropractice.match.matches

import org.bukkit.entity.Player
import works.hirosuke.hiropractice.match.EnumMatch
import works.hirosuke.hiropractice.match.Match

class Skywars(teams: List<List<Player>>) : Match(teams) {

    override val type: EnumMatch = EnumMatch.SKYWARS

    override fun begin() {
        TODO("Not yet implemented")
    }

    override fun end() {
        TODO("Not yet implemented")
    }
}