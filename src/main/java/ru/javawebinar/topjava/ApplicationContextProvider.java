package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.atomic.AtomicInteger;

public class ApplicationContextProvider {

    private static final LoggerWrapper LOG = LoggerWrapper.get(ApplicationContextProvider.class);

    private static ConfigurableApplicationContext appCtx;
    private static final Object object = new Object();
    private static AtomicInteger count = new AtomicInteger();

    public static ConfigurableApplicationContext getAppCtx() {
        synchronized (object) {
            if (appCtx == null) {
                LOG.info("Load application context");
                appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
            }
        }
        return appCtx;
    }

    public static void closeAppCtx() {
        if (count.get() == 1) {
            appCtx.close();
        } else count.incrementAndGet();
    }
}
