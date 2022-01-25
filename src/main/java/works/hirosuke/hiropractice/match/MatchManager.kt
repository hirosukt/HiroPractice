package works.hirosuke.hiropractice.match

import org.bukkit.Material
import org.bukkit.entity.Player
import works.hirosuke.hiropractice.HiroPractice.Companion.hiro
import works.hirosuke.hiropractice.match.matches.Sumo

object MatchManager {
    fun isMatching(player: Player): Boolean {
        return findMatch(player) != null
    }

    /**
     * Find a match player joining.
     * if not found, return null.
     */
    fun findMatch(player: Player): Match? {
        MatchData.matches.forEach {
            val team = it.teams.firstOrNull { team -> team.members.contains(player) }
            if (team != null) return it
        }

        return null
    }

    fun getInstance(match: EnumMatch, teams: List<Team>): Match {
        return when(match) {
            EnumMatch.SUMO -> Sumo(teams)
        }
    }

    fun getEnumByIcon(icon: Material): EnumMatch? {
        return EnumMatch.values().firstOrNull { it.icon == icon }
    }
}