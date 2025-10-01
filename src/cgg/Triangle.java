package cgg;
import tools.*;
import static tools.Functions.*;

import cgg.materials.PhongMaterial;

public class Triangle implements IShape{

    Vertex a,b,c;
    IMaterial material;

    public Triangle(Vertex a, Vertex b, Vertex c, IMaterial material) {
        this.a=a;
        this.b=b;
        this.c=c;
        this.material = material;
    }


    public Hit intersect(Ray ray) {
        Vec3 eAB = subtract(b.position(), a.position());
        Vec3 eAC = subtract(c.position(), a.position());

        Vec3 nabc= normalize(cross(eAB,eAC));

        if (dot(nabc,ray.direction()) == 0) {
            return null;
        }
        double area = length(cross(eAB,eAC))/2;

        double denom = dot(ray.direction(), nabc);//nenner
        if (denom == 0){
            return null;
        }

        double nom = dot(subtract(a.position(),ray.position()), nabc); //Zähler
        double t = nom/denom;
        if(t < 0) {
            return null;
        }
        Vec3 p = ray.pointAt(t);

        Vec3 nbcp = normalize(cross(subtract(b.position(), p),subtract(c.position(), p)));
        Vec3 ncap = normalize(cross(subtract(c.position(), p),subtract(a.position(), p)));
        Vec3 nabp = normalize(cross(subtract(a.position(), p),subtract(b.position(), p)));



        double bcp = (length(cross(subtract(b.position(), p),subtract(c.position(), p)))/2);
        double su = dot(nabc, nbcp);
        double u = su * (bcp/area);

        if(( u > 0 || almostEqual(u, 0) )&&( u < 1 || almostEqual(u, 1))) {

        double cap = (length(cross(subtract(c.position(), p),subtract(a.position(), p)))/2);
        double sv = dot(nabc, ncap);
        double v = sv * (cap/area);


        if ((v > 0 || almostEqual(v, 0) )&&( v < 1 || almostEqual(v, 1))) {
            double w = 1-u-v;
            if ((w > 0 || almostEqual(w, 0)) &&( w < 1 || almostEqual(w, 1))) {
                Vec3 normal = interplolate(a.normal(), b.normal(), c.normal(), new Vec3(w, u, v));

                normal = normalize(normal);

                Color color = interpolate(a.color(), b.color(), c.color(), new Vec3(w, u, v));

                Vec2 uv = interplolate(a.uv(), b.uv(), c.uv(), new Vec3(w, u, v)); // wenn es auch eine Vec2-Version gibt
                uv = vec2(uv.x(), 1-uv.y());

                return new Hit(t, p, normal, material, uv, ray);
            }
            }
        }

        // double abp = (length(cross(subtract(a.position(), p),subtract(b.position(), p)))/2);
        // double sw = dot(nabc, nabp);
        // double w = sw * (abp/area);

        return null;

    }

    public BoundingBox getBounds() {

        BoundingBox bbox = BoundingBox.around(a.position(), b.position(), c.position());
        return bbox;
        
    }

}
