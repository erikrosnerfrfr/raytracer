package cgg;
import static tools.Functions.*;

import tools.BoundingBox;
import tools.Mat44;
import tools.Vec3;
import tools.Vec2;

public class Plane implements IShape{

    public Vec3 anker; // Mittelpunkt
    public double p; // Formfaktor also Stern, Kugel, Viereck usw
    public Vec3 n = vec3(0,0,1); // Normalenvektor zur xy ebende
    public double radius;
    public IMaterial material;
    public BoundingBox bbox;

    public Plane(Vec3 anker, double radius, double p, IMaterial material) {
        this.anker=anker;
        this.p=p;
        this.radius=radius;
        this.material=material;
    }

    public Hit intersect (Ray ray) {

        double denom = dot(ray.direction(), n);//nenner
        if (denom == 0){
            return null;
        }

        double nom = dot(subtract(anker,ray.position()), n); //Zähler
        double t = nom/denom;
        if(t < 0) {
            return null;
        }

        Vec3 hitpoint = ray.pointAt(t);
        Vec3 distance = subtract(hitpoint, anker);
        double distancelength = length(distance);
        //double xp = Math.pow((Math.pow(hitpoint.x(),p) + Math.pow(hitpoint.y(),p)),1/p);
        //double d = Math.pow(Math.pow(Math.abs(distance.x()), p) + Math.pow(Math.abs(distance.y()), p), 1.0 / p);
        double d = Math.pow(Math.pow(Math.abs(distance.x()),p) + (Math.pow(Math.abs(distance.y()),p)),1/p);
        if (d>radius){
            return null;
        }

        Vec2 texcords = new Vec2(
            (hitpoint.x() + radius/2)/radius,
            (hitpoint.y() + radius/2)/radius
        );


        return new Hit(
            t,
            hitpoint,
            Vec3.zAxis,
            material,
            texcords,
            ray
        );
    }

    public BoundingBox getBounds() {
        return new BoundingBox(
            vec3(-radius, -radius, EPSILON),
            vec3(radius, radius, -EPSILON)
        );//.transform(Mat44.identity);
    }

}
