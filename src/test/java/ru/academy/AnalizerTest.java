package ru.academy;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AnalizerTest {

    private Analizer analyzer;

    @Test
    void getListStoreOnBasseynaya_whenOpenStoreOnStreetBasseynaya_thenId() {

        List<Store> stores = Arrays.asList(
                new Store(1, "Пятерочка", "ул. Бассейная, 10", true),
                new Store(2, "Магнит", "ул. Ленина, 5", true)
        );

        analyzer = new Analizer();
        List<Store> storesOnBasseynaya = analyzer.getListStoreOnBasseynaya(stores);

        assertEquals(1, storesOnBasseynaya.size());  // попробовать get(0)

    }

    @Test
    void getListStoreOnBasseynaya_whenOpenStoreOnStreetBasseynoy_thenReturnEmptyList() {

        List<Store> stores = Arrays.asList(
                new Store(1, "Пятерочка", "ул. Гагарина, 10", true),
                new Store(2, "Магнит", "ул. Ленина, 5", false)
        );

        analyzer = new Analizer();

        List<Store> storesOnBasseynaya = analyzer.getListStoreOnBasseynaya(stores);

        assertTrue(storesOnBasseynaya.isEmpty());
    }

    @Test
    void getListStoreOnBasseynaya_whenOpenStoreOnStreetBasseynoy_thenReturnAllStore() {

        Store oneStore = new Store(1, "Пятерочка", "ул. Бассейная, 10", true);
        Store secondStore = new Store(2, "Магнит", "ул. Бассейная, 5", true);
        List<Store> stores = Arrays.asList(
                oneStore,
                secondStore,
                new Store(4, "Дикси", "ул. Гагарина, 20", true)
        );

        analyzer = new Analizer();

        List<Store> storesOnBasseynaya = analyzer.getListStoreOnBasseynaya(stores);

        assertTrue(storesOnBasseynaya.containsAll(List.of(oneStore, secondStore)));

        assertEquals(2, storesOnBasseynaya.size());
    }

//   3     Найти товары, которых осталось меньше 50 единиц на складах.
//        (Вывести Product.name и Warehouse.quantity где quantity < 50)

    @Test
    void getStoreWithSmallQuantityProdukts_littleInTheWarehouse_getThereAreFewProducts() {

        List<Product> products = Arrays.asList(
                new Product(1, "Помидоры", "Овощи", 120.0),
                new Product(2, "Огурцы", "Овощи", 40.0)
        );

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(1, 1, 60),
                new Warehouse(1, 2, 35)
        );

        analyzer = new Analizer();
        Map<String, Integer> productsFromStore = analyzer.getStoreWithSmallQuantityProdukts();   // продукты из (название) магазина - магазин с продуктами

        productsFromStore = Map.of("Огурцы", 35);

        assertTrue(warehouses.get(1).getQuantity() < 50);

        assertEquals(products.get(0).getId(), warehouses.get(1).getProductId());

        assertEquals("Огурцы", products.get(1).getName());

    }

    @Test
    void getStoreWithSmallQuantityProdukts_lackOfStores_getThru() {

        List<Product> products = Arrays.asList(
                new Product(1, "Помидоры", "Овощи", 120.0),
                new Product(2, "Огурцы", "Овощи", 40.0)
        );

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(1, 3, 60),
                new Warehouse(1, 4, 35)
        );

        analyzer = new Analizer();
        List<Product> productsFromStore = analyzer.getStoreWithSmallQuantityProdukts();

        assertTrue(warehouses.get(1).getQuantity() < 50);

        assertNotSame(warehouses.get(1).getProductId(), products.get(0).getId());

    }

    @Test
    void getStoreWithSmallQuantityProdukts_enoughProducts_getFals() {              // достаточно продуктов

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(1, 3, 60),
                new Warehouse(1, 4, 55)
        );

        analyzer = new Analizer();
        List<Product> productsFromStore = analyzer.getStoreWithSmallQuantityProdukts();

        assertFalse(warehouses.get(0).getQuantity() < 50);

        assertFalse(warehouses.get(1).getQuantity() < 50);

    }

//   4.         Вывести топ-3 самых продаваемых товаров (по количеству проданных единиц).
//            (Агрегация по Sale.quantity с группировкой по productId).

    @Test
    void getListMostPurchasedProducts_threeProductoutOfFour_getThrue() {           // список самых покупаемых продуктов - три продукта из четырёх

        List<Sale> sales = Arrays.asList(
                new Sale(1, 1, 1, 5, "2024-05-01"),  // В Пятерочке продано 5 помидоров
                new Sale(2, 1, 2, 3, "2024-05-01"),  // 3 огурца
                new Sale(3, 2, 1, 2, "2024-05-02"),  // В Магните 2 помидора
                new Sale(4, 3, 3, 10, "2024-05-03")  // В Перекрестке 10 яблок
        );

        List<Product> products = Arrays.asList(
                new Product(1, "Помидоры", "Овощи", 120.0),
                new Product(2, "Огурцы", "Овощи", 90.0),
                new Product(3, "Яблоки", "Фрукты", 80.0),
                new Product(4, "Молоко", "Молочные продукты", 70.0)
        );

        analyzer = new Analizer();

        List<String> topProduct = analyzer.getListMostPurchasedProducts();

        topProduct = Arrays.asList("Яблоки", "Помидоры", "Огурцы");

        assertEquals(sales.get(0).getProductId(), products.get(0).getId());
        assertEquals(sales.get(1).getProductId(), products.get(1).getId());
        assertEquals(sales.get(3).getProductId(), products.get(2).getId());

        assertTrue(sales.get(3).getQuantity() >= sales.get(0).getQuantity());
        assertTrue(sales.get(0).getQuantity() >= sales.get(1).getQuantity());

    }

    @Test
    void getListMostPurchasedProducts_salesLeadersAreAbsent_() { // лидеры продвж отсутмтвуют

        List<Sale> sales = Arrays.asList(
                new Sale(1, 1, 1, 5, "2024-05-01"),
                new Sale(2, 1, 2, 5, "2024-05-01"),
                new Sale(3, 2, 1, 5, "2024-05-02"),
                new Sale(4, 2, 1, 3, "2024-05-02"),
                new Sale(5, 3, 3, 5, "2024-05-03")
        );

        analyzer = new Analizer();

        List<String> topProduct = analyzer.getListMostPurchasedProducts();

        topProduct = List.of(null);

        assertEquals(sales.get(0).getQuantity(), sales.get(1).getQuantity());
        assertEquals(sales.get(1).getQuantity(), sales.get(2).getQuantity());
        assertEquals(sales.get(2).getQuantity(), sales.get(4).getQuantity());
        assertEquals(sales.get(4).getQuantity(), sales.get(0).getQuantity());

    }
//       5        Найти магазины, где нет ни одного товара категории "Фрукты".
//            (Проверить, что в Warehouse нет записей с product.category = "Фрукты" для данного storeId).

    @Test
    void getLisfStoreNotFruct_filterShopsWithProducts_getThrue() {  // магазины без фруктов - фильтруем магазины с фруктами

        List<Store> stores = Arrays.asList(
                new Store(1, "Пятерочка", "ул. Бассейная, 10", true),
                new Store(3, "Перекресток", "ул. Бассейная, 15", false),
                new Store(4, "Дикси", "ул. Гагарина, 20", true)
        );

        List<Product> products = Arrays.asList(
                new Product(1, "Помидоры", "Овощи", 120.0),
                new Product(2, "Яблоки", "Фрукты", 80.0),
                new Product(4, "Молоко", "Молочные продукты", 70.0)
        );

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(1, 1, 50),  // В магазине 1 -  50 помидоров
                new Warehouse(3, 2, 100),  // В Перекрестке 3 - 100 яблок
                new Warehouse(4, 4, 40)    // В Дикси 4 - 40 молока
        );

        List<String> shopWithoutFruit = analyzer.getLisfStoreNotFruct();
        shopWithoutFruit = List.of("Пятерочка", "Дикси");

        assertEquals(products.get(0).getId(), warehouses.get(0).getProductId());
        assertEquals(products.get(1).getId(), warehouses.get(1).getProductId());
        assertEquals(products.get(2).getId(), warehouses.get(2).getProductId());

        assertEquals(warehouses.get(0).getProductId(), stores.get(0).getName());
        assertEquals(warehouses.get(1).getProductId(), stores.get(1).getName());
        assertEquals(warehouses.get(2).getProductId(), stores.get(2).getName());

    }

    @Test
    void getLisfStoreNotFruct_filterShopsWithNotProducts_getThrue() {  // магазины без фруктов - фильтруем магазины без фруктов

        List<Store> stores = Arrays.asList(
                new Store(1, "Пятерочка", "ул. Бассейная, 10", true),
                new Store(3, "Перекресток", "ул. Бассейная, 15", false),
                new Store(4, "Дикси", "ул. Гагарина, 20", true)
        );

        List<Product> products = Arrays.asList(
                new Product(1, "Помидоры", "Овощи", 120.0),
                new Product(2, "Яблоки", "Фрукты", 80.0),
                new Product(4, "Молоко", "Молочные продукты", 70.0)
        );

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(1, 1, 50),  // В магазине 1 -  50 помидоров
                new Warehouse(3, 2, 100),  // В Перекрестке 3 - 100 молока
                new Warehouse(4, 4, 40)    // В Дикси 4 - 40 яблок
        );

        List<String> shopWithoutFruit = analyzer.getLisfStoreNotFruct();
        shopWithoutFruit = List.of("Пятерочка", "Дикси");

        assertEquals(products.get(1).getId(), warehouses.get(1).getProductId());
        assertEquals(warehouses.get(1).getId(), stores.get(1).getId());

        assertNotSame(stores.get(1).getName(), stores.get(0).getName());
        assertNotSame(stores.get(1).getName(), stores.get(2).getName());

    }

    @Test
    void getLisfStoreNotFruct_allStoreWithFruit_getThrue() {                     // все магазины с фруктами - во всех магазинах фрукты

        List<Store> stores = Arrays.asList(
                new Store(1, "Пятерочка", "ул. Бассейная, 10", true),
                new Store(3, "Перекресток", "ул. Бассейная, 15", false),
                new Store(4, "Дикси", "ул. Гагарина, 20", true)
        );

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(1, 2, 50),
                new Warehouse(3, 2, 100),
                new Warehouse(4, 2, 40)
        );

        List<String> shopWithoutFruit = analyzer.getLisfStoreNotFruct();
        shopWithoutFruit = List.of(null);

        assertTrue(warehouses.get(0).getProductId() == 2);
        assertTrue(warehouses.get(1).getProductId() == 2);
        assertTrue(warehouses.get(2).getProductId() == 2);

    }

//   6.          Посчитать выручку каждого магазина за май 2024.
//            Сумма Sale.quantity * Product.price с фильтром по дате

    @Test
    void getAmountForMay_(){

    }

//    7.       Определить, в каком магазине самый большой ассортимент товаров.
//            (Магазин с наибольшим количеством уникальных productId в Warehouse).

    @Test
    void gatMaxProductRange_() {       // максимальный ассортимент продуктов - три продукта в одном магазине

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(1, 1, 50),  // В магазине 1 (Пятерочка) 50 помидоров
                new Warehouse(1, 2, 30),   // Огурцов 30
                new Warehouse(1, 3, 30),   // Яблок 30
                new Warehouse(2, 1, 20),   // В Магните 20 помидоров
                new Warehouse(3, 3, 100)  // В Перекрестке 100 яблок
        );

        List<Store> stores = Arrays.asList(
                new Store(1, "Пятерочка", "ул. Бассейная, 10", true),
                new Store(3, "Перекресток", "ул. Бассейная, 15", false),
                new Store(2, "Дикси", "ул. Гагарина, 20", true)
        );

        List<String> naxTheМariety  = analyzer.getMaxProductRange();
        naxTheМariety = List.of("Пятерочка");



    }

//    8        Найти товары, которые есть в наличии, но ни разу не продавались.
//             (Товары, которые есть в Warehouse, но нет в Sale).
    @Test
    void getListProductsNotSold_() {            // список не проданных продуктов

        List<Product> products = Arrays.asList(

//                new Product(1, "Помидоры", "Овощи", 120.0),
                new Product(1, "Огурцы", "Овощи", 90.0),
                new Product(2, "Яблоки", "Фрукты", 80.0),
                new Product(3, "Молоко", "Молочные продукты", 70.0)
        );

        List<Warehouse> warehouses = Arrays.asList(
//                new Warehouse(1, 1, 50),  // В магазине 1 (Пятерочка) 50 помидоров
                new Warehouse(1, 1, 30),   // Огурцов 30
                new Warehouse(3, 2, 100),  // В Перекрестке 100 яблок
                new Warehouse(4, 3, 40)    // В Дикси 40 молока
        );

        List<Sale> sales = Arrays.asList(
                new Sale(1, 1, 1, 5, "2024-05-01"),  // В Пятерочке продано 5 огурцов
                new Sale(2, 1, 3, 3, "2024-05-01"),  // 3 молока
                new Sale(4, 3, 3, 10, "2024-05-03")  // В Перекрестке 10 молока
        );

        List<String> ListProducts = analyzer.getListProductsNotSold();
        ListProducts = List.of("Яблоки");

//       warehouses.removeAll(sales)
        
    }

//              Вывести среднюю цену товаров в каждой категории.
//            (Группировка по Product.category и вычисление среднего price).

                    //      КОРОЧЕ, ТУТ ПОКА ПРОПУСТИТЬ

    @Test
    void getAveragePriceOfGoods_() {                   // средняя цена товара

        List<Product> products = Arrays.asList(
                new Product(1, "Помидоры", "Овощи", 120.0),
                new Product(2, "Огурцы", "Овощи", 100.0),
                new Product(3, "Яблоки", "Фрукты", 80.0),
                new Product(4, "Молоко", "Молочные продукты", 70.0)
        );

        Map<String, Double> average = analyzer.getAveragePriceOfGoods();
        average = Map.of("Овощи", 110.0, "Фрукты", 80.0, "Молочные продукь", 70.0);

        assertTrue(products.get(0).getCategory().equals(products.get(1).getCategory()));
        assertTrue(110.0 == (average(products.get(0).getPrice() + products.get(1).getPrice()));

        assertFalse(products.get(0).getCategory().equals(products.get(2).getCategory()));
        assertFalse(products.get(0).getCategory().equals(products.get(3).getCategory()));
        assertFal    se(products.get(2).getCategory().equals(products.get(3).getCategory()));

        assertTrue(average(products.get(3).getPrice()) == 80);

    }

//                      Найти магазины, где продажи отсутствовали более 2 дней.
//            (Магазины, у которых нет записей в Sale за последние 2 дня от текущей даты).

                                //      НУ ТУТ ВООБЩЕ КАПЕЦ  O.O


}
