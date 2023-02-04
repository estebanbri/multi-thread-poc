package app;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleJob {

    void startScheduleWithFixedDelay() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        long initialDelay = 2000;
        long delay = 1000;
        System.out.println("start() iniciado: " + new Date());
        executorService.scheduleWithFixedDelay(() -> {
            System.out.println("Ejecutando, date: " + new Date());
        }, initialDelay, delay, TimeUnit.MILLISECONDS);
    }

    void startScheduleWithFixedRate() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        long initialDelay = 2000;
        long delay = 1000;
        System.out.println("start() iniciado: " + new Date());
        executorService.scheduleAtFixedRate(() -> {
            System.out.println("Ejecutando, date: " + new Date());
        }, initialDelay, delay, TimeUnit.MILLISECONDS);
    }

}
