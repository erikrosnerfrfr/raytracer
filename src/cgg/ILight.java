package cgg;
import tools.*;

public interface ILight {

    public record LightInfo(Vec3 direction, Color intensity, double distance) {
    }
    LightInfo info (Vec3 position);

}
