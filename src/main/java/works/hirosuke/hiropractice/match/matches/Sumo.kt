package works.hirosuke.hiropractice.match.matches

import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import works.hirosuke.hiropractice.match.*
import works.hirosuke.hiropractice.util.isInvulnerable

class Sumo(override var teams: List<Team>): Match(teams) {

    override val type: EnumMatch = EnumMatch.SUMO
    override val countdownSeconds: Int = 5
    override val aliveTeams: List<MutableTeam> = teams.map { it.toMutableTeam() }
    override val zeroDamage: Boolean = true
    override val noFood: Boolean = true
    override val noDelay: Boolean = false
    override val noDamage: Boolean = false

    override fun onDeath(player: Player) {
        aliveTeams.firstOrNull { it.members.contains(player) }?.members?.remove(player)
        spectator(player)
        player.isInvulnerable = true

        if (aliveTeams.filter { it.members.isNotEmpty() }.size <= 1) {
            end()
        }
    }

    override fun startOriginal(teams: List<Team>) {
        var spawnAtOpposition = false
        fun spawn(player: Player) = player.teleport(Location(player.world, 10000.5, 100.0, if (spawnAtOpposition) -4.5 else 5.5, if (spawnAtOpposition) 0f else 180f, 0f))

        teams.forEach { team ->
            team.members.forEach {
                it.inventory.clear()
                spawn(it)
                it.gameMode = GameMode.SURVIVAL
                it.allowFlight = false
                it.isInvulnerable = true
            }

            spawnAtOpposition = !spawnAtOpposition
        }

        countdown {
            teams.forEach { team ->
                team.members.forEach {
                    spawn(it)
                    it.isInvulnerable = false
                }
            }
        }
    }

    override fun endOriginal() {

    }

    override fun scoreboardOriginal() {
        TODO("Not yet implemented")
    }
}