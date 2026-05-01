package hometask4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * реализация честной очереди через ReentrantLock
 * @param <T>
 */
public class FairBlockingQueue<T> {
    private final ReentrantLock queueLock = new ReentrantLock(true);
    private final Condition emptyQueue = queueLock.newCondition();
    private final Condition fullQueue = queueLock.newCondition();
    private final int capacity;
    private final AtomicInteger size = new AtomicInteger(0);

    /// я знаю что есть ArrayBlockingQueue
    /// но тут спеиально реализую с нуля
    private final Queue<T> dataQueue;

    public FairBlockingQueue(int capacity) {
        this.capacity = capacity;
        dataQueue = new LinkedList<>();
    }

    public void enqueue(T data) {
        queueLock.lock();
        try {
            while (getSize() == capacity) {
                fullQueue.await();
            }
            dataQueue.add(data);
            size.incrementAndGet();
            emptyQueue.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            queueLock.unlock();
        }
    }

    public T dequeue() {
        queueLock.lock();
        try {
            while (getSize() == 0) {
                emptyQueue.await();
            }
            size.decrementAndGet();
            fullQueue.signalAll();
            return dataQueue.poll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            queueLock.unlock();
        }
    }

    /// слегка избыточная реализация
    /// но зато значение всегда актуальное
    public synchronized int getSize() {
        return size.get();
    }
}
