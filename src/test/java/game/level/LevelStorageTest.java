package game.level;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import game.AbstractTest;
import game.entities.Door;
import game.entities.movingEntities.attackEntities.enemies.Enemy;
import game.entities.movingEntities.attackEntities.enemies.Wolf;
import game.entities.movingEntities.attackEntities.player.Player;
import game.entities.npc.Merchant;
import game.entities.npc.NPC;
import game.entities.npc.Villager;
import game.utils.Keybindings;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LevelStorageTest extends AbstractTest {
  private LevelStorage levelStorage;
  private Level level0;

  @BeforeEach
  void setupModel() {
    Keybindings keybindings = new Keybindings();
    this.levelStorage =
        new LevelStorage(
            new World(new Vector2(), false),
            new Player(keybindings),
            keybindings,
            "levelData/testData.json");
    level0 = levelStorage.loadLevel(0);
  }

  @Test
  void numberOfLevelsCorrect() {
    for (int i = 0; i <= 2; i++) {
      levelStorage.loadLevel(i);
    }
  }

  @Test
  void friendsLoadedCorrectly() {
    ArrayList<NPC> friends = level0.getFriends();
    assertEquals(2, friends.size());
    // Merchant
    assertEquals(Merchant.class, friends.get(0).getClass());
    assertEquals(new Vector2(), new Vector2(friends.get(0).getX(), friends.get(0).getY()));
    // Villager
    assertEquals(Villager.class, friends.get(1).getClass());
    assertEquals(new Vector2(100, 100), new Vector2(friends.get(1).getX(), friends.get(1).getY()));
  }

  @Test
  void foesLoadedCorrectly() {
    ArrayList<Enemy> foes = level0.getFoes();
    assertEquals(1, foes.size());
    assertEquals(Wolf.class, foes.get(0).getClass());
    assertEquals(new Vector2(200, 500), new Vector2(foes.get(0).getX(), foes.get(0).getY()));
  }

  @Test
  void doorsLoadedCorrectly() {
    ArrayList<Door> doors = level0.getDoors();
    assertEquals(2, doors.size());
    // Door 1
    assertEquals(new Vector2(400, 400), new Vector2(doors.get(0).getX(), doors.get(0).getY()));
    assertEquals(1, doors.get(0).getToLevel());
    assertEquals(new Vector2(100, 100), doors.get(0).getToPosition());
    assertTrue(doors.get(0).isPlayerDependent());
    // Door 2
    assertEquals(new Vector2(300, 300), new Vector2(doors.get(1).getX(), doors.get(1).getY()));
    assertEquals(2, doors.get(1).getToLevel());
    assertEquals(new Vector2(200, 200), doors.get(1).getToPosition());
    assertFalse(doors.get(1).isPlayerDependent());
  }
}
