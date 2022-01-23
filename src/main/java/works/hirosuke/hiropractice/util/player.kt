package works.hirosuke.hiropractice.util

import org.bukkit.entity.Player

fun Player.isGround(): Boolean {
    return !location.subtract(0.0, 0.1, 0.0).block.isEmpty
}