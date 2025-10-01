package cgg.a03;

import cgg.Image;
import cgg.Raytracer;
import cgg.Sphere;
import cgg.Transform;
import cgg.materials.PhongMaterial;
import cgg.PointLight;
import cgg.ILight;
import cgg.Camera;
import cgg.DirectionalLight;
import tools.ImageTexture;
import tools.Mat44;
import tools.Vec3;

import static tools.Functions.*;
import cgg.Scene;

import java.util.ArrayList;
import java.util.List;

import static tools.Color.*;

public class Main {

    public static void main(String[] args) {
        int width = 1000;
        int height = 1000;


    

    Scene scene = new Scene();
    //ImageTexture imageTexture = new ImageTexture(args[0]);

    

    scene.add(new PointLight(vec3(5,2,0), new tools.Color(20.0,0,0)));
    scene.add(new PointLight(vec3(-5,2,0), new tools.Color(0.0,20,0)));
    scene.add(new PointLight(vec3(-10,7,-7), new tools.Color(0,0,30.0)));
    scene.add(new PointLight(vec3(0,4.9,-6), new tools.Color(15.0,15.0,15.0)));
    scene.add(new DirectionalLight(vec3(1,0,1), new tools.Color(0.1,0.0,0.0)));

    //scene.add(new DirectionalLight(vec3(1,-1,-1), new tools.Color(0.2,0.2,0.2)));


    //scene.add(new DirectionalLight(vec3(-1,0,0), red));
    
   // scene.add(new Sphere(vec3(0,0,-6), 2, new PhongMaterial(gray, red, 5)));

    scene.add(new Sphere(vec3(0,6,-6), 1, white));

    scene.add(new Sphere(vec3(3,-2,-2), 2.5, black));
    scene.add(new Sphere(vec3(-2.5,-2.5,-2), 1.5, black));

    scene.add(new Sphere(vec3(-6,-3,-12), 1, red));
    scene.add(new Sphere(vec3(-6,-2,-12), 1, white));
    scene.add(new Sphere(vec3(-6,-1,-12), 1, red));
    scene.add(new Sphere(vec3(-6,0,-12), 1, white));
    scene.add(new Sphere(vec3(-6,1,-12), 1, red));
    scene.add(new Sphere(vec3(-6,2,-12), 1, white));
    scene.add(new Sphere(vec3(-6,3,-12), 1, red));
    scene.add(new Sphere(vec3(-6,4,-12), 1, white));
    scene.add(new Sphere(vec3(-6,5,-12), 1, red));

    scene.add(new Sphere(vec3(-6,5,-12), 1, white));
    scene.add(new Sphere(vec3(-6,5,-11), 1, red));
    scene.add(new Sphere(vec3(-6,5,-10), 1, white));
    scene.add(new Sphere(vec3(-6,5,-9), 1, red));
    scene.add(new Sphere(vec3(-6,5,-8), 1, white));
    scene.add(new Sphere(vec3(-6,5,-7), 1, red));
    scene.add(new Sphere(vec3(-6,5,-6), 1, white));
    scene.add(new Sphere(vec3(-6,5,-5), 1, red));




    scene.add(new Sphere(vec3(0,2,-9), 2, blue));
    scene.add(new Sphere(vec3(0,-1,-9), 0.5, blue));

    scene.add(new Sphere(vec3(3,5,-8), 2, magenta));
    scene.add(new Sphere(vec3(3,2,-8), 0.5, magenta));

    scene.add(new Sphere(vec3(-1,-1,-6), 1, cyan));
    scene.add(new Sphere(vec3(-1,-4,-6), 0.5, cyan));

    scene.add(new Sphere(vec3(-0.75,-2,-4), 0.4, green));




    scene.add(new Sphere(vec3(0,-1000,0), 997, white));
    scene.add(new Sphere(vec3(1005,0,0), 997, black));
    

    Mat44 rotation = rotate(Vec3.zAxis, 0);
    Mat44 move = move(vec3(0,0,0));
    Mat44 scale = scale(1,1,1);

    Transform transformation = new Transform(multiply(move, rotation, scale));
    Camera cam = new Camera(Math.PI/2, width, height, transformation);

    var image = new Image(width, height);
    Raytracer raytracer = new Raytracer(cam,image, scene);


    
    for (int x = 0; x != width; x++)
      for (int y = 0; y != height; y++)
        // Sets the color for one particular pixel.
        image.setPixel(x, y, raytracer.getColor(vec2(x, y)));

    // Write the image to disk.
    image.writePNG("a03-phong");
    //System.out.println(scene.toString());
    System.out.println("Bild ist fertig");


    
        

    }

}
