package works.hirosuke.hiropractice.match

import org.bukkit.Material
import org.bukkit.entity.Player
import works.hirosuke.hiropractice.HiroPractice.Companion.hiro
import works.hirosuke.hiropractice.match.matches.Sumo

object MatchManager {

    fun getInstance(match: EnumMatch, teams: List<Team>): Match {
        return when(match) {
            EnumMatch.SUMO -> Sumo(teams)
        }
    }

    fun getEnumByIcon(icon: Material): EnumMatch? {
        return EnumMatch.values().firstOrNull { it.icon == icon }
    }
}