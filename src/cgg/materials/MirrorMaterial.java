package cgg.materials;
import static tools.Functions.dot;
import static tools.Functions.multiply;
import static tools.Functions.normalize;

import cgg.Hit;
import cgg.IMaterial;
import cgg.Ray;
import tools.*;

public record MirrorMaterial(Color diffuse, Color specular, double shininess)  implements IMaterial{

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

        Vec3 n = Functions.normalize(hit.nOrmalenVec());
        Ray ray = hit.ray();
        Vec3 rayAusfallsWinkel = normalize(Functions.subtract(ray.direction(), multiply(n, 2*dot(ray.direction(),n))));

        return new Ray(hit.xTreffer(),rayAusfallsWinkel);
    }

}
