package net.gegy1000.llibrary.animation.controller;

import net.gegy1000.llibrary.animation.AnimationInstance;

import java.util.Collections;
import java.util.Set;

public final class NoAnimationController implements AnimationController {
    public static final AnimationController INSTANCE = new NoAnimationController();

    private NoAnimationController() {
    }

    @Override
    public void tick() {
    }

    @Override
    public boolean run(AnimationInstance instance) {
        return false;
    }

    @Override
    public boolean stop(AnimationInstance instance) {
        return false;
    }

    @Override
    public void stopAll() {
    }

    @Override
    public Set<AnimationInstance> getActiveAnimations() {
        return Collections.emptySet();
    }
}
