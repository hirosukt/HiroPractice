package works.hirosuke.hiropractice.match

import org.bukkit.GameMode
import org.bukkit.entity.Player
import works.hirosuke.hiropractice.match.matches.Sumo

object MatchManager {

    fun isMatching(player: Player): Boolean {
        return player.gameMode == GameMode.SURVIVAL
    }

    fun getInstance(match: EnumMatch): Match {
        return when (match) {
            EnumMatch.SUMO -> Sumo()
        }
    }

    fun getInstanceByName(name: String): Match? {
        return getInstance(EnumMatch.values().firstOrNull { it.name == name.uppercase() } ?: return null)
    }
}