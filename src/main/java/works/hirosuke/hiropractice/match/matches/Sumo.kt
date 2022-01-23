package works.hirosuke.hiropractice.match.matches

import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import works.hirosuke.hiropractice.match.EnumMatch
import works.hirosuke.hiropractice.match.Match
import works.hirosuke.hiropractice.match.MatchManager
import works.hirosuke.hiropractice.match.Team

class Sumo(override var teams: List<Team>): Match(teams) {

    override val type: EnumMatch = EnumMatch.SUMO
    override val countdownSeconds: Int = 5
    override val aliveTeams: MutableList<Team> = mutableListOf()

    override fun onDeath(player: Player) {
        findTeam(player).members.remove(player)
        spectator(player)
    }

    override fun start(vararg teams: Team) {
        MatchManager.matches.add(this)

        var spawnAtOpposition = false

        teams.forEach { team ->
            team.members.forEach {
                it.teleport(Location(it.world, 10000.5, 11.0, if (spawnAtOpposition) -5.5 else 5.5))
                it.gameMode = GameMode.SURVIVAL
                it.allowFlight = false
            }

            spawnAtOpposition = !spawnAtOpposition
        }

        startCountdown()

        afterCountdown {

        }
    }

    override fun end() {
        reset()
    }
}