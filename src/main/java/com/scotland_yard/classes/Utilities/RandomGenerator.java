package com.scotland_yard.classes.Utilities;

import java.util.Random;

public class RandomGenerator {
    public static int getInt(int max){
        Random rand = new Random();
        return rand.nextInt(0, max);
    }
}
