package net.gegy1000.llibrary.animation.controller;

import net.gegy1000.llibrary.animation.Animation;
import net.gegy1000.llibrary.animation.AnimationInstance;
import net.gegy1000.llibrary.animation.AnimationKind;
import net.gegy1000.llibrary.animation.animator.Animator;
import net.minecraft.entity.Entity;

import java.util.Optional;
import java.util.Set;

public interface AnimationController {
    void tick();

    boolean run(AnimationInstance instance);

    boolean stop(AnimationInstance instance);

    Set<AnimationInstance> getActiveAnimations();

    static AnimationController no() {
        return NoAnimationController.INSTANCE;
    }

    default <A extends Animation> Optional<AnimationInstance> run(AnimationKind<A> kind) {
        return this.run(kind, kind.defaultConstructor.get());
    }

    default <A extends Animation> Optional<AnimationInstance> run(AnimationKind<A> kind, A animation) {
        AnimationInstance instance = AnimationInstance.create(kind, animation);
        if (this.run(instance)) {
            return Optional.of(instance);
        } else {
            return Optional.empty();
        }
    }

    default boolean isActive(AnimationInstance animation) {
        return this.getActiveAnimations().contains(animation);
    }

    default void accept(Animator animator) {
        this.getActiveAnimations().forEach(animator::accept);
    }

    default EntityAnimationController forEntity(Entity entity) {
        return new EntityAnimationController(entity, this);
    }
}
