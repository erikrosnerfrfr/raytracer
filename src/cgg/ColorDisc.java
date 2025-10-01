package cgg;

import tools.*;

public class ColorDisc implements ISampler {

    private final Vec2 position;
    private final Color color;
    private final double radius;

    public ColorDisc (Vec2 position, Color color, double radius) {
        this.position = position;
        this.color = color;
        this.radius = radius;
    }
    

    public Color getColor(Vec2 point) {
        double distancex = point.x() - position.x();
        double distancey = point.y() - position.y();
        double xydistance = Math.sqrt((distancex * distancex) + (distancey*distancey));
        if (xydistance <= radius) {
            return color;
        }
        else {return Color.black;}
        
    }
    public Vec2 getPosition (ColorDisc disc) {
        return position;
    }
    

}
