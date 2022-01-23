package works.hirosuke.hiropractice.match

import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import works.hirosuke.hiropractice.HiroPractice.Companion.hiro
import works.hirosuke.hiropractice.queue.QueueManager
import works.hirosuke.hiropractice.util.ItemUtil
import works.hirosuke.hiropractice.util.SchedularUtil
import works.hirosuke.hiropractice.util.SchedularUtil.runTaskLater
import works.hirosuke.hiropractice.util.SchedularUtil.runTaskTimer


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

        teams.forEach { team ->
            team.members.forEach { member ->
                member.showPlayer(player)
            }
        }

        aliveTeams.forEach { team ->
            team.members.forEach { member ->
                member.hidePlayer(player)
            }
        }
    }

    fun startOriginal(teams: List<Team>) {
        MatchData.matches.add(this)

        teams.forEach { team ->
            team.members.forEach {
                QueueManager.dequeue(it)
            }
        }

        start(teams)
    }

    fun endOriginal() {
        reset()
        MatchData.matches.remove(this)
        end()
    }

    fun startCountdown() {
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
    }

    inline fun afterCountdown(crossinline block: BukkitRunnable.() -> Unit) {
        hiro.runTaskLater((countdownSeconds * 20).toLong(), block)
    }

    private fun reset() {
        teams.forEach { team ->
            team.members.forEach { player ->
                player.teleport(Location(player.world, 0.0, 6.0, 0.0))
                player.gameMode = GameMode.ADVENTURE
                player.allowFlight = false
                player.sendMessage("Winner is ${aliveTeams.filter { it.members.isNotEmpty() }[0].members.joinToString(", ") { it.name }}")
                player.inventory.setItem(0, ItemUtil.UNRANKED_SELECTOR)
            }
        }

        hiro.server.onlinePlayers.forEach { player ->
            hiro.server.onlinePlayers.forEach { member ->
                player.showPlayer(member)
                member.showPlayer(player)
            }
        }
    }
}