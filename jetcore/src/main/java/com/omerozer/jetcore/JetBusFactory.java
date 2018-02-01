package com.omerozer.jetcore;

/**
 * Created by omerozer on 1/30/18.
 */
class JetBusFactory implements JetFactoryInterface {

    JetClassLoader jetClassLoader;

    JetBusFactory(JetClassLoader classLoader){
        this.jetClassLoader = classLoader;
    }

    @Override
    public EventHandler create(Object parent) {
        return jetClassLoader.initializeEventHandlerForObject(parent);
    }
}
