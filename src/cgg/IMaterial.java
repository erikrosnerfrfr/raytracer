package cgg;
import tools.*;

public interface IMaterial {
    Color getDiffuse(Hit hit);
    Color getSpecular(Hit hit);
    double getShininess(Hit hit);
    Color getEmission(Hit hit);
    Ray getSecondarRay(Hit hit);
    double getReflection(Hit hit);
}
