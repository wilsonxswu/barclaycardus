package integration.com.bcus.inventory.mgmt;

public class Threading {
    int counter;

    class Mythread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i <= 10000; i++) counter++;
            System.out.println("run = " + counter);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Threading a = new Threading();
        Mythread t1 = a.new Mythread();
        t1.start();
        t1.join();
        System.out.println("" + a.counter);
    }

}
