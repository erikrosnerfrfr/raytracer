package cgg;

import tools.Color;
import tools.Functions;

public record GlowingMaterial(Color diffuse, Color specular, double shininess, Color glow, double glowFactor) implements IMaterial{

    @Override
    public Color getEmission(Hit hit) {
        return Functions.multiply(glow, glowFactor);
    }

    @Override
    public Color getDiffuse(Hit hit) {
        return diffuse;
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
