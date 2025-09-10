package ap.exercises.FinalExam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        products.add(new Product("java", 200));
        products.add(new Product("java", 200));
        products.add(new Book("OS", 20, "smith", "CP"));
        products.add(new Book("OS", 20, "smith", "CP"));
        products.add(new Pen("colored pen", 20, "blue"));
        products.add(new Pen("colored pen", 20, "blue"));
        products.add(new Pen("colored pen", 60, "black"));
        products.add(new Pen("colored pen", 50, "red"));
        System.out.println("list of all products:");
        ProductTools.printProducts(products);
        System.out.println("---------------------------------");
        List<Product> noDuplicates = ProductTools.removeDuplicates(products);
        System.out.println("sorted by the price : ");
        List<Product> sorted = ProductTools.sortPrice(products);
        printProducts(sorted);
        System.out.println("----------------------------------");
        System.out.println("no duplicates: ");
        List<Product> duplicates = ProductTools.removeDuplicates(products);
        printProducts(duplicates);
        System.out.println("show the most expensive book and pen: ");
        Map<String, Product> MostExpensive = ProductTools.MostExpensive(products);

        if (MostExpensive.containsKey("MOST EXPENSIVE")) {
            Product pen = MostExpensive.get("most expensive");
            System.out.println("pen is: " + pen.getName() +
                    "price " + pen.getPrice());
            if (pen instanceof Pen) {
                System.out.println("color " + ((Pen) pen).getColor());
            }
        } else {
          //  System.out.println("not found");
        }
        if (MostExpensive.containsKey("MOST EXPENSIVE")) {
            Product book = MostExpensive.get("most expensive");
            System.out.println("book is: " + book.getName() +
                    "price " + book.getPrice());
            if (book instanceof Book) {
                System.out.println("writer " + ((Book) book).getWriter() +
                        " title " + ((Book) book).getTitle());
            }
        } else {
           // System.out.println("not found");
        }



        Map<String, Integer> penColors = ProductTools.countPens(products);
        System.out.println("count of pens: ");
        for (Map.Entry<String, Integer> entry : penColors.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

    }
    public static void printProducts(List<Product> products) {
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }

}
