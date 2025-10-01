package cgg;

import tools.ISampler;
import tools.*;
import static tools.Functions.vec2;
import static tools.Functions.add;
import static tools.Functions.divide;
import static tools.Color.*;


public record SuperSampler(ISampler sampler, SAMPLINGTYPE type, int numSamples) implements ISampler{

public enum SAMPLINGTYPE {
    POINT,
    GRID,
    RANDOM,
    STRATIFIED
};
public SuperSampler {
    numSamples = (int) Math.floor(Math.sqrt(numSamples));
}

public Color getColor (Vec2 pixel) {
    switch (type) {
        default: {
            return sampler.getColor(pixel);
        }
        case POINT: {
            return sampler.getColor(vec2(pixel.u()+0.5, pixel.v()+0.5));
        }
        case GRID: {
            Color color = black;
            for (int xi = 0; xi < numSamples; xi++) {
                for (int yj = 0; yj < numSamples; yj++) {
                    double xs = xi + (xi + 0.5)/numSamples;
                    double ys = yj + (yj + 0.5)/numSamples;
                    Vec2 gridPixel = add(pixel, vec2(xs, ys));
                    color = add(color,sampler.getColor(gridPixel));
                }
            }
            return divide(color, numSamples*numSamples);
        }

        case RANDOM: {
            Color color = black;
            for (int x = 0; x < numSamples; x++) {
                for (int y = 0; y < numSamples; y++) {

                double rx = Math.random();
                double ry = Math.random();
                double xs = x + rx;
                double ys = y + ry;
                Vec2 gridPixel = add(pixel, vec2(xs, ys));
                color = add(color,sampler.getColor(gridPixel));
                }
            }
            return divide(color, numSamples*numSamples);
        }

        case STRATIFIED: {
            Color color = black;
            for (int xi = 0; xi < numSamples; xi++) {
                for (int yi = 0; yi < numSamples; yi++) {

                double rx = Math.random();
                double ry = Math.random();
                double xs = (xi + rx)/numSamples;
                double ys = (yi + ry)/numSamples;
                Vec2 gridPixel = add(pixel, vec2(xs, ys));
                color = add(color,sampler.getColor(gridPixel));
                }
            }
            return divide(color, numSamples*numSamples);
        }
    }
}

}
