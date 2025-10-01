package cgg;
import tools.BoundingBox;
import tools.Mat44;

import static tools.Functions.EPSILON;
import static tools.Functions.identity;
import static tools.Functions.multiplyPoint;
import static tools.Functions.vec3;
import static tools.Functions.multiplyDirection;

import java.util.ArrayList;

public class Group implements IShape, IAnimatable{

    Transform transform;
    public ArrayList<IShape> shapes = new ArrayList<>();
    BoundingBox bbox = BoundingBox.empty;
    boolean dirtyBox = true;

    public Group() {
        this.transform = new Transform(identity());
        dirtyBox = true;
    }

    public Group (Mat44 localToWorld) {
        this.transform = new Transform(localToWorld);
                dirtyBox = true;

    }

    public Group (Transform transform) {
        this.transform = transform;
                dirtyBox = true;

    }

    public void addShape (IShape shape) {
        shapes.add(shape);
        dirtyBox = true;
    }



    public Hit intersect (Ray ray) {

        if(!getBounds().intersect(ray.position(), ray.direction(), ray.tmin(), ray.tmax())){
            return null;
        }

        Ray localRay = transform.rayToLocal(ray);

        Hit shortestHit = null;
        double closestT = ray.tmax();

        for (var s : shapes) {
            Hit hit = s.intersect(localRay);
            if (hit != null && hit.t() > EPSILON && hit.t() < closestT) {
                closestT = hit.t();
                shortestHit = hit;
            }
        }
        if (shortestHit != null) { // Hier muss noch was gemacht werden glaub ich
            return new Hit (
                shortestHit.t(),
                multiplyPoint(transform.localToWorld, shortestHit.xTreffer()),
                multiplyDirection(transform.localToWorldNormal, shortestHit.nOrmalenVec()),
                shortestHit.iMaterial(),
                shortestHit.uv(),
                transform.rayToWorld(shortestHit.ray())
            );
            
        } else {return null;}

    }

    public BoundingBox getBounds() {
        if(dirtyBox){
        BoundingBox localbbox = BoundingBox.empty;
        for (var s : shapes) {
            localbbox = localbbox.extend(s.getBounds());
            }
            bbox = localbbox.transform(transform.localToWorld);
            dirtyBox=false;
        }   
        return bbox;
    }

    public void setTransform(Mat44 xform) {
        this.transform = new Transform(tools.Functions.multiply(this.transform.localToWorld(),xform));
    }

}
