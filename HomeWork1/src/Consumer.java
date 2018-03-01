import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Consumer implements Runnable {
    final ReentrantLock lock;
    public Consumer(ReentrantLock lock)
    {
        this.lock=lock;
    }

    @Override
    public void run() {

        while (true) {

            if (Main.deposit.contains(Random())) {
                synchronized (lock) {
                    lock.lock();
                    System.out.println("Consumer consum a product" + Random());
                    Main.deposit.pop();
                    lock.unlock();
                }
            } else {
                try {
                    System.out.println("Consumer sleep");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private int Random()
    {
        int product;
        Random random=new Random();
        product=random.nextInt(5);
        return product;
    }
}
