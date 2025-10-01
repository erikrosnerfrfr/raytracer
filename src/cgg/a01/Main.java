
package cgg.a01;

import cgg.Image;
import cgg.Ray;
import cgg.Sphere;
import cgg.ConstantColor;
import cgg.Camera;
import cgg.ColorDisc;
import cgg.ColoredDiscs;
import static tools.Functions.*;
import static tools.Color.*;


public class Main {

  public static void main(String[] args) {
    int width = 400;
    int height = 400;

    // This class instance defines the contents of the image.
    var constant = new ConstantColor(yellow);
    var circle = new ColorDisc(vec2(200,200),green,50);
    var circlepattern= new ColoredDiscs(width, height);


    /* Kameratest
    var cam = new Camera(Math.PI * 0.5, 10, 10);
    System.out.println(cam.generateRay(vec2(0.0,0.0)));
    System.out.println(cam.generateRay(vec2(5,5)));
    System.out.println(cam.generateRay(vec2(10,10)));
    */
    



    
    // Creates an image and iterates over all pixel positions inside the image.
    var image = new Image(width, height);
    for (int x = 0; x != width; x++)
      for (int y = 0; y != height; y++)
        // Sets the color for one particular pixel.
        image.setPixel(x, y, circlepattern.getColor(vec2(x, y)));

    // Write the image to disk.
    image.writePNG("a01-circlepattern"); 
    
  } 
}
