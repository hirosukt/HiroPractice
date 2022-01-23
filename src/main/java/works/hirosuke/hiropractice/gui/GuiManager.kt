package works.hirosuke.hiropractice.gui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import works.hirosuke.hiropractice.match.EnumMatch
import works.hirosuke.hiropractice.util.ItemUtil

object GuiManager {

    fun unranked(): Inventory {
        val inventory = Bukkit.createInventory(null, 54, "§4§lUnranked Matches")
        EnumMatch.values().forEach {
            inventory.addItem(ItemUtil.create(it.icon, "§6§l" + it.displayName, amount = it.queueings() + 1))
        }
        return inventory
    }
}