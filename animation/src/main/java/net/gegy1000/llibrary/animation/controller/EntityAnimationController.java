package net.gegy1000.llibrary.animation.controller;

import net.gegy1000.llibrary.animation.AnimationInstance;
import net.gegy1000.llibrary.animation.LLibraryAnimation;
import net.gegy1000.llibrary.animation.network.AnimateEntityMessage;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Set;

public final class EntityAnimationController implements AnimationController {
    private final Entity entity;
    private final AnimationController inner;

    public EntityAnimationController(Entity entity, AnimationController inner) {
        this.entity = entity;
        this.inner = inner;
    }

    @Override
    public void tick() {
        this.inner.tick();
    }

    @Override
    public boolean run(AnimationInstance instance) {
        if (this.inner.run(instance) && this.shouldSync()) {
            AnimateEntityMessage message = new AnimateEntityMessage(this.entity, instance);
            LLibraryAnimation.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.entity), message);
        }
        return false;
    }

    @Override
    public boolean stop(AnimationInstance instance) {
        return this.inner.stop(instance);
    }

    @Override
    public Set<AnimationInstance> getActiveAnimations() {
        return this.inner.getActiveAnimations();
    }

    private boolean shouldSync() {
        return !this.entity.world.isRemote;
    }
}
