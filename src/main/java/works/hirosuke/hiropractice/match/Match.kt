package works.hirosuke.hiropractice.match


abstract class Match() {

    abstract val type: EnumMatch
    abstract val countdownSeconds: Int

    abstract fun onDeath()
    abstract fun start()
    abstract fun end()
}