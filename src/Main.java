import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static  volatile ArrayList<Integer> deposit=new ArrayList<Integer>();


    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock=new ReentrantLock();
        Semaphore semFree =new Semaphore(2);
        Semaphore semFull=new Semaphore(0);
        final Object condProd=new Object();
        final Object condCons=new Object();
        Thread consumer=new Thread(new Consumer(lock,semFull,semFree,condCons,condProd));
        Thread producer=new Thread(new Producer(lock,semFull,semFree,condCons,condProd));

        consumer.start();
        producer.start();

        consumer.join();
        producer.join();
    }
}
