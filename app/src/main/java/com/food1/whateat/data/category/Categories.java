package com.food1.whateat.data.category;

public class Categories {

    public static final Category KOREA = new Category("한식");
    public static final Category CHINA = new Category("중식");
    public static final Category JAPAN = new Category("일식");
    public static final Category WESTERN = new Category("양식");
    public static final Category FAST = new Category("패스트푸드");
    public static final Category ASIA = new Category("아시안");

    public static Category findByName(String name) {
        if (name.equals("한식")) {
            return KOREA;
        }
        else if (name.equals("중식")) {
            return CHINA;
        }
        else if (name.equals("일식")) {
            return JAPAN;
        }
        else if (name.equals("양식")) {
            return WESTERN;
        }
        else if (name.equals("패스트푸드")) {
            return FAST;
        }
        else if (name.equals("아시안")) {
            return ASIA;
        }
        return null;
    }
}
