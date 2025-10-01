package cgg;

import tools.ImageTexture;
import tools.Mat44;

import static tools.Functions.*;

import tools.BoundingBox;
import tools.Functions;
import tools.Vec2;

public class Background implements IShape {

    IMaterial material = new SkyMaterial (new ImageTexture("src/textures/nightSky.png"));

    public IMaterial getMaterial(){
        return material;
    }

    public Hit intersect(Ray ray) {
        if (ray.tmax()>=Double.MAX_VALUE) {

            double u, v;

            v = Math.acos(ray.direction().y()) / Math.PI;
            u = (Math.atan2(ray.direction().z(), ray.direction().x()) + Math.PI) / (2 * Math.PI);

            Vec2 uv = vec2(u,v);

            return new Hit(
                Double.MAX_VALUE,
                ray.pointAt(Double.MAX_VALUE),
                negate(ray.direction()),
                material,
                uv,
                ray
            );

            

        } else {return null;}
    }


    public BoundingBox getBounds() {
        return new BoundingBox(
            (vec3(-Double.MAX_VALUE,-Double.MAX_VALUE,Double.MAX_VALUE)),
            (vec3(Double.MAX_VALUE,Double.MAX_VALUE, -Double.MAX_VALUE))

        ).transform(Mat44.identity);
    }


}
