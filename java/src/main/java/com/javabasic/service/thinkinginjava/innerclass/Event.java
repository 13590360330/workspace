package com.javabasic.service.thinkinginjava.innerclass;

/**
 * TODO [事件触发器 P208]
 *
 * 通过创建不同的Event来表现不同的行为
 */

public abstract class Event {

    private long eventTime;
    protected final long delayTime;

    public Event(long delayTime) {
        this.delayTime = delayTime;
        start();
    }

    public final void start() { //allows restart
        eventTime = System.nanoTime() + delayTime;
    }

    public boolean ready() {
        return System.nanoTime() >= eventTime;
    }

    public abstract void action();

}
