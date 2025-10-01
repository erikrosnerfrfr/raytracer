package cgg;

import tools.BoundingBox;

public interface IShape {
    Hit intersect (Ray ray);
    BoundingBox getBounds();
}
