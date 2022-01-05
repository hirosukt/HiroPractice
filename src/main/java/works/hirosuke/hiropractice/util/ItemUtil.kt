package works.hirosuke.hiropractice.util

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ItemUtil {

    fun createItem(material: Material = Material.AIR, displayName: String = "", lore: List<String> = listOf()): ItemStack {
        val item = ItemStack(material)
        val meta = item.itemMeta
        meta.displayName = displayName
        meta.lore = lore
        item.itemMeta = meta
        return item
    }
}