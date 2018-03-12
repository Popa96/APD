import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Consumer implements Runnable {
    final ReentrantLock lock;
    Semaphore semFull;
    Semaphore semFree;
    public Consumer(ReentrantLock lock,Semaphore semFull,Semaphore semFree)
    {
        this.lock=lock;
        this.semFull=semFull;
        this.semFree=semFree;
    }

    @Override
    public void run() {
        while (true) {
            int consum = Random();
            try {
                semFull.acquire();
            }catch (Exception e) {
                e.printStackTrace();
                continue;
            }
                synchronized (lock) {
                    if (Main.deposit.contains(consum) && !Main.deposit.isEmpty()) {
                        lock.lock();
                        System.out.println("Consumer consum a product" + consum);
                        Main.deposit.remove();
                        lock.unlock();
                    }
                }
                semFree.release();
                try {
                    System.out.println("Consumer sleep");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
