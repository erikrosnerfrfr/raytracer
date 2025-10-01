package cgg;

import cgg.Hit;
import cgg.IMaterial;
import tools.Color;
import tools.Functions;
import tools.ISampler;
import tools.Vec3;
import static tools.Functions.*;

public class SkyMaterial implements IMaterial {

    public ISampler emission;

    public SkyMaterial(ISampler emission) {
        this.emission = emission;
    }

    public Color getEmission(Hit hit){

        return emission.getColor(hit.uv());
        
    }

    public Color getDiffuse(Hit hit) {
        return null;
    }

    @Override
    public Color getSpecular(Hit hit) {
        return null;
    }

    @Override
    public double getShininess(Hit hit) {
        return 0;
    }

    public double getReflection(Hit hit){
        return 0;
    }

    public Ray getSecondarRay(Hit hit){
        return null;
    }




}