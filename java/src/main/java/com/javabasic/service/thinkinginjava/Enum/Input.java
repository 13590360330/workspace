package com.javabasic.service.thinkinginjava.Enum;

import java.util.Random;

/**
 * TODO [使用enum的状态机1,例子-自动售货机,记录商品的瞬时状态]
 */
public enum Input {
    NICKEL( 5 ), DIME( 10 ), QUARTER( 25 ), DOLLAR( 100 ),
    TOOTHPASTE( 200 ), CHIPS( 75 ), SODA( 100 ), SOAP( 50 ),

    /**以下两个枚举没有价格字段,外界想要获取价格时直接报错*/
    ABORT_TRANSACTION {
        public int amount() {
            throw new RuntimeException( "ABORT.amount()" );
        }
    },
    STOP {
        public int amount() {
            throw new RuntimeException( "SHUT_DOWN.amount()" );
        }
    };

    /**价格*/
    int value;

    Input(int value) {
        this.value = value;
    }

    Input() {
    }

    int amount() {
        return value;
    }

    static Random rand = new Random( 47 );

    public static Input randomSelection() {
        return values()[rand.nextInt( values().length - 1 )];
    }

}
