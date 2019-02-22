package net.gegy1000.llibrary.animation.controller;

import net.gegy1000.llibrary.animation.AnimatedAction;
import net.gegy1000.llibrary.animation.Animation;
import net.gegy1000.llibrary.animation.animator.Animator;

import java.util.Optional;
import java.util.stream.Stream;

public abstract class DistinctAnimationController implements AnimationController {
    private AnimatedAction<?> activeAnimation;

    @Override
    public void updateAnimations() {
        if (this.activeAnimation == null) {
            return;
        }

        if (this.activeAnimation.updateState() == Animation.UpdateResult.COMPLETE) {
            this.activeAnimation = null;
        }
    }

    @Override
    public void accept(Animator animator) {
        if (this.activeAnimation != null) {
            animator.handle(this.activeAnimation);
        }
    }

    @Override
    public <A extends Animation<?>> Optional<AnimatedAction<A>> perform(A animation) {
        if (!animation.applies(this)) {
            return Optional.empty();
        }
        AnimatedAction<A> reference = AnimatedAction.create(animation);
        this.activeAnimation = reference;
        return Optional.of(reference);
    }

    @Override
    public void stop(AnimatedAction<?> animation) {
        if (this.isActive(animation)) {
            this.activeAnimation = null;
        }
    }

    @Override
    public boolean isActive(AnimatedAction<?> animation) {
        return animation.equals(this.activeAnimation);
    }

    @Override
    public Stream<AnimatedAction<?>> performingActions() {
        if (this.activeAnimation != null) {
            return Stream.of(this.activeAnimation);
        } else {
            return Stream.empty();
        }
    }
}
