package cgg;
import tools.*;
import static tools.Functions.*;

public record LinearRotator(
    IAnimatable iAnimatable, Vec3 axis, double startAngle, double angularVelocity
) implements IAnimator {

    public void update(double time) {
        iAnimatable.setTransform(
            rotate(axis, startAngle + time * angularVelocity)
        );
    }
}
