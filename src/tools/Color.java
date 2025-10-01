package tools;

import static tools.Functions.*;

/**
 * Represents a color with red, green, and blue channels.
 * This class provides constants for common colors.
 */
public record Color(double r, double g, double b) {
    

    // black (0, 0, 0)
    public static final Color black = color(0, 0, 0);
    // gray color  .5, 0.5)
    public static final Color gray = color(0.5, 0.5, 0.5);
    // white (1, 1, 1)
    public static final Color white = color(1, 1, 1);
    // red (1, 0, 0)
    public static final Color red = color(1, 0, 0);
    // green (0, 1, 0)
    public static final Color green = color(0, 1, 0);
    // blue (0, 0, 1)
    public static final Color blue = color(0, 0, 1);
    // cyan (0, 1, 1)
    public static final Color cyan = color(0, 1, 1);
    // magenta (1, 0, 1)
    public static final Color magenta = color(1, 0, 1);
    // yellow (1, 1, 0)
    public static final Color yellow = color(1, 1, 0);

    /**
     * Returns a string representation of the color.
     *
     * @return A string in the format "(Color: r.rr g.gg b.bb)".
     */
    /* 
     public void setR (double r) {
        this.r=r;
     }
     public double getR () {
        return r;
     }
     public void addR(Color color, double value) {
        color.setR(color.getR()+value);
     }
        */
    @Override
    public String toString() {
        return String.format("(Color: %.2f %.2f %.2f)", r, g, b);
    }
}