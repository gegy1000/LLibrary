package net.gegy1000.llibrary.example.animation;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class ExampleAnimatableEntityRenderer extends MobRenderer<ExampleAnimatableEntity, ExampleAnimatableEntityModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/pig/pig.png");

    public ExampleAnimatableEntityRenderer(EntityRendererManager manager) {
        super(manager, new ExampleAnimatableEntityModel(), 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(ExampleAnimatableEntity entity) {
        return TEXTURE;
    }
}
