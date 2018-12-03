/**
 * DANA.id
 * PT. Espay Debit Indonesia Koe.
 * Copyright (c) 2017-2018 All Rights Reserved.
 */
package com.example.data;

import java.util.List;
import java.util.Map;

/**
 * @author Randi Waranugraha (randi.waranugraha@dana.id)
 * @version $Id: SerializerFacade.java, v 0.1 2018‐07‐20 15.11 randi.waranugraha Exp $$
 */
public interface SerializerFacade {

    /**
     * Serialize an {@link Object} to JSON
     *
     * @param object to serialize
     * @param clazz  Class of object
     */
    String serialize(Object object, Class clazz);

    /**
     * Serialize an {@link Object} to JSON
     *
     * @param object to serialize
     */
    String serialize(Object object);

    /**
     * Deserialize a json representation of an {@link Object}
     *
     * @param string A JSON string to deserialize
     * @param clazz  Class of T
     */
    <T> T deserialize(String string, Class<T> clazz);

    /**
     * Deserialize a json representation of a {@link Map}
     *
     * @param string A JSON string to deserialize
     */
    Map<String, String> deserializeMap(String string);

    /**
     * Deserialize a json representation of a {@link List}
     *
     * @param string A JSON string to deserialize
     */
    List<String> deserializeList(String string);

}