package cgg.a02;

import cgg.Image;
import cgg.Ray;
import cgg.Raytracer;
import cgg.Sphere;
import cgg.Transform;
import tools.Mat44;
import tools.Vec3;
import cgg.ConstantColor;
import cgg.DirectionalLight;
import cgg.PointLight;
import cgg.ILight;
import cgg.Camera;
import cgg.ColorDisc;
import cgg.ColoredDiscs;
import static tools.Functions.*;
import cgg.Scene;

import java.util.ArrayList;
import java.util.List;

import static tools.Color.*;

public class Main {

    public static void main(String[] args) {
        int width = 400;
        int height = 400;

    /*var s1 = new Sphere(vec3(0, 0, -2), 1);
    var s2 = new Sphere(vec3(0, -1, -2), 1);
    var s3 = new Sphere(vec3(0, 0, 0), 1);
    var r1 = new Ray(vec3(0, 0, 0), vec3(0, 0, -1));
    var r2 = new Ray(vec3(0, 0, 0), vec3(0, 1, -1));
    System.out.println(s1.intersect(r1));
    System.out.println(s1.intersect(r2));
    System.out.println(s2.intersect(r1));
    System.out.println(s3.intersect(r1));*/

Mat44 rotation = rotate(Vec3.zAxis, 0);
    Mat44 move = move(vec3(0,0,0));
    Mat44 scale = scale(1,1,1);

    Transform transformation = new Transform(multiply(move, rotation, scale));
    Camera cam = new Camera(Math.PI/2, width, height, transformation);
    /* 
    scene.add (new Sphere(vec3(-5,-5,-60), 13, magenta));
    scene.add (new Sphere(vec3(5,-5,-60), 13, magenta));
    scene.add (new Sphere(vec3(0,5,-60), 10, magenta));
    scene.add (new Sphere(vec3(0,10,-60), 10, magenta));
    scene.add (new Sphere(vec3(0,15,-60), 10, magenta));
    scene.add (new Sphere(vec3(0,20,-60), 10, magenta));
    scene.add (new Sphere(vec3(0,35,-60), 12, magenta));*/

    /*
    double radius = 2;
    tools.Color color = new tools.Color(0.1,0.1,0.1);
    tools.Color color2 = new tools.Color(0,0,0.1);
    int z = -3;
    for (int x = -50; x < 50; x++) {
        z = z-3;
        for (int y = 50; y > -50; y--) {
            if (x%5==0 && y %5==0) {
                scene.add(new Sphere(vec3(x,y,z), radius, color));
                color = tools.Functions.add(color,color2);
                
                //radius ++;
                }
        }
                
    }*/

    Scene scene = new Scene();
    
    
    //scene.add(new Sphere(vec3(0,0,-3), 5, green));

    double radius = 1.5;
    tools.Color color = new tools.Color(1.0,0.0,0.0);
    tools.Color color2 = new tools.Color(-0.005,0.001,0.02);
    tools.Color color3 = new tools.Color(0,0.001,0.00);
    
    for (int x = -80; x < 80; x++) {
        for (int y = 80; y > -80; y--) {
            tools.Color colorr = new tools.Color(0.8,0.0,0.0);
            for (int z = -30; z > -160; z--){
                
                colorr = tools.Functions.add(colorr,color2);
                if (x%10==0 && y %10==0 && z%10==0) {
                scene.add(new Sphere(vec3(x,y,z), radius, colorr));
                
                }
                
                //radius ++;
                }
        }//color = tools.Functions.add(color,color2);
                
    }
    

    var image = new Image(width, height);
    Raytracer raytracer = new Raytracer(cam,image, scene);


    
    for (int x = 0; x != width; x++)
      for (int y = 0; y != height; y++)
        // Sets the color for one particular pixel.
        image.setPixel(x, y, raytracer.getColor(vec2(x, y)));

    // Write the image to disk.
    image.writePNG("a02-spheres");
    //System.out.println(scene.toString());   

    }

}
