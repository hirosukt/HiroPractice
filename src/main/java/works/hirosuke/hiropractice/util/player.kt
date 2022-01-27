package works.hirosuke.hiropractice.util

import org.bukkit.entity.Player

val invulnerable = mutableListOf<Player>()
var Player.isInvulnerable: Boolean
    get() = this in invulnerable
    set(value) = (if (value) { if (!isInvulnerable) invulnerable.add(this) else { } } else invulnerable.remove(this)) as Unit

fun Player.isGround(): Boolean {
    return !location.subtract(0.0, 0.05, 0.0).block.isEmpty
}

fun Player.isInWater(): Boolean {
    return this.world.getBlockAt(this.location).isLiquid
}