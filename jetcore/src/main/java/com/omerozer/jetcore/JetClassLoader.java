package com.omerozer.jetcore;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by omerozer on 1/30/18.
 */
class JetClassLoader {
    private Map<Class<?>,Constructor<?>> CACHE = new LinkedHashMap<>();

    private Constructor<?> getConstructorForBus(Class<?> clazz)  {
        if(CACHE.containsKey(clazz)){
            return CACHE.get(clazz);
        }

        ClassLoader classLoader = clazz.getClassLoader();

        Constructor<?> constructor = null;
        try {
            constructor = classLoader.loadClass(clazz.getCanonicalName()+"_Jet").
                    getConstructor(Object.class);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        CACHE.put(clazz,constructor);
        return constructor;
    }

    public EventHandler initializeEventHandlerForObject(Object object)  {
        Constructor<?> busConstructor = getConstructorForBus(object.getClass());
        try {
            return (EventHandler) busConstructor.newInstance(object);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    Constructor<?> getConstructorForEventIndex(){
        try {
            return JetEventIndexInterface.class.getClassLoader().loadClass("com.omerozer.jetcore.JetEventIndex").getConstructor();
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JetEventIndexInterface createEventIndex(){
        try {
            return (JetEventIndexInterface)getConstructorForEventIndex().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    Constructor<?> getConstructorForAndroidThreadSwitcher(){
        try {
            return ThreadSwitcherInterface.class.getClassLoader().loadClass("com.omerozer.jetcore.AndroidThreadSwitcher").getConstructor();
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ThreadSwitcherInterface createAndroidThreadSwitcher(){
        try {
            return (ThreadSwitcherInterface) getConstructorForAndroidThreadSwitcher().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
