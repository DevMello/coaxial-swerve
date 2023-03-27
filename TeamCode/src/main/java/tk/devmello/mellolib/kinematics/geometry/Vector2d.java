package tk.devmello.mellolib.kinematics.geometry;

import tk.devmello.mellolib.kinematics.util.Angle;

public class Vector2d {

    public double x;
    public double y;
    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vector2d() {
        this.x = 0;
        this.y = 0;
    }

    public static Vector2d polar(double r, double theta) {
        return new Vector2d(r * Math.cos(theta), r * Math.sin(theta));
    }

    public double norm() {
        return Math.sqrt(x * x + y * y);
    }

    public double angle() {
        return Angle.normalize(Math.atan2(y, x));
    }

    public double dot(Vector2d other) {
        return x * other.x + y * other.y;
    }

    public double angleBetween(Vector2d other) {
        return Math.acos((this.dot(other)) / (this.norm() * other.norm()));
    }

     public Vector2d rotate(double theta) {
        return new Vector2d(x * Math.cos(theta) - y * Math.sin(theta), x * Math.sin(theta) + y * Math.cos(theta));
    }

    public Vector2d plus(Vector2d other) {
        return new Vector2d(x + other.x, y + other.y);
    }

    public Vector2d minus(Vector2d other) {
        return new Vector2d(x - other.x, y - other.y);
    }
    public Vector2d div(double scalar) {
        return new Vector2d(x / scalar, y / scalar);
    }
    public Vector2d times(double scalar) {
        return new Vector2d(scalar * x, scalar * y);
    }
    public Vector2d unaryMinus() {
        return new Vector2d(-x, -y);
    }
    public double dot(double x, double y) {
        return this.x * x + this.y * y;
    }
    public double distTo(Vector2d other) {
        return this.minus(other).norm();
    }
    public Vector2d projectOnto(Vector2d other) {
        return other.times(this.dot(other) / (other.dot(other)));
    }
    public Vector2d projectOntoUnit(Vector2d other) {
        return other.times(this.dot(other));
    }
    public Vector2d rotated(double angle) {
        double newX = x * Math.cos(angle) - y * Math.sin(angle);
        double newY = x * Math.sin(angle) + y * Math.cos(angle);
        return new Vector2d(newX, newY);
    }
    public boolean epsilonEquals(Vector2d other) {
        return x == other.x && y == other.y;
    }
    @Override
    public String toString() {
        return String.format("(%.3f, %.3f)", x, y);
    }

}
