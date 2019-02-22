package net.gegy1000.llibrary.animation.controller;

import net.gegy1000.llibrary.animation.Animation;
import net.gegy1000.llibrary.animation.LLibraryAnimation;
import net.gegy1000.llibrary.animation.AnimatedAction;
import net.gegy1000.llibrary.animation.network.AnimateEntityMessage;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Optional;

public final class DistinctEntityAnimationController extends DistinctAnimationController {
    private final Entity entity;

    public DistinctEntityAnimationController(Entity entity) {
        this.entity = entity;
    }

    @Override
    public <A extends Animation<?>> Optional<AnimatedAction<A>> perform(A animation) {
        Optional<AnimatedAction<A>> startedAnimation = super.perform(animation);
        if (startedAnimation.isPresent() && this.shouldSync()) {
            AnimateEntityMessage message = new AnimateEntityMessage(this.entity, animation);
            LLibraryAnimation.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.entity), message);
        }

        return startedAnimation;
    }

    private boolean shouldSync() {
        return !this.entity.world.isRemote;
    }
}
