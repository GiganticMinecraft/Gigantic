package click.seichi.gigantic.schedule

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scheduler.BukkitTask
import java.util.concurrent.TimeUnit

/**
 * @author tar0ss
 */
abstract class AbstractScheduler(
        protected val plugin: Plugin,
        protected val bukkitScheduler: BukkitScheduler
) : Scheduler() {

    override fun createWorker() = SpigotWorker()

    /**
     * Schedule  task with the given runnable.
     *
     * @param runnable The runnable.
     * @return The bukkit task instance.
     */
    protected abstract fun schedule(runnable: Runnable): BukkitTask

    /**
     * Schedule a task with a specific delay given in minecraft server ticks.
     *
     * @param runnable The runnable.
     * @param delay    The delay.
     * @return The bukkit task instance.
     */
    protected abstract fun schedule(runnable: Runnable, delay: Long): BukkitTask

    /**
     * Schedule a task with a specific delay created by the initial delay and the following intervals.
     *
     * @param runnable The runnable.
     * @param delay    The delay.
     * @param interval The interval
     * @return The bukkit task instance.
     */
    protected abstract fun schedule(runnable: Runnable, delay: Long, interval: Long): BukkitTask


    inner class SpigotWorker : Worker() {

        private val compositeDisposable = CompositeDisposable()


        override fun isDisposed() = compositeDisposable.isDisposed

        override fun schedule(runnable: Runnable): Disposable {
            val bukkitTask = this@AbstractScheduler.schedule(
                    runnable
            )
            val disposable = DisposableBukkitTask(bukkitTask)
            compositeDisposable.add(disposable)
            return disposable
        }

        override fun schedule(runnable: Runnable, delay: Long, unit: TimeUnit): Disposable {
            val bukkitTask = this@AbstractScheduler.schedule(
                    runnable,
                    convertTimeToTicks(delay, unit)
            )
            val disposable = DisposableBukkitTask(bukkitTask)
            compositeDisposable.add(disposable)
            return disposable
        }

        override fun schedulePeriodically(runnable: Runnable, initialDelay: Long, period: Long, unit: TimeUnit): Disposable {
            val bukkitTask = this@AbstractScheduler.schedule(
                    runnable,
                    convertTimeToTicks(initialDelay, unit),
                    convertTimeToTicks(period, unit)
            )
            val disposable = DisposableBukkitTask(bukkitTask)
            compositeDisposable.add(disposable)
            return disposable
        }

        override fun dispose() = compositeDisposable.dispose()

        /**
         * Convert java time to bukkit ticks.
         *
         * @param time     The time.
         * @param timeUnit The time unit.
         * @return The time in bukkit ticks.
         */
        private fun convertTimeToTicks(time: Long, timeUnit: TimeUnit): Long {
            return Math.round((timeUnit.toMillis(time) / 50F).toDouble())
        }

    }

    /**
     * Wrap a bukkit task in a disposable.
     *
     * @param bukkitTask The bukkit task.
     */
    private class DisposableBukkitTask(
            private val bukkitTask: BukkitTask
    ) : Disposable {

        private var disposed = false


        override fun isDisposed(): Boolean {
            return disposed && !bukkitTask.owner.server.scheduler.isCurrentlyRunning(bukkitTask.taskId)
        }

        override fun dispose() {
            disposed = true
            this.bukkitTask.cancel()
        }

    }
}