
package com.example.domain;

import io.reactivex.Scheduler;

/**
 *
 */
public interface PostExecutionThread {

    Scheduler getScheduler();
}