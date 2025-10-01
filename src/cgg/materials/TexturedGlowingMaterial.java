package cgg.materials;

import cgg.Hit;
import cgg.IMaterial;
import cgg.Ray;
import tools.Color;
import tools.Functions;
import tools.ISampler;

public record TexturedGlowingMaterial(ISampler diffuse, Color specular, double shininess, Color glow, double glowFactor) implements IMaterial{

    @Override
    public Color getEmission(Hit hit) {
        return Functions.multiply(glow, glowFactor);
    }

    @Override
    public Color getDiffuse(Hit hit) {
        return diffuse.getColor(hit.uv());
    }

    @Override
    public Color getSpecular(Hit hit) {
        return specular;

    }

    @Override
    public double getShininess(Hit hit) {
        return shininess;
    }
    public double getReflection(Hit hit){
        return 0;
    }

    public Ray getSecondarRay(Hit hit){
        return null;
    }
}
