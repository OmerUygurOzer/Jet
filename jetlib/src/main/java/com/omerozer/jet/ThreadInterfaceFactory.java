package com.omerozer.jet;

/**
 * Created by omerozer on 1/30/18.
 */
public class ThreadInterfaceFactory {

    private static ThreadSwitcherInterface DUMMY = new ThreadSwitcherInterface() {
        @Override
        public void handleTask(Runnable runnable) {
            runnable.run();
        }
    };

    static ThreadSwitcherInterface create(JetConfigs configs,JetClassLoader jetClassLoader){

        if(configs.getEnv().equals(JetEnv.ANDROID)){
                return jetClassLoader.createAndroidThreadSwitcher();
        }

        return DUMMY;
    }

}
