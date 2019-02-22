package net.gegy1000.llibrary.animation.controller;

import net.gegy1000.llibrary.animation.Animation;
import net.gegy1000.llibrary.animation.AnimatedAction;
import net.gegy1000.llibrary.animation.animator.Animator;

import java.util.Optional;
import java.util.stream.Stream;

public interface AnimationController {
    void updateAnimations();

    void accept(Animator animator);

    <A extends Animation<?>> Optional<AnimatedAction<A>> perform(A animation);

    void stop(AnimatedAction<?> animation);

    boolean isActive(AnimatedAction<?> animation);

    Stream<AnimatedAction<?>> performingActions();
}
