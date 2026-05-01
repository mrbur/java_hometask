package hometask5;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentBank {
    final ConcurrentHashMap<String, BankAccount> accounts = new ConcurrentHashMap<>(2);
    public BankAccount createAccount(Integer amount) {
        if(amount == null) {
            throw new IllegalArgumentException("amount is null");
        }
        if(amount < 0) {
            throw new IllegalArgumentException("amount less then 0");
        }
        var ac = new BankAccount(UUID.randomUUID().toString(), BigDecimal.valueOf(amount));
        accounts.put(ac.getAccountId(), ac);
        return ac;
    }
    public void transfer(BankAccount source, BankAccount target, Integer amount) {
        if(source == null || target == null) {
            throw new IllegalArgumentException("source or target accont is null");
        }
        if(amount == null) {
            throw new IllegalArgumentException("amount is null");
        }
        if(amount < 0) {
            throw new IllegalArgumentException("amount less then 0");
        }
        target.transfer(source, BigDecimal.valueOf(amount));
    }

    /**
     * это слабосоглассованный метод для подсчёта итоговой суммы
     * консистентнее будет использовать CopyOnWriteArrayList, но эти копии сожрут всю оперативку,
     * я сознательно выбрал именно HashMap
     * @return
     */
    public synchronized BigDecimal getTotalBalance() {
        var sum = new BigDecimal(0);
        for (BankAccount ac : accounts.values()) {
            sum = sum.add(ac.getBalance());
        }
        return sum;
    }

    @Override
    public String toString() {
        return "ConcurrentBank{" +
                "accounts=" + accounts +
                '}';
    }
}
