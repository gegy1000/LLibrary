package net.gegy1000.llibrary.animation;

import net.gegy1000.llibrary.LLibrary;
import net.gegy1000.llibrary.LLibraryModule;
import net.gegy1000.llibrary.animation.controller.AnimationController;
import net.gegy1000.llibrary.animation.controller.VoidAnimationController;
import net.gegy1000.llibrary.animation.network.AnimateEntityMessage;
import net.gegy1000.llibrary.capability.NullStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class LLibraryAnimation implements LLibraryModule {
    public static final String NETWORK_PROTOCOL = "1";

    @CapabilityInject(AnimationController.class)
    private static Capability<AnimationController> controllerCap;

    private static ForgeRegistry<Animation<?>> animationRegistry;

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
        CapabilityManager.INSTANCE.register(AnimationController.class, new NullStorage<>(), () -> VoidAnimationController.INSTANCE);

        CHANNEL.messageBuilder(AnimateEntityMessage.class, 0)
                .encoder(AnimateEntityMessage::serialize)
                .decoder(AnimateEntityMessage::deserialize)
                .consumer(LLibraryAnimation::handleEntityAnimationMessage)
                .add();
    }

    private static void handleEntityAnimationMessage(AnimateEntityMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getLogicalSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> {
                Minecraft client = Minecraft.getInstance();
                Entity entity = message.getEntity(client.world);
                Animation<?> animation = message.getAnimation();

                LazyOptional<AnimationController> controllerCap = entity.getCapability(controllerCap());
                controllerCap.ifPresent(controller -> controller.perform(animation));
            });

            context.setPacketHandled(true);
        }
    }

    private void createRegistries(RegistryEvent.NewRegistry event) {
        animationRegistry = (ForgeRegistry<Animation<?>>) new RegistryBuilder<Animation<?>>()
                .setType(Animation.type())
                .setName(new ResourceLocation(LLibrary.MODID, "animations"))
                .disableSaving()
                .create();
    }

    @Nonnull
    public static ForgeRegistry<Animation<?>> animationRegistry() {
        if (animationRegistry == null) {
            throw new IllegalStateException("Animation Registry is not yet initialized");
        }
        return animationRegistry;
    }

    @Nonnull
    public static Capability<AnimationController> controllerCap() {
        if (controllerCap == null) {
            throw new IllegalStateException("Animation Controller Capability is not yet injected");
        }
        return controllerCap;
    }
}
