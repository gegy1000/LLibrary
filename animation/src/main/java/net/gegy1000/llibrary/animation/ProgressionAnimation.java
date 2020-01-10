package net.gegy1000.llibrary.animation;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;

public final class ProgressionAnimation implements Animation {
    private final int length;
    private int ticks;

    private ProgressionAnimation(int length) {
        this.length = length;
    }

    public static ProgressionAnimation ofLength(int length) {
        return new ProgressionAnimation(length);
    }

    @Override
    public AnimationTickResult tick() {
        return ++this.ticks >= this.length ? AnimationTickResult.STOP : AnimationTickResult.CONTINUE;
    }

    @Override
    public CompoundNBT writeTo(CompoundNBT nbt) {
        nbt.putInt("t", this.ticks);
        return nbt;
    }

    @Override
    public void readFrom(CompoundNBT nbt) {
        this.ticks = nbt.getInt("t");
    }

    public float getProgress(float partialTicks) {
        float ticks = MathHelper.clamp(this.ticks + partialTicks, 0, this.length);
        return ticks / this.length;
    }

    public float getProgress() {
        return this.getProgress(0.0F);
    }
}
