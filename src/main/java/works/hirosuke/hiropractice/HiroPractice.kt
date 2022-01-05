package works.hirosuke.hiropractice;

import org.bukkit.plugin.java.JavaPlugin;

class HiroPractice : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        logger.info("plugin has loaded.")
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("plugin has unloaded.")
    }
}
