/**
 * DANA.id
 * PT. Espay Debit Indonesia Koe.
 * Copyright (c) 2017-2018 All Rights Reserved.
 */
package com.example.data;

import java.util.List;

/**
 *
 */
public interface PreferenceFacade {

    /**
     * set {@link String} value based on key
     *
     * @param key   parameter identifier
     * @param value {@link String}
     */
    void saveData(String key, String value);

    /**
     * get {@link String} value based on key
     *
     * @param key parameter identifier
     * @return {@link String}
     */
    String getString(String key);

    /**
     * set {@link Integer} value based on key
     *
     * @param key   parameter identifier
     * @param value {@link Integer}
     */
    void saveData(String key, Integer value);

    /**
     * get {@link Integer} value based on key
     *
     * @param key parameter identifier
     * @return {@link Integer}
     */
    Integer getInteger(String key);

    /**
     * set {@link Long} value based on key
     *
     * @param key   parameter identifier
     * @param value {@link Long}
     */
    void saveData(String key, Long value);

    /**
     * get {@link Long} value based on key
     *
     * @param key parameter identifier
     * @return {@link Long}
     */
    Long getLong(String key);

    /**
     * set {@link Float} value based on key
     *
     * @param key   parameter identifier
     * @param value {@link Float}
     */
    void saveData(String key, Float value);

    /**
     * get {@link Float} value based on key
     *
     * @param key parameter identifier
     * @return {@link Float}
     */
    Float getFloat(String key);

    /**
     * set {@link Boolean} value based on key
     *
     * @param key   parameter identifier
     * @param value {@link Boolean}
     */
    void saveData(String key, Boolean value);

    /**
     * get {@link Boolean} value based on key
     *
     * @param key parameter identifier
     * @return {@link Boolean}
     */
    Boolean getBoolean(String key);

    /**
     * set Object value based on key
     *
     * @param key parameter identifier
     */
    <T> void saveData(String key, T obj);

    /**
     * get Object value based on key
     *
     * @param key      parameter identifier
     * @param classOfT class of Object
     */
    <T> T getObject(String key, Class<T> classOfT);

    /**
     * get Object value based on key
     *
     * @param key      parameter identifier
     * @param classOfT class of Object
     */
    <T> List<T> getObjects(String key);

    /**
     * clear data based on key
     *
     * @param key parameter identifier
     */
    void clearData(String key);

    /**
     * clear all data
     */
    void clearAllData();
}