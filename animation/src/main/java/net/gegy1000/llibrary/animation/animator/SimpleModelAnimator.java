package net.gegy1000.llibrary.animation.animator;

import net.gegy1000.llibrary.animation.Animation;
import net.gegy1000.llibrary.animation.AnimationInstance;
import net.gegy1000.llibrary.animation.AnimationKind;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class SimpleModelAnimator implements Animator {
    private final Map<AnimationKind<?>, Consumer<?>> animators = new HashMap<>();

    public <A extends Animation> SimpleModelAnimator bind(AnimationKind<A> kind, Consumer<A> handler) {
        this.animators.put(kind, handler);
        return this;
    }

    @Override
    public void accept(AnimationInstance animation) {
        Consumer<?> handler = this.animators.get(animation.getRawKind());
        if (handler != null) {
            this.accept(animation, handler);
        }
    }

    @SuppressWarnings("unchecked")
    private <S> void accept(AnimationInstance animation, Consumer<S> handler) {
        handler.accept((S) animation.getRawAnimation());
    }
}
