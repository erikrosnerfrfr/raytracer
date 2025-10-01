package cgg.a06;

import cgg.Image;
import cgg.Raytracer;
import cgg.Sphere;
import cgg.SuperSampler;
import cgg.SuperSampler.SAMPLINGTYPE;
import cgg.materials.MirrorMaterial;
import cgg.materials.NoisePhongMaterial;
import cgg.materials.PhongMaterial;
import cgg.materials.TexturedGlowingMaterial;
import cgg.materials.TexturedPhongMaterial;
import cgg.PointLight;
import cgg.ILight;
import cgg.IMaterial;
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
        int width = 800;
        int height = 800;

        var image = new Image(width, height);

    

    Scene scene = new Scene();

    Mat44 move = move(vec3(0,-1,0));
    Mat44 rotation = multiply(rotate(Vec3.zAxis, 10), rotate(Vec3.xAxis,10));
    Mat44 scale = scale(1,1,1);

    Transform transformation = new Transform(multiply(scale, rotation, move));
    Camera cam = new Camera(Math.PI/2, width, height, transformation);
    Raytracer raytracer = new Raytracer(cam,image, scene);

    scene.setRaytracer(raytracer);


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

    IMaterial mirror = new MirrorMaterial(black, black, 100, 1);

    IMaterial glowingMat = new GlowingMaterial(yellow, red, 10, red, 0.4);
    IMaterial glowingChess = new TexturedGlowingMaterial(imageTexture4, red, 100, cyan, 0.1);

    scene.add (new PointLight(vec3(-10,1,-10), new tools.Color(0,30,0)));
    scene.add (new PointLight(vec3(10,1,-10), new tools.Color(15,0,15)));
    scene.add (new PointLight(vec3(0,5,1), new tools.Color(10,10,5)));
    scene.add (new DirectionalLight(vec3(2,-1,-6.5), new tools.Color(0,0.2,0.2)));


    
    scene.add(new Sphere(vec3(0,-1002,-5), 997, schach));
    scene.add (new Sphere(vec3(-15, 15, -20), 5, erde));

    double radius = 2;
    Sphere sphere = new Sphere(vec3(0, 0, 0), radius, color(0.01,0.01,0.02));

    Mat44 scaleLimb = scale(vec3(0.3, 1.0, 0.3));
    Mat44 scaleBody = scale(vec3(0.6, 1.0, 0.6));
    Mat44 scaleHead = scale(vec3(0.5, 0.5, 0.5));

    Vec3 moveArmLeft = vec3(-1.2 * radius, 0, 0);
    Vec3 moveArmRight = vec3(1.2 * radius, 0, 0);

    Vec3 moveLegLeft = vec3(-0.7 * radius, 0, 0);
    Vec3 moveLegRight = vec3(0.7 * radius, 0, 0);

    Vec3 armDown = vec3(0, -radius, 0);
    Vec3 legDown = vec3(0, -1.5 * radius, 0);
    Vec3 moveHead = vec3(0, 1.2 * radius, 0);

    Group leftUpperArm = new Group(multiply(move(moveArmLeft), rotate(Vec3.zAxis, -15), scaleLimb));
    leftUpperArm.addShape(sphere);

    Group leftLowerArm = new Group(multiply(move(armDown), scaleLimb));
    leftLowerArm.addShape(new Sphere (vec3(0,0,0), radius, mirror));
    leftUpperArm.addShape(leftLowerArm);

    Group rightUpperArm = new Group(multiply(move(moveArmRight), rotate(Vec3.zAxis, 15), scaleLimb));
    rightUpperArm.addShape(sphere);

    Group rightLowerArm = new Group(multiply(move(armDown), scaleLimb));
    rightLowerArm.addShape(new Sphere (vec3(0,0,0), radius, mirror));
    rightUpperArm.addShape(rightLowerArm);

    Group leftLeg = new Group(multiply(move(tools.Functions.add(moveLegLeft,legDown)), scaleLimb));
    leftLeg.addShape(sphere);

    Group rightLeg = new Group(multiply(move(tools.Functions.add(moveLegRight,legDown)), scaleLimb));
    rightLeg.addShape(sphere);

    Group head = new Group(multiply(move(moveHead), scaleHead));
    head.addShape(new Sphere(vec3(0,0,0), radius, color(0.1,0.1,0.2)));

    Group leftEye = new Group(multiply(move(vec3(radius/4,0,radius)), scale(0.2,0.1,0.2)));
    leftEye.addShape(new Sphere(vec3(0,0,0), 1, glow));

    Group rightEye = new Group(multiply(move(vec3(-radius/4,0,radius)), scale(0.2,0.1,0.2)));
    rightEye.addShape(new Sphere(vec3(0,0,0), 1, glow));

    Group eyes = new Group();
    eyes.addShape(leftEye);
    eyes.addShape(rightEye);

    head.addShape(eyes);


    Group body = new Group(scaleBody);
    body.addShape(sphere);
    body.addShape(head);
    body.addShape(leftUpperArm);
    body.addShape(rightUpperArm);
    body.addShape(leftLeg);
    body.addShape(rightLeg);

    
   /* 
    Group group = new Group();
    group.addShape(new Sphere(vec3(-1,0,-3), 1, white));
    group.addShape(new Sphere(vec3(1,0,-3), 1, white));

    Group group2 = new Group();
    group2.addShape(group);
    group2.addShape(new Sphere(vec3(0,0,-5),2,green)); */

 
    
    
    

    Group person = new Group(move(0,0,-7));
    person.addShape(body);
    double size = 0.05;

    for (int x = -30; x < 30; x= x+7) {
        Mat44 movePerson = move(x,0,-12);
        Mat44 scalePerson = scale(2,2,2);
        Group backperson = new Group (new Transform(movePerson));
        backperson.addShape(person);
        scene.add(backperson);
        size = (size + 0.1)*1.1;
    }

    scene.add(person);

    

    Mat44 rotateMoon = multiply(rotate(Vec3.xAxis, 20), rotate(Vec3.yAxis, 10));
    Mat44 moveMoon = move (vec3(-2,6,-10));
    Transform transformMoon = new Transform(multiply(moveMoon,rotateMoon));
    Group mmoon = new Group(transformMoon);
    mmoon.addShape(new Sphere(vec3(0,0,0), 2, moon));
    scene.add(mmoon);
    scene.add(new Sphere(vec3(12,20,-20), 10, mirror));


    var supersampler = new SuperSampler(raytracer, SAMPLINGTYPE.STRATIFIED, 2);


    
    for (int x = 0; x != width; x++)
      for (int y = 0; y != height; y++)
        // Sets the color for one particular pixel.
        image.setPixel(x, y, supersampler.getColor(vec2(x, y)));

    // Write the image to disk.
    image.writePNG("a06-transforms");
    //System.out.println(scene.toString());
    System.out.println("Bild ist fertig");


    
        

    }

}

