import hometask4.BlockingQueue;

public class Main {
    public static void main(String[] args) {
        final BlockingQueue<Integer> queue = new BlockingQueue<>(1000);

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 100000; i++) {
                queue.enqueue(i);
                System.out.println("Поток добавил: " + i);

            }
            System.out.println("Готово! Все числа в очереди.");
        });
        Thread consumer = new Thread(() -> {
            int cur;
            while (true) {
                cur = queue.dequeue();
                System.out.println("Поток считал: " + cur);
                if (cur==100000)return;
            }
        });

        producer.start();
        consumer.start();
    }
}