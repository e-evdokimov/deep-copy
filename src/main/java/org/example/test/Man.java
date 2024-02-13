package org.example.test;

import java.util.*;

public class Man {
    private String name;
    private int age;
    private List<String> favoriteBooks;

    private Man[] friends;


    public Man(String name, int age, List<String> favoriteBooks, List<Man> friends) {
        this.name = name;
        this.age = age;
        this.favoriteBooks = favoriteBooks;
        setFriends(friends);
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

    public List<Man> getFriends() {
        return Arrays.asList(friends);
    }

    public void setFriends(List<Man> friends) {
        if (friends == null) {
            this.friends = new Man[0];
        } else {
            this.friends = friends.toArray(new Man[0]);
        }
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
        return Objects.hash(name, age, favoriteBooks);
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
