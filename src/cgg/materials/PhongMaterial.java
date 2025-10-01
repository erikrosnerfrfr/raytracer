package cgg.materials;
import static tools.Functions.EPSILON;

import cgg.Hit;
import cgg.IMaterial;
import cgg.Ray;
import tools.*;

public record PhongMaterial(Color diffuse, Color specular, double shininess)  implements IMaterial{

    public Color getDiffuse (Hit hit) {
        return diffuse;
    }

    public Color getSpecular (Hit hit) {
        return specular;
    }

    public double getShininess (Hit hit) {
        return shininess;
    }
    public Color getEmission (Hit hit) {
        return Color.black;
    }
    public double getReflection(Hit hit){
        return 0;
    }

    public Ray getSecondarRay(Hit hit){
        double distance, x, y, z;
        distance = 3;
        Vec3 r = new Vec3(0,0,0);
        while(distance < -1 || distance > 1){
        x = 2 * Math.random() - 1;
        y = 2 * Math.random() - 1;
        z = 2 * Math.random() - 1;
        distance = Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2);
        r = new Vec3(x,y,z);
        }
        Vec3 dr = Functions.normalize(Functions.add(r, hit.nOrmalenVec()));
        Ray random = new Ray(hit.xTreffer(), dr);
        Vec3 epsilonxTreffer = random.pointAt(EPSILON);

        return new Ray(epsilonxTreffer, dr);
        
    
    }

}
