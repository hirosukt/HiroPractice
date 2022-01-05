package works.hirosuke.hiropractice;

import org.bukkit.plugin.java.JavaPlugin;
import works.hirosuke.hiropractice.command.commands.ManagerCommand

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

        ManagerCommand.register()
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("plugin has unloaded.")
    }
}
