package works.hirosuke.hiropractice.match

import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import works.hirosuke.hiropractice.HiroPractice.Companion.hiro
import works.hirosuke.hiropractice.util.SchedularUtil
import works.hirosuke.hiropractice.util.SchedularUtil.runTaskLater
import works.hirosuke.hiropractice.util.SchedularUtil.runTaskTimer


abstract class Match(open var teams: List<Team>) {

    abstract val type: EnumMatch
    abstract val countdownSeconds: Int
    abstract val aliveTeams: List<MutableTeam>

    abstract fun onDeath(player: Player)
    abstract fun start(vararg teams: Team)
    abstract fun end()

    fun findTeam(player: Player): Team {
        return aliveTeams.first { it.members.contains(player) }.toTeam()
    }

    fun spectator(player: Player) {
        player.allowFlight = true
        player.isFlying = true
        player.gameMode = GameMode.ADVENTURE

        aliveTeams.forEach { team ->
            team.members.forEach { member ->
                member.hidePlayer(player)
            }
        }

        teams.forEach { team ->
            team.members.forEach { player ->
                player.showPlayer(player)
            }
        }
    }

    fun startCountdown() {
        hiro.runTaskTimer(0, 20) {
            var count = countdownSeconds

            teams.forEach { team ->
                team.members.forEach {
                    if (count == countdownSeconds) {
                        it.sendTitle("START", "")
                        cancel()
                        return@forEach
                    }

                    it.sendTitle("START IN $count", "")
                }
            }

            count--
        }
    }

    inline fun afterCountdown(crossinline block: BukkitRunnable.() -> Unit) {
        hiro.runTaskLater(((countdownSeconds + 1) * 20).toLong(), block)
    }

    fun reset() {
        teams.forEach { team ->
            team.members.forEach { player ->
                player.teleport(player.world.spawnLocation)
                player.gameMode = GameMode.ADVENTURE
                player.allowFlight = false
                player.sendTitle("WINNER IS ${aliveTeams.filter { it.members.isNotEmpty() }.joinToString(", ")}", "")
            }
        }
    }
}