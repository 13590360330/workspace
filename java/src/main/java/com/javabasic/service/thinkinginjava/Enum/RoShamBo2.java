package com.javabasic.service.thinkinginjava.Enum;

import com.javabasic.dao.Competitor;

import java.util.EnumMap;

import static com.javabasic.service.thinkinginjava.Enum.Outcome.*;

/**
 * TODO [使用EnumMap分发 P618]
 */
public enum RoShamBo2 implements Competitor<RoShamBo2, Outcome> {

    PAPER, SCISSORS, ROCK;

    static EnumMap<RoShamBo2, EnumMap<RoShamBo2, Outcome>> table =
            new EnumMap<RoShamBo2, EnumMap<RoShamBo2, Outcome>>( RoShamBo2.class );

    static {
        for (RoShamBo2 it : RoShamBo2.values()) {
            table.put( it, new EnumMap<RoShamBo2, Outcome>( RoShamBo2.class ) );
        }
        initRow( PAPER, DRAW, LOSE, WIN );
        initRow( SCISSORS, WIN, DRAW, LOSE );
        initRow( ROCK, LOSE, WIN, DRAW );
    }

    /**EnumMap 中存入RoShamBo2和rock的比较结果*/
    private static void initRow(RoShamBo2 rock, Outcome lose, Outcome win, Outcome draw) {
        EnumMap<RoShamBo2, Outcome> row = RoShamBo2.table.get( rock );
        row.put( PAPER, lose );
        row.put( SCISSORS, win );
        row.put( ROCK, draw );
    }

    @Override
    public Outcome compete(RoShamBo2 competitor) {
        return table.get( this ).get( competitor );
    }

    public static void main(String[] args) {
        RoShamBo.play( RoShamBo2.class, 20 );
    }
}


