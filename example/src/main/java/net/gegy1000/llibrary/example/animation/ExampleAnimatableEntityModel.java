package net.gegy1000.llibrary.example.animation;

import net.gegy1000.llibrary.animation.LLibraryAnimation;
import net.gegy1000.llibrary.animation.animator.SimpleModelAnimator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.PigModel;

public class ExampleAnimatableEntityModel extends PigModel<ExampleAnimatableEntity> {
    private final SimpleModelAnimator animator;

    public ExampleAnimatableEntityModel() {
        this.animator = new SimpleModelAnimator();
        this.animator.bind(AnimationExample.BOUNCE, animation -> {
            float progress = animation.getProgress(Minecraft.getInstance().getRenderPartialTicks());
            this.body.rotationPointY += (float) Math.sin(2.0 * Math.PI * progress);
        });
    }

    @Override
    public void setRotationAngles(ExampleAnimatableEntity entity, float limbSwing, float limbSwingAmount, float age, float netHeadYaw, float headPitch, float scaleFactor) {
        super.setRotationAngles(entity, limbSwing, limbSwingAmount, age, netHeadYaw, headPitch, scaleFactor);

        this.resetPose();

        entity.getCapability(LLibraryAnimation.controllerCap())
                .ifPresent(controller -> controller.accept(this.animator));
    }

    private void resetPose() {
        this.body.rotationPointY = 11.0F;
    }
}
