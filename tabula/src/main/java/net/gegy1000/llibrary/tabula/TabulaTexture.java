package net.gegy1000.llibrary.tabula;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;

public final class TabulaTexture implements AutoCloseable {
    private final NativeImage image;

    TabulaTexture(NativeImage image) {
        this.image = image;
    }

    public DynamicTexture upload(ResourceLocation location) {
        DynamicTexture dynamicTexture = new DynamicTexture(this.image);
        Minecraft.getInstance().getTextureManager().loadTexture(location, dynamicTexture);

        return dynamicTexture;
    }

    @Override
    public void close() {
        this.image.close();
    }
}
