package net.gegy1000.llibrary.animation;

import net.gegy1000.llibrary.animation.controller.AnimationController;

import java.util.Objects;

public final class SimpleAnimation extends Animation<SimpleAnimation.State> {
    private final float length;
    private boolean unique;

    public SimpleAnimation(float length) {
        this.length = length;
    }

    public SimpleAnimation unique() {
        this.unique = true;
        return this;
    }

    @Override
    public State createState() {
        return new State(this.length);
    }

    @Override
    public UpdateResult updateState(State state) {
        if (state.ticks < state.length) {
            state.ticks++;
            return UpdateResult.CONTINUE;
        }
        return UpdateResult.COMPLETE;
    }

    @Override
    public boolean applies(AnimationController controller) {
        if (!this.unique) {
            return true;
        }

        return controller.performingActions().noneMatch(it -> this.equals(it.getAnimation()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof SimpleAnimation) {
            SimpleAnimation animation = (SimpleAnimation) obj;
            return Objects.equals(this.getRegistryName(), animation.getRegistryName());
        }

        return false;
    }

    public static class State implements AnimationState {
        private final float length;
        private int ticks;

        State(float length) {
            this.length = length;
        }

        public float getLength() {
            return this.length;
        }

        public float getTicks(float deltaTime) {
            return Math.min(this.ticks + deltaTime, this.length);
        }

        public float getProgress(float deltaTime) {
            return this.getTicks(deltaTime) / this.length;
        }
    }
}
