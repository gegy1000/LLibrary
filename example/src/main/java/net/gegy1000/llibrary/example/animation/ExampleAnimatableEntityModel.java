package net.gegy1000.llibrary.example.animation;

import net.gegy1000.llibrary.animation.LLibraryAnimation;
import net.gegy1000.llibrary.animation.animator.SimpleModelAnimator;
import net.gegy1000.llibrary.animation.controller.AnimationController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.ModelPig;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;

public class ExampleAnimatableEntityModel extends ModelPig {
    private final SimpleModelAnimator animator;

    public ExampleAnimatableEntityModel() {
        this.animator = new SimpleModelAnimator();
        this.animator.bind(ExampleAnimation.BOUNCE, state -> {
            float progress = state.getProgress(Minecraft.getInstance().getRenderPartialTicks());
            float animation = (float) Math.sin(2.0 * Math.PI * progress);
            this.body.rotationPointY = 11.0F + animation;
        });
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

        this.body.rotationPointY = 11.0F;

        LazyOptional<AnimationController> controller = entity.getCapability(LLibraryAnimation.controllerCap());
        controller.ifPresent(animator -> animator.accept(this.animator));
    }
}
