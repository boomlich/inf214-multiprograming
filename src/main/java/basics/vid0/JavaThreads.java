package basics.vid0;

public class JavaThreads {

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();

        myThread.start();
        Thread.yield();

        System.out.println("Hello from the main thread");

        myThread.join();
    }


}
