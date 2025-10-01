
package cgg;

import tools.*;

// Represents the contents of an image. Provides the same color for all pixels.
public record ConstantColor(Color color) implements ISampler {

    // Returns the color for the given position.
    public Color getColor(Vec2 point) {
        return color;
    }
}
