package net.gegy1000.llibrary.example.animation;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class ExampleAnimatableEntityRenderer extends RenderLiving<ExampleAnimatableEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/pig/pig.png");

    public ExampleAnimatableEntityRenderer(RenderManager manager) {
        super(manager, new ExampleAnimatableEntityModel(), 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(ExampleAnimatableEntity entity) {
        return TEXTURE;
    }
}
