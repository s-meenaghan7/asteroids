package com.me.asteroids;

import javafx.animation.AnimationTimer;

public abstract class ProjectileTimer extends AnimationTimer {

    private final long MOD = 400; // higher values slow down firing rate
    private final long sleepTime;
    private long prevTime = 0;

    public ProjectileTimer() {
        this.sleepTime = MOD * 1_000_000;
    }

    @Override
    public void handle(long now) {

        if ((now - prevTime) < sleepTime)
            return;

        prevTime = now;

        handle();
    }

    public abstract void handle();
}
