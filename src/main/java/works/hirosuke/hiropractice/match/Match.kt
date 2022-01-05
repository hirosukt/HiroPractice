package works.hirosuke.hiropractice.match

import org.bukkit.entity.Player

abstract class Match(val teams: List<List<Player>>) {

    abstract fun begin()
    abstract fun end()

    // end()で呼び出す
    fun reset() {

    }
}