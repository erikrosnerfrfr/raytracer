package cgg.materials;
import static tools.Color.black;
import static tools.Functions.*;

import cgg.Hit;
import cgg.IMaterial;
import cgg.Ray;
import tools.*;

public record TexturedTransmissiveMaterial(ISampler diffuse, ISampler specular, double shininess, double n)  implements IMaterial{

    public Color getDiffuse (Hit hit) {
        return diffuse.getColor(hit.uv());
    }

    public Color getSpecular (Hit hit) {
        return specular.getColor(hit.uv());
    }

    public double getShininess (Hit hit) {
        return shininess;
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

}
