package works.hirosuke.hiropractice.match

import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import works.hirosuke.hiropractice.HiroPractice.Companion.hiro
import works.hirosuke.hiropractice.util.ItemUtil
import works.hirosuke.hiropractice.util.runTaskLater
import works.hirosuke.hiropractice.util.runTaskTimer


abstract class Match(open var teams: List<Team>) {

    abstract val type: EnumMatch
    abstract val countdownSeconds: Int
    abstract val aliveTeams: List<MutableTeam>
    abstract val noDamage: Boolean
    abstract val noFood: Boolean

    abstract fun onDeath(player: Player)
    abstract fun start(teams: List<Team>)
    abstract fun end()

    fun findTeam(player: Player): Team {
        return aliveTeams.first { it.members.contains(player) }.toTeam()
    }

    fun spectator(player: Player) {
        player.allowFlight = true
        player.isFlying = true
        player.gameMode = GameMode.ADVENTURE
    }

    fun startOriginal(teams: List<Team>) {
        MatchData.matches.add(this)

        start(teams)
    }

    fun endOriginal() {
        reset()
        MatchData.matches.remove(this)
        end()
    }

    inline fun countdown(crossinline after: BukkitRunnable.() -> Unit) {
        var count = countdownSeconds
        hiro.runTaskTimer(0, 20) {

            teams.forEach { team ->
                team.members.forEach {
                    it.sendMessage("Start in $count")
                }
            }

            count--

            if (count == 0) {
                teams.forEach { team ->
                    team.members.forEach {
                        it.sendMessage("Start")
                    }
                }

                cancel()
            }
        }

        hiro.runTaskLater((countdownSeconds * 20).toLong(), after)
    }

    private fun reset() {
        teams.forEach { team ->
            team.members.forEach { player ->
                player.gameMode = GameMode.ADVENTURE
                player.sendMessage("Winner is ${aliveTeams.filter { it.members.isNotEmpty() }[0].members.joinToString(", ") { it.name }}")

                hiro.runTaskLater(40) {
                    player.teleport(Location(player.world, 0.0, 6.0, 0.0))
                    player.allowFlight = false
                    player.inventory.setItem(0, ItemUtil.UNRANKED_SELECTOR)
                }
            }
        }
    }
}