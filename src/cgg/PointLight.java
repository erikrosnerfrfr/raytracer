package cgg;
import tools.*;

public record PointLight(Vec3 lightposition, Color intensity) implements ILight{

    public LightInfo info (Vec3 hitposition) {
        Vec3 direction = Functions.subtract(hitposition, lightposition);
        double distance = Functions.length(direction);
        Vec3 normalizedDirection = Functions.normalize(direction);
        Color rangedIntensity = Functions.divide(intensity, (Math.pow(distance,2)));
        return new LightInfo(normalizedDirection, rangedIntensity, distance);
    }

}
