package works.hirosuke.hiropractice.event

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.*
import works.hirosuke.hiropractice.HiroPractice.Companion.hiro
import works.hirosuke.hiropractice.match.EnumMatch
import works.hirosuke.hiropractice.match.MatchManager
import works.hirosuke.hiropractice.util.*

object PlayerEvent: Listener {

    @EventHandler
    fun on(e: BlockBreakEvent) {
        if (e.player.gameMode != GameMode.CREATIVE) e.isCancelled = true
    }

    @EventHandler
    fun on(e: BlockPlaceEvent) {
        if (e.player.gameMode != GameMode.CREATIVE) e.isCancelled = true
    }

    @EventHandler
    fun on(e: PlayerJoinEvent) {
        e.player.teleport(Location(e.player.world, 0.5, 10.0, 0.5))
        ItemUtil.setLobbyItem(e.player)

        hiro.server.onlinePlayers.forEach { player ->
            hiro.server.onlinePlayers.forEach { member ->
                player.showPlayer(member)
                member.showPlayer(player)
            }
        }
    }

    @EventHandler
    fun on(e: PlayerInteractEvent) {
        if (e.action == Action.PHYSICAL && e.clickedBlock.type == Material.SOIL) {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun on(e: PlayerQuitEvent) {
        e.player.dequeue()

        if (e.player.isMatching()) {
            e.player.findMatch()?.onDeath(e.player)
        }
    }

    @EventHandler
    fun on(e: PlayerDropItemEvent) {
        e.player
    }

    @EventHandler
    fun on(e: FoodLevelChangeEvent) {
        val player = e.entity
        if (player !is Player) return

        e.isCancelled = player.findMatch()?.noFood == true || !player.isMatching()
    }

    @EventHandler
    fun on(e: PlayerDeathEvent) {
        e.drops.clear()
    }

    @EventHandler
    fun on(e: PlayerMoveEvent) {
        val player = e.player

        if (!player.isMovable()) player.teleport(e.from)

        if (player.findMatch()?.type == EnumMatch.SUMO) {
            if (player.isInWater()) {
                player.findMatch()?.onDeath(player)
            }
        }
    }

    @EventHandler
    fun on(e: EntityDamageByEntityEvent) {
        val player = e.entity
        val attacker = e.damager

        if (player !is Player || attacker !is Player) return

        if (!player.isMatching()) {
            e.isCancelled = true
            return
        }

        if (player.findMatch()?.noDamage == true) {
            e.damage = 0.0
        }

        val x = .7
        val y = .3
        val z = .7
        val airx = .3
        val airy = .15
        val airz = .3

//        val manager = ProtocolLibrary.getProtocolManager()
//        val container = PacketContainer(PacketType.Play.Server.ENTITY_VELOCITY).apply {
//            integers.write(0, player.entityId)
//
//            integers.write(1, if (player.isGround()) x else airx)
//            integers.write(2, if (player.isGround()) y else airy)
//            integers.write(3, if (player.isGround()) z else airz)
//        }
//
//        manager.sendServerPacket(player, container)

        (player as CraftPlayer).handle.playerConnection.sendPacket(
            PacketPlayOutEntityVelocity(
                player.entityId,
                if (player.isGround()) x else airx,
                if (player.isGround()) y else airy,
                if (player.isGround()) z else airz
            )
        )
    }
}