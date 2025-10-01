package cgg.materials;

import cgg.Hit;
import cgg.IMaterial;
import cgg.Ray;
import tools.Color;
import tools.ISampler;
import tools.ImprovedNoise;
import tools.Vec3;
import tools.Functions;

public record NoisePhongMaterial(ISampler diffuse, ISampler specular, double value, double shininess)implements IMaterial {

    public Color getDiffuse (Hit hit) {
        Color color = diffuse.getColor(hit.uv());
        Vec3 hitpoint = hit.xTreffer();
        double noise = ImprovedNoise.noise(hitpoint.x() * value, hitpoint.y() * value, hitpoint.z() * value);
        return Functions.add(color,Functions.multiply(color, noise));
    }

    public Color getSpecular (Hit hit) {
        return specular.getColor(hit.uv());
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
        return null;
    }

}
