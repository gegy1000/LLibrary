package net.gegy1000.llibrary.animation.controller;

import net.gegy1000.llibrary.animation.Animation;
import net.gegy1000.llibrary.animation.AnimatedAction;
import net.gegy1000.llibrary.animation.animator.Animator;

import java.util.Optional;
import java.util.stream.Stream;

public final class VoidAnimationController implements AnimationController {
    public static final AnimationController INSTANCE = new VoidAnimationController();

    private VoidAnimationController() {
    }

    @Override
    public void updateAnimations() {
    }

    @Override
    public void accept(Animator animator) {
    }

    @Override
    public <A extends Animation<?>> Optional<AnimatedAction<A>> perform(A animation) {
        return Optional.empty();
    }

    @Override
    public void stop(AnimatedAction<?> animation) {
    }

    @Override
    public boolean isActive(AnimatedAction<?> animation) {
        return false;
    }

    @Override
    public Stream<AnimatedAction<?>> performingActions() {
        return Stream.empty();
    }
}
