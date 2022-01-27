package works.hirosuke.hiropractice.match

import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import works.hirosuke.hiropractice.HiroPractice.Companion.hiro
import works.hirosuke.hiropractice.util.ItemUtil
import works.hirosuke.hiropractice.util.dequeue
import works.hirosuke.hiropractice.util.runTaskLater
import works.hirosuke.hiropractice.util.runTaskTimer


abstract class Match(open var teams: List<Team>) {

    abstract val type: EnumMatch
    abstract val countdownSeconds: Int
    protected abstract val aliveTeams: List<MutableTeam>
    abstract val zeroDamage: Boolean
    abstract val noDamage: Boolean
    abstract val noFood: Boolean
    abstract val noDelay: Boolean

    abstract fun onDeath(player: Player)
    protected abstract fun startOriginal(teams: List<Team>)
    protected abstract fun endOriginal()
    protected abstract fun scoreboard()

    fun findTeam(player: Player): Team {
        return aliveTeams.first { it.members.contains(player) }.toTeam()
    }

    protected fun spectator(player: Player) {
        player.allowFlight = true
        player.isFlying = true
        player.gameMode = GameMode.ADVENTURE
        player.inventory.contents.forEach { player.world.dropItemNaturally(player.location, it) }
        player.inventory.clear()
    }

    fun start(teams: List<Team>) {
        MatchData.matches.add(this)

        teams.forEach { team ->
            team.members.forEach {
                it.dequeue()
            }
        }

        startOriginal(teams)
    }

    fun end() {

        teams.forEach { team ->
            team.members.forEach { player ->
                player.gameMode = GameMode.ADVENTURE
                player.sendMessage("§7§l§m                                                 ")
                player.sendMessage("Winner is §6§l${aliveTeams.filter { it.members.isNotEmpty() }[0].members.joinToString(", ") { it.name }}")
                player.sendMessage("§7§l§m                                                 ")
                player.playSound(player.location, Sound.ORB_PICKUP, .5f, 1f)

                hiro.runTaskLater(50) {
                    player.teleport(Location(player.world, 0.5, 10.0, 0.5))
                    player.allowFlight = false

                    ItemUtil.setLobbyItem(player)
                }
            }
        }

        MatchData.matches.remove(this)
        endOriginal()
    }

    protected inline fun countdown(crossinline after: BukkitRunnable.() -> Unit) {
        var count = countdownSeconds
        hiro.runTaskTimer(0, 20) {

            teams.forEach { team ->
                team.members.forEach {
                    it.sendMessage("§7Start in $count")
                    it.playSound(it.location, Sound.CLICK, .5f, 1f)
                }
            }

            count--

            if (count == -1) {
                teams.forEach { team ->
                    team.members.forEach {
                        it.sendMessage("§7§lStart")
                    }
                }

                cancel()
            }
        }

        hiro.runTaskLater((countdownSeconds * 20).toLong(), after)
    }
}