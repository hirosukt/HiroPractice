package works.hirosuke.hiropractice.match

import org.bukkit.Material
import works.hirosuke.hiropractice.queue.QueueData

enum class EnumMatch(val icon: Material, val displayName: String) {
    SUMO(Material.LEASH, "Sumo"),
    BOXING(Material.DIAMOND_CHESTPLATE, "Boxing")
    ;

    fun queueings(): Int {
        return QueueData.queues.filter { it.value == this }.size
    }
}