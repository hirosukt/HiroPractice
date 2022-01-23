package works.hirosuke.hiropractice.match

import org.bukkit.entity.Player

class MutableTeam(val members: MutableList<Player>) {

    fun toTeam(): Team {
        return Team(members)
    }
}