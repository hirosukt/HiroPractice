package works.hirosuke.hiropractice.match.matches

import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import works.hirosuke.hiropractice.HiroPractice.Companion.hiro
import works.hirosuke.hiropractice.match.*
import works.hirosuke.hiropractice.util.MatchUtil

class Sumo(override var teams: List<Team>): Match(teams) {

    override val type: EnumMatch = EnumMatch.SUMO
    override val countdownSeconds: Int = 5
    override val aliveTeams: List<MutableTeam> = teams.map { it.toMutableTeam() }
    override val noDamage: Boolean = true
    override val noFood: Boolean = true

    override fun onDeath(player: Player) {
        aliveTeams.firstOrNull { it.members.contains(player) }?.members?.remove(player)
        spectator(player)

        if (aliveTeams.filter { it.members.isNotEmpty() }.size <= 1) {
            endOriginal()
        }
    }

    override fun start(teams: List<Team>) {
        var spawnAtOpposition = false

        teams.forEach { team ->
            team.members.forEach {
                it.inventory.clear()
                it.teleport(Location(it.world, 10000.5, 11.0, if (spawnAtOpposition) -4.5 else 5.5, if (spawnAtOpposition) 0f else 180f, 0f))
                it.gameMode = GameMode.SURVIVAL
                it.allowFlight = false

                MatchUtil.setMovable(it, false)
            }

            spawnAtOpposition = !spawnAtOpposition
        }

        countdown {
            teams.forEach { team ->
                team.members.forEach {
                    MatchUtil.setMovable(it, true)
                }
            }
        }
    }

    override fun end() {

    }
}