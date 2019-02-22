package net.gegy1000.llibrary.animation.network;

import net.gegy1000.llibrary.animation.Animation;
import net.gegy1000.llibrary.animation.LLibraryAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class AnimateEntityMessage {
    private final int entityId;
    private final Animation<?> animation;

    public AnimateEntityMessage(int entityId, Animation<?> animation) {
        this.entityId = entityId;
        this.animation = animation;
    }

    public AnimateEntityMessage(Entity entity, Animation<?> animation) {
        this(entity.getEntityId(), animation);
    }

    public void serialize(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeInt(LLibraryAnimation.animationRegistry().getID(this.animation));
    }

    public static AnimateEntityMessage deserialize(PacketBuffer buffer) {
        int entityId = buffer.readInt();

        Animation<?> animation = LLibraryAnimation.animationRegistry().getValue(buffer.readInt());
        return new AnimateEntityMessage(entityId, animation);
    }

    public Entity getEntity(World world) {
        return world.getEntityByID(this.entityId);
    }

    public Animation<?> getAnimation() {
        return this.animation;
    }
}
