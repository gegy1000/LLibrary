package net.gegy1000.llibrary.animation.network;

import net.gegy1000.llibrary.animation.AnimationInstance;
import net.gegy1000.llibrary.animation.LLibraryAnimation;
import net.gegy1000.llibrary.animation.controller.AnimationController;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public final class AnimateEntityMessage {
    private final int entityId;
    private final AnimationInstance animation;

    private AnimateEntityMessage(int entityId, AnimationInstance animation) {
        this.entityId = entityId;
        this.animation = animation;
    }

    public AnimateEntityMessage(Entity entity, AnimationInstance animation) {
        this(entity.getEntityId(), animation);
    }

    public void writeTo(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
        this.animation.writeTo(buffer);
    }

    public static AnimateEntityMessage readFrom(PacketBuffer buffer) {
        int entityId = buffer.readInt();
        AnimationInstance animation = AnimationInstance.readFrom(buffer);
        return new AnimateEntityMessage(entityId, animation);
    }

    public Entity getEntity(World world) {
        return world.getEntityByID(this.entityId);
    }

    public void runOn(AnimationController controller) {
        controller.run(this.animation);
    }

    public static boolean handle(AnimateEntityMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> {
                Minecraft client = Minecraft.getInstance();
                Entity entity = message.getEntity(client.world);

                LazyOptional<AnimationController> controllerCap = entity.getCapability(LLibraryAnimation.controllerCap());
                controllerCap.ifPresent(message::runOn);
            });
        }

        return true;
    }
}
