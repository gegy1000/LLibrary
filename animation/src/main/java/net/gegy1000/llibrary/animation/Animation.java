package net.gegy1000.llibrary.animation;

import net.gegy1000.llibrary.animation.controller.AnimationController;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class Animation<S extends AnimationState> extends ForgeRegistryEntry<Animation<?>> {
    public abstract S createState();

    public abstract UpdateResult updateState(S state);

    public boolean applies(AnimationController controller) {
        return true;
    }

    @SuppressWarnings("unchecked")
    public static Class<Animation<?>> type() {
        return (Class<Animation<?>>) (Object) Animation.class;
    }

    public enum UpdateResult {
        CONTINUE,
        COMPLETE
    }
}
