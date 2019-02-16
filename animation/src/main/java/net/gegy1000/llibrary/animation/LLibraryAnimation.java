package net.gegy1000.llibrary.animation;

import net.gegy1000.llibrary.capability.NullStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("llibrary-animation")
public class LLibraryAnimation {
    public static final Logger LOGGER = LogManager.getLogger(LLibraryAnimation.class);

    @CapabilityInject(Animator.class)
    public static Animator ANIMATOR;

    public LLibraryAnimation() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(Animator.class, new NullStorage<>(), Animator.Void::new);
    }
}
