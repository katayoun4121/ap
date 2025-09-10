package ap.exercises.FinalExam;

import java.util.*;

public class ProductTools {

        public static List<Product> removeDuplicates(List<Product> products) {
            List<Product> result = new ArrayList<>();
            Set<String> duplicated = new HashSet<>();

          for (Product product : products) {
              String repPro = product.getName() + "|" + product.getPrice();
              if (!duplicated.contains(repPro)) {
                  result.add(product);
                  duplicated.add(repPro);
              }
          }
            return result;
        }
    public static List<Product> sortPrice(List<Product> products) {
        List<Product> sortedList = new ArrayList<>(products);

        Collections.sort(sortedList, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Double.compare(p1.getPrice(), p2.getPrice());
            }
        });

        return sortedList;
    }
    public static List<Product> prioritizeBooks(List<Product> products) {
        Map<String, Product> productMap = new HashMap<>();

        for (Product product : products) {
            String name = product.getName();

            if (!productMap.containsKey(name)) {
                productMap.put(name, product);
            } else {
                Product existing = productMap.get(name);

                if (product instanceof Book && !(existing instanceof Book)) {
                    productMap.put(name, product);
                }
            }
        }

        return new ArrayList<>(productMap.values());
    }
    public static void printProducts(List<Product> products) {
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }
    public static Map<String, Product> MostExpensive(List<Product> products) {
        Product MostExPen = null;
        Product MostExBook = null;
        for (Product product : products) {
            if (product instanceof Pen) {
                Pen pen = (Pen) product;
                if (MostExPen == null || pen.getPrice() > MostExPen.getPrice()) {
                    MostExPen = pen;
                }
            } else if (product instanceof Book) {
                Book book = (Book) product;
                if (MostExBook == null || book.getPrice() > MostExBook.getPrice()) {
                    MostExBook = book;
                }
            }
        }
        Map<String, Product> result = new HashMap<>();
        if (MostExPen != null) {
            result.put("for pen: ",MostExPen);
        }
        if (MostExBook != null) {
            result.put("for book: ", MostExBook);
        }

        return result;
    }
    public static Map<String, Integer> countPens(List<Product> products) {
        Map<String, Integer> colorCount = new HashMap<>();

        for (Product product : products) {
            if (product instanceof Pen) {
                Pen pen = (Pen) product;
                String color = pen.getColor();
                colorCount.put(color, colorCount.getOrDefault(color, 0) + 1);
            }
        }
        return colorCount;
    }

}

