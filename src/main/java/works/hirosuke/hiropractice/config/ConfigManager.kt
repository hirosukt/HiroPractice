package works.hirosuke.hiropractice.config

import org.bukkit.Location
import org.bukkit.inventory.Inventory
import works.hirosuke.hiropractice.match.EnumMatch

object ConfigManager {

    val inventories = mutableMapOf<EnumMatch, Inventory>()
    val positions1 = mutableMapOf<EnumMatch, Location>()
    val positions2 = mutableMapOf<EnumMatch, Location>()

    fun loadInventories() {

    }

    fun loadPositions1() {

    }

    fun loadPositions2() {

    }

    fun loadAll() {

    }

    fun getInventory(match: EnumMatch) {
        inventories.firstNotNullOf { it.key == match }
    }

    fun getPosition1(match: EnumMatch) {
        positions1.firstNotNullOf { it.key == match }
    }

    fun getPosition2(match: EnumMatch) {
        positions2.firstNotNullOf { it.key == match }
    }
}