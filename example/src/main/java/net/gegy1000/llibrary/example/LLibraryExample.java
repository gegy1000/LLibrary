package net.gegy1000.llibrary.example;

import net.gegy1000.llibrary.animation.LLibraryAnimation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("llibrary-example")
public class LLibraryExample {
    public static final Logger LOGGER = LogManager.getLogger(LLibraryExample.class);

    public LLibraryExample() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(FMLCommonSetupEvent event) {
        LOGGER.info(LLibraryAnimation.ANIMATOR);
    }
}
