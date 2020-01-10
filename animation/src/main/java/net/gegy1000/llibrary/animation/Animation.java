package net.gegy1000.llibrary.animation;

import net.minecraft.nbt.CompoundNBT;

public interface Animation {
    AnimationTickResult tick();

    CompoundNBT writeTo(CompoundNBT nbt);

    void readFrom(CompoundNBT nbt);
}
