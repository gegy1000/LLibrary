package net.gegy1000.llibrary.animation;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import java.util.Optional;

public final class AnimationInstance {
    private final AnimationKind<?> kind;
    private final Animation animation;

    private AnimationInstance(AnimationKind<?> kind, Animation animation) {
        this.kind = kind;
        this.animation = animation;
    }

    public static <A extends Animation> AnimationInstance create(AnimationKind<A> kind, A animation) {
        return new AnimationInstance(kind, animation);
    }

    public AnimationKind<?> getRawKind() {
        return this.kind;
    }

    public Animation getRawAnimation() {
        return this.animation;
    }

    public void writeTo(PacketBuffer buffer) {
        buffer.writeInt(LLibraryAnimation.animationRegistry().getID(this.kind));
        buffer.writeCompoundTag(this.animation.writeTo(new CompoundNBT()));
    }

    public void writeTo(CompoundNBT nbt) {
        ResourceLocation id = LLibraryAnimation.animationRegistry().getKey(this.kind);
        if (id == null) throw new IllegalStateException("Cannot serialize unregistered");

        nbt.putString("id", id.toString());
        nbt.put("state", this.animation.writeTo(new CompoundNBT()));
    }

    public static Optional<AnimationInstance> readFrom(PacketBuffer buffer) {
        AnimationKind<?> kind = LLibraryAnimation.animationRegistry().getValue(buffer.readInt());
        if (kind == null) return Optional.empty();

        Animation animation = kind.defaultConstructor.get();
        animation.readFrom(buffer.readCompoundTag());
        return Optional.of(new AnimationInstance(kind, animation));
    }

    public static Optional<AnimationInstance> readFrom(CompoundNBT nbt) {
        ResourceLocation id = new ResourceLocation(nbt.getString("id"));
        AnimationKind<?> kind = LLibraryAnimation.animationRegistry().getValue(id);
        if (kind == null) return Optional.empty();

        Animation animation = kind.defaultConstructor.get();
        animation.readFrom(nbt.getCompound("state"));
        return Optional.of(new AnimationInstance(kind, animation));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj instanceof AnimationInstance) {
            AnimationInstance instance = (AnimationInstance) obj;
            return this.kind.equals(instance.kind) && this.animation == instance.animation;
        }

        return false;
    }
}
