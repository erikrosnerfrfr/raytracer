
package cgg;


import tools.*;

public class Image implements tools.IImage {

    private final int width;
    private final int height;
    private final double [] data;



    public Image(int width, int height) {
        this.width=width;
        this.height=height;
        this.data = new double[width*height*3];
        // TODO Provides storage for the image data. For each pixel in the image
        // three double values are needed to store the pixel components.
    }

    public void setPixel(int x, int y, Color color) {
        int index = (y * width +x) *3;
        data[index] = color.r();
        data[index + 1] = color.g();
        data[index + 2] = color.b();
        // TODO Stores the RGB color components for one particular pixel addressed
        // by it's coordinates in the image.
    }

    public Color getPixel(int x, int y) {
        // TODO Retrieves the RGB color components for one particular pixel addressed
        // by it's coordinates in the image.
        int index = (y*width+x)*3;
        double r = data[index];
        double g = data[index+1];
        double b = data [index+2];
        return new Color(r, g, b);

    }

    public void writePNG(String name) {
        // TODO This call also needs to be adjusted once Image() and setPixel()
        // are implemented. Use
        // ImageWriter.writePNG(String basename, double[] data, int width, int height) to
        // write the image data to disk in PNG format.
        ImageWriter.writePNG(name, data, width, height);
        System.out.println("Complete the implementation of class cgg.Image for assignment 1.");
    }

    public void writeHDR(String name) {
        // TODO This call also needs to be adjusted once Image() and setPixel()
        // are implemented. Use
        // ImageWriter.writeHDR(String basename, double[] data, int width, int height) to
        // write the image data to disk in OpenEXR format.
    }

    public int width() {
        // TODO This is just a dummy value to make the compiler happy. This
        // needs to be adjusted such that the actual width of the Image is
        // returned.
        return width;
    }

    public int height() {
        // TODO This is just a dummy value to make the compiler happy. This
        // needs to be adjusted such that the actual height of the Image is
        // returned.
        return height;
    }
}
