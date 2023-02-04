package app;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  Utilidad: Para threads que necesitan comunicacion/sincronizacion,
 *  es decir threads que se encuentran relacionados comparten datos/ variables/ locks
 *  En este ejemplo hacemos que ambos threads vayan ejecutandose intercalados, una vez cada uno.
 *  Alternativas:
 *   synchronize + wait + notifyAll
 *   lock + await + signalAll
 */
public class WaitingJob {

    public void start() {
        Service service = new Service();

        new Thread(() -> {
            while(true){
                service.hacerAlgoPrimero();
            }
        }).start();
        new Thread(() ->  {
            while(true){
                service.hacerAlgoSegundo();
            }
        }).start();
    }

    static class Service {

        Lock lock = new ReentrantLock();
        Condition firstCondition = lock.newCondition();
        Condition secondCondition = lock.newCondition();
        boolean firstCanProcess = true;
        boolean secondCanProcess = false;

        public void hacerAlgoSegundo() {
                lock.lock();
                System.out.println("Lock adquirido por " + Thread.currentThread().getName() + ", state=" + Thread.currentThread().getState());
                try {
                    while(!secondCanProcess) { // Usar un while en vez de if protege de spurious wakeup: raramente se despiertan los threads sin hacerles notify/signal pero aveces pasa
                        System.out.println("Por dormirse y liberar lock " + Thread.currentThread().getName() + ", state=" + Thread.currentThread().getState());
                        firstCondition.await(); // avoid cpu cycles, suspend current thread and release lock
                    }
                    algo2();
                    firstCanProcess = true;
                    secondCanProcess = false;
                    secondCondition.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Lock  liberado por " + Thread.currentThread().getName() + ", state=" + Thread.currentThread().getState());
                    lock.unlock();
                    sleepOnlyForTesting(2000);
                }
        }

        public void hacerAlgoPrimero() {
                lock.lock();
                System.out.println("Lock adquirido por " + Thread.currentThread().getName() + ", state=" + Thread.currentThread().getState());
                try {
                    while (!firstCanProcess) { // Usar un while en vez de if protege de spurious wakeup: raramente se despiertan los threads sin hacerles notify/signal pero aveces pasa
                        System.out.println("Por dormirse y liberar lock " + Thread.currentThread().getName() + ", state=" + Thread.currentThread().getState());
                        secondCondition.await(); // avoid cpu cycles, suspend current thread and release lock
                    }
                    algo1();
                    firstCanProcess = false;
                    secondCanProcess = true;
                    firstCondition.signalAll();
                }catch(InterruptedException e){
                    e.printStackTrace();
                } finally {
                    System.out.println("Lock  liberado por " + Thread.currentThread().getName() + ", state=" + Thread.currentThread().getState());
                    lock.unlock();
                    sleepOnlyForTesting(2000);
                }
        }

        private void algo1() {
            System.out.println("{");
        }

        private void algo2() {
            System.out.println("}");
        }

        private void sleepOnlyForTesting(long millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }



}




