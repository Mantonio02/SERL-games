package game.entities.movingEntities.attackEntities.enemies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.Gdx;
import game.AbstractTest;
import game.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BossTest extends AbstractTest {
  private Boss boss;
  private Model model;

  @BeforeEach
  void setup() {
    boss = spy(new Boss());
    boss.setPosition(24, 24);

    model = new Model();
    model.setDebugMode();
    model.create();
    boss.createBody(model.getWorld());
  }

  @Test
  void assetNotNull() {
    assertNotNull(Gdx.files.internal("sprites/enemySprites/bossSprites/IdleRight.png"));
    assertNotNull(Gdx.files.internal("sprites/enemySprites/bossSprites/AttackRight.png"));
    assertNotNull(Gdx.files.internal("sounds/leshen_scream.mp3"));
  }

  @Test
  void testValues() {
    assertEquals(boss.getHealth(), 300);
    assertEquals(boss.getMaxHealth(), 300);
    assertEquals(boss.getStrength(), 0);
    assertEquals(boss.getMovementSpeed(), 1f);
  }

  @Test
  void testHealthBar() {
    EnemyHealthBar healtbar = boss.getBossHealthBar();

    assertEquals(healtbar.getHealth(), 300);
    boss.takeDamage(20);
    healtbar.act(1);
    assertEquals(healtbar.getHealth(), 280);

    assertNotNull(healtbar);
  }

  @Test
  void testDeath() {
    boss.takeDamage(310);
    boss.act(1f);

    verify(boss).remove();
  }
}
