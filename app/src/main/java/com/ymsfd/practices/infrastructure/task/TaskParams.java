package com.ymsfd.practices.infrastructure.task;

import com.ymsfd.practices.infrastructure.support.exception.PracticesException;

import java.util.HashMap;

public class TaskParams {

    private HashMap<String, Object> params = null;

    public TaskParams(String key, Object value) {
        this();
        put(key, value);
    }

    public TaskParams() {
        params = new HashMap<String, Object>();
    }

    public void put(String key, Object value) {
        params.put(key, value);
    }

    public boolean getBoolean(String key) throws PracticesException {
        Object object = get(key);
        if (object.equals(Boolean.FALSE)
                || (object instanceof String && ((String) object)
                .equalsIgnoreCase("false"))) {
            return false;
        } else if (object.equals(Boolean.TRUE)
                || (object instanceof String && ((String) object)
                .equalsIgnoreCase("true"))) {
            return true;
        }

        throw new PracticesException(key + " is not a Boolean.");
    }

    public Object get(String key) {
        return params.get(key);
    }

    public double getDouble(String key) throws PracticesException {
        Object object = get(key);
        try {
            return object instanceof Number ? ((Number) object).doubleValue()
                    : Double.parseDouble((String) object);
        } catch (Exception e) {
            throw new PracticesException(key + " is not a number.");
        }
    }

    public int getInt(String key) throws PracticesException {
        Object object = get(key);
        try {
            return object instanceof Number ? ((Number) object).intValue()
                    : Integer.parseInt((String) object);
        } catch (Exception e) {
            throw new PracticesException(key + " is not an int.");
        }
    }

    public String getString(String key) throws PracticesException {
        Object object = get(key);
        return object == null ? null : object.toString();
    }

    public boolean has(String key) {
        return this.params.containsKey(key);
    }

}
