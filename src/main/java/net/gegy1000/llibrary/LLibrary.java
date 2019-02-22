package net.gegy1000.llibrary;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ServiceLoader;

@Mod(LLibrary.MODID)
public class LLibrary {
    public static final String MODID = "llibrary";
    public static final Logger LOGGER = LogManager.getLogger(LLibrary.class);

    private final ServiceLoader<LLibraryModule> modules;

    public LLibrary() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);

        this.modules = ServiceLoader.load(LLibraryModule.class);
        for (LLibraryModule module : this.modules) {
            module.initialize(eventBus);
        }
    }

    private void setup(FMLCommonSetupEvent event) {
    }
}
