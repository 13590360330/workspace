package com.javabasic.service.thinkinginjava.Enum;

import com.javabasic.dao.Competitor;
import com.javabasic.service.thinkinginjava.io.Logs;
import static com.javabasic.service.thinkinginjava.Enum.Outcome.*;

/**
 * TODO [多路分发 P615,应用于,多种选择或状态对应多种结果的场景上]
 * <p>
 * a.compete( b )  涉及两路分发,判断a和b的类型
 */

enum Outcome {WIN, LOSE, DRAW}

class RoShamBo {
    public static <T extends Competitor<T, Outcome>> void match(T a, T b) {
        System.out.println( a + " vs. " + b + ": " + a.compete( b ) );
    }

    public static <T extends Enum<T> & Competitor<T, Outcome>> void play(Class<T> rsbClass, int size) {
        for (int i = 0; i < size; i++)
            match( Enums.random( rsbClass ), Enums.random( rsbClass ) );
    }
}

public enum RoShamBo1 implements Competitor<RoShamBo1, Outcome> {
    /**
     * 设置每个实例的3种状态,状态顺序,由实例及compete()方法共同决定
     */
    PAPER( DRAW, LOSE, WIN ),
    SCISSORS( WIN, DRAW, LOSE ),
    ROCK( LOSE, WIN, DRAW );
    private Outcome vPAPER, vSCISSORS, vROCK;

    RoShamBo1(Outcome paper, Outcome scissors, Outcome rock) {
        this.vPAPER = paper;
        this.vSCISSORS = scissors;
        this.vROCK = rock;
    }

    /**
     * 核心,依据输入判定结果状态
     */
    @Override
    public Outcome compete(RoShamBo1 competitor) {
        switch (competitor) {
            default:
            case PAPER:
                return vPAPER;
            case SCISSORS:
                return vSCISSORS;
            case ROCK:
                return vROCK;
        }
    }

    public static void main(String[] args) {
        Logs.getLogs( "RoShamBo1" );
        RoShamBo.play( RoShamBo1.class, 20 );
    }
}
