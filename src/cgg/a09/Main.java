package cgg.a09;

import java.util.concurrent.*;


import cgg.Image;
import cgg.Raytracer;
import cgg.Sphere;
import cgg.SuperSampler;
import cgg.SuperSampler.SAMPLINGTYPE;
import cgg.materials.*;
import cgg.PointLight;
import cgg.IMaterial;
import cgg.Background;
import cgg.Camera;
import cgg.DirectionalLight;
import cgg.GlowingMaterial;
import textures.*;
import tools.ImageTexture;
import tools.Color;
import tools.ISampler;
import cgg.IShape;
import cgg.SuperSampler;
import tools.Mat44;
import tools.Vec3;
import cgg.Transform;
import cgg.Group;
import cgg.Plane;

import static tools.Functions.*;
import cgg.Scene;

import java.util.ArrayList;
import java.util.List;

import com.mokiat.data.front.parser.IMTLParser;

import static tools.Color.*;
import cgg.BVH;

public class Main {

    public static void main(String[] args) {
        int width = 800;
        int height = 800;

        TransmissiveMaterial.transmissionFunctionTests();

        var image = new Image(width, height);

        Group root = new Group();
        

    Scene scene = new Scene();
    scene.add(new Background());

    Mat44 move = move(vec3(0,3,0));
    Mat44 rotation = multiply(rotate(Vec3.zAxis, 0), rotate(Vec3.xAxis,-10));
    Mat44 scale = scale(1,1,1);

    Transform transformation = new Transform(multiply(move, rotation, scale));
    Camera cam = new Camera(Math.PI/2, width, height, transformation);
    Raytracer raytracer = new Raytracer(cam,image, scene);

    //scene.setRaytracer(raytracer);


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

    ISampler imageTexture6 = new ImageTexture("src/textures/flowmetal.png");
    IMaterial leaves = new TexturedPhongMaterial(imageTexture6,imageTexture6, 100);

    ISampler imageTexture7 = new ImageTexture("src/textures/bozo.png");
    IMaterial bozo = new TexturedPhongMaterial(imageTexture7,imageTexture7, 100);

    ISampler imageTexture8 = new ImageTexture("src/textures/erde.png");
    IMaterial erde = new TexturedPhongMaterial(imageTexture8,imageTexture8, 100);

    ISampler imageTexture9 = new ImageTexture("src/textures/mond.png");
    IMaterial mond = new EmissiveMaterial(imageTexture9,white, 100);

    IMaterial sunEmission = new EmissiveMaterial(imageTexture3, white, 100);



    IMaterial glow = new GlowingMaterial(black, yellow, 10, new tools.Color(0.5,0.5,0), 1);

    IMaterial mirror = new MirrorMaterial(new Color(1,1,1), white, 100);
    IMaterial darkmirror = new MirrorMaterial(new Color(0.2,0.2,0.3), white, 100);


      scene.add (new PointLight(vec3(-10,1,-7), new tools.Color(0,20,0)));
      scene.add (new PointLight(vec3(10,1,-7), new tools.Color(10,0,10)));
     scene.add (new PointLight(vec3(0,5,1), new tools.Color(1,1,10)));
    // scene.add (new DirectionalLight(vec3(0.3,-1,-0.3), new tools.Color(0.1,0.1,0.3)));

    

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
  // for (int x = -30; x < 30; x+= 3) {
  //       for (int y = 40; y > -10; y-= 3) {
  //         for (int z = -2; z > -50; z-=3) {
  //           double xs = 2*Math.random()-1 + x;
  //           double ys = 2*Math.random()-1 + y;
  //           double zs = 2*Math.random()-1 + z;

  //           Transform dropletTransform = new Transform(
  //             multiply(move(xs,ys,zs), rotate(Vec3.zAxis, 30), scale(0.1,0.4,0.1))
  //           );
  //           Group droplets = new Group (dropletTransform);
  //           droplets.addShape(new Sphere(vec3(0,0,0), 1, new TransmissiveMaterial(1.4)));
  //           root.addShape(droplets);
  //         }
  //       }
  //     }

    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    Transform plane = new Transform(multiply(move(vec3(0,-2,0)), rotate(Vec3.xAxis,-90), scale(vec3(200,200,200))));
    Group planegroup = new Group (plane);

    planegroup.addShape(new Plane(vec3(0,0,0), 1, 100, new TexturedMirrorMaterial(imageTexture4, imageTexture4, 100)));
    root.addShape(planegroup);

//////////////////////////////////////////////////////

    // Transform mirrorTransform = new Transform(multiply(move(vec3(0,0,-10)), /*rotate(Vec3.xAxis,-90),*/ scale(vec3(1,2,1))));
    // Group mirrorPlane = new Group (mirrorTransform);

    // Group leftEdge = new Group(new Transform(multiply(move(vec3(-3,0, 0.1)), scale(vec3(0.1,1,10.2)))));
    // leftEdge.addShape(new Plane(vec3(0,0,0), 3, 100, new PhongMaterial(black, cyan, 100)));

    // Group rightEdge = new Group(new Transform(multiply(move(vec3(3,0,0.1)), scale(vec3(0.1,1,10.2)))));
    // rightEdge.addShape(new Plane(vec3(0,0,0), 3, 100, new PhongMaterial(black, cyan, 100)));

    // Group topEdge = new Group(new Transform(multiply(move(vec3(0,3, 0.1)), scale(vec3(1,0.1,10.2)))));
    // topEdge.addShape(new Plane(vec3(0,0,0), 3, 100, new PhongMaterial(black, cyan, 100)));



    // mirrorPlane.addShape(new Plane(vec3(0,0,0), 3, 100, new MirrorMaterial(white, white, 100)));
    // mirrorPlane.addShape(rightEdge);
    // mirrorPlane.addShape(leftEdge);
    // mirrorPlane.addShape(topEdge);
    // scene.add(mirrorPlane);

   // scene.add(new Sphere(vec3(0,0,-5), 2, new TransmissiveMaterial(1.5)));

    double radius = 2;
    Sphere sphere = new Sphere(vec3(0, 0, 0), radius, new Color(0.1,0.1,0.2));

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
    leftLowerArm.addShape(new Sphere (vec3(0,0,0), radius, sunEmission));
    leftUpperArm.addShape(leftLowerArm);

    Group rightUpperArm = new Group(multiply(move(moveArmRight), rotate(Vec3.zAxis, 15), scaleLimb));
    rightUpperArm.addShape(sphere);

    Group rightLowerArm = new Group(multiply(move(armDown), scaleLimb));
    rightLowerArm.addShape(new Sphere (vec3(0,0,0), radius, sunEmission));
    rightUpperArm.addShape(rightLowerArm);

    Group leftLeg = new Group(multiply(move(tools.Functions.add(moveLegLeft,legDown)), scaleLimb));
    leftLeg.addShape(sphere);

    Group rightLeg = new Group(multiply(move(tools.Functions.add(moveLegRight,legDown)), scaleLimb));
    rightLeg.addShape(sphere);

    Group head = new Group(multiply(move(moveHead), scaleHead));
    head.addShape(new Sphere(vec3(0,0,0), radius, new TransmissiveMaterial(1.5)));

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

    Group person = new Group(move(0,2,0));
    person.addShape(body);
    //root.addShape(person);

    Plane glassPlane = new Plane (vec3(0,0,0), 1, 1000, new TransmissiveMaterial(1.4));

    Group frontCube = new Group (new Transform(move(0,0,1)));
    Group backCube = new Group (new Transform(multiply(move(0,0,-1), rotate(Vec3.yAxis, 180))));
    Group leftCube = new Group (new Transform(multiply(move(0,0,0), rotate(Vec3.yAxis, -90))));
    Group rightCube = new Group (new Transform(multiply(move(0,0,0), rotate(Vec3.yAxis, 90))));
    Group topCube = new Group (new Transform(multiply(move(0,1,0), rotate(Vec3.xAxis, -90))));
    Group bottomCube = new Group (new Transform(multiply(move(0,-1,0), rotate(Vec3.yAxis, 90))));
    Group cube = new Group(new Transform(multiply(move(-2,0,-6), rotate(Vec3.yAxis, 20), scale(2,2,2))));

    frontCube.addShape(glassPlane);
    backCube.addShape(glassPlane);
    leftCube.addShape(glassPlane);
    rightCube.addShape(glassPlane);
    topCube.addShape(glassPlane);
    bottomCube.addShape(glassPlane);

    cube.addShape(frontCube);
    cube.addShape(backCube);
    cube.addShape(leftCube);
    cube.addShape(rightCube);
    cube.addShape(topCube);
    cube.addShape(bottomCube);

    //root.addShape(cube);

    //root.addShape(new Sphere(vec3(3, 0, -6), 2, new TransmissiveMaterial(1.2)));
    //////////////////////////////////////////////////////////////////////////////////////////////////

    int shapeCount = 0;
    int shapeLimit = 32768;
    int maxZ = -10000;

    int ii = 0;

    for (int z = -2; z > maxZ; z--) {
        for (int x = -ii; x <= ii; x++) {
            if (shapeCount >= shapeLimit) {
                break;
            }
            Group group = new Group(multiply(move(x,0,z), scale(0.2,0.2,0.2)));
            group.addShape(person);
            //Sphere skibidi = new Sphere(vec3(x, 0, z), 0.3, new TransmissiveMaterial(1.4));
            root.addShape(group);
            shapeCount+= 8; // ich 8 Kugeln in meiner person hab
        }
        ii++;
    }


    var supersampler = new SuperSampler(raytracer, SAMPLINGTYPE.STRATIFIED, 8);


    BVH bvh = new BVH();
    Group bvhRoot = bvh.build(root);
    long startTime = System.currentTimeMillis();


      scene.add(bvhRoot);

        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Color>> pixels = new ArrayList<>(width*height);
    
    for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int fx = x;
                final int fy = y;
                pixels.add(executor.submit(() -> supersampler.getColor(vec2(fx, fy))));
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
    long endTime = System.currentTimeMillis();
    System.out.println("Anzahl: "+ shapeLimit +", Zeit: " + ((endTime-startTime)/1000) + " Sekunden");


    try {
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    // Write the image to disk.
    image.writePNG("a09-bvh");
    //System.out.println(scene.toString());
    System.out.println("Bild ist fertig");


    
        

    }

}

