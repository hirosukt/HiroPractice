package works.hirosuke.hiropractice.event

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import works.hirosuke.hiropractice.gui.GuiManager
import works.hirosuke.hiropractice.match.EnumMatch
import works.hirosuke.hiropractice.match.MatchManager
import works.hirosuke.hiropractice.match.matches.Sumo
import works.hirosuke.hiropractice.queue.QueueManager
import works.hirosuke.hiropractice.util.dequeue
import works.hirosuke.hiropractice.util.enqueue

object GuiEvent: Listener {

    @EventHandler
    fun on(e: InventoryClickEvent) {
        val player = e.whoClicked

        if (player !is Player) return

        if (e.view.title == "§4§lUnranked Matches") {
            e.isCancelled = true
            val item = e.currentItem ?: return
            when (item.type) {
                Material.LEASH -> {
                    player.enqueue(EnumMatch.SUMO)
                }
                else -> return
            }

            player.closeInventory()
            e.whoClicked.sendMessage("§7You queued §6${MatchManager.getEnumByIcon(item.type)?.displayName}§7.")
        }
    }

    @EventHandler
    fun on(e: PlayerInteractEvent) {
        if (e.action in listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR)) {
            val item = (e.item ?: return).type
            e.isCancelled = true
            when (item) {
                Material.IRON_SWORD -> e.player.openInventory(GuiManager.unranked())
                Material.TORCH -> {
                    e.player.dequeue()
                    e.player.sendMessage("§7Leaved queue.")
                }
                else -> e.isCancelled = false
            }
        }
    }
}