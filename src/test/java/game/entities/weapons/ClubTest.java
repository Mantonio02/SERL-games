package game.entities.weapons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.Gdx;
import game.AbstractTest;
import game.entities.movingEntities.attackEntities.player.Player;
import game.model.Model;
import game.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClubTest extends AbstractTest {
  private Weapon weapon;
  private Player player;
  private Model model;

  @BeforeEach
  public void setup() {
    this.model = new Model();
    model.setDebugMode();
    model.create();
    this.player = model.getPlayer();
    this.weapon = new Club(player);
    this.weapon.createBody(model.getWorld());
    player.setWeapon(weapon);

    // Act once to set weapon angle
    player.act(Gdx.graphics.getDeltaTime());
  }

  @Test
  void optimalAttackSpeed() {
    player.startAttacking();
    while (player.isAttacking()) {
      model.render();
      player.act(Gdx.graphics.getDeltaTime());
      weapon.act(Gdx.graphics.getDeltaTime());
    }

    assertEquals(-135, player.getWeapon().getBody().getAngle() / Constants.DEGREE_TO_RADIAN);
  }

  @Test
  void optimalAttackSpeed100Times() {
    for (int i = 0; i < 100; i++) {
      optimalAttackSpeed();
    }
  }
}
