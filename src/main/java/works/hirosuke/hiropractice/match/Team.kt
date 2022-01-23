package works.hirosuke.hiropractice.match

import org.bukkit.entity.Player

class Team(val members: List<Player>) {

    fun toMutableTeam(): MutableTeam {
        return MutableTeam(members.toMutableList())
    }
}