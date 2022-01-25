package works.hirosuke.hiropractice.util

import org.bukkit.entity.Player

private val unmovable = mutableListOf<Player>()

fun Player.isGround(): Boolean {
    return !location.subtract(0.0, 0.05, 0.0).block.isEmpty
}

fun Player.isMovable(): Boolean {
    return this !in unmovable
}

fun Player.setMovable(value: Boolean) {
    if (value) unmovable.remove(this) else if (this !in unmovable) unmovable.add(this)
}

fun Player.isInWater(): Boolean {
    return this.world.getBlockAt(this.location).isLiquid
}