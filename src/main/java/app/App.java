package app;

public class App {


    public static void main(String[] args) {

        // new WaitingJob().start();
        // new TimeWaitingJob().start();
        // new VolatileJob().start();
        //new ScheduleJob().startScheduleWithFixedDelay();
        new ScheduleJob().startScheduleWithFixedRate();
    }


}
