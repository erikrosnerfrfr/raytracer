package cgg;
import tools.*;
import static tools.Functions.*;

public record DirectionalLight(Vec3 direction, Color intensity) implements ILight{
    public DirectionalLight {
        direction = normalize(direction);
    }

    public LightInfo info (Vec3 position) {
        return new LightInfo(direction, intensity, Double.MAX_VALUE);
    }

}
