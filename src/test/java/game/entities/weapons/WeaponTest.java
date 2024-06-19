package game.entities.weapons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.math.Vector2;
import game.AbstractTest;
import game.entities.movingEntities.attackEntities.player.Player;
import game.model.Model;
import game.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WeaponTest extends AbstractTest {
  private Player player;
  private Vector2 playerPosition;
  private Vector2 weaponPosition;

  @BeforeEach
  public void setup() {
    Model model = new Model();
    model.setDebugMode();
    model.create();
    this.player = model.getPlayer();
    playerPosition = player.getBody().getWorldCenter();
    weaponPosition = player.getWeapon().getBody().getWorldCenter();
  }

  @Test
  void weaponPositionedAtPlayerGrip() {
    // Player's position plus player grip in meters
    Vector2 expected =
        playerPosition.cpy().add(player.getCurrentGrip().cpy().scl(1 / Constants.PPM));
    Vector2 actual = weaponPosition;
    float epsilon = 0.01f;

    assertEquals(expected.x, actual.x, epsilon);
    assertEquals(expected.y, actual.y, epsilon);
  }
}
