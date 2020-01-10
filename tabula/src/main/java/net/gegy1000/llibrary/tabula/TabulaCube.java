package net.gegy1000.llibrary.tabula;

import com.mojang.datafixers.Dynamic;
import net.gegy1000.llibrary.util.math.Vec2i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public final class TabulaCube implements TabulaCubeContainer {
    private final TabulaIdentity identity;

    private Transform transform = new Transform();
    private Texture texture = new Texture();
    private Shape shape = new Shape();
    private Visibility visibility = new Visibility();

    private final List<TabulaCube> children = new ArrayList<>();

    public TabulaCube(TabulaIdentity identity) {
        this.identity = identity;
    }

    public TabulaIdentity getIdentity() {
        return this.identity;
    }

    public Transform getTransform() {
        return this.transform;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public Shape getShape() {
        return this.shape;
    }

    public Visibility getVisibility() {
        return this.visibility;
    }

    public List<TabulaCube> getChildren() {
        return Collections.unmodifiableList(this.children);
    }

    public static <T> TabulaCube readFrom(Dynamic<T> root) {
        TabulaIdentity identity = TabulaIdentity.readFrom(root)
                .orElse(TabulaIdentity.create("unnamed"));

        TabulaCube cube = new TabulaCube(identity);
        cube.transform = Transform.readFrom(root);
        cube.shape = Shape.readFrom(root);
        cube.texture = Texture.readFrom(root);
        cube.visibility = Visibility.readFrom(root);

        cube.children.addAll(root.get("children").asList(TabulaCube::readFrom));

        return cube;
    }

    @Override
    public Stream<TabulaCube> directCubes() {
        return this.children.stream();
    }

    @Override
    public Stream<TabulaCube> allCubes() {
        return this.children.stream().flatMap(TabulaCube::allCubes);
    }

    @Override
    public String toString() {
        return "TabulaCube{" + "identity='" + this.identity + "'}";
    }

    public static final class Transform {
        private Vec3d position = Vec3d.ZERO;
        private Vec3d offset = Vec3d.ZERO;
        private Vec3d rotation = Vec3d.ZERO;
        private Vec3d scale = new Vec3d(1, 1, 1);

        public Vec3d getPosition() {
            return this.position;
        }

        public Vec3d getOffset() {
            return this.offset;
        }

        public Vec3d getRotation() {
            return this.rotation;
        }

        public Vec3d getScale() {
            return this.scale;
        }

        public static <T> Transform readFrom(Dynamic<T> root) {
            Transform transform = new Transform();

            TabulaParsing.readVec3d(root.get("position"))
                    .ifPresent(position -> transform.position = position);

            TabulaParsing.readVec3d(root.get("offset"))
                    .ifPresent(offset -> transform.offset = offset);

            TabulaParsing.readVec3d(root.get("rotation"))
                    .ifPresent(rotation -> transform.rotation = rotation);

            TabulaParsing.readVec3d(root.get("scale"))
                    .ifPresent(scale -> transform.scale = scale);

            return transform;
        }
    }

    public static final class Shape {
        private Vec3i dimensions = new Vec3i(1, 1, 1);
        private double cubeGrow;

        public Vec3i getDimensions() {
            return this.dimensions;
        }

        public double getCubeGrow() {
            return this.cubeGrow;
        }

        public static <T> Shape readFrom(Dynamic<T> root) {
            Shape shape = new Shape();

            TabulaParsing.readVec3i(root.get("dimensions"))
                    .ifPresent(dimensions -> shape.dimensions = dimensions);

            shape.cubeGrow = root.get("mcScale").asDouble(shape.cubeGrow);

            return shape;
        }
    }

    public static final class Texture {
        private Vec2i offset;
        private boolean mirrored;

        public Vec2i getOffset() {
            return this.offset;
        }

        public boolean isMirrored() {
            return this.mirrored;
        }

        public static <T> Texture readFrom(Dynamic<T> root) {
            Texture texture = new Texture();

            TabulaParsing.readVec2i(root.get("txOffset"))
                    .ifPresent(offset -> texture.offset = offset);

            texture.mirrored = root.get("mirrored").asBoolean(texture.mirrored);

            return texture;
        }
    }

    public static final class Visibility {
        private double opacity = 100.0;
        private boolean hidden;

        public double getOpacity() {
            return this.opacity;
        }

        public boolean isHidden() {
            return this.hidden;
        }

        public static <T> Visibility readFrom(Dynamic<T> root) {
            Visibility visibility = new Visibility();

            visibility.opacity = root.get("opacity").asDouble(visibility.opacity);
            visibility.hidden = root.get("hidden").asBoolean(visibility.hidden);

            return visibility;
        }
    }
}
