package net.gegy1000.llibrary.example.animation;

import net.gegy1000.llibrary.RegUtil;
import net.gegy1000.llibrary.animation.Animation;
import net.gegy1000.llibrary.animation.SimpleAnimation;
import net.gegy1000.llibrary.example.LLibraryExample;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = LLibraryExample.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(LLibraryExample.MODID)
public class ExampleAnimation {
    public static final EntityType<ExampleAnimatableEntity> EXAMPLE_ANIMATABLE = RegUtil.injected();
    public static final SimpleAnimation BOUNCE = RegUtil.injected();

    @SubscribeEvent
    public static void onRegisterAnimations(RegistryEvent.Register<Animation<?>> event) {
        event.getRegistry().registerAll(
                RegUtil.withName(new SimpleAnimation(10.0F).unique(), "bounce")
        );
    }

    @SubscribeEvent
    public static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().registerAll(
                RegUtil.withName(
                        EntityType.Builder.create(ExampleAnimatableEntity.class, ExampleAnimatableEntity::new)
                                .tracker(64, 1, false)
                                .build("example_animatable"),
                        "example_animatable"
                )
        );
    }
}
