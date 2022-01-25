package works.hirosuke.hiropractice.util

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object ItemUtil {

    private val UNRANKED_SELECTOR = create(Material.IRON_SWORD, "§4§lUnranked Match")
    private val DEQUEUE = create(Material.TORCH, "§4§lLeave Queue")

    fun setLobbyItem(player: Player) {
        player.inventory.setItem(0, UNRANKED_SELECTOR)
        player.inventory.setItem(8, DEQUEUE)
    }

    fun create(material: Material = Material.AIR, displayName: String = "", lore: List<String> = listOf(), amount: Int = 1, unbreakable: Boolean = false): ItemStack {
        val item = ItemStack(material)
        val meta = item.itemMeta
        meta.displayName = displayName
        meta.lore = lore
        meta.spigot().isUnbreakable = unbreakable
        if (unbreakable) meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        item.itemMeta = meta
        item.amount = amount
        return item
    }
}