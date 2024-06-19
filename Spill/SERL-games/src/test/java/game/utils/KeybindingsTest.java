package game.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import game.AbstractTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KeybindingsTest extends AbstractTest {
  private Keybindings keybindings;
  private Preferences prefs;

  @BeforeEach
  void setup() {
    prefs = new PreferencesMock();

    prefs.putInteger("SPRINT", Input.Keys.SHIFT_LEFT);
    prefs.putInteger("ATTACK", Input.Keys.SPACE);
    prefs.putInteger("INTERACT", Input.Keys.E);
    prefs.putInteger("OPEN SHOP", Input.Keys.X);
    prefs.putInteger("HEALING", Input.Keys.UNKNOWN);
    prefs.putInteger("RIGHT", Input.Keys.RIGHT);
    prefs.putInteger("LEFT", Input.Keys.LEFT);
    prefs.putInteger("UP", Input.Keys.UP);
    prefs.putInteger("DOWN", Input.Keys.DOWN);

    keybindings = new Keybindings(prefs);
  }

  @AfterEach
  void after() {
    keybindings.resetKeybinds();
  }

  @Test
  public void testGetBinding() {
    int binding = keybindings.getBinding("ATTACK");
    assertEquals(Input.Keys.SPACE, binding, "The ATTACK binding should return SPACE keycode");
  }

  @Test
  public void testSetKeybinding() {
    keybindings.setKeybinding("HEALING", Input.Keys.R);
    assertEquals(
        Input.Keys.R,
        prefs.getInteger("HEALING", -1),
        "The HEALING binding should update to R keycode");
  }

  @Test
  public void testResetKeybinds() {
    keybindings.setKeybinding("SPRINT", Input.Keys.ALT_LEFT);
    keybindings.setKeybinding("ATTACK", Input.Keys.T);
    keybindings.setKeybinding("INTERACT", Input.Keys.Y);

    keybindings.resetKeybinds();

    assertEquals(Input.Keys.SHIFT_LEFT, prefs.getInteger("SPRINT", -1));
    assertEquals(Input.Keys.SPACE, prefs.getInteger("ATTACK", -1));
    assertEquals(Input.Keys.E, prefs.getInteger("INTERACT", -1));
  }
}
