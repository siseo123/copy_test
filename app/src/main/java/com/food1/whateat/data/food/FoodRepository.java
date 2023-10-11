package com.food1.whateat.data.food;

import com.food1.whateat.data.category.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodRepository {

    private final Map<String, Food> foodMap = new HashMap<>();
    private final Map<Category, List<Food>> categoryFoodMap = new HashMap<>();

//    public void addFood(Food food) {
//        foodMap.put(food.getName(), food);
//        if (food.getCategory() != null) {
//            List<Food> foods = categoryFoodMap.get(food.getCategory());
//            if (foods == null) {
//                categoryFoodMap.put(food.getCategory(), new ArrayList<>());
//                return;
//            }
//            categoryFoodMap.get(food.getCategory()).add(food);
//        }
//    }
//
//    public int addFoodNotDuplicate(Food food) {
//        if (foodMap.containsKey(food.getId())) {
//            return -1;
//        }
//        addFood(food);
//        return 0;
//    }
//
//    public void findFood(String foodName) {
//        foodMap.get(foodName);
//    }
//
//    public void remove(String foodName) {
//        foodMap.remove(foodName);
//    }
//
//    @SuppressLint("NewApi")
//    public Collection<Food> getFoodsByCategory(Category category) {
//        return categoryFoodMap.computeIfAbsent(category, category1 -> new ArrayList<>());
//    }
//
//    public Collection<Food> getCollection() {
//        return foodMap.values();
//    }
//
//    public static void addDefault(FoodRepository foodRepository) {
//        foodRepository.addFood(Food.builder("비빔밥")
//                .category(Categories.KOREA)
//                .build()
//        );
//        foodRepository.addFood(Food.builder("냉면")
//                .category(Categories.KOREA)
//                .build()
//        );
//        foodRepository.addFood(Food.builder("분식")
//                .category(Categories.KOREA)
//                .build()
//        );
//        foodRepository.addFood(Food.builder("전")
//                .category(Categories.KOREA)
//                .build()
//        );
//        foodRepository.addFood(Food.builder("죽")
//                .category(Categories.KOREA)
//                .build()
//        );
//        foodRepository.addFood(Food.builder("국수")
//                .category(Categories.KOREA)
//                .build()
//        );
//        foodRepository.addFood(Food.builder("갈비탕")
//                .category(Categories.KOREA)
//                .build()
//        );
//    }
}
