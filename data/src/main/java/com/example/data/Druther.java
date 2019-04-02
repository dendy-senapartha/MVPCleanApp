
package com.example.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.securepreferences.SecurePreferences;

import java.util.List;

/**
 *
 */
public class Druther implements PreferenceFacade {

    private static final String DEFAULT_PREFERENCE = BuildConfig.APPLICATION_ID;

    private final Builder builder;

    private SharedPreferences sharedPreferences;

    private Druther(Builder builder) {
        if (builder.preferenceGroup == null || builder.preferenceGroup.length() == 0) {
            builder.preferenceGroup = DEFAULT_PREFERENCE;
        }
        if (builder.serializerFacade == null) {
            builder.serializerFacade = new Serializer();
        }

        this.builder = builder;

        if(TextUtils.isEmpty(builder.password)) {
            sharedPreferences = builder.context.getSharedPreferences(builder.preferenceGroup, 0);
        } else {
            sharedPreferences = new SecurePreferences(builder.context, builder.password, builder.preferenceGroup);
        }
    }

    @Override
    public void saveData(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    @Override
    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    @Override
    public void saveData(String key, Integer value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    @Override
    public Integer getInteger(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    @Override
    public void saveData(String key, Long value) {
        sharedPreferences.edit().putLong(key, value).apply();
    }

    @Override
    public Long getLong(String key) {
        return sharedPreferences.getLong(key, -1);
    }

    @Override
    public void saveData(String key, Float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    @Override
    public Float getFloat(String key) {
        return sharedPreferences.getFloat(key, -1);
    }

    @Override
    public void saveData(String key, Boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    @Override
    public Boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    @Override
    public <T> void saveData(String key, T obj) {
        saveData(key, builder.serializerFacade.serialize(obj));
    }

    @Override
    public <T> T getObject(String key, Class<T> classOfT) {
        String serializedObject = getString(key);
        return builder.serializerFacade.deserialize(serializedObject, classOfT);
    }

    @Override
    public <T> List<T> getObjects(String key) {
        String serializedObject = getString(key);
        return (List<T>) builder.serializerFacade.deserializeList(serializedObject);

    }

    @Override
    public void clearData(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    @Override
    public void clearAllData() {
        sharedPreferences.edit().clear().apply();
    }

    public static class Builder {

        private Context context;

        private String preferenceGroup;

        private SerializerFacade serializerFacade;

        private String password;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * setPreferenceGroup name
         *
         * @param preferenceGroup preferenceGroup name
         */
        public Builder setPreferenceGroup(String preferenceGroup) {
            this.preferenceGroup = preferenceGroup;
            return this;
        }

        /**
         * setSerializerFacade
         *
         * @param serializerFacade serializerFacade
         */
        public Builder setSerializerFacade(SerializerFacade serializerFacade) {
            this.serializerFacade = serializerFacade;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        /**
         * build {@link Druther} instance
         *
         * @return {@link Druther}
         */
        public Druther build() {
            return new Druther(this);
        }
    }
}