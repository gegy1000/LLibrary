package net.gegy1000.llibrary.tabula;

import java.util.Optional;
import java.util.stream.Stream;

public interface TabulaCubeContainer {
    Stream<TabulaCube> directCubes();

    Stream<TabulaCube> allCubes();

    default Optional<TabulaCube> findByIdentifier(String identifier) {
        return this.allCubes()
                .filter(cube -> cube.getIdentity().getIdentifier().equals(identifier))
                .findFirst();
    }
}
