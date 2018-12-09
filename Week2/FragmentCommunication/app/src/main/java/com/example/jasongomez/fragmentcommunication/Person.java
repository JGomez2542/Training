package com.example.jasongomez.fragmentcommunication;

import java.io.Serializable;

public class Person implements Serializable {

    String name;
    String age;
    String favoriteAnimal;

    public Person(String name, String age, String favoriteAnimal) {
        this.name = name;
        this.age = age;
        this.favoriteAnimal = favoriteAnimal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFavoriteAnimal() {
        return favoriteAnimal;
    }

    public void setFavoriteAnimal(String favoriteAnimal) {
        this.favoriteAnimal = favoriteAnimal;
    }
}
