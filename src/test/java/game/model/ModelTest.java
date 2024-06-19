package game.model;

import static org.junit.jupiter.api.Assertions.*;

import game.AbstractTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelTest extends AbstractTest {
  private Model model;

  @BeforeEach
  void setupModel() {
    this.model = new Model();
    model.setDebugMode();
    model.create();
  }

  @Test
  void testAssetNotNull() {
    assertNotNull(model.getPlayer());
    assertNotNull(model.getMap());
    assertNotNull(model.getPlayer().getBody());
  }

  @Test
  void PlayerCollisionWithMap() {
    // MANUAL TEST:
    // 1. Insert line ```b2dr.render(model.getWorld(), camera.combined.scl(PPM));``` in render() in
    // GameScreen after
    // the lines rendering the map and player.
    // 2. Run the application
    // 3. Take the necessary steps to start the game
    // 4. See that the bodies of the player and map objects are drawn
    // 5. Use the arrows to walk the player over to a map object (terrain, like a rock)
    // 6. See that the player is not allowed to walk through the object
    // 7. See that the bodies of the player and the map object do not overlap
  }
}
