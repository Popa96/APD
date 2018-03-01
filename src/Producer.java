import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Producer implements Runnable {

    ReentrantLock lock;
    public Producer(ReentrantLock lock)
    {
        this.lock=lock;
    }
    @Override
    public void run()
    {

        while(true) {
            if(Main.deposit.size()<5) {
                if (!Main.deposit.contains(Random())) {
                    synchronized (lock) {
                        lock.lock();
                        System.out.println("Producer product a product" + Random());
                        Main.deposit.add(Random());
                        lock.unlock();
                    }
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            else{
                try {
                    System.out.println("Producer sleep");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private int Random()
    {
        Random random=new Random();
        int producer=random.nextInt(5);
        return producer;
    }
}
