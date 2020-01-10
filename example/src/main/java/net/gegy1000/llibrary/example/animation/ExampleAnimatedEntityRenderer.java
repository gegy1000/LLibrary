package net.gegy1000.llibrary.example.animation;

import net.gegy1000.llibrary.animation.LLibraryAnimation;
import net.gegy1000.llibrary.animation.animator.SimpleModelAnimator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.util.ResourceLocation;

public class ExampleAnimatedEntityRenderer extends MobRenderer<ExampleAnimatedEntity, ExampleAnimatedEntityRenderer.Model> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/pig/pig.png");

    public ExampleAnimatedEntityRenderer(EntityRendererManager manager) {
        super(manager, new Model(), 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(ExampleAnimatedEntity entity) {
        return TEXTURE;
    }

    static class Model extends PigModel<ExampleAnimatedEntity> {
        private final SimpleModelAnimator animator;

        Model() {
            this.animator = new SimpleModelAnimator();
            this.animator.bind(AnimationExample.BOUNCE, animation -> {
                float progress = animation.getProgress(Minecraft.getInstance().getRenderPartialTicks());
                this.body.rotationPointY += (float) Math.sin(2.0 * Math.PI * progress);
            });
        }

        @Override
        public void setRotationAngles(ExampleAnimatedEntity entity, float limbSwing, float limbSwingAmount, float age, float netHeadYaw, float headPitch, float scaleFactor) {
            super.setRotationAngles(entity, limbSwing, limbSwingAmount, age, netHeadYaw, headPitch, scaleFactor);

            this.resetPose();

            entity.getCapability(LLibraryAnimation.controllerCap())
                    .ifPresent(controller -> controller.accept(this.animator));
        }

        private void resetPose() {
            this.body.rotationPointY = 11.0F;
        }
    }
}
