package com.hillel.anatoliibondarenko.homework8;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Book", 50, false, LocalDate.parse("01-01-2023", formatter)));
        products.add(new Product(2, "Book", 100, true, LocalDate.parse("01-01-2022", formatter)));
        products.add(new Product(3, "Book", 300, true, LocalDate.parse("01-01-2022", formatter)));
        products.add(new Product(4, "Book", 400, true, LocalDate.parse("01-01-2022", formatter)));
        products.add(new Product(12, "Book", 500, false, LocalDate.parse("09-01-2023", formatter)));
        products.add(new Product(13, "Book", 75, true, LocalDate.parse("09-01-2023", formatter)));
        products.add(new Product(5, "Toy", 100, false, LocalDate.parse("01-01-2022", formatter)));
        products.add(new Product(6, "Toy", 200, false, LocalDate.parse("01-01-2022", formatter)));
        products.add(new Product(7, "Toy", 300, false, LocalDate.parse("01-01-2022", formatter)));
        products.add(new Product(8, "Toy", 400, false, LocalDate.parse("15-01-2023", formatter)));
        products.add(new Product(9, "Lamp", 10, false, LocalDate.parse("01-01-2022", formatter)));
        products.add(new Product(10, "Lamp", 10, false, LocalDate.parse("01-01-2022", formatter)));
        products.add(new Product(11, "Lamp", 20, false, LocalDate.parse("16-01-2023", formatter)));

        System.out.println("Implementation a method to get all products as a list whose category is equivalent to “Book”\n" +
                " and whose price is more than 250.");
        System.out.println(Products.listEqualTypesAndMorePrice(products, "Book", 250));
        System.out.println();

        System.out.println("Implementation a method to get all products as a list, the category of which is equivalent\n " +
                "to “Book” and with the possibility of applying a discount. The final list contains products with \n" +
                "a 10% discount already applied. The original list does not need to be changed.");
        System.out.println(Products.listEqualTypesWithDiscount(products, "Book", 10));
        System.out.println();

        System.out.println("Implementation a method to get the cheapest product from the “Book” category.");
        System.out.println("If no product is found (a situation where there is no product with the category you\n " +
                "are looking for), throw an exception with the message “Product [category: category_name] not found”.");
        try {
            System.out.println(Products.productMinPrice(products, "Book"));
            System.out.println(Products.productMinPrice(products, "Cat"));
        } catch (NoSuchProductTypeException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("Method to get the last three added products");
        System.out.println(Products.listLastAddedProducts(products, 3));
        System.out.println();


        System.out.println("Calculating the total cost of products that respond the given criteria:");
        System.out.println(Products.calcTotalCostPerCondition(products,
                product -> (product.getType().equals("Book") && product.getPrice() <= 75 && product.getCreateDate().getYear() == 2023)));
        System.out.println();

        System.out.println("Grouping objects by product type.");
        System.out.println(Products.listGroupByType(products));
    }

}
