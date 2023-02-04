package app;

import java.util.Date;

/**
 *  Utilidad: Solo para threads que no necesitan comunicacion/sincronizacion,
 *  es decir threads que no se encuentran relacionado por nada ni comparten ni datos/ni variables/ni locks
 *  Importante: usar un Thread.sleep() para no consumir demasiado cpu
 */
public class TimeWaitingJob {

    public void start() {

        Task task = new Task();

        new Thread(task)
                .start();
        new Thread(task)
                .start();
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    doingSomething();
                    Thread.sleep(4000); // waiting on condition (java.lang.Thread.State: TIMED_WAITING (sleeping))
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        private void doingSomething() {
            System.out.println("Trabajando " + Thread.currentThread().getName() + "state=" + Thread.currentThread().getState() + " " + new Date());
        }

    }
}





