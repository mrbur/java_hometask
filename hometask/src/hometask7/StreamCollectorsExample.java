package hometask7;

import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class StreamCollectorsExample {
    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 500.0),
                new Order("Smartphone", 900.0)
        );

        //Создайте список заказов с разными продуктами и их стоимостями.

        //я так понял что нужно просто список, в котором у всех заказов точно разные продукты
        System.out.println(orders.stream().collect(Collectors.toMap(
                Order::getProduct,
                product->product,
                (order1, _)->order1
        )).values().stream().toList());

        //Группируйте заказы по продуктам.
        System.out.println(
                orders.stream().collect(Collectors.groupingBy(Order::getProduct, Collectors.toList()))
        );

        //Для каждого продукта найдите общую стоимость всех заказов.
        System.out.println(
                orders.stream().collect(Collectors.groupingBy(Order::getProduct, Collectors.summingDouble(Order::getCost)))
        );

        //Отсортируйте продукты по убыванию общей стоимости.
        System.out.println(
                String.valueOf(orders.stream().collect(
                        Collectors.groupingBy(Order::getProduct, TreeMap::new, Collectors.summingDouble(Order::getCost))
                ))
        );

        //Выберите три самых дорогих продукта.
        System.out.println(
                orders.stream()
                        .sorted(Comparator.comparing(Order::getCost).reversed())
                        .limit(3).toList()
        );

        //Выведите результат: список трех самых дорогих продуктов и их общая стоимость.
        System.out.println(
                String.valueOf(orders.stream()
                        .sorted(Comparator.comparing(Order::getCost).reversed())
                        .limit(3)
                        .collect(Collectors.teeing(
                                Collectors.toList(),                       // 1. Собираем все объекты в список
                                Collectors.summingDouble(Order::getCost),  // 2. Считаем общую сумму
                                (List orderList, Double total) ->  "список трех самых дорогих продуктов: " + orderList + " Сумма: " + total
                        ))
        ));
    }
}