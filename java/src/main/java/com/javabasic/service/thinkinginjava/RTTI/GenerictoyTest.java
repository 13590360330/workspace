package com.javabasic.service.thinkinginjava.RTTI;


/**
 * TODO Class<? extends Number>  P322
 */
public class GenerictoyTest {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Class<FancyToy> fancyToyClass = FancyToy.class;
        FancyToy fancyToy = fancyToyClass.newInstance();
        Class<? super FancyToy> superclass = fancyToyClass.getSuperclass();  //死记getSuperclass()时用的super
        Object object = superclass.newInstance();
    }
}

interface HasBatteries {
}

interface Waterproof {
}

interface Shoots {
}

class Toy {
    Toy() {
    }

    Toy(int i) {
    }
}

class FancyToy extends Toy implements HasBatteries, Waterproof, Shoots {
    FancyToy() {
        super( 1 );
    }
}