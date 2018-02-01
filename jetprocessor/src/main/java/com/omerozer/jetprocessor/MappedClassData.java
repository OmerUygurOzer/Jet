package com.omerozer.jetprocessor;

import javax.lang.model.element.TypeElement;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by omerozer on 1/27/18.
 */

class MappedClassData {
    TypeElement enclosingClass;
    Map<String, HandlerMethods> handlerMethodsMap;

    MappedClassData(TypeElement enclosingClass) {
        handlerMethodsMap = new HashMap<String, HandlerMethods>();
        this.enclosingClass = enclosingClass;
    }

    public HandlerMethods getHandlerMethods(String event) {
        if (!handlerMethodsMap.containsKey(event)) {
            handlerMethodsMap.put(event, new HandlerMethods());
        }
        return handlerMethodsMap.get(event);
    }

    public void setHandlerMethodsMap(
            Map<String, HandlerMethods> handlerMethodsMap) {
        this.handlerMethodsMap = handlerMethodsMap;
    }

    String getClassName() {
        return enclosingClass.getQualifiedName().toString();
    }
}
