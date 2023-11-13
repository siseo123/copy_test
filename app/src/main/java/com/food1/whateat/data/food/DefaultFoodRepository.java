package com.food1.whateat.data.food;

import com.food1.whateat.data.category.Categories;
import com.food1.whateat.data.category.Category;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultFoodRepository {

    private final Map<String, Food> foodMap = new HashMap<>();
    private final Map<String, List<Food>> categoryFoodMap = new HashMap<>();

    public DefaultFoodRepository() {
        init();
    }

    public boolean containFoodByName(String name) {
        return foodMap.containsKey(name);
    }

    public Collection<Food> getFoods() {
        return foodMap.values();
    }
    
    public void addFood(Food food) {
        foodMap.put(food.getName(), food);
        for (Category category : food.getCategories()) {
            List<Food> foods = categoryFoodMap.computeIfAbsent(category.getName(), v -> new ArrayList<>());
            foods.add(food);
        }
    }

    public int addFoodNotDuplicate(Food food) {
        if (foodMap.containsKey(food.getName())) {
            return -1;
        }
        addFood(food);
        return 0;
    }


    public List<Food> getFoodsByCategory(Category category) {
        return categoryFoodMap.computeIfAbsent(category.getName(), category1 -> new ArrayList<>());
    }

    public void init() {
        addFood(Food.builder("비빔밥").addCategory(Categories.KOREA).build());
        addFood(Food.builder("냉면").addCategory(Categories.KOREA).build());
        addFood(Food.builder("분식").addCategory(Categories.KOREA).build());
        addFood(Food.builder("전").addCategory(Categories.KOREA).build());
        addFood(Food.builder("죽").addCategory(Categories.KOREA).build());
        addFood(Food.builder("국수").addCategory(Categories.KOREA).build());
        addFood(Food.builder("갈비탕").addCategory(Categories.KOREA).build());
        addFood(Food.builder("삼겹살").addCategory(Categories.KOREA).build());
        addFood(Food.builder("삼계탕").addCategory(Categories.KOREA).build());
        addFood(Food.builder("보쌈").addCategory(Categories.KOREA).build());
        addFood(Food.builder("족발").addCategory(Categories.KOREA).build());
        addFood(Food.builder("찜닭").addCategory(Categories.KOREA).build());
        addFood(Food.builder("부대찌개").addCategory(Categories.KOREA).build());
        addFood(Food.builder("불고기").addCategory(Categories.KOREA).build());
        addFood(Food.builder("국밥").addCategory(Categories.KOREA).build());
        addFood(Food.builder("생선구이").addCategory(Categories.KOREA).build());
        addFood(Food.builder("감자탕").addCategory(Categories.KOREA).build());
        addFood(Food.builder("꽃게").addCategory(Categories.KOREA).build());
        addFood(Food.builder("제육볶음").addCategory(Categories.KOREA).build());
        addFood(Food.builder("육개장").addCategory(Categories.KOREA).build());

        addFood(Food.builder("중국집").addCategory(Categories.CHINA).build());
        addFood(Food.builder("마라").addCategory(Categories.CHINA).build());
        addFood(Food.builder("양꼬치").addCategory(Categories.CHINA).build());

        addFood(Food.builder("회").addCategory(Categories.JAPAN).build());
        addFood(Food.builder("초밥").addCategory(Categories.JAPAN).build());
        addFood(Food.builder("덮밥").addCategory(Categories.JAPAN).build());
        addFood(Food.builder("돈까스").addCategory(Categories.JAPAN).build());
        addFood(Food.builder("라멘").addCategory(Categories.JAPAN).build());
        addFood(Food.builder("소바").addCategory(Categories.JAPAN).build());
        addFood(Food.builder("우동").addCategory(Categories.JAPAN).build());
        addFood(Food.builder("카레").addCategory(Categories.JAPAN).build());
        addFood(Food.builder("샤브샤브").addCategory(Categories.JAPAN).build());
        
        addFood(Food.builder("파스타").addCategory(Categories.WESTERN).build());
        addFood(Food.builder("스테이크").addCategory(Categories.WESTERN).build());
        addFood(Food.builder("필라프").addCategory(Categories.WESTERN).build());
        addFood(Food.builder("리조또").addCategory(Categories.WESTERN).build());
        addFood(Food.builder("샐러드").addCategory(Categories.WESTERN).build());
        addFood(Food.builder("샌드위치").addCategory(Categories.WESTERN).build());
        addFood(Food.builder("토스트").addCategory(Categories.WESTERN).build());

        addFood(Food.builder("치킨").addCategory(Categories.FAST).build());
        addFood(Food.builder("피자").addCategory(Categories.FAST).build());
        addFood(Food.builder("버거").addCategory(Categories.FAST).build());

        addFood(Food.builder("쌀국수").addCategory(Categories.ASIA).build());
        addFood(Food.builder("팟타이").addCategory(Categories.ASIA).build());
        addFood(Food.builder("나시고랭").addCategory(Categories.ASIA).build());

        
        
    }
}
