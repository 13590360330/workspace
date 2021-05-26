package com.javabasic.service.effectivejava.basic;

public class Room implements AutoCloseable{

    private static class State implements Runnable{
        int numJunkPiles;

        State(int numJunkPiles){
            this.numJunkPiles = numJunkPiles;
        }

        @Override
        public void run() {
            System.out.println("Cleaning room");
            numJunkPiles = 0;
        }
    }

    private final State state;

    public Room(int numJunkPiles){
        state = new State( numJunkPiles );
    }

    @Override
    public void close() throws Exception {

    }
}
