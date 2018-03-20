import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Consumer implements Runnable {
    final ReentrantLock lock;
    Semaphore semFull;
    Semaphore semFree;
    final Object condCons;
    final Object condProd;
    public Consumer(ReentrantLock lock,Semaphore semFull,Semaphore semFree,Object condCons,Object condProd)
    {
        this.lock=lock;
        this.semFull=semFull;
        this.semFree=semFree;
        this.condCons=condCons;
        this.condProd=condProd;
    }

    @Override
    public void run() {
        while (true) {
            int consum = Random();
           /* try {
                semFull.acquire();
            }catch (Exception e) {
                e.printStackTrace();

            }*/



                synchronized (lock) {
                    if(Main.deposit.size()==0)
                    {
                        try {
                            synchronized (condCons) {
                                condCons.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                    if (Main.deposit.contains(consum) && !Main.deposit.isEmpty()) {
                        lock.lock();
                        System.out.println("Consumer consum a produs" + consum);
                        Main.deposit.remove(Random());
                        lock.unlock();
                    }
                }
                synchronized (condProd) {
                    condProd.notify();
                }
                //semFree.release();
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
