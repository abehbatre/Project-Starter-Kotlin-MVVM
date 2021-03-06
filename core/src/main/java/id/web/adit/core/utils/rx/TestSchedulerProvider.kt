package id.web.adit.core.utils.rx

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestSchedulerProvider: SchedulerProvider {
    override fun computation(): Scheduler = Schedulers.trampoline()
    override fun io(): Scheduler = Schedulers.trampoline()
    override fun ui(): Scheduler = Schedulers.trampoline()
}
