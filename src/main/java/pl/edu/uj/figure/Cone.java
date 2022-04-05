package pl.edu.uj.figure;


import static java.lang.Math.sqrt;

class Cone implements Figure3D {

    private final double radius;
    private final double height;
    private final double length;

    public Cone(double radius, double height) {
        this.radius = radius;
        this.height = height;
        this.length = sqrt(radius * radius + height * height);
    }

    @Override
    public double volume() {

        return 1.0 / 3.0 * Math.PI * radius * radius * height;
    }

    @Override
    public double surface() {

        return Math.PI * radius * (length + radius);
    }
}
