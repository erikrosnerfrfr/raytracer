package tools;

public record ColorSampler(Color color) implements ISampler {

  @Override
  public Color getColor(Vec2 at) {
    return color;
  }
}
