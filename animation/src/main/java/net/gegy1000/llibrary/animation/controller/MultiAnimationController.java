package net.gegy1000.llibrary.animation.controller;

import net.gegy1000.llibrary.animation.AnimationInstance;
import net.gegy1000.llibrary.animation.AnimationTickResult;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class MultiAnimationController implements AnimationController {
    private final Set<AnimationInstance> activeAnimations = new HashSet<>();
    private final Set<AnimationInstance> immutableActiveAnimations = Collections.unmodifiableSet(this.activeAnimations);

    @Override
    public void tick() {
        this.activeAnimations.removeIf(instance -> {
            AnimationTickResult tickResult = instance.getRawAnimation().tick();
            return tickResult == AnimationTickResult.STOP;
        });
    }

    @Override
    public boolean run(AnimationInstance instance) {
        if (!instance.getRawKind().canRunOn(this)) return false;
        return this.activeAnimations.add(instance);
    }

    @Override
    public boolean stop(AnimationInstance instance) {
        return this.activeAnimations.remove(instance);
    }

    @Override
    public void stopAll() {
        this.activeAnimations.clear();
    }

    @Override
    public Set<AnimationInstance> getActiveAnimations() {
        return this.immutableActiveAnimations;
    }
}
