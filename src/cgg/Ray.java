package cgg;
import static tools.Functions.EPSILON;

import tools.*;

public record Ray(Vec3 position, Vec3 direction, double tmin, double tmax) {


    public Ray (Vec3 position, Vec3 direction) {
        this(position, direction, EPSILON, Double.MAX_VALUE);
    }

    public Vec3 pointAt (double t) {
        return Functions.add(position,  Functions.multiply(direction, t)); ///richtig??
    }

    public boolean isValid(double t) {
        if (t >= tmin && t <= tmax) {
            return true;
        } else{return false;}
    }
}
