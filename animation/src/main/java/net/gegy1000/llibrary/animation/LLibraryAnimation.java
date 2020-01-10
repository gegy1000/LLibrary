package net.gegy1000.llibrary.animation;

import com.google.common.base.Preconditions;
import net.gegy1000.llibrary.LLibrary;
import net.gegy1000.llibrary.LLibraryModule;
import net.gegy1000.llibrary.animation.controller.AnimationController;
import net.gegy1000.llibrary.animation.controller.NoAnimationController;
import net.gegy1000.llibrary.animation.network.AnimateEntityMessage;
import net.gegy1000.llibrary.capability.NullStorage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import javax.annotation.Nonnull;

public class LLibraryAnimation implements LLibraryModule {
    public static final String NETWORK_PROTOCOL = "1";

    @CapabilityInject(AnimationController.class)
    private static Capability<AnimationController> controllerCap;

    private static ForgeRegistry<AnimationKind<?>> animationRegistry;

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(LLibrary.MODID, "animation"))
            .networkProtocolVersion(() -> NETWORK_PROTOCOL)
            .clientAcceptedVersions(NETWORK_PROTOCOL::equals)
            .serverAcceptedVersions(NETWORK_PROTOCOL::equals)
            .simpleChannel();

    @Override
    public void initialize(IEventBus eventBus) {
        eventBus.addListener(this::setup);
        eventBus.addListener(this::createRegistries);
    }

    private void setup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(AnimationController.class, new NullStorage<>(), () -> NoAnimationController.INSTANCE);

        CHANNEL.messageBuilder(AnimateEntityMessage.class, 0)
                .encoder(AnimateEntityMessage::writeTo).decoder(AnimateEntityMessage::readFrom)
                .consumer(AnimateEntityMessage::handle)
                .add();
    }

    private void createRegistries(RegistryEvent.NewRegistry event) {
        animationRegistry = (ForgeRegistry<AnimationKind<?>>) new RegistryBuilder<AnimationKind<?>>()
                .setType(AnimationKind.registryType())
                .setName(new ResourceLocation(LLibrary.MODID, "animations"))
                .disableSaving()
                .create();
    }

    @Nonnull
    public static ForgeRegistry<AnimationKind<?>> animationRegistry() {
        return (ForgeRegistry<AnimationKind<?>>) GameRegistry.findRegistry(AnimationKind.registryType());
    }

    @Nonnull
    public static Capability<AnimationController> controllerCap() {
        return Preconditions.checkNotNull(controllerCap, "animation controller capability not yet injected");
    }
}
