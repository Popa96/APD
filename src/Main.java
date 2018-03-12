import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static  volatile LinkedList<Integer> deposit=new LinkedList<Integer>();


    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock=new ReentrantLock();
        Semaphore semFree =new Semaphore(2);
        Semaphore semFull=new Semaphore(0);
        Thread consumer=new Thread(new Consumer(lock,semFull,semFree));
        Thread producer=new Thread(new Producer(lock,semFull,semFree));

        consumer.start();
        producer.start();

        consumer.join();
        producer.join();
    }
}
