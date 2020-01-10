package net.gegy1000.llibrary.example.animation;

import net.gegy1000.llibrary.animation.LLibraryAnimation;
import net.gegy1000.llibrary.animation.controller.AnimationController;
import net.gegy1000.llibrary.animation.controller.DistinctAnimationController;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class ExampleAnimatedEntity extends MobEntity {
    private final AnimationController animationController = new DistinctAnimationController().forEntity(this);
    private final LazyOptional<AnimationController> animatorHolder = LazyOptional.of(() -> this.animationController);

    public ExampleAnimatedEntity(EntityType<ExampleAnimatedEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();
        this.animationController.tick();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        this.animationController.run(AnimationExample.BOUNCE);
        return false;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction direction) {
        return LLibraryAnimation.controllerCap().orEmpty(capability, this.animatorHolder);
    }
}
