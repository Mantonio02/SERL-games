package game.buffs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.AbstractTest;
import game.entities.movingEntities.attackEntities.player.Player;
import game.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BuffTest extends AbstractTest {
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
  void testSpeedBuff() {
    float speedBefore = player.getMovementSpeed();
    float multiplier = 1.5f;
    var buff = new SpeedBuff(1, multiplier);
    this.player.appendBuff(buff);
    float speedAfter = player.getMovementSpeed();

    assertTrue(speedAfter > speedBefore, "Player speed is not affected by speed buff");
    assertEquals(String.format("Speed x %f", multiplier), buff.toString());
  }

  @Test
  void testDamageBuff() {
    int damageBefore = player.getStrength();
    var buff = new DamageBuff(1, 5);
    this.player.appendBuff(buff);
    int damageAfter = player.getStrength();

    assertTrue(damageBefore < damageAfter, "Player Damage is not affected by damage buff");
    assertEquals("Damage + 5", buff.toString());
  }

  @Test
  void testHealthBuff() {
    int healthBefore = player.getHealth();
    var buff = new HealthBuff(1, 5);
    this.player.appendBuff(buff);
    int healthAfter = player.getHealth();

    assertTrue(healthBefore < healthAfter, "Player Damage is not affected by damage buff");
    assertEquals("Health increased by 5", buff.toString());
  }

  @Test
  void testBuffRunsOut() {
    int damageBefore = player.getStrength();
    int healthBefore = player.getHealth();
    float speedBefore = player.getMovementSpeed();

    this.player.appendBuff(new DamageBuff(0, 5));
    this.player.appendBuff(new HealthBuff(0, 5));
    this.player.appendBuff(new SpeedBuff(0, 1.5f));

    this.player.act(1f);

    int damageAfter = player.getStrength();
    int healthAfter = player.getHealth();
    float speedAfter = player.getMovementSpeed();

    assertEquals(damageBefore, damageAfter, "Buff is not running out");
    assertEquals(healthBefore, healthAfter, "Buff is not running out");
    assertEquals(speedBefore, speedAfter, "Buff is not running out");
  }
}
