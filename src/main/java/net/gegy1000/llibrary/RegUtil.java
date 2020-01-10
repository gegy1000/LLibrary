package net.gegy1000.llibrary;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

public class RegUtil {
    @Nonnull
    @SuppressWarnings("ConstantConditions")
    public static <T> T injected() {
        return null;
    }

    public static <T extends IForgeRegistryEntry<T>> Generic<T> generic(IForgeRegistry<T> registry) {
        return new Generic<>(registry);
    }

    public static Blocks blocks(IForgeRegistry<Block> registry) {
        return new Blocks(registry);
    }

    public static Items items(IForgeRegistry<Item> registry) {
        return new Items(registry);
    }

    public static Entities entities(IForgeRegistry<EntityType<?>> registry) {
        return new Entities(registry);
    }

    public static class Items {
        private final IForgeRegistry<Item> registry;
        private Supplier<Item.Properties> propertiesSupplier = Item.Properties::new;

        private Items(IForgeRegistry<Item> registry) {
            this.registry = registry;
        }

        public Items withProperties(Supplier<Item.Properties> propertiesSupplier) {
            this.propertiesSupplier = propertiesSupplier;
            return this;
        }

        public Items add(String name, Item item) {
            ResourceLocation registryName = GameData.checkPrefix(name, false);
            item.setRegistryName(registryName);

            this.registry.register(item);

            return this;
        }

        public Items add(String name, Function<Item.Properties, Item> function) {
            Item item = function.apply(this.propertiesSupplier.get());
            return this.add(name, item);
        }

        public Items add(Block block, BiFunction<Block, Item.Properties, Item> function) {
            ResourceLocation registryName = block.getRegistryName();
            Preconditions.checkNotNull(registryName, "block registry name not set");

            Item item = function.apply(block, this.propertiesSupplier.get());
            item.setRegistryName(registryName);

            this.registry.register(item);

            return this;
        }
    }

    public static class Blocks {
        private final IForgeRegistry<Block> registry;
        private Supplier<Block.Properties> propertiesSupplier;

        private Blocks(IForgeRegistry<Block> registry) {
            this.registry = registry;
        }

        public Blocks withProperties(Supplier<Block.Properties> propertiesSupplier) {
            this.propertiesSupplier = propertiesSupplier;
            return this;
        }

        public Blocks add(String name, Block block) {
            ResourceLocation registryName = GameData.checkPrefix(name, true);
            block.setRegistryName(registryName);

            this.registry.register(block);

            return this;
        }

        public Blocks add(String name, Function<Block.Properties, Block> function) {
            Preconditions.checkNotNull(this.propertiesSupplier, "properties supplier not set");
            Block block = function.apply(this.propertiesSupplier.get());
            return this.add(name, block);
        }
    }

    public static class Entities {
        private final IForgeRegistry<EntityType<?>> registry;

        private Entities(IForgeRegistry<EntityType<?>> registry) {
            this.registry = registry;
        }

        public Entities add(String name, EntityType.Builder<?> builder) {
            ResourceLocation registryName = GameData.checkPrefix(name, true);

            EntityType<?> type = builder.build(registryName.toString());
            type.setRegistryName(registryName);

            this.registry.register(type);

            return this;
        }
    }

    public static class Generic<T extends IForgeRegistryEntry<T>> {
        private final IForgeRegistry<T> registry;

        private Generic(IForgeRegistry<T> registry) {
            this.registry = registry;
        }

        public Generic<T> add(String name, T entry) {
            ResourceLocation registryName = GameData.checkPrefix(name, true);
            entry.setRegistryName(registryName);

            this.registry.register(entry);

            return this;
        }
    }
}
