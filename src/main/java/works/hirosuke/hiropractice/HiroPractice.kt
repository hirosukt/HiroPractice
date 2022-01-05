package works.hirosuke.hiropractice;

import org.bukkit.plugin.java.JavaPlugin;

class HiroPractice : JavaPlugin() {

    companion object {
        lateinit var hiro: JavaPlugin
    }

    init {
        hiro = this
    }

    override fun onEnable() {
        // Plugin startup logic
        logger.info("plugin has loaded.")
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("plugin has unloaded.")
    }
}
