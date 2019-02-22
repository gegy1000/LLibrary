package net.gegy1000.llibrary.animation;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public final class AnimatedAction<A extends Animation<?>> {
    private final A animation;
    private final AnimationState state;

    private AnimatedAction(A animation, AnimationState state) {
        this.animation = animation;
        this.state = state;
    }

    public static <A extends Animation<?>> AnimatedAction<A> create(A animation) {
        return new AnimatedAction<>(animation, animation.createState());
    }

    public Animation.UpdateResult updateState() {
        return apply(this, Animation::updateState);
    }

    @SuppressWarnings("unchecked")
    public static <A extends Animation<S>, S extends AnimationState> void accept(AnimatedAction<?> animation, BiConsumer<A, S> handler) {
        handler.accept((A) animation.animation, (S) animation.state);
    }

    @SuppressWarnings("unchecked")
    public static <A extends Animation<S>, S extends AnimationState, R> R apply(AnimatedAction<?> animation, BiFunction<A, S, R> handler) {
        return handler.apply((A) animation.animation, (S) animation.state);
    }

    public A getAnimation() {
        return this.animation;
    }
}
