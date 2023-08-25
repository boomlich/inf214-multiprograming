package basics.vid1;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();

        CountingThread t1 = new CountingThread(counter);
        CountingThread t2 = new CountingThread(counter);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(counter.getCount());

    }
}
