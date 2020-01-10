package net.gegy1000.llibrary.tabula;

import com.mojang.datafixers.Dynamic;
import net.gegy1000.llibrary.LLibrary;
import net.gegy1000.llibrary.util.math.Vec2i;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public final class TabulaModel implements TabulaCubeContainer {
    private String name = "unnamed";
    private String author = "unknown";

    private Vec2i textureSize = new Vec2i(64, 64);

    private Vec3d globalScale = new Vec3d(1, 1, 1);

    private final List<TabulaCube> cubes = new ArrayList<>();
    private final List<TabulaCubeGroup> cubeGroups = new ArrayList<>();

    private final List<TabulaAnimation> animations = new ArrayList<>();

    public String getName() {
        return this.name;
    }

    public String getAuthor() {
        return this.author;
    }

    public Vec2i getTextureSize() {
        return this.textureSize;
    }

    public Vec3d getGlobalScale() {
        return this.globalScale;
    }

    public List<TabulaCube> getCubes() {
        return Collections.unmodifiableList(this.cubes);
    }

    @Override
    public Stream<TabulaCube> directCubes() {
        return this.cubes.stream();
    }

    @Override
    public Stream<TabulaCube> allCubes() {
        return this.cubes.stream().flatMap(TabulaCube::allCubes);
    }

    @Override
    public String toString() {
        return "TabulaModel{name='" + this.name + "'}";
    }

    public static <T> TabulaModel readFrom(Dynamic<T> root) {
        int version = root.get("projVersion").asInt(0);
        if (version != 4) {
            LLibrary.LOGGER.warn("Encountered unsupported tabula version '{}'.. attempting parse anyway", version);
        }

        TabulaModel model = new TabulaModel();

        root.get("modelName").asString()
                .ifPresent(name -> model.name = name);

        root.get("authorName").asString()
                .ifPresent(author -> model.author = author);

        model.textureSize = new Vec2i(
                root.get("textureWidth").asInt(64),
                root.get("textureHeight").asInt(64)
        );

        TabulaParsing.readVec3d(root.get("scale"))
                .ifPresent(scale -> model.globalScale = scale);

        model.cubes.addAll(root.get("cubes").asList(TabulaCube::readFrom));
        model.cubeGroups.addAll(root.get("cubeGroups").asList(TabulaCubeGroup::readFrom));

        model.animations.addAll(root.get("animations").asList(dynamic -> TabulaAnimation.readFrom(dynamic, model)));

        return model;
    }
}
