package com.example;

import java.util.Random;

public class JokeProvider {
    public String getJoke() {
        String[] jokes = {
                "Programming is 10% science, 20% ingenuity, and 70% getting the ingenuity to work with the science.",
                "The generation of random numbers is too important to be left to chance.",
                "The three most dangerous things in the world are a programmer with a soldering iron, a hardware engineer with a software patch, and a user with an idea.",
                "A programmer walks to the butcher shop and buys a kilo of meat. An hour later he comes back upset that the butcher shortchanged him by 24 grams.",
                "One hundred little bugs in the code. One hundred little bugs. Fix a bug, link the fix in, one hundred little bugs in the code."
        };

        Random random = new Random(System.currentTimeMillis());
        return jokes[random.nextInt(jokes.length)];
    }
}
