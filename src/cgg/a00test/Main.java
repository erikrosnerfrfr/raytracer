package cgg.a00test;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import cgg.Image;
import cgg.Raytracer;
import cgg.Sphere;
import cgg.Transform;
import cgg.materials.PhongMaterial;
import cgg.PointLight;
import cgg.ILight;
import cgg.Camera;
import cgg.DirectionalLight;
import tools.Color;
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
        int width = 500;
        int height = 500;


    

    Scene scene = new Scene();
    //ImageTexture imageTexture = new ImageTexture(args[0]);

    

    scene.add(new PointLight(vec3(5,2,0), new tools.Color(20.0,0,0)));
    scene.add(new PointLight(vec3(-5,2,0), new tools.Color(0.0,20,0)));
    scene.add(new PointLight(vec3(-10,7,-7), new tools.Color(0,0,30.0)));
    scene.add(new PointLight(vec3(0,4.9,-6), new tools.Color(15.0,15.0,15.0)));
    scene.add(new DirectionalLight(vec3(1,0,1), new tools.Color(0.1,0.0,0.0)));

    scene.add(new Sphere(vec3(0,6,-6), 1, white));




    scene.add(new Sphere(vec3(0,2,-9), 2, blue));
    scene.add(new Sphere(vec3(0,-1,-9), 0.5, blue));

    scene.add(new Sphere(vec3(3,5,-8), 2, magenta));
    scene.add(new Sphere(vec3(3,2,-8), 0.5, magenta));

    scene.add(new Sphere(vec3(-1,-1,-6), 1, cyan));
    scene.add(new Sphere(vec3(-1,-4,-6), 0.5, cyan));

    scene.add(new Sphere(vec3(-0.75,-2,-4), 0.4, green));




    scene.add(new Sphere(vec3(0,-1000,0), 997, white));
    scene.add(new Sphere(vec3(1005,0,0), 997, black));

    Mat44 currentTransform = Mat44.identity;
Scanner test = new Scanner(System.in);
String input;

while (!(input = test.nextLine()).equals("fertig")) {
    Mat44 movement = Mat44.identity;

    switch (input) {
        case "vorne":
            movement = move(vec3(0,0,-1));
            break;
        case "hinten":
            movement = move(vec3(0,0,1));
            break;
        case "links":
            movement = rotate(Vec3.yAxis, 45);
            break;
        case "rechts":
            movement = rotate(Vec3.yAxis, -45);
            break;
    }

    currentTransform = multiply(movement, currentTransform);

    Camera cam = new Camera(Math.PI/2, width, height, new Transform(currentTransform));
    Image image = new Image(width, height);
    Raytracer raytracer = new Raytracer(cam, image, scene);

    int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Color>> pixels = new ArrayList<>(width*height);
    
    for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int fx = x;
                final int fy = y;
                pixels.add(executor.submit(() -> raytracer.getColor(vec2(fx, fy))));
            }
        }

        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                try {
                    image.setPixel(x, y, pixels.get(i++).get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        executor.shutdown();
    
    executor.shutdown();
    try {
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    image.writePNG("scanner_test.png");
}
        System.out.println("Bewegungsrichtung angeben oder fertig eintippen");
        input = test.nextLine();

    }
    

    
        

    }





