package id.web.adit.core.utils.rx

import io.reactivex.Scheduler

/**
 * adit 25/02/20.
 */
interface SchedulerProvider {
    fun computation(): Scheduler
    fun io(): Scheduler
    fun ui(): Scheduler
}
