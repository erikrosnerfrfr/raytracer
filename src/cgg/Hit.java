package cgg;

import tools.Vec3;
import tools.Vec2;

public record Hit(
    double t,
    Vec3 xTreffer,
    Vec3 nOrmalenVec,
    IMaterial iMaterial,
    Vec2 uv,
    Ray ray
) {}
