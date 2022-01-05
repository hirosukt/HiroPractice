package works.hirosuke.hiropractice.match

import org.bukkit.entity.Player

abstract class Match(private val teams: List<List<Player>>) {

    val size = teams.size

    abstract val type: EnumMatch

    abstract fun begin()
    abstract fun end()

    // end()で呼び出す
    fun reset() {

    }
}