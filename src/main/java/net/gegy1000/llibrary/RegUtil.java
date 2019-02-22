package net.gegy1000.llibrary;

import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public class RegUtil {
    @Nonnull
    @SuppressWarnings("ConstantConditions")
    public static <T> T injected() {
        return null;
    }

    public static <T extends IForgeRegistryEntry<T>> T withName(T entry, String name) {
        entry.setRegistryName(GameData.checkPrefix(name));
        return entry;
    }
}
