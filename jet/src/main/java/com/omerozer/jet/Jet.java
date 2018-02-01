package com.omerozer.jet;

/**
 * Created by omerozer on 1/30/18.
 */
public class Jet {

    public static Jet init(JetConfigs configs) {
        return new Jet(configs);
    }

    public static Jet init() {
        return new Jet(new JetConfigs());
    }

    private JetConfigs configs;

    private JetDock jetDock;

    private ThreadSwitcherInterface threadSwitcher;

    private JetClassLoader jetClassLoader;

    private Jet(JetConfigs configs) {
        this.configs = configs;
        this.jetClassLoader = new JetClassLoader();
        this.jetDock = new JetDock(jetClassLoader);
        this.threadSwitcher = ThreadInterfaceFactory.create(configs,jetClassLoader);
    }

    public void dock(final Object object) {
        this.jetDock.dock(object);
    }

    public void undock(Object object) {
        this.jetDock.undock(object);
    }

    public void fire(final String event,final Object object) {
        threadSwitcher.handleTask(new Runnable() {
            @Override
            public void run() {
                jetDock.fire(event,object);
            }
        });
    }

    public void error(final String event,final Object object){
        threadSwitcher.handleTask(new Runnable() {
            @Override
            public void run() {
                jetDock.error(event,object);
            }
        });
    }



}
