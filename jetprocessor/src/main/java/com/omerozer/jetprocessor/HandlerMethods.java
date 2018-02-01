package com.omerozer.jetprocessor;

import javax.lang.model.element.ExecutableElement;

/**
 * Created by omerozer on 1/28/18.
 */

class HandlerMethods {

    String event;
    ExecutableElement successMethod;
    ExecutableElement errorMethod;
    boolean noError;

    HandlerMethods() {

    }

    public HandlerMethods(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public ExecutableElement getSuccessMethod() {
        return successMethod;
    }

    public void setSuccessMethod(ExecutableElement successMethod) {
        this.successMethod = successMethod;
    }

    public ExecutableElement getErrorMethod() {
        return errorMethod;
    }

    public void setErrorMethod(ExecutableElement errorMethod) {
        this.errorMethod = errorMethod;
    }

    public boolean isNoError() {
        return noError;
    }

    public void setNoError(boolean noError) {
        this.noError = noError;
    }
}
