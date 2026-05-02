package hometask4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Основная часть домашнего задания
 * в реализации - не честная очередь,
 * но про порядок добавления или считывания ничего не было сказано,
 * потому очередь именно такая
 * @param <T>
 */
public class BlockingQueue<T> {
    private final int capacity;
    private final AtomicInteger size = new AtomicInteger(0);

    /// я знаю что есть ArrayBlockingQueue для таких задач, но для подробности реализации выбрал связный
    /// но тут спеиально реализую с нуля
    private final Queue<T> dataQueue;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
        dataQueue = new LinkedList<>();
    }

    public synchronized void enqueue(T data) {
        try {
            while (getSize() == capacity) {
                wait();
            }
            dataQueue.add(data);
            size.incrementAndGet();
            notifyAll();//в задании не указано ничего про честность, потому пробуждение потоков я сделал не честным
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized T dequeue() {
        try {
            while (getSize() == 0) {
                wait();
            }
            notifyAll();//в задании не указано ничего про честность, потому пробуждение потоков я сделал не честным
            size.decrementAndGet();
            return dataQueue.poll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /// слегка избыточная реализация
    /// но зато значение всегда актуальное
    public synchronized int getSize() {
        return size.get();
    }
}
