package net.gegy1000.llibrary.tabula;

import com.google.common.collect.Streams;
import com.mojang.datafixers.Dynamic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public final class TabulaCubeGroup implements TabulaCubeContainer {
    private final TabulaIdentity identity;

    private Visibility visibility = new Visibility();

    private final List<TabulaCube> cubes = new ArrayList<>();
    private final List<TabulaCubeGroup> cubeGroups = new ArrayList<>();

    public TabulaCubeGroup(TabulaIdentity identity) {
        this.identity = identity;
    }

    public TabulaIdentity getIdentity() {
        return this.identity;
    }

    public Visibility getVisibility() {
        return this.visibility;
    }

    public List<TabulaCube> getCubes() {
        return Collections.unmodifiableList(this.cubes);
    }

    public List<TabulaCubeGroup> getCubeGroups() {
        return Collections.unmodifiableList(this.cubeGroups);
    }

    public static <T> TabulaCubeGroup readFrom(Dynamic<T> root) {
        TabulaIdentity identity = TabulaIdentity.readFrom(root)
                .orElse(TabulaIdentity.create("unnamed"));

        TabulaCubeGroup group = new TabulaCubeGroup(identity);
        group.visibility = Visibility.readFrom(root);

        group.cubes.addAll(root.get("cubes").asList(TabulaCube::readFrom));
        group.cubeGroups.addAll(root.get("cubeGroups").asList(TabulaCubeGroup::readFrom));

        return group;
    }

    @Override
    public Stream<TabulaCube> directCubes() {
        return this.cubes.stream();
    }

    @Override
    public Stream<TabulaCube> allCubes() {
        return Streams.concat(
                this.cubes.stream().flatMap(TabulaCube::allCubes),
                this.cubeGroups.stream().flatMap(TabulaCubeGroup::allCubes)
        );
    }

    @Override
    public String toString() {
        return "TabulaCubeGroup{" + "identity='" + this.identity + "'}";
    }

    public static class Visibility {
        private boolean hidden;

        public boolean isHidden() {
            return this.hidden;
        }

        public static <T> Visibility readFrom(Dynamic<T> root) {
            Visibility visibility = new Visibility();
            visibility.hidden = root.get("hidden").asBoolean(visibility.hidden);

            return visibility;
        }
    }
}
