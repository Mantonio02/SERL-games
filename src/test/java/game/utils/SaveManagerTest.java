package game.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.Preferences;
import game.AbstractTest;
import game.entities.movingEntities.attackEntities.player.Player;
import game.model.Model;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SaveManagerTest extends AbstractTest {
  private SaveManager saveManager;
  private Preferences prefs;
  private Model model;
  private static int defaultValue = -1;

  @BeforeEach
  void setup() {
    model = new Model();
    model.setDebugMode();
    model.create();

    prefs = new PreferencesMock();
    saveManager = new SaveManager(prefs, model);
  }

  @AfterEach
  void teardown() {
    saveManager.deleteSave();
  }

  @Test
  void testHasSaveFile() {
    assertFalse(saveManager.hasSaveFile(), "Save file should be empty!");
    fillSaveFile();

    assertTrue(saveManager.hasSaveFile(), "Save file should not be empty!");
  }

  @Test
  void testSaveGame() {
    fillSaveFile();
    assertEquals(prefs.getInteger("HEALTH", defaultValue), 70);

    saveManager.saveGameState();
    assertEquals(model.getPlayer().getHealth(), prefs.getInteger("HEALTH", defaultValue));
    assertEquals(model.getPlayer().getXp(), prefs.getInteger("XP", defaultValue));
  }

  @Test
  void testLoadSave() {
    Player mockPlayer = model.getPlayer();

    fillSaveFile();
    saveManager.loadGameState(mockPlayer);

    assertEquals(70, mockPlayer.getHealth());
    assertEquals(70, mockPlayer.getXp());
    assertEquals(5, mockPlayer.getLevel());
    assertEquals(100f, mockPlayer.getX());
    assertEquals(100f, mockPlayer.getY());
  }

  private void fillSaveFile() {
    // Values which is different from model.getPlayer()
    prefs.putInteger("HEALTH", 70);
    prefs.putInteger("XP", 70);
    prefs.putInteger("Level", 5);
    prefs.putFloat("X-pos", 100f);
    prefs.putFloat("Y-pos", 100f);
    prefs.putBoolean("ENEMY0_DEAD", true);
  }
}
