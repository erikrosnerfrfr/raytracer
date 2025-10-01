package cgg.a11test;

import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cgg.*;
import cgg.materials.*;
import cgg.SuperSampler.SAMPLINGTYPE;
import textures.*;
import tools.*;
import static tools.Functions.*;

import tools.Wavefront.MeshData;
import tools.Wavefront.TriangleData;

import com.mokiat.data.front.parser.IMTLParser;

import static tools.Color.*;

public class Main {

    public static void main(String[] args) {
        int width = 1280;
        int height = 1280;

        //TransmissiveMaterial.transmissionFunctionTests();

        var image = new Image(width, height);

        Group root = new Group();
        

    Scene scene = new Scene();
    scene.add(new Background());

    Mat44 move = move(vec3(0,5,10));
    Mat44 rotation = multiply(rotate(Vec3.zAxis, 0), rotate(Vec3.xAxis,0));
    Mat44 scale = scale(1,1,1);

    Transform transformation = new Transform(multiply(move, rotation, scale));
    Camera cam = new Camera(Math.PI/2, width, height, transformation);
    Raytracer raytracer = new Raytracer(cam,image, scene);

    //scene.setRaytracer(raytracer);


    ISampler imageTexture3 = new ImageTexture("src/textures/flowmetal.png");

    ISampler imageTexture4 = new ImageTexture("src/textures/schach.png");
    IMaterial schach = new TexturedPhongMaterial(imageTexture4,imageTexture4, 100);

    ISampler imageTexture7 = new ImageTexture("src/textures/bozo.png");
    IMaterial bozo = new TexturedPhongMaterial(imageTexture7,imageTexture7, 100);

    IMaterial sunEmission = new EmissiveMaterial(imageTexture3, white, 100);



    IMaterial glow = new GlowingMaterial(black, yellow, 10, new tools.Color(0.5,0.5,0), 1);

    IMaterial mirror = new MirrorMaterial(new Color(1,1,1), white, 100);
    IMaterial darkmirror = new MirrorMaterial(new Color(0.2,0.2,0.3), white, 100);


       scene.add (new PointLight(vec3(-10,5,-5.5), new tools.Color(2,15,2)));
       scene.add (new PointLight(vec3(10,5,-5.5), new tools.Color(10,0,10)));
      scene.add (new PointLight(vec3(0,10,-7), new tools.Color(1,5,10)));
    // scene.add (new DirectionalLight(vec3(0.3,-1,-0.3), new tools.Color(0.1,0.1,0.3)));

     Group pidgeon = new Group(multiply(move(0.8,3.2,7), rotate(Vec3.yAxis, 25), scale(0.3,0.3,0.3)));
     pidgeon.addShape(ObjectConverter.loadOBJModel("src/objects/Model_D0901B73/D0901B73.obj"));
     //root.addShape(pidgeon);

    //  Group wolf = new Group(multiply(move(-1,-0.2,-3), rotate(Vec3.yAxis, -25), scale(3,3,3)));
    //  wolf.addShape(ObjectConverter.loadOBJModel("src/objects/obj/Wolf_One_obj.obj", new MirrorMaterial(white, white, 100)));
    //  root.addShape(wolf);

    //  Group gun = new Group(multiply(move(1,0.9,-2), rotate(Vec3.yAxis, 45), scale(0.13,0.13,0.13)));
    //  gun.addShape(ObjectConverter.loadOBJModel("src/objects/gun/gungun/M24_R_Low_Poly_Version_obj.obj", new PhongMaterial(black, white, 100)));
    //  root.addShape(gun);

      Group skull = new Group(multiply(move(-3,2.8,6), rotate(Vec3.yAxis, -30), rotate(Vec3.zAxis, 20), scale(0.01,0.01,0.01)));
      skull.addShape(ObjectConverter.loadOBJModel("src/objects/skull/Skull.obj", mirror));
      //root.addShape(skull);

    //  System.out.println("Das Mapping mit der gun hat nicht so ganz funktioniert aber es lädt weiter");
    //  System.out.println(" Dauert etwa 300 Sekunden bei mir");
        Group bobo = new Group(new Transform(multiply(move(-6, 0.5, -7), rotate(Vec3.yAxis, 20), scale(0.5,0.5,0.5))));
    bobo.addShape(new Sphere(vec3(0,0,0), 1, bozo));
    bobo.addShape(new Sphere(vec3(0.2,0,0.6), 0.4, red));
    bobo.addShape(new Sphere(vec3(-0.2,0,0.6), 0.4, red));
    //root.addShape(bobo);

    //  Group skull = new Group(multiply(move(0,0,-3), rotate(Vec3.yAxis, 0), scale(0.4,0.4,0.4)));
    //  skull.addShape(ObjectConverter.loadOBJModel("src/objects/ugandan/AncientUgandan.obj"));
    //  root.addShape(skull);

    // Group leon = new Group(multiply(move(0,-5,-20), rotate(Vec3.yAxis, 45), scale(50,50,50)));
    // leon.addShape(ObjectConverter.loadOBJModel("src/objects/77775/77775/77775.obj", new PhongMaterial(green, white, 100)));
    // root.addShape(leon);


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
  

    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    Transform plane = new Transform(multiply(move(vec3(0,-2,0)), rotate(Vec3.xAxis,-120), scale(vec3(200,200,200))));
    Group planegroup = new Group (plane);

    planegroup.addShape(new Plane(vec3(0,0,0), 1, 100, new TexturedMirrorMaterial(imageTexture4, imageTexture4, 100)));
    root.addShape(planegroup);

//////////////////////////////////////////////////////

    Transform mirrorTransform = new Transform(multiply(move(vec3(0,0,-10)), /*rotate(Vec3.xAxis,-90),*/ scale(vec3(1,2,1))));
    Group mirrorPlane = new Group (mirrorTransform);

    Group leftEdge = new Group(new Transform(multiply(move(vec3(-3,0, 0.1)), scale(vec3(0.1,1,10.2)))));
    leftEdge.addShape(new Plane(vec3(0,0,0), 3, 100, new PhongMaterial(black, cyan, 100)));

    Group rightEdge = new Group(new Transform(multiply(move(vec3(3,0,0.1)), scale(vec3(0.1,1,10.2)))));
    rightEdge.addShape(new Plane(vec3(0,0,0), 3, 100, new PhongMaterial(black, cyan, 100)));

    Group topEdge = new Group(new Transform(multiply(move(vec3(0,3, 0.1)), scale(vec3(1,0.1,10.2)))));
    topEdge.addShape(new Plane(vec3(0,0,0), 3, 100, new PhongMaterial(black, cyan, 100)));



    mirrorPlane.addShape(new Plane(vec3(0,0,0), 3, 100, new MirrorMaterial(white, white, 100)));
    mirrorPlane.addShape(rightEdge);
    mirrorPlane.addShape(leftEdge);
    mirrorPlane.addShape(topEdge);

    Group mirrors = new Group();
    double angle = -45;
    for ( int i = 0; i < 5; i ++) {
        Group mirrorrow = new Group(new Transform(multiply(rotate(Vec3.yAxis, angle), move(0,4,-15))));
        mirrorrow.addShape(mirrorPlane);
        mirrors.addShape(mirrorrow);
        angle += 22.5;
    }
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

    Group person = new Group(new Transform(multiply(move(2,2.8,6), rotate(Vec3.yAxis, -20), scale(0.3,0.3,0.3))));
    person.addShape(body);
    //root.addShape(person);

    Plane glassPlane = new Plane (vec3(0,0,0), 1, 1000, new TransmissiveMaterial(1.2));

    Group frontCube = new Group (new Transform(move(0,0,1)));
    Group backCube = new Group (new Transform(multiply(move(0,0,-1), rotate(Vec3.yAxis, 180))));
    Group leftCube = new Group (new Transform(multiply(move(-1,0,0), rotate(Vec3.yAxis, -90))));
    Group rightCube = new Group (new Transform(multiply(move(1,0,0), rotate(Vec3.yAxis, 90))));
    Group topCube = new Group (new Transform(multiply(move(0,1,0), rotate(Vec3.xAxis, -90))));
    Group bottomCube = new Group (new Transform(multiply(move(0,-1,0), rotate(Vec3.xAxis, 90))));
    Group cube = new Group(new Transform(multiply(move(0,5,-5), rotate(Vec3.zAxis, 0), rotate(Vec3.xAxis, 0), scale(1,1,1))));

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


    var supersampler = new SuperSampler(raytracer, SAMPLINGTYPE.STRATIFIED, 4);


    
    long startTime = System.currentTimeMillis();
    

 
    int numFrames = 122;
    int fps = 24;
    double frequency = 1.0/fps;

    scene.animators.add(new LinearRotator(cube,Vec3.yAxis, 0, 45));

    Group alldrops = new Group();

for (int x = -30; x < 30; x+= 3) {
        for (int y = 40; y > -10; y-= 3) {
          for (int z = -2; z > -50; z-=3) {
            double xs = 2*Math.random()-1 + x;
            double ys = 2*Math.random()-1 + y;
            double zs = 2*Math.random()-1 + z;

            Transform dropletTransform = new Transform(
              multiply(move(xs,ys,zs), rotate(Vec3.zAxis, 30), scale(0.05,0.2,0.05))
            );
            Group droplets = new Group (dropletTransform);
            droplets.addShape(new Sphere(vec3(0,0,0), 1, mirror));
            alldrops.addShape(droplets);
          }
        }
      }

      Group outerglassBalls = new Group(new Transform(move(0,0,-5)));
      outerglassBalls.addShape(new Sphere(vec3(-4, 1, 0), 1, mirror));
      outerglassBalls.addShape(new Sphere(vec3(4, 10, 0), 1, mirror));

          scene.animators.add(new LinearRotator(outerglassBalls,Vec3.yAxis, 0, -180));


      Group innerglassBalls1 = new Group(new Transform(move(0,0,-5)));
      innerglassBalls1.addShape(new Sphere(vec3(-3, 2, 0), 0.7, mirror));
      innerglassBalls1.addShape(new Sphere(vec3(3, 9, 0), 0.7, mirror));

          scene.animators.add(new LinearRotator(innerglassBalls1,Vec3.yAxis, 0, 135));

Group innerglassBalls2 = new Group(new Transform(move(0,0,-5)));
      innerglassBalls2.addShape(new Sphere(vec3(-2, 3, 0), 0.6, mirror));
      innerglassBalls2.addShape(new Sphere(vec3(2, 8, 0), 0.6, mirror));

          scene.animators.add(new LinearRotator(innerglassBalls2,Vec3.yAxis, 0, -90));

Group innerglassBalls3 = new Group(new Transform(move(0,0,-5)));
      innerglassBalls3.addShape(new Sphere(vec3(-2, 4, 0), 0.5, mirror));
      innerglassBalls3.addShape(new Sphere(vec3(2, 7, 0), 0.5, mirror));

          scene.animators.add(new LinearRotator(innerglassBalls3,Vec3.yAxis, 0, 45));



    
    scene.animators.add(new LinearMover(alldrops, vec3(0,0,0), vec3(1,-3,0)));

    for (int i = 0; i < numFrames; i ++) {

        scene.update(frequency);

        Group bvhRoot = null;
        root.shapes.clear();
        scene.world.shapes.clear();
        
        
        //root.addShape(bobo);
        root.addShape(planegroup);
        root.addShape(cube);
        root.addShape(innerglassBalls1);
        root.addShape(innerglassBalls2);
        root.addShape(innerglassBalls3);
        root.addShape(outerglassBalls);
         root.addShape(alldrops);
         root.addShape(skull);
         root.addShape(person);
        // root.addShape(mirrors);
        //root.addShape(pidgeon);

    BVH bvh = new BVH();
    bvhRoot = bvh.build(root);



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

        int ee = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                try {
                    image.setPixel(x, y, pixels.get(ee++).get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        executor.shutdown();
    
    executor.shutdown();
    long endTime = System.currentTimeMillis();
    System.out.println(/*"Anzahl: "+ shapeLimit +", Zeit: " +*/ ((endTime-startTime)/1000) + " Sekunden");


    try {
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    // Write the image to disk.
    image.writePNG(String.format("frame%03d", i));
    //System.out.println(scene.toString());
    System.out.println("Bild ist fertig");
    }

    //     int numThreads = Runtime.getRuntime().availableProcessors();
    //     ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    //     List<Future<Color>> pixels = new ArrayList<>(width*height);
    
    // for (int y = 0; y < height; y++) {
    //         for (int x = 0; x < width; x++) {
    //             final int fx = x;
    //             final int fy = y;
    //             pixels.add(executor.submit(() -> supersampler.getColor(vec2(fx, fy))));
    //         }
    //     }

    //     int i = 0;
    //     for (int y = 0; y < height; y++) {
    //         for (int x = 0; x < width; x++) {
    //             try {
    //                 image.setPixel(x, y, pixels.get(i++).get());
    //             } catch (Exception e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }

    //     executor.shutdown();
    
    // executor.shutdown();
    // long endTime = System.currentTimeMillis();
    // System.out.println(/*"Anzahl: "+ shapeLimit +", Zeit: " +*/ ((endTime-startTime)/1000) + " Sekunden");


    // try {
    //     executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    // } catch (InterruptedException e) {
    //     e.printStackTrace();
    // }

    // // Write the image to disk.
    // image.writePNG("a11-animation");
    // //System.out.println(scene.toString());
    // System.out.println("Bild ist fertig");

Group opencube = new Group(new Transform(multiply(move(0,2,10), rotate(Vec3.yAxis, 0), scale(0.1,0.1,0.1))));

    // Group openfrontCube = new Group (new Transform(multiply(move(0,0,1), rotate(Vec3.yAxis, 180))));
    // Group openbackCube = new Group (new Transform(multiply(move(0,0,-1), rotate(Vec3.yAxis, 0))));
    // Group openleftCube = new Group (new Transform(multiply(move(-1,0,0), rotate(Vec3.yAxis, 90))));
    // Group openrightCube = new Group (new Transform(multiply(move(1,0,0), rotate(Vec3.yAxis, -90))));
    // Group opentopCube = new Group (new Transform(multiply(move(0,1,0), rotate(Vec3.xAxis, 90))));
    // Group openbottomCube = new Group (new Transform(multiply(move(0,-1,0), rotate(Vec3.xAxis, -90))));

    // openfrontCube.addShape(new Plane(vec3(0,0,0), 5, 1000, new PhongMaterial(black, black, 0)));
    // openbackCube.addShape(new Plane(vec3(0,0,0), 5, 1000, new PhongMaterial(black, black, 0)));
    // openleftCube.addShape(new Plane(vec3(0,0,0), 5, 1000, new PhongMaterial(black, black, 0)));
    // openrightCube.addShape(new Plane(vec3(0,0,0), 5, 1000, new PhongMaterial(black, black, 0)));
    // opentopCube.addShape(new Plane(vec3(0,0,0), 5, 1000, new PhongMaterial(black, black, 0)));
    // openbottomCube.addShape(new Plane(vec3(0,0,0), 5, 1000, new PhongMaterial(black, black, 0)));

    // opencube.addShape(openfrontCube);
    // opencube.addShape(openleftCube);
    // opencube.addShape(openrightCube);
    // opencube.addShape(opentopCube);
    // opencube.addShape(openbottomCube);
    
        

    }

}

