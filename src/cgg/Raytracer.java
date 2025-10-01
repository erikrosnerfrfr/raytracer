package cgg;

import tools.*;

public class Raytracer implements ISampler {

    private Scene scene;
    private Camera camera;
    private Image image;
    private double width;
    private double height;
    private Background bg;

    public Raytracer (Camera camera, Image image, Scene scene) {
        this.scene = scene;
        this.camera = camera;
        this.image = image;
        bg = new Background();
    }

    

    public Color getColor (Vec2 pixel) {
        Ray ray = camera.generateRay(pixel);

        return raytrace (ray);

    }

    

    Color raytrace (Ray ray) {

        return pathtrace(ray, 9);
        }

    public Color pathtrace(Ray ray, int depth) {

        if (ray == null|| depth == 0) {
            return Color.black;
        }
        Hit hit = scene.intersect(ray);

        if (hit == null) {
            return bg.material.getEmission(bg.intersect(ray));
        }

        Color direct = scene.shade(hit, ray);

        Color globale = tools.Functions.multiply(hit.iMaterial().getDiffuse(hit), 
                        pathtrace(hit.iMaterial().getSecondarRay(hit), depth-1));

        return tools.Functions.add(hit.iMaterial().getEmission(hit), direct, globale);

    }
        

}
