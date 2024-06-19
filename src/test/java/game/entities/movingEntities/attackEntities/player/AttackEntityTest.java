package game.entities.movingEntities.attackEntities.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import game.AbstractTest;
import game.entities.movingEntities.attackEntities.AttackEntity;
import game.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AttackEntityTest extends AbstractTest {
  Model model;
  AttackEntity player;

  @BeforeEach
  public void setup() {
    this.model = new Model();
    model.setDebugMode();
    model.create();
    this.player = model.getPlayer();
  }

  @Test
  public void testTakeDamage() {
    float healthBefore = player.getHealth();
    player.takeDamage(20);
    float healthAfter = player.getHealth();
    assertNotEquals(healthBefore, healthAfter);
    assertEquals(healthAfter, 80);
  }
}
