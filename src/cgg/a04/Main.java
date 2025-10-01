package cgg.a04;

import cgg.Image;
import cgg.Raytracer;
import cgg.Sphere;
import cgg.Transform;
import cgg.materials.NoisePhongMaterial;
import cgg.materials.PhongMaterial;
import cgg.materials.TexturedGlowingMaterial;
import cgg.materials.TexturedPhongMaterial;
import cgg.materials.*;
import cgg.PointLight;
import cgg.ILight;
import cgg.IMaterial;
import cgg.Camera;
import cgg.DirectionalLight;
import cgg.GlowingMaterial;

import tools.ImageTexture;
import tools.Mat44;
import tools.Vec3;
import tools.ISampler;

import static tools.Functions.*;
import cgg.Scene;

import java.util.ArrayList;
import java.util.List;

import com.mokiat.data.front.parser.IMTLParser;

import static tools.Color.*;

public class Main {

    public static void main(String[] args) {
        int width = 1000;
        int height = 1000;


    Mat44 rotation = rotate(Vec3.zAxis, 0);
    Mat44 move = move(vec3(0,0,0));
    Mat44 scale = scale(1,1,1);

    Transform transformation = new Transform(multiply(move, rotation, scale));
    Camera cam = new Camera(Math.PI/2, width, height, transformation);
    

    Scene scene = new Scene();
    ISampler imageTexture1 = new ImageTexture("src/textures/stoneBrick.png");
    IMaterial stone = new TexturedPhongMaterial(imageTexture1,imageTexture1, 100);

    ISampler imageTexture2 = new ImageTexture("src/textures/mmMoon.png");
    IMaterial moon = new TexturedPhongMaterial(imageTexture2,imageTexture2, 100);

    ISampler imageTexture3 = new ImageTexture("src/textures/sonne.png");
    IMaterial sonne = new TexturedPhongMaterial(imageTexture3,imageTexture3, 100);

    ISampler imageTexture4 = new ImageTexture("src/textures/schach.png");
    IMaterial schach = new TexturedPhongMaterial(imageTexture4,imageTexture4, 100);

    ISampler imageTexture5 = new ImageTexture("src/textures/wood.jpeg");
    IMaterial wood = new TexturedPhongMaterial(imageTexture5,imageTexture5, 100);

    ISampler imageTexture6 = new ImageTexture("src/textures/leaves.png");
    IMaterial leaves = new TexturedPhongMaterial(imageTexture6,imageTexture6, 100);

    ISampler imageTexture7 = new ImageTexture("src/textures/bozo.png");
    IMaterial bozo = new TexturedPhongMaterial(imageTexture7,imageTexture7, 100);

    ISampler imageTexture8 = new ImageTexture("src/textures/erde.png");
    IMaterial erde = new TexturedPhongMaterial(imageTexture8,imageTexture8, 100);

    IMaterial glow = new GlowingMaterial(black, yellow, 10, new tools.Color(0.5,0.5,0), 1);

    IMaterial glowingMat = new GlowingMaterial(yellow, red, 10, red, 0.4);
    IMaterial glowingChess = new TexturedGlowingMaterial(imageTexture4, red, 100, cyan, 0.1);


    //scene.add(new Sphere(vec3(0,0,-7), 2, mali));
    //scene.add(new Sphere(vec3(3,-0,-5), 1.5, iMaterial1));
    scene.add(new Sphere(vec3(-2,6,-10), 2, moon));
    scene.add(new Sphere(vec3(-2,-3.5,-6), 0.5, wood));
    scene.add(new Sphere(vec3(-2,-4,-6), 0.5, wood));
    scene.add(new Sphere(vec3(-2,-3,-6), 0.5, wood));
    scene.add(new Sphere(vec3(-2,-4.5,-6), 0.5, wood));

    scene.add(new Sphere(vec3(-2.5,-2.5,-5.5), 0.7, leaves));
    scene.add(new Sphere(vec3(-1.5,-2.5,-5.5), 0.7, leaves));
    scene.add(new Sphere(vec3(-2,-2.5,-6.5), 0.7, leaves));
    scene.add(new Sphere(vec3(-2,-2,-6), 0.7, leaves));

    scene.add(new Sphere(vec3(0,0,-5), 2, new TransmissiveMaterial(1.33)));


    scene.add(new Sphere(vec3(2.3,-2.5,-2), 2, stone));
    scene.add(new Sphere(vec3(1,-0.3,-1.7), 0.5, bozo));
    scene.add(new Sphere(vec3(0.8,-0.25,-1.3), 0.11, new tools.Color(1, 0.4, 0.4)));
    scene.add(new Sphere(vec3(1,-0.25,-1.2), 0.1, new tools.Color(1, 0.4, 0.4)));

    scene.add(new PointLight(vec3(0.5, 1, 0), white));


    //scene.add(new Sphere(vec3(2,0,-4), 2, urrde));
    //scene.add(new Sphere(vec3(-2,0,-6), 1.5, glowingChess));



    scene.add(new Sphere(vec3(0,-1002,0), 997, white));
    scene.add(new Sphere(vec3(10,5,-15), 0.3, glow));
    scene.add(new Sphere(vec3(15,10,-15), 0.3, glow));
    scene.add(new Sphere(vec3(10,15,-15), 0.3, glow));
    scene.add(new Sphere(vec3(-10,5,-15), 0.3, glow));
    scene.add(new Sphere(vec3(-15,10,-15), 0.3, glow));
    scene.add(new Sphere(vec3(-10,15,-15), 0.3, glow));
    scene.add(new Sphere(vec3(0,6,-15), 0.3, glow));
    scene.add(new Sphere(vec3(15,20,-30), 3, sonne));
    scene.add(new PointLight(vec3(12,17,-25), new tools.Color(30,30,0)));



    //scene.add (new PointLight(vec3(-5,5,0), new tools.Color(15,7,0)));
    //scene.add (new PointLight(vec3(5,4,-1), new tools.Color(0,15,3)));
    scene.add (new PointLight(vec3(-10,1,-1), new tools.Color(0,0,20)));
    scene.add (new PointLight(vec3(10,5,10), new tools.Color(0,10,10)));
    scene.add (new PointLight(vec3(0,20,6), new tools.Color(20,20,0)));
    scene.add (new PointLight(vec3(-10,0,-20), new tools.Color(0,20,0)));


    scene.add (new DirectionalLight(vec3(2,1,-6.5), new tools.Color(0,0.1,0.1)));


    

    var image = new Image(width, height);
    Raytracer raytracer = new Raytracer(cam,image, scene);


    
    for (int x = 0; x != width; x++)
      for (int y = 0; y != height; y++)
        // Sets the color for one particular pixel.
        image.setPixel(x, y, raytracer.getColor(vec2(x, y)));

    // Write the image to disk.
    image.writePNG("a04-textures");
    //System.out.println(scene.toString());
    System.out.println("Bild ist fertig");


    
        

    }

}
