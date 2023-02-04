package app;

import java.util.Date;

/**
 * Ejemplo simple de volatile que desde el hilo de MyThreadB cortamos la ejecucion del while ejecutado por MyThreadA
 */
public class VolatileJob {

    // boolean isRunning = true; // could or could not be available to other threads, it is uncertain.
    volatile boolean isRunning = true; // With volatile, JMM can gurantee the updated value will be available to other threads.

    void start() {
        new Thread(() -> {
            while(isRunning) {
                System.out.println("Ejecutandose MyThreadA " + new Date());
            }
            System.out.println("END");
        }).start();
        new Thread(() -> {
            sleep(); // solo para probar un segundo y luego cambiar el estado de isRunning dentro de MyThreadB a false asi corta el while MyThreadA
            isRunning = false;
        }).start();
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}


