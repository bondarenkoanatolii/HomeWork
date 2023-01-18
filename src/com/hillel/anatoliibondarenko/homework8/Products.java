package com.hillel.anatoliibondarenko.homework8;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Products {

    public static List<Product> listEqualTypesAndMorePrice(List<Product> products, String type, double price) {
        return products.stream()
                .filter(product -> product.getType().equals(type)
                        && product.getPrice() > price).collect(Collectors.toList());

    }

    public static List<Product> listEqualTypesWithDiscount(List<Product> products, String type, double discount) {
        /* The list should contain products with a possible discount. The original list does not need to be changed.
        We clone the list so that it was not linked to the source.*/
        List<Product> discountProducts = products.stream()
                .map(product -> new Product(product.getId(), product.getType(), product.getPrice(), product.isDiscount(), product.getCreateDate()))
                .collect(Collectors.toList());

        return discountProducts.stream()
                .filter(product -> product.getType().equals(type) && product.isDiscount())
                .peek(product -> product.setPrice(product.getPrice() * (100 - discount) / 100))
                .collect(Collectors.toList());
    }

    public static Product productMinPrice(List<Product> products, String type) {
        return products.stream()
                .filter(product -> product.getType().equals(type))
                .min(Comparator.comparingDouble(Product::getPrice))
                .orElseThrow(() -> (new NoSuchProductTypeException(type)));

    }

    public static List<Product> listLastAddedProducts(List<Product> products, int count) {
        return products.stream()
                .sorted(Comparator.comparing(Product::getCreateDate).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    public static double calcTotalCostPerCondition(List<Product> products, Predicate<? super Product> condition) {
               return products.stream()
                .filter(condition)
                .reduce( 0.0, (totalCost, product) -> totalCost + product.getPrice(), Double::sum);

    }

    public static Map<String, List<Product>> listGroupByType(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getType));
    }

}
