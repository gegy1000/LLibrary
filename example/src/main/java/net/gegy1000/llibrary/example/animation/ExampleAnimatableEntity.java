package net.gegy1000.llibrary.example.animation;

import net.gegy1000.llibrary.animation.LLibraryAnimation;
import net.gegy1000.llibrary.animation.controller.AnimationController;
import net.gegy1000.llibrary.animation.controller.DistinctEntityAnimationController;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class ExampleAnimatableEntity extends EntityLiving {
    private final AnimationController animationController = new DistinctEntityAnimationController(this);
    private final LazyOptional<AnimationController> animatorHolder = LazyOptional.of(() -> this.animationController);

    public ExampleAnimatableEntity(World world) {
        super(ExampleAnimation.EXAMPLE_ANIMATABLE, world);
        this.setSize(0.9F, 0.9F);
    }

    @Override
    public void tick() {
        super.tick();
        this.animationController.updateAnimations();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        this.animationController.perform(ExampleAnimation.BOUNCE);
        return false;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return LLibraryAnimation.controllerCap().orEmpty(capability, this.animatorHolder);
    }
}
