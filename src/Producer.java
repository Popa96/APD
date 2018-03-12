import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Producer implements Runnable {

    ReentrantLock lock;
    Semaphore semFree;
    Semaphore semFull;
    public Producer(ReentrantLock lock,Semaphore semFull,Semaphore semFree)
    {
        this.lock=lock;
        this.semFull=semFull;
        this.semFree=semFree;
    }
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Producer sleep");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            try {
                semFree.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            }
            synchronized (lock) {
                  if (Main.deposit.size() < 5) {
                      if (!Main.deposit.contains(Random())) {
                          lock.lock();
                          System.out.println("Producer product a produs" + Random());
                          Main.deposit.add(Random());
                          lock.unlock();
                      } else {
                          System.out.println("Producer sleep");
                      }
                  }
              }
            semFull.release();

        }
    }
    private int Random()
    {
        Random random=new Random();
        int producer=random.nextInt(5);
        return producer;
    }
}
