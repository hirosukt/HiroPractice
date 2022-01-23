package works.hirosuke.hiropractice.match

import org.bukkit.entity.Player

object MatchData {

    val matches = mutableMapOf<Match, MutableList<MutableList<Player>>>()
}