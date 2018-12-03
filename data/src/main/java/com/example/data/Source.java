package com.example.data;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Choices of data source
 * local = persistence data (example : shared prefference, local db, etc)
 * network = from BE
 * mock = mock data in app
 */

@StringDef({Source.LOCAL, Source.NETWORK, Source.MOCK})
@Retention(RetentionPolicy.SOURCE)
public @interface Source {

    String LOCAL = "local";

    String NETWORK = "network";

    String MOCK = "mock";
}
