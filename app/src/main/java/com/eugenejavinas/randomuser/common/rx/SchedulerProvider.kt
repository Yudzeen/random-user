package com.eugenejavinas.randomuser.common.rx

import io.reactivex.rxjava3.core.Scheduler

interface SchedulerProvider {

    fun io(): Scheduler
    fun mainThread(): Scheduler

}