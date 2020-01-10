package net.gegy1000.llibrary.tabula;

import com.mojang.datafixers.DynamicLike;
import net.gegy1000.llibrary.util.math.Vec2i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.List;
import java.util.Optional;

final class TabulaParsing {
    public static <T> Optional<Vec3d> readVec3d(DynamicLike<T> dynamic) {
        List<Number> components = dynamic.asList(d -> d.asNumber(0.0));
        if (components.size() == 3) {
            return Optional.of(new Vec3d(
                    components.get(0).doubleValue(),
                    components.get(1).doubleValue(),
                    components.get(2).doubleValue()
            ));
        }
        return Optional.empty();
    }

    public static <T> Optional<Vec3i> readVec3i(DynamicLike<T> dynamic) {
        List<Number> components = dynamic.asList(d -> d.asNumber(0));
        if (components.size() == 3) {
            return Optional.of(new Vec3i(
                    components.get(0).intValue(),
                    components.get(1).intValue(),
                    components.get(2).intValue()
            ));
        }
        return Optional.empty();
    }

    public static <T> Optional<Vec2i> readVec2i(DynamicLike<T> dynamic) {
        List<Number> components = dynamic.asList(d -> d.asNumber(0));
        if (components.size() == 2) {
            return Optional.of(new Vec2i(
                    components.get(0).intValue(),
                    components.get(1).intValue()
            ));
        }
        return Optional.empty();
    }
}
