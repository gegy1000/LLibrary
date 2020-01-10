package net.gegy1000.llibrary.util.math;

public final class Vec2i implements Comparable<Vec2i> {
    public final int x;
    public final int y;

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj instanceof Vec2i) {
            Vec2i vec = (Vec2i) obj;
            return this.x == vec.x && this.y == vec.y;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.y * 31 + this.x;
    }

    @Override
    public int compareTo(Vec2i vec) {
        return this.y == vec.y ? this.x - vec.x : this.y - vec.y;
    }

    @Override
    public String toString() {
        return "Vec2i{x=" + this.x + ", y=" + this.y + "}";
    }
}
