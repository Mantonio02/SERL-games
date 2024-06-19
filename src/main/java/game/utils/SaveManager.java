package game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import game.entities.movingEntities.attackEntities.player.Player;
import game.items.BlueFlask;
import game.items.GreenFlask;
import game.items.IItem;
import game.items.PurpleFlask;
import game.items.RedFlask;
import game.model.Model;

/** SaveManager saves the current state of the game in a xml file called Gamestate. */
public class SaveManager {

  private Preferences prefs;
  private Model model;

  public SaveManager(Model model) {
    prefs = Gdx.app.getPreferences("Gamestate");
    this.model = model;
  }

  public SaveManager(Preferences prefs, Model model) {
    this.prefs = prefs;
    this.model = model;
  }

  /** Save current game state. */
  public void saveGameState() {
    prefs.putInteger("XP", model.getPlayer().getXp());
    prefs.putInteger("Level", model.getPlayer().getLevel());
    prefs.putFloat("X-pos", model.getPlayer().getBody().getPosition().x);
    prefs.putFloat("Y-pos", model.getPlayer().getBody().getPosition().y);
    prefs.putInteger("HEALTH", model.getPlayer().getHealth());

    prefs.remove("ItemCount");
    int itemCount = model.getPlayer().getHud().getHotBar().getNumItems();
    prefs.putInteger("ItemCount", itemCount);

    int counter = 0;
    for (IItem item : model.getPlayer().getItems()) {
      if (item != null) {
        prefs.putString("ITEM" + counter, item.toString());
        counter++;
      }
    }

    prefs.flush();
  }

  /** Load current previous game state. */
  public void loadGameState(Player player) {
    if (hasSaveFile()) {
      player.setHealth(prefs.getInteger("HEALTH"));
      player.setXp(prefs.getInteger("XP"));
      player.setLevel(prefs.getInteger("Level"));

      player.setPosition(prefs.getFloat("X-pos"), prefs.getFloat("Y-pos"));
      player
          .getBody()
          .setTransform(
              prefs.getFloat("X-pos"), prefs.getFloat("Y-pos"), player.getBody().getAngle());

      player.getHud().getHotBar().clearItems();
      int itemCount = prefs.getInteger("ItemCount", 0);
      if (itemCount == 0) return;
      for (int i = 0; i < itemCount; i++) {
        String itemKey = "ITEM" + i;
        if (prefs.contains(itemKey)) {
          String itemName = prefs.getString(itemKey);
          player.addItem(matchItem(itemName));
        }
      }
    }
  }

  /**
   * Check if previous save file has data
   *
   * @return true if previous save file has data.
   */
  public boolean hasSaveFile() {
    return prefs.getInteger("HEALTH", 0) != 0;
  }

  /** Delete data on save file. */
  public void deleteSave() {
    prefs.clear();
    prefs.flush();
  }

  private IItem matchItem(String name) {
    return switch (name) {
      case "Red Flask" -> new RedFlask();
      case "Blue Flask" -> new BlueFlask();
      case "Green Flask" -> new GreenFlask();
      case "Purple Flask" -> new PurpleFlask();
      default -> throw new IllegalArgumentException("Unknown item: " + name);
    };
  }
}
