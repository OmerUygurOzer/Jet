package com.omerozer.jetcore;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by omerozer on 1/30/18.
 */

class JetClassLoader {

    private static final String JET_FACTORY = "JetFactory";
    private static final String JET_EVENT_INDEX = "com.omerozer.jetcore.JetEventIndex";
    private static final String ANDROID_THREAD_SWITCHER = "com.omerozer.jetcore.AndroidThreadSwitcher";

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
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        CACHE.put(clazz,constructor);
        return constructor;
    }

    EventHandler initializeEventHandlerForObject(Object object)  {
        Constructor<?> busConstructor = getConstructorForBus(object.getClass());

        try {
            return (EventHandler) busConstructor.newInstance(object);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Constructor<?> getConstructorForEventIndex(){
        try {
            return JetEventIndexInterface.class.getClassLoader().loadClass(JET_EVENT_INDEX).getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    JetEventIndexInterface createEventIndex(){
        try {
            return (JetEventIndexInterface)getConstructorForEventIndex().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Constructor<?> getConstructorForAndroidThreadSwitcher(){

        try {
            return ThreadSwitcherInterface.class.getClassLoader().loadClass(ANDROID_THREAD_SWITCHER).getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

     ThreadSwitcherInterface createAndroidThreadSwitcher(){

         try {
             return (ThreadSwitcherInterface) getConstructorForAndroidThreadSwitcher().newInstance();
         } catch (InstantiationException e) {
             e.printStackTrace();
         } catch (IllegalAccessException e) {
             e.printStackTrace();
         } catch (InvocationTargetException e) {
             e.printStackTrace();
         }

         return null;
    }
}
