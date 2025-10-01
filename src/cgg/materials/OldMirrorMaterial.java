

package cgg.materials;
import cgg.Hit;
import cgg.IMaterial;
import cgg.Ray;
import tools.*;

public record OldMirrorMaterial (Color diffuse, Color specular, double shininess, double reflectVal)  implements IMaterial{

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
        return reflectVal;
    }

    public Ray getSecondarRay(Hit hit){
        return null;
    }

}
