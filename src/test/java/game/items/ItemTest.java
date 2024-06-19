package game.items;

import static org.junit.jupiter.api.Assertions.assertTrue;

import game.AbstractTest;
import game.entities.movingEntities.attackEntities.player.Player;
import game.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemTest extends AbstractTest {
  private Player player;
  private Model model;

  @BeforeEach
  public void setup() {
    model = new Model();
    model.setDebugMode();
    model.create();

    this.player = model.getPlayer();
  }

  @Test
  void testRedFlask() {
    var flask = new RedFlask();

    assertTrue(flask.getItemIcon() != null);
    assertTrue(flask.toString().equals("Red Flask"));
    assertTrue(flask.description().equals("Boost your attack"));
    assertTrue(flask.price() == 30);

    var before = this.player.getStrength();
    flask.interact(this.player);
    var after = this.player.getStrength();

    assertTrue(after > before, "Player is not recieving buff on interact");
  }

  @Test
  void testBlueFlask() {
    var flask = new BlueFlask();

    assertTrue(flask.getItemIcon() != null);
    assertTrue(flask.toString().equals("Blue Flask"));
    assertTrue(flask.description().equals("Boost your speed"));
    assertTrue(flask.price() == 30);

    var before = this.player.getMovementSpeed();
    flask.interact(this.player);
    var after = this.player.getMovementSpeed();

    assertTrue(after > before, "Player is not recieving buff on interact");
  }

  @Test
  void testGreenFlask() {
    var flask = new GreenFlask();

    assertTrue(flask.getItemIcon() != null);
    assertTrue(flask.toString().equals("Green Flask"));
    assertTrue(flask.description().equals("Temporarily increase your health"));
    assertTrue(flask.price() == 30);

    var before = this.player.getHealth();
    flask.interact(this.player);
    var after = this.player.getHealth();

    assertTrue(after > before, "Player is not recieving buff on interact");
  }

  @Test
  void testPurpleFlask() {
    var flask = new PurpleFlask();

    assertTrue(flask.getItemIcon() != null);
    assertTrue(flask.toString().equals("Purple Flask"));
    assertTrue(flask.description().equals("Greatly boost your attack, at the cost of your health"));
    assertTrue(flask.price() == 30);

    var beforeH = this.player.getHealth();
    var beforeD = this.player.getStrength();
    flask.interact(this.player);
    var afterH = this.player.getHealth();
    var afterD = this.player.getStrength();

    assertTrue(afterH < beforeH, "Player is not recieving buff on interact");
    assertTrue(afterD > beforeD, "Player is not recieving buff on interact");
  }
}
