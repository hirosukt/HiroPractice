package works.hirosuke.hiropractice.util

import org.bukkit.entity.Player
import works.hirosuke.hiropractice.match.Match
import works.hirosuke.hiropractice.match.MatchData


fun Player.isMatching(): Boolean {
    return findMatch() != null
}

/**
 * Find a match player joining.
 * if not found, return null.
 */
fun Player.findMatch(): Match? {
    MatchData.matches.forEach {
        val team = it.teams.firstOrNull { team -> team.members.contains(this) }
        if (team != null) return it
    }

    return null
}