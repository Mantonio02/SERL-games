package game.entities.movingEntities.attackEntities.player;

public class HealthBar {

  private final int width;
  private final int height;

  /**
   * Creates a new bar to display health points.
   *
   * @param maxHealth the maximum amount of health
   */
  public HealthBar(float maxHealth) {
    this.width = 200;
    this.height = 20;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
