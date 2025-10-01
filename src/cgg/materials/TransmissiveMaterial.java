package cgg.materials;
import static tools.Color.black;
import static tools.Functions.*;

import cgg.Hit;
import cgg.IMaterial;
import cgg.Ray;
import cgg.Sphere;
import tools.*;

public record TransmissiveMaterial(double n)  implements IMaterial{

    public Color getDiffuse (Hit hit) {
        return Color.white;
    }

    public Color getSpecular (Hit hit) {
        return Color.white;
    }

    public double getShininess (Hit hit) {
        return 0;
    }
    public Color getEmission (Hit hit) {
        return black;
    }
    public double getReflection(Hit hit){
        return 0;
    }

    public Ray getSecondarRay(Hit hit){

        double n1 = 1;
        double n2 = n;

        Vec3 incident = hit.ray().direction();
        Vec3 normal = hit.nOrmalenVec();

        if (dot(hit.nOrmalenVec(), incident) > 0) {
            normal = Functions.negate(hit.nOrmalenVec());
            double tmp = 0;
            tmp = n1;
            n1 = n2;
            n2 = tmp;
        }


        Vec3 secondaryOrigin = hit.ray().pointAt(hit.t()+EPSILON);
        Vec3 secondaryDirection = vec3(0,0,0);

        if (refract(incident, normal, n1, n2) != null) {

        if(Math.random() > schlick(incident, normal, n1, n2)) {
            secondaryDirection = refract(incident, normal, n1, n2);
        }
        else { secondaryDirection = reflect(incident, normal);}
    }
    else { secondaryDirection = reflect(incident, normal);}

        return new Ray(secondaryOrigin, normalize(secondaryDirection));
        
    
    }

    public static Vec3 refract (Vec3 incident, Vec3 normal, double n1, double n2) {
        double r = n1/n2;
        double c = -dot(normal, incident);
        double discriminant = 1 - r*r * (1 - c*c);

        if (discriminant < 0) {
            return reflect(incident, normal);
        }

        Vec3 dt = normalize(add(multiply(r,incident), multiply((r*c - Math.sqrt(discriminant)), normal)));

        return dt;
    }



    public static Vec3  reflect(Vec3 incident, Vec3 normal){

        Vec3 rayAusfallsWinkel = normalize(Functions.subtract(incident, multiply(normal, 2*dot(incident,normal))));

        return rayAusfallsWinkel;
    }



    public static double schlick (Vec3 incident, Vec3 normal, double n1, double n2) {
        double r0 = Math.pow(((n1-n2)/(n1+n2)),2);

        double rWinkel = r0 + (1-r0)*Math.pow((1 + dot(normal,incident)),5); // evtl 1 - statt 1+? beide vec3s nochmal angucken, formel ist 1-cos(einfall)
        return rWinkel;
    }

    public static void transmissionFunctionTests() {
        Sphere sphere = new Sphere(vec3(0,0,-2), 1, new TransmissiveMaterial(1.33));
        Ray ray1 = new Ray(vec3(0,0,0), vec3(0,0,-1), 0, Double.MAX_VALUE);
        Hit hit1 = sphere.intersect(ray1);
        Ray ray2 = new Ray(vec3(0,0,-2), vec3(0,0,-1), 0, Double.MAX_VALUE);
        Hit hit2 = sphere.intersect(ray2);
        Ray ray3 = new Ray(vec3(0,0,-4), vec3(0,0,-1), 0, Double.MAX_VALUE);
        Hit hit3 = sphere.intersect(ray3);
        System.out.println("hit 1: point " + hit1.xTreffer() + ", normal " + hit1.nOrmalenVec());
        System.out.println("hit 2: point " + hit2.xTreffer() + ", normal " + hit2.nOrmalenVec());
        System.out.println("hit 3: " + hit3);
        System.out.println("reflect()");
        System.out.println("..." + reflect( vec3(0.000, 0.000, 0.000), vec3(0.000, 1.000, 0.000)) + " (0.000, 0.000, 0.000)");
        System.out.println("..." + reflect( vec3(0.707, -0.707, 0.000), vec3(0.000, 1.000, 0.000)) + " (0.707, 0.707, 0.000)");
        System.out.println("..." + reflect( vec3(0.707, 0.707, 0.000), vec3(0.000, 1.000, 0.000)) + " (0.707, -0.707, 0.000)");
        System.out.println("schlick()");
        System.out.println("..." + schlick( vec3(0.707, 0.707, 0.000), vec3(0.000, 1.000, 0.000), 1 , 1.5) + " 13.9579");
        System.out.println("..." + schlick( vec3(0.707, 0.707, 0.000), vec3(0.000, 1.000, 0.000), 1.5 , 1 ) + " 13.9579");
        System.out.println("..." + schlick( vec3(0.995, -0.100, 0.000), vec3(0.000, 1.000, 0.000), 1 , 1.5) + " 0.608435");
        System.out.println("..." + schlick( vec3(0.995, -0.100, 0.000), vec3(0.000, 1.000, 0.000), 1.5 , 1 ) + " 0.608435");
        System.out.println("refract()");
        System.out.println("..." + refract( vec3(0.707, 0.707, 0.000), vec3(0.000, 1.000, 0.000), 1 , 1.5) + " (0.471, -0.882, 0.000))");
        System.out.println("..." + refract( vec3(0.707, 0.707, 0.000), vec3(0.000, 1.000, 0.000), 1.5 , 1 ) + " - s");
        System.out.println("..." + refract( vec3(0.995, -0.100, 0.000), vec3(0.000, 1.000, 0.000), 1 , 1.5) + " (0.663, -0.748, 0.000))");
        System.out.println("..." + refract( vec3(0.995, -0.100, 0.000), vec3(0.000, 1.000, 0.000), 1.5 , 1 ) + " - ");
        System.out.println("..." + refract( vec3(0.100, -0.995, 0.000), vec3(0.000, 1.000, 0.000), 1 , 1.5) + " (0.066, -0.998, 0.000))");
        System.out.println("..." + refract( vec3(0.100, -0.995, 0.000), vec3(0.000, 1.000, 0.000), 1.5 , 1 ) + " (0.149, -0.989, 0.000)");
    }

}
