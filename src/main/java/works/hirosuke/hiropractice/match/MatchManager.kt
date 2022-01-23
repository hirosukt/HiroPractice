package works.hirosuke.hiropractice.match

import org.bukkit.entity.Player
import works.hirosuke.hiropractice.match.matches.Sumo

object MatchManager {

    val matches = mutableListOf<Match>()

    fun isMatching(player: Player): Boolean {
        return findMatch(player) != null
    }

    /**
     * Find a match player joining.
     * if not found, return null.
     */
    fun findMatch(player: Player): Match? {
        MatchData.matches.forEach {
            it.value.forEach { team ->
                if (team.contains(player)) {
                    return it.key
                }
            }
        }

        return null
    }

    fun getInstance(match: EnumMatch, teams: List<Team>): Match {
        return when(match) {
            EnumMatch.SUMO -> Sumo(teams)
        }
    }
}