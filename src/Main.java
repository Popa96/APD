import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static  volatile LinkedList<Integer> deposit=new LinkedList<Integer>();

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock=new ReentrantLock();
       Thread consumer=new Thread(new Consumer(lock));
       Thread producer=new Thread(new Producer(lock));

       consumer.start();
       producer.start();

       consumer.join();
       producer.join();
    }
}
