package works.hirosuke.hiropractice.config

import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import works.hirosuke.hiropractice.HiroPractice.Companion.hiro
import works.hirosuke.hiropractice.match.EnumMatch
import java.io.File

object ConfigManager {

    val inventories = mutableMapOf<EnumMatch, Array<ItemStack>>()
    val armors = mutableMapOf<EnumMatch, Array<ItemStack>>()
    val stages = mutableMapOf<EnumMatch, MutableList<Location>>()

    val directory = File("${hiro.dataFolder}/kits")

    /**
     * Setup kit files and kits directory.
     * and create missing file.
     */
    fun setupDirectory() {
        if (!directory.exists()) directory.mkdir()

        EnumMatch.values().forEach {
            val file = File("${directory}/${it.name}.yml")
            if (!file.exists()) file.createNewFile()
        }
    }

    fun loadInventories() {
        EnumMatch.values().forEach {
            val file = File("${directory}/${it.name}.yml")
            val yaml = YamlConfiguration()
            yaml.load(file)

            inventories[it] = yaml.getList("inventory") as Array<ItemStack>
        }
    }

    fun loadArmors() {
        EnumMatch.values().forEach {
            val file = File("${directory}/${it.name}.yml")
            val yaml = YamlConfiguration()
            yaml.load(file)

            armors[it] = yaml.getList("armors") as Array<ItemStack>
        }
    }

    fun loadAll() {

    }

    fun getInventory(match: EnumMatch) {
        inventories.firstNotNullOf { it.key == match }
    }
}