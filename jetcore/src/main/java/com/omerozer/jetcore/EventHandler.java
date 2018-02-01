package com.omerozer.jetcore;

/**
 * Created by omerozer on 1/27/18.
 */

public abstract class EventHandler {

    public abstract void handleSuccess(String event, Object message);

    public abstract void handleError(String event, Object message);

    public abstract String[] getEvents();

    public abstract Object accessParent();

    @Override
    public boolean equals(Object obj) {
        return accessParent().equals(obj);
    }

    @Override
    public int hashCode() {
        return accessParent().hashCode();
    }
}
