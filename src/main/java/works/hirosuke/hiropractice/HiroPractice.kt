package works.hirosuke.hiropractice
import org.bukkit.plugin.java.JavaPlugin
import works.hirosuke.hiropractice.command.commands.ManagerCommand
import works.hirosuke.hiropractice.config.ConfigManager
import works.hirosuke.hiropractice.event.ChatEvent
import works.hirosuke.hiropractice.event.GuiEvent
import works.hirosuke.hiropractice.event.LobbyEvent
import works.hirosuke.hiropractice.event.PlayerEvent

class HiroPractice : JavaPlugin() {

    companion object {
        lateinit var hiro: JavaPlugin
    }

    init {
        hiro = this
    }

    override fun onEnable() {
        // Plugin startup logic
        ManagerCommand.register()

//        ConfigManager.setupDirectory()

        listOf(ChatEvent, GuiEvent, LobbyEvent, PlayerEvent).forEach {
            server.pluginManager.registerEvents(it, this)
        }

        logger.info("plugin has loaded.")
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("plugin has unloaded.")
    }
}
