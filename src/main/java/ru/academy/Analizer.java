package ru.academy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Analizer {

    public List<Store> getListStoreOnBasseynaya(List<Store> stores) {
        return
                stores.stream()
                        .filter(store -> store.isOpen && (store.getAddress().contains("Бассейная")))
                        .toList();
    }

//       3     Найти товары, которых осталось меньше 50 единиц на складах.
//            (Вывести Product.name и Warehouse.quantity где quantity < 50)

    public Map<String, Integer> getStoreWithSmallQuantityProdukts(List<Product> products, List<Warehouse> warehouses) {
        return
                warehouses.stream()
                        .filter(warehouse -> warehouse.getQuantity() <50 )
                        .collect(Collectors.toMap(
                                warehouses -> warehouses.
                        ))
//                        .collect(Collectors.toMap(

//                                store -> store,
//                                warehouse -> warehouse.getQuanrity
//                        ))
    }

//    private Map<Student, Double> averageGrade() {
//        return
//                students.stream()
//                        .collect(Collectors.toMap(
//                                student -> student,
//                                student -> student.getGrades().values().stream()
//                                        .mapToDouble(Double::doubleValue)  // объект (в Маре отсутствуют примитивы) меняю на примитивный
//                                        .average()
//                                        .orElse(-1))
//                        );

    private String getNameProduct(List<Product> products, int productId) {
        return
                products.stream()
                        .filter(product -> product.getId() == productId)
                        .toList();
    }



//       5        Найти магазины, где нет ни одного товара категории "Фрукты".
//               (Проверить, что в Warehouse нет записей с product.category = "Фрукты" для данного storeId).

    public List<Store> getLisfStoreNotFruct(List<Store> stores, List<Warehouse> warehouses, List<Product> products) {
        return
                stores.stream()
                        .filter(store -> store.)
    }
}
