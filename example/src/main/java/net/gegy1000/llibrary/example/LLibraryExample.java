package net.gegy1000.llibrary.example;

import net.gegy1000.llibrary.example.animation.ExampleAnimatedEntity;
import net.gegy1000.llibrary.example.animation.ExampleAnimatedEntityRenderer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(LLibraryExample.MODID)
public class LLibraryExample {
    public static final String MODID = "llibrary-example";
    public static final Logger LOGGER = LogManager.getLogger(LLibraryExample.class);

    public LLibraryExample() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::setupClient);
    }

    private void setup(FMLCommonSetupEvent event) {
    }

    private void setupClient(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ExampleAnimatedEntity.class, ExampleAnimatedEntityRenderer::new);
    }
}
