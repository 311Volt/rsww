package com.yetistudios.rsww.common.util;

import java.util.Random;

public class RandomUtil {

    private final static Random random = new Random();

    public static int randomInt(int bound) {
        return random.nextInt(bound);
    }

    public static int randomInt(int origin, int bound) {
        if(origin >= bound) {
            return origin;
        }
        return random.nextInt(1 + bound - origin) + origin;
    }

}
