package net.gegy1000.llibrary.capability;

import net.minecraft.nbt.INBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class NullStorage<T> implements Capability.IStorage<T> {
    @Nullable
    @Override
    public INBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
        return null;
    }

    @Override
    public void readNBT(Capability<T> capability, T instance, EnumFacing side, INBTBase nbt) {
    }
}
