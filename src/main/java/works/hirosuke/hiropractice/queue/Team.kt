package works.hirosuke.hiropractice.queue

import org.bukkit.entity.Player

class Team(val member: List<Player>) {

    fun isMember(player: Player): Boolean {
        return player in member
    }
}