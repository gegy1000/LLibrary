package net.gegy1000.llibrary.animation;

import net.gegy1000.llibrary.animation.controller.AnimationController;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.function.Predicate;
import java.util.function.Supplier;

public final class AnimationKind<A extends Animation> extends ForgeRegistryEntry<AnimationKind<?>> {
    public final Supplier<A> defaultConstructor;

    private Predicate<AnimationController> controllerTest = c -> true;

    private AnimationKind(Supplier<A> defaultConstructor) {
        this.defaultConstructor = defaultConstructor;
    }

    public static <A extends Animation> AnimationKind<A> of(Supplier<A> constructor) {
        return new AnimationKind<>(constructor);
    }

    public AnimationKind<A> unique() {
        this.controllerTest = c -> c.getActiveAnimations().stream()
                .noneMatch(a -> a.getRawKind().equals(this));
        return this;
    }

    public boolean canRunOn(AnimationController controller) {
        return this.controllerTest.test(controller);
    }

    @SuppressWarnings("unchecked")
    public static Class<AnimationKind<?>> registryType() {
        return (Class<AnimationKind<?>>) (Object) AnimationKind.class;
    }
}
