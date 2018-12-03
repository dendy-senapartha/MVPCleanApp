/**
 * DANA.id
 * PT. Espay Debit Indonesia Koe.
 * Copyright (c) 2017-2018 All Rights Reserved.
 */
package com.example.domain;

import io.reactivex.Scheduler;

/**
 *
 */
public interface PostExecutionThread {

    Scheduler getScheduler();
}