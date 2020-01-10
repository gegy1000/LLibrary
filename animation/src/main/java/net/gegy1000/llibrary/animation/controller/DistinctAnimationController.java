package net.gegy1000.llibrary.animation.controller;

import com.google.common.collect.ImmutableSet;
import net.gegy1000.llibrary.animation.AnimationInstance;
import net.gegy1000.llibrary.animation.AnimationTickResult;

import java.util.Collections;
import java.util.Set;

public final class DistinctAnimationController implements AnimationController {
    private AnimationInstance activeAnimation;

    @Override
    public void tick() {
        if (this.activeAnimation == null) return;

        AnimationTickResult tickResult = this.activeAnimation.getRawAnimation().tick();
        if (tickResult == AnimationTickResult.STOP) {
            this.activeAnimation = null;
        }
    }

    @Override
    public boolean run(AnimationInstance instance) {
        if (!instance.getRawKind().canRunOn(this)) return false;

        this.activeAnimation = instance;
        return true;
    }

    @Override
    public boolean stop(AnimationInstance instance) {
        if (instance.equals(this.activeAnimation)) {
            this.activeAnimation = null;
            return true;
        }
        return false;
    }

    @Override
    public Set<AnimationInstance> getActiveAnimations() {
        if (this.activeAnimation != null) {
            return ImmutableSet.of(this.activeAnimation);
        } else {
            return Collections.emptySet();
        }
    }
}
