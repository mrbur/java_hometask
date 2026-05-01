package hometask5;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private final ReentrantLock lock = new ReentrantLock();
    private final String accountId;
    private final AtomicReference<BigDecimal> balance;

    public BankAccount(String accountId, BigDecimal amount) {
        if(accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException("accountId is blank");
        }
        if(amount == null) {
            throw new IllegalArgumentException("amount is null");
        }
        if(amount.signum() == -1) {
            throw new IllegalArgumentException("amount less then 0");
        }
        this.accountId = accountId;
        this.balance = new AtomicReference<>(amount);
    }

    public void deposit(BigDecimal depositAmount) {
        if(depositAmount == null) {
            throw new IllegalArgumentException("depositAmount is null");
        }
        if(depositAmount.signum() != 1) {
            throw new IllegalArgumentException("amount 0 or less");
        }
        balance.updateAndGet(balance -> balance.add(depositAmount));
    }

    public void withdraw(BigDecimal withdrawAmount) {
        if(withdrawAmount == null) {
            throw new IllegalArgumentException("withdrawAmount is null");
        }
        if(withdrawAmount.signum() != 1) {
            throw new IllegalArgumentException("withdrawAmount 0 or less");
        }
        balance.updateAndGet(balance -> {
            if(withdrawAmount.compareTo(balance) >= 0) {
                throw new IllegalArgumentException("withdrawAmount is too big to withdraw");
            }
            return balance.subtract(withdrawAmount);
        });
    }

    public void transfer(BankAccount source, BigDecimal transferAmount) {
        boolean sourceLock = source.lock.tryLock();
        boolean targetLock = this.lock.tryLock();

        try {
            /**
             * я осознанно выбрал это решение так как оно
             * - исключает дедлоки
             * - ситуации когда трансфер не пройдёт - очень редкая жизненная ситуация и 99.9999999% трансферов пройдут
             * - нет требования в задании
             */
            if (!(sourceLock && targetLock)) {
                throw new RuntimeException("Не можем захватить оба объекта, трансфер прерван");
            }
            try {
                source.withdraw(transferAmount);
                try {
                    this.deposit(transferAmount);
                }
                catch (Exception e) {
                    source.deposit(transferAmount);
                    throw new RuntimeException(e);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } finally {
            if (sourceLock) source.lock.unlock();
            if (targetLock) this.lock.unlock();
        }
    }

    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        return this.balance.get();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(getAccountId(), that.getAccountId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getAccountId());
    }


    @Override
    public String toString() {
        return "BankAccount{" +
                "balance=" + balance.get() +
                '}';
    }
}
