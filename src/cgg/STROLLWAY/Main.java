package cgg.STROLLWAY;

import cgg.Image;
import cgg.Raytracer;
import cgg.Sphere;
import cgg.SuperSampler;
import cgg.SuperSampler.SAMPLINGTYPE;
import cgg.materials.MirrorMaterial;
import cgg.materials.NoisePhongMaterial;
import cgg.materials.PhongMaterial;
import cgg.materials.TexturedGlowingMaterial;
import cgg.materials.TexturedMirrorMaterial;
import cgg.materials.TexturedPhongMaterial;
import cgg.PointLight;
import cgg.ILight;
import cgg.IMaterial;
import cgg.Background;
import cgg.Camera;
import cgg.DirectionalLight;
import cgg.GlowingMaterial;
import textures.*;
import tools.ImageTexture;
import tools.ISampler;
import cgg.SuperSampler;
import tools.Mat44;
import tools.Vec3;
import cgg.Transform;
import cgg.Group;

import static tools.Functions.*;
import cgg.Scene;

import java.util.ArrayList;
import java.util.List;

import com.mokiat.data.front.parser.IMTLParser;

import static tools.Color.*;

public class Main {

    public static void main(String[] args) {
        int width = 1600;
        int height = 1600;

        var image = new Image(width, height);
        

    Scene scene = new Scene();
    scene.add(new Background());

    Mat44 move = move(vec3(0,0,0));
    Mat44 rotation = multiply(rotate(Vec3.zAxis, 0), rotate(Vec3.xAxis,0));
    Mat44 scale = scale(1,1,1);

    Transform transformation = new Transform(multiply(scale, rotation, move));
    Camera cam = new Camera(Math.PI/2, width, height, transformation);
    Raytracer raytracer = new Raytracer(cam,image, scene);

    scene.setRaytracer(raytracer);


    ISampler imageTexture3 = new ImageTexture("src/textures/sonne.png");
    IMaterial sonne = new TexturedPhongMaterial(imageTexture3,imageTexture3, 100);

    ISampler imageTexture4 = new ImageTexture("src/textures/schach.png");
    IMaterial schach = new TexturedPhongMaterial(imageTexture4,imageTexture4, 100);

    ISampler imageTexture5 = new ImageTexture("src/textures/MirrorStrollway.png");
    IMaterial stroll = new TexturedPhongMaterial(imageTexture5,imageTexture5, 100);

    ISampler imageTexture6 = new ImageTexture("src/textures/leaves.png");
    IMaterial leaves = new TexturedPhongMaterial(imageTexture6,imageTexture6, 100);

    ISampler imageTexture8 = new ImageTexture("src/textures/erde.png");
    IMaterial erde = new TexturedPhongMaterial(imageTexture8,imageTexture8, 100);

    IMaterial glow = new GlowingMaterial(black, yellow, 10, new tools.Color(0.5,0.5,0), 1);

    IMaterial mirror = new MirrorMaterial(white, white, 100);


    scene.add (new PointLight(vec3(-10,1,-10), new tools.Color(0,30,0)));
    scene.add (new PointLight(vec3(10,1,-10), new tools.Color(15,0,15)));
    scene.add (new PointLight(vec3(0,5,1), new tools.Color(10,10,5)));
    scene.add (new DirectionalLight(vec3(2,-1,-6.5), new tools.Color(0,0.2,0.2)));


    
    //scene.add(new Sphere(vec3(0,-1002,-5), 997, green));
    //scene.add (new Sphere(vec3(-15, 15, -20), 5, erde));
    scene.add(new Sphere(vec3(0,0,-5), 2, new TexturedMirrorMaterial(imageTexture5, imageTexture5, 100)));
    //scene.add(new Sphere(vec3(2,-2,-5), 1, new TexturedMirrorMaterial(imageTexture7, imageTexture7, 100)));

    


    var supersampler = new SuperSampler(raytracer, SAMPLINGTYPE.STRATIFIED, 8);


    
    for (int x = 0; x != width; x++)
      for (int y = 0; y != height; y++)
        // Sets the color for one particular pixel.
        image.setPixel(x, y, supersampler.getColor(vec2(x, y)));

    // Write the image to disk.
    image.writePNG("strollway");
    //System.out.println(scene.toString());
    System.out.println("Bild ist fertig");


    
        

    }

}

