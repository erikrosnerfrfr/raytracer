package cgg;
import static tools.Functions.invert;
import static tools.Functions.transpose;
import tools.Vec3;
import static tools.Functions.multiplyDirection;
import static tools.Functions.multiplyPoint;

import tools.Mat44;

public class Transform {

    Mat44 localToWorld;
    Mat44 worldToLocal;
    Mat44 localToWorldNormal;

    public Transform (Mat44 transform) {
        this.localToWorld = transform;
        this.worldToLocal = invert(transform);
        this.localToWorldNormal = transpose(this.worldToLocal);
    }

    public Mat44 localToWorld() {
        return localToWorld;
    }

    public Mat44 localToWorldNormal(){
        return localToWorldNormal;
    }

    public Ray rayToLocal (Ray ray) {

        Vec3 originToLocal = multiplyPoint(worldToLocal, ray.position());
        Vec3 directionToLocal = multiplyDirection(worldToLocal, ray.direction());
        return new Ray (originToLocal, directionToLocal);
        
    }

    public Ray rayToWorld (Ray ray) {

        Vec3 originToWorld = multiplyPoint(localToWorld, ray.position());
        Vec3 directionToWorld = multiplyDirection(localToWorld, ray.direction());
        return new Ray (originToWorld, directionToWorld);
        
    }

}
