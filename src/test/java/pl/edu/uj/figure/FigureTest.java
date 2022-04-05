package pl.edu.uj.figure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FigureTest {

    @Test
    public void testSphere() {
        double radius = 7.0;
        Sphere sphere = new Sphere(radius);

        assertEquals(1436.76, sphere.volume(), 0.01);
        assertEquals(461.81, sphere.surface(), 0.01);
    }

    @Test
    public void testQube() {
        double length = 2.1;
        double breadth = 3.2;
        double height = 4.3;
        Cuboid cuboid = new Cuboid(length, breadth, height);

        assertEquals(28.90, cuboid.volume(), 0.01);
        assertEquals(59.02, cuboid.surface(), 0.01);
    }

    @Test
    public void testCone() {
        double radius = 2.1;
        double height = 4.3;
        Cone cone = new Cone(radius, height);

        assertEquals(19.86, cone.volume(), 0.01);
        assertEquals(45.43, cone.surface(), 0.01);
    }
}
