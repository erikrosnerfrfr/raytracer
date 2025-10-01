package cgg;
import java.util.List;

import cgg.ILight.LightInfo;

import java.util.ArrayList;
import static tools.Color.*;

import static tools.Functions.EPSILON;
import static tools.Functions.multiply;
import static tools.Functions.negate;
import static tools.Functions.normalize;
import static tools.Functions.dot;
import static tools.Functions.length;


import tools.*;

public class Scene {

    public Group world = new Group();
    ArrayList<ILight> lights = new ArrayList<>();
    Raytracer raytracer;
    public ArrayList<IAnimator> animators= new ArrayList<>();

    public Vec3 r;

    
    public void add(IShape shape) {
        world.addShape(shape);
    }
    public void add (ILight light) {
        lights.add(light);
    }
    public void setRaytracer (Raytracer raytracer) {
        this.raytracer = raytracer;
    }

    public void update (double time) {
        for (var animator : animators) {
            animator.update(time);
            world.getBounds();
        }
    }






    public Hit intersect (Ray ray) {

        return world.intersect(ray);
    
    }

    public Color shade (Hit hit, Ray ray) {

        Color ambient = black;
        Color diffuse = black;
        Color specular = black;
        Color finalColor = black;
        Color glow = black;
        Color reflection = black;
        double reflect = 0;

        IMaterial iMaterial = hit.iMaterial();

        if (iMaterial.getReflection(hit) != 0) {
                reflect = iMaterial.getReflection(hit);
            }
            

        for (var light : lights) {
            LightInfo lightInfo = light.info(hit.xTreffer());
            Hit shadow = intersect (new Ray(
                hit.xTreffer(),
                negate(lightInfo.direction()),
                EPSILON,
                lightInfo.distance()
                )
            );


            if (iMaterial.getEmission(hit) != Color.black) { // für leuchtendes Material
                glow = iMaterial.getEmission(hit);
            }



            Color phong = black;  // 
            diffuse = black;
            specular = black;
            var kd = iMaterial.getDiffuse(hit);
            var ks = iMaterial.getSpecular(hit);
            var alpha = iMaterial.getShininess(hit);
            //var ka = multiply(kd, 0.2);
            

            //ambient = multiply(kd, multiply(0.2, lightInfo.intensity()));

            if (shadow == null) {

                Ray negV = hit.ray(); // zu Hit Ray hinzugefügt um Vec3 Koordinaten des Rays aufrufen zu können
                Vec3 v = Functions.subtract(ray.position(), hit.xTreffer()); // v = kamerarayposition - trefferpunkt
                v = normalize(v);
                Vec3 s = negate(normalize(lightInfo.direction()));
                Vec3 n = Functions.normalize(hit.nOrmalenVec());


                double scalarNS = Math.max(0, dot(n,s));
                //System.out.println("Skalar: " + scalarNS + "v: " + v + "s: " + s); //zum debuggen benutzt
                diffuse = Functions.add(diffuse,Functions.multiply(kd, multiply(lightInfo.intensity(),(scalarNS))));


                Vec3 d = negate(s);
                Vec3 b = multiply((dot(negate(d),(n))),n);
                r = Functions.add(d,multiply(2,b));
                r = normalize(r);

                // Formel für rayAusfallsWinkel von KI entnommen. Hatte erst versucht über r einen
                // Spiegelstrahl zu erstellen, hab dann erst gemerkt, dass ich eine andere Formel brauche,
                // die r nicht von der Lichtquelle abhängig macht
                Vec3 rayAusfallsWinkel = normalize(Functions.subtract(ray.direction(), multiply(n, 2*dot(ray.direction(),n))));
                
                double scalarVR = Math.max(0, dot(v,r));
                specular = Functions.add(specular,Functions.multiply(ks, multiply(lightInfo.intensity(),Math.pow(scalarVR, alpha))));
                phong = Functions.add(diffuse, specular);

                if (reflect>0) {
                
                Ray reflectionRay = new Ray(hit.xTreffer(), rayAusfallsWinkel);
                finalColor=Functions.divide(Functions.add(finalColor,raytracer.raytrace(reflectionRay)),2); 
                }


            }
            finalColor = Functions.add(finalColor, phong, glow, reflection);
        }
        
        return finalColor;
    }


}
