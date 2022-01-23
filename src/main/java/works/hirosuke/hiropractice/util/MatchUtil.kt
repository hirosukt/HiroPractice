package works.hirosuke.hiropractice.util

import org.bukkit.Material
import org.bukkit.entity.Player

object MatchUtil {

    private val unmovable = mutableListOf<Player>()

    fun isMovable(player: Player): Boolean {
        return player !in unmovable
    }

    fun setMovable(player: Player, value: Boolean) {
        if (value) unmovable.remove(player) else if (player !in unmovable) unmovable.add(player)
    }

    fun isInWater(player: Player): Boolean {
        return player.location.block.type == Material.WATER
    }
}