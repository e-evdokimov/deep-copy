package org.example.test;

import java.util.*;

public class Man {
    private String name;
    private int age;
    private List<String> favoriteBooks;


    public Man(String name, int age, List<String> favoriteBooks) {
        this.name = name;
        this.age = age;
        this.favoriteBooks = favoriteBooks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavoriteBooks(List<String> favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Man man = (Man) o;
        return age == man.age
            && Objects.equals(name, man.name)
            && Objects.equals(favoriteBooks, man.favoriteBooks);
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(age);
        result = 31 * result + (name == null ? 0 : name.hashCode());
        result = 31 * result + (favoriteBooks == null ? 0 : favoriteBooks.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Man{" +
            "name='" + name + '\'' +
            ", age=" + age +
            ", favoriteBooks=" + favoriteBooks +
            '}';
    }

}
