package pl.edu.uj.figure;

class Cuboid implements Figure3D {

    private final double length;
    private final double breadth;
    private final double height;

    public Cuboid(double length, double breadth, double height) {
        this.length = length;
        this.breadth = breadth;
        this.height = height;
    }

    @Override
    public double volume() {

        return length * breadth * height;
    }

    @Override
    public double surface() {

        return 2.0 * (length * breadth + breadth * height + length * height);
    }
}

