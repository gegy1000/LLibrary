package net.gegy1000.llibrary.tabula;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.DynamicLike;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class TabulaAnimation {
    private final TabulaIdentity identity;

    private boolean looping;

    private final List<Component> components = new ArrayList<>();

    public TabulaAnimation(TabulaIdentity identity) {
        this.identity = identity;
    }

    public TabulaIdentity getIdentity() {
        return this.identity;
    }

    public boolean isLooping() {
        return this.looping;
    }

    public List<Component> getComponents() {
        return Collections.unmodifiableList(this.components);
    }

    @Override
    public String toString() {
        return "TabulaAnimation{" + "identity='" + this.identity + "'}";
    }

    public static <T> TabulaAnimation readFrom(Dynamic<T> root, TabulaCubeContainer cubeContainer) {
        TabulaIdentity identity = TabulaIdentity.readFrom(root)
                .orElse(TabulaIdentity.create("unnamed"));

        TabulaAnimation animation = new TabulaAnimation(identity);
        animation.looping = root.get("visibility").asBoolean(animation.looping);

        Map<TabulaCube, Dynamic<T>> components = root.get("components").asMap(
                key -> key.asString()
                        .flatMap(cubeContainer::findByIdentifier)
                        .orElseThrow(() -> new JsonSyntaxException("non-string key")),
                Functions.identity()
        );

        components.forEach((targetCube, componentRoot) -> {
            animation.components.add(Component.readFrom(componentRoot, targetCube));
        });

        return animation;
    }

    public static final class Component {
        private final TabulaIdentity identity;
        private final TabulaCube target;

        private int start = 0;
        private int length = 1;

        private PropertyFrame<Vec3d> position = new PropertyFrame<>(Vec3d.ZERO, Vec3d.ZERO);
        private PropertyFrame<Vec3d> rotation = new PropertyFrame<>(Vec3d.ZERO, Vec3d.ZERO);
        private PropertyFrame<Vec3d> scale = new PropertyFrame<>(Vec3d.ZERO, Vec3d.ZERO);

        private PropertyFrame<Double> opacity = new PropertyFrame<>(0.0, 0.0);

        public Component(TabulaIdentity identity, TabulaCube target) {
            this.identity = identity;
            this.target = target;
        }

        public TabulaIdentity getIdentity() {
            return this.identity;
        }

        public TabulaCube getTarget() {
            return this.target;
        }

        public int getStart() {
            return this.start;
        }

        public int getEnd() {
            return this.start + this.length;
        }

        public int getLength() {
            return this.length;
        }

        public PropertyFrame<Vec3d> getPosition() {
            return this.position;
        }

        public PropertyFrame<Vec3d> getRotation() {
            return this.rotation;
        }

        public PropertyFrame<Vec3d> getScale() {
            return this.scale;
        }

        public PropertyFrame<Double> getOpacity() {
            return this.opacity;
        }

        public static <T> Component readFrom(Dynamic<T> root, TabulaCube target) {
            TabulaIdentity identity = TabulaIdentity.readFrom(root)
                    .orElse(TabulaIdentity.create("unnamed"));

            Component component = new Component(identity, target);

            component.start = root.get("startKey").asInt(component.start);
            component.length = root.get("length").asInt(component.length);

            PropertyFrame.readFrom(root, "pos", TabulaParsing::readVec3d)
                    .ifPresent(position -> component.position = position);

            PropertyFrame.readFrom(root, "rot", TabulaParsing::readVec3d)
                    .ifPresent(rotation -> component.rotation = rotation);

            PropertyFrame.readFrom(root, "scale", TabulaParsing::readVec3d)
                    .ifPresent(scale -> component.scale = scale);

            PropertyFrame.readFrom(root, "opacity", d -> Optional.of(d.asDouble(0.0)))
                    .ifPresent(opacity -> component.opacity = opacity);

            return component;
        }
    }

    public static final class PropertyFrame<T> {
        private final T initial;
        private final T delta;

        public PropertyFrame(T initial, T delta) {
            this.initial = initial;
            this.delta = delta;
        }

        public T getInitial() {
            return this.initial;
        }

        public T getDelta() {
            return this.delta;
        }

        public static <V, T> Optional<PropertyFrame<V>> readFrom(Dynamic<T> root, String name, Function<DynamicLike<T>, Optional<V>> parse) {
            Optional<V> initial = parse.apply(root.get(name + "Offset"));
            Optional<V> delta = parse.apply(root.get(name + "Change"));
            if (initial.isPresent() && delta.isPresent()) {
                return Optional.of(new PropertyFrame<>(initial.get(), delta.get()));
            }
            return Optional.empty();
        }
    }
}
