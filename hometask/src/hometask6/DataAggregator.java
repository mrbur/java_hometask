package hometask6;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DataAggregator {

    //для получения данных в том же потоке
    private CompletableFuture<ProductInfo> getProductInfo(String productName) {
        if(productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("productName is null or blank");
        }
        final ProductInfo resultProductInfo = new ProductInfo(productName);

        CompletableFuture<Double> priceFuture = CompletableFuture.supplyAsync(this::fetchPrice).exceptionally(ex->0.0);
        CompletableFuture<String> deskFuture = CompletableFuture.supplyAsync(this::fetchDescription).exceptionally(ex->"Нет данных");
        CompletableFuture<Double> ratingFuture = CompletableFuture.supplyAsync(this::fetchRating).exceptionally(ex->0.0);

        return priceFuture
                .thenCombine(deskFuture, (price, desk) -> {
                    resultProductInfo.setPrice(price);
                    resultProductInfo.setDescription(desk);
                    return resultProductInfo;
                })
                .thenCombine(ratingFuture, (productInfo, rating) -> {
                    productInfo.setRating(rating);
                    return productInfo;
                });
    }

    ///синхронный метод, тут мы ждём в основном потоке пока отработают все вызовы
    /// это будет медленно, синхронно
    public ProductInfo aggregateProductInfo(String productName) {
        return getProductInfo(productName).join();
    }

    ///асинхронный метод, тут мы не ждём и отрабатываем лямбду по завершению
    public void aggregateProductInfoAsync(String productName, Consumer<ProductInfo> onProductInfoRecieve) {
        getProductInfo(productName).thenAccept(productInfo -> onProductInfoRecieve.accept(productInfo));
    }

    private double fetchPrice() {
        try {
            Thread.sleep((long) (3000*Math.random()));
            if(Math.random() < 0.2) {
                throw new RuntimeException("Внезапная ошибка");
            }
            return 123;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private String fetchDescription() {
        try {
            Thread.sleep((long) (3000*Math.random()));
            if(Math.random() < 0.2) {
                throw new RuntimeException("Внезапная ошибка");
            }
            return "Описание";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private double fetchRating() {
        try {
            Thread.sleep((long) (3000*Math.random()));
            if(Math.random() < 0.2) {
                throw new RuntimeException("Внезапная ошибка");
            }
            return 2;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
