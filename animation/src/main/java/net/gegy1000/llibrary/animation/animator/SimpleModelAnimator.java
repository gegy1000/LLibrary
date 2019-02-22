package net.gegy1000.llibrary.animation.animator;

import net.gegy1000.llibrary.animation.Animation;
import net.gegy1000.llibrary.animation.AnimationState;
import net.gegy1000.llibrary.animation.AnimatedAction;

import java.util.HashMap;
import java.util.Map;

public final class SimpleModelAnimator implements Animator {
    private final Map<Animation<?>, AnimationHandler<?>> animators = new HashMap<>();

    public <S extends AnimationState> SimpleModelAnimator bind(Animation<S> animation, AnimationHandler<S> handler) {
        this.animators.put(animation, handler);
        return this;
    }

    @Override
    public void handle(AnimatedAction<?> playedAnimation) {
        AnimationHandler<?> handler = this.animators.get(playedAnimation.getAnimation());
        if (handler == null) {
            return;
        }

        this.handle(playedAnimation, handler);
    }

    private <A extends Animation<S>, S extends AnimationState> void handle(AnimatedAction<?> playedAnimation, AnimationHandler<S> handler) {
        AnimatedAction.accept(playedAnimation, (A animation, S state) -> handler.animate(state));
    }

    public interface AnimationHandler<S extends AnimationState> {
        void animate(S state);
    }
}
