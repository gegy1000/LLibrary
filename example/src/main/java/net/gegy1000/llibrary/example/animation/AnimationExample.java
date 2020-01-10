package net.gegy1000.llibrary.example.animation;

import net.gegy1000.llibrary.RegUtil;
import net.gegy1000.llibrary.animation.AnimationKind;
import net.gegy1000.llibrary.animation.ProgressionAnimation;
import net.gegy1000.llibrary.example.LLibraryExample;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = LLibraryExample.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(LLibraryExample.MODID)
public class AnimationExample {
    public static final EntityType<ExampleAnimatableEntity> EXAMPLE_ANIMATABLE = RegUtil.injected();
    public static final AnimationKind<ProgressionAnimation> BOUNCE = RegUtil.injected();

    @SubscribeEvent
    public static void onRegisterAnimations(RegistryEvent.Register<AnimationKind<?>> event) {
        RegUtil.generic(event.getRegistry())
                .add("bounce",
                        AnimationKind.of(() -> ProgressionAnimation.ofLength(10))
                                .unique()
                );
    }

    @SubscribeEvent
    public static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event) {
        RegUtil.entities(event.getRegistry())
                .add("example_animatable",
                        EntityType.Builder.create(ExampleAnimatableEntity::new, EntityClassification.CREATURE)
                                .size(0.9F, 0.9F)
                                .setTrackingRange(64)
                                .setUpdateInterval(1)
                                .setShouldReceiveVelocityUpdates(false)
                );
    }
}
