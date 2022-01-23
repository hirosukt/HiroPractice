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

object GuiEvent: Listener {

    @EventHandler
    fun on(e: InventoryClickEvent) {
        if (e.view.title == "Unranked Matches") {
            when (e.currentItem.type) {
                Material.LEASH -> {
                    QueueManager.enqueue(EnumMatch.SUMO, e.whoClicked as Player)
                }
            }
        }
    }

    @EventHandler
    fun on(e: PlayerInteractEvent) {
        if (e.action in listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR)) {
            if (e.item.type == Material.IRON_SWORD) {
                e.player.openInventory(GuiManager.unranked())
            }
        }
    }
}