package net.gegy1000.llibrary.animation;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

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

    public static AnimationInstance readFrom(PacketBuffer buffer) {
        AnimationKind<?> kind = LLibraryAnimation.animationRegistry().getValue(buffer.readInt());
        Animation state = kind.defaultConstructor.get();
        state.readFrom(buffer.readCompoundTag());
        return new AnimationInstance(kind, state);
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
