package cgg;
import tools.Vec3;

public record LinearMover(
    IAnimatable iAnimatable, Vec3 startPos, Vec3 dir
) implements IAnimator{

    public void update (double time) {
        iAnimatable.setTransform(tools.Functions.move(tools.Functions.add(startPos, tools.Functions.multiply(dir, time))));
    }

}
