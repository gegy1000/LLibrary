package net.gegy1000.llibrary.tabula;

import com.mojang.datafixers.Dynamic;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Optional;

public final class TabulaIdentity {
    private static final int IDENTIFIER_LENGTH = 20;

    private final String identifier;
    private String name;

    public TabulaIdentity(String identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    public static TabulaIdentity create(String name) {
        return new TabulaIdentity(RandomStringUtils.randomAscii(IDENTIFIER_LENGTH), name);
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + "#" + this.identifier;
    }

    @Override
    public int hashCode() {
        return this.identifier.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj instanceof TabulaIdentity) {
            return ((TabulaIdentity) obj).identifier.equals(this.identifier);
        }

        return false;
    }

    public static <T> Optional<TabulaIdentity> readFrom(Dynamic<T> root) {
        return root.get("identifier").asString().map(identifier -> {
            return new TabulaIdentity(identifier, root.get("name").asString("unnamed"));
        });
    }
}
