package game.entities.movingEntities.attackEntities.player;

// TODO: Create a class Bar to represent both Experience and Health bar? Right now, the seem
// identical.
public class ExperienceBar {
  private final int width;
  private final int height;

  /** Creates a new bar to display experience points. */
  public ExperienceBar() {
    this.width = 125;
    this.height = 25;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
