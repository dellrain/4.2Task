package com.cgvsu.math;

// Это заготовка для собственной библиотеки для работы с линейной алгеброй
public class Vector3f {

    float x, y, z;
    float w = 1;

    public Vector3f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getW() {
        return w;
    }


    public static Vector3f sumVector(Vector3f v1, Vector3f v2) {
        return new Vector3f(
                v1.getX() + v2.getX(),
                v1.getY() + v2.getY(),
                v1.getZ() + v2.getZ()
        );
    }

    public float getLength() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public Vector3f normalize() {
        final float LENGTH = this.getLength();

        this.x /= LENGTH;
        this.y /= LENGTH;
        this.z /= LENGTH;

        return this;
    }


    public boolean equals(Vector3f other) {
        // todo: желательно, чтобы это была глобальная константа
        final float eps = 1e-7f;
        return Math.abs(x - other.x) < eps && Math.abs(y - other.y) < eps && Math.abs(z - other.z) < eps;
    }


}
