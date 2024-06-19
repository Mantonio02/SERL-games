package game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import game.entities.movingEntities.attackEntities.player.Player;
import game.model.Model;
import java.util.LinkedHashMap;
import java.util.Map;

/** Class for controlling and get keybinds. */
public class Keybindings {
  private final LinkedHashMap<String, Integer> bindings;
  private final Preferences prefs;

  /**
   * Hashmap of [action: keycode] which stores all the inputs. Important new keycode such as
   * consume, is added here, and it will be automatically added to the HashSet in {@link Model}.
   */
  private static final LinkedHashMap<String, Integer> DEFAULT_BINDINGS = new LinkedHashMap<>();

  static {
    DEFAULT_BINDINGS.put("SPRINT", Input.Keys.SHIFT_LEFT);
    DEFAULT_BINDINGS.put("ATTACK", Input.Keys.SPACE);
    DEFAULT_BINDINGS.put("INTERACT", Input.Keys.E);
    DEFAULT_BINDINGS.put("OPEN SHOP", Input.Keys.X);
    DEFAULT_BINDINGS.put("HEALING", Input.Keys.UNKNOWN); // Not implemented yet
    DEFAULT_BINDINGS.put("CONSUME", Input.Keys.C);
    DEFAULT_BINDINGS.put("RIGHT", Input.Keys.RIGHT);
    DEFAULT_BINDINGS.put("LEFT", Input.Keys.LEFT);
    DEFAULT_BINDINGS.put("UP", Input.Keys.UP);
    DEFAULT_BINDINGS.put("DOWN", Input.Keys.DOWN);
    DEFAULT_BINDINGS.put(
        "TRAVEL", Input.Keys.P); // For travelling to new Map by interaction with door.
  }

  public Keybindings() {
    bindings = new LinkedHashMap<>();
    prefs = Gdx.app.getPreferences("MyGameSettings");
    loadBindings();
  }

  /** Add custom preferences */
  public Keybindings(Preferences preferences) {
    bindings = new LinkedHashMap<>();
    this.prefs = preferences;
    loadBindings();
  }

  private void loadBindings() {
    if (bindings.isEmpty()) {
      for (Map.Entry<String, Integer> entry : DEFAULT_BINDINGS.entrySet()) {
        int binding = prefs.getInteger(entry.getKey(), entry.getValue());
        bindings.put(entry.getKey(), binding);
      }
    }
  }

  /**
   * Get keycode given action name.
   *
   * @param action name of action
   * @return keycode
   */
  public int getBinding(String action) {
    return bindings.get(action);
  }

  /** Set keycodes back to default, can be used in a possible reset button. */
  public void resetKeybinds() {
    bindings.clear();
    for (Map.Entry<String, Integer> entry : Keybindings.DEFAULT_BINDINGS.entrySet()) {
      bindings.put(entry.getKey(), entry.getValue());
      prefs.putInteger(entry.getKey(), entry.getValue());
    }
    prefs.flush();
  }

  /**
   * Set new keycode to given action.
   *
   * @param action name of action; Ex: ATTACK, UP, ...., LEFT
   * @param keyCode keycode to set to
   */
  public void setKeybinding(String action, int keyCode) {
    bindings.put(action, keyCode);
    prefs.putInteger(action, keyCode);
    prefs.flush();
  }

  /**
   * @return The full current keybindings map; {action: keycode}
   */
  public LinkedHashMap<String, Integer> getKeyBindingMap() {
    return this.bindings;
  }

  /**
   * Check if given action is controlled by {@link Player} or something else like {@link
   * game.entities.npc.NPC}. Important new keycode which is controlled by player is added here. Will
   * affect by the key setting screen and logic.
   *
   * @param action the action to check.
   * @return true if action is controlled by player.
   */
  public boolean isMovementKeycode(String action) {
    return switch (action) {
      case "SPRINT", "UP", "RIGHT" -> true;
      case "LEFT" -> true;
      case "DOWN" -> true;
      case "ATTACK" -> true;
      case "HEALING" -> true;
      case "INTERACT" -> false;
      case "OPEN SHOP" -> false;
      case "CONSUME" -> true;
      case "TRAVEL" -> true; // Set to true since player controls action.
      default -> false;
    };
  }
}
