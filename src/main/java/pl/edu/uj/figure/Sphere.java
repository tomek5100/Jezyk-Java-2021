package pl.edu.uj.figure;


class Sphere implements Figure3D {

    private final double radius;

    public Sphere(double radius) {
        this.radius = radius;

    }

    @Override
    public double volume() {

        return 4.0 / 3.0 * Math.PI * radius * radius * radius;
    }

    @Override
    public double surface() {

        return 3.0 * Math.PI * radius * radius;
    }
}
