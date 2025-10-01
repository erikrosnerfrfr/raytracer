package cgg;

import static tools.Functions.multiplyDirection;
import static tools.Functions.multiplyPoint;
import static tools.Functions.normalize;

import tools.*;

public class Camera {

    private double alpha;
    private double width;
    private double height;
    private Transform transform;



    public Camera (double alpha, double width, double height, Transform transform) {
        this.alpha = alpha;
        this.width = width;
        this.height = height;
        this.transform = transform;
    }


    public Ray generateRay (Vec2 pixel) {
        //Vec2 Bildmitte = Functions.vec2(width/2, height/2);
        //functions Zeile 280
        double u = pixel.u();
        double v = pixel.v();

        double x = u - (width/2);
        double y = -(v - (height/2));
        double z = -((width/2)/(Math.tan(alpha/2)));

        Vec3 origin = Functions.vec3(0.0,0.0,0.0);
        Vec3 direction = Functions.vec3(x, y, z);

        direction = Functions.divide (direction, Functions.length(direction));

        origin = multiplyPoint(transform.localToWorld, origin);
        direction = multiplyDirection(transform.localToWorld, direction);

        Ray ray = new Ray (origin, direction);

        return ray;
        

    }


}
