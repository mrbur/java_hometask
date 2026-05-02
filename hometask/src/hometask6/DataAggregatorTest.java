package hometask6;

public class DataAggregatorTest {
    static DataAggregator dataAggregator = new DataAggregator();


    public static void main(String[] args) throws InterruptedException {
        dataAggregator.aggregateProductInfoAsync("Ноутбук", System.out::println);
        Thread.sleep(3000);

        System.out.println(dataAggregator.aggregateProductInfo("Клавиатура"));
    }
}
