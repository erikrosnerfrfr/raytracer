package cgg;

import tools.*;
import static tools.Functions.*;

import cgg.materials.PhongMaterial;

public record Sphere(Vec3 center, double radius, IMaterial iMaterial) implements IShape{

    public Sphere (Vec3 center, double radius, Color color) {
        this(center, radius, new PhongMaterial (color,Color.white, 100.0));
    }


    public Hit intersect (Ray ray) {

        

        double a = Functions.dot(ray.direction(), ray.direction()); // da Foliennr 6/7 a = d^2
        double b = 2 * Functions.dot(Functions.subtract(ray.position(), center), ray.direction() ); // da b = 2x0d
        double c = Functions.dot(Functions.subtract(ray.position(), center),Functions.subtract(ray.position(), center)) - radius * radius; // da c = x0^2-r^2

        double t1 = (-b + Math.sqrt((b*b)-(4*a*c)))/(2*a);
        double t2 = (-b - Math.sqrt((b*b)-(4*a*c)))/(2*a);

        Vec3 hitVect1 = ray.pointAt(t1);
        Vec3 hitVect2 = ray.pointAt(t2);


        Vec3 normalenVect1 = Functions.subtract(hitVect1, center);
        Vec3 normalizednormalenVect1 = Functions.divide(normalenVect1, Functions.length(normalenVect1));

        double vxt1 = Math.acos(normalizednormalenVect1.y()) / Math.PI;
        double uxt1 = (Math.atan2(normalizednormalenVect1.z(), normalizednormalenVect1.x()) + Math.PI / 2) / (2 * Math.PI);

        Vec2 uvt1 = Functions.vec2(uxt1, vxt1);
        //uvt1 = Functions.add(uvt1, multiply(uvt1, noiset1));


        Vec3 normalenVect2 = Functions.subtract(hitVect2, center);
        Vec3 normalizednormalenVect2 = Functions.divide (normalenVect2, Functions.length(normalenVect2));

        double vxt2 = Math.acos(normalizednormalenVect2.y())/Math.PI;
        double uxt2 = (Math.atan2(normalizednormalenVect2.z(),normalizednormalenVect2.x()) + (Math.PI /2))/(2*Math.PI);

        Vec2 uvt2 = Functions.vec2(uxt2,vxt2);
        //uvt2 = Functions.add(uvt2, multiply(uvt2, noiset2));

        //t1 = ImprovedNoise.noise(normalizednormalenVect1.x(), normalizednormalenVect1.y(), normalizednormalenVect1.z());
        //t2 = ImprovedNoise.noise(normalizednormalenVect2.x(), normalizednormalenVect2.y(), normalizednormalenVect2.z());


        double discriminant = (b*b) - (4*a*c);

//////////////////////

        if (discriminant < 0) {
            return null;
        }

///////////////////////

        if (discriminant == 0 && ray.isValid(t1)) {
            return new Hit (
            t1, 
            hitVect1,
            normalizednormalenVect1,
            iMaterial,
            uvt1,
            ray
            );
        }

//////////////////


        if (discriminant > 0) {
            if (t1<t2 && ray.isValid(t1)) {

                return new Hit (
                t1, 
                hitVect1,
                normalizednormalenVect1,
                iMaterial,
                uvt1,
                ray
            );
            } else if (t1>t2 && !(ray.isValid(t2))){

                return new Hit (
                t1, 
                hitVect1,
                (normalizednormalenVect1),
                iMaterial,
                uvt1,
                ray
                );}

            if (t2<t1 && ray.isValid(t2)) {
                return new Hit (t2, hitVect2, normalizednormalenVect2, iMaterial, uvt2, ray);
            } 
            
            else if (t2>t1 && !(ray.isValid(t1))) {
                return new Hit (t2, hitVect2, (normalizednormalenVect2), iMaterial, uvt2, ray);
            }
            
        } return null;



        

    }

    public BoundingBox getBounds() {
        return new BoundingBox(
            (Functions.add(center, multiply(vec3(-1,-1,1), radius))),
            (Functions.add(center, multiply(vec3(1,1,-1), radius)))

        ).transform(Mat44.identity);
    }

}
