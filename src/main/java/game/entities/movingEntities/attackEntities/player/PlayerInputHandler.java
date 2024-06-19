package game.entities.movingEntities.attackEntities.player;

import com.badlogic.gdx.Input;
import game.utils.Keybindings;

public class PlayerInputHandler {
  private final Player player;
  private long lastScrollTime;
  private final Keybindings keybindings;
  private boolean leftKeyPressed;
  private boolean rightKeyPressed;
  private boolean upKeyPressed;
  private boolean downKeyPressed;
  private boolean pKeyPressed;

  // Which key was pressed first if multiple opposite direction keys are pressed
  private int xDirection; // -1 for left, 0 for none, 1 for right
  private int yDirection; // -1 for up, 0 for none, 1 for down

  /**
   * Creates a new PlayerInputHandler that handles user input according to the rules for {@link
   * Player}'s movement.
   *
   * @param player the player to handle user input for.
   */
  public PlayerInputHandler(Player player, Keybindings keybindings) {
    this.player = player;

    this.xDirection = 0;
    this.yDirection = 0;
    this.keybindings = keybindings;
  }

  /**
   * Handles the keyboard input according to the rules for {@link Player}'s movement.
   *
   * @param keycode the key that was pressed.
   * @return whether the input was processed.
   */
  protected boolean handleInputDown(int keycode) {
    if (keycode == keybindings.getBinding("ATTACK")) {
      if (!player.isAttacking()) {
        player.stopRunning();
        player.startAttacking();
      }
    } else if (keycode == Input.Keys.Y) {
      player.takeDamage(player.getHealth());
    } else if (keycode == keybindings.getBinding("LEFT")) {
      leftKeyPressed = true;
      if (xDirection == 0) xDirection = -1;
    } else if (keycode == keybindings.getBinding("RIGHT")) {
      rightKeyPressed = true;
      if (xDirection == 0) xDirection = 1;
    } else if (keycode == keybindings.getBinding("UP")) {
      upKeyPressed = true;
      if (yDirection == 0) yDirection = 1;
    } else if (keycode == keybindings.getBinding("DOWN")) {
      downKeyPressed = true;
      if (yDirection == 0) yDirection = -1;
    } else if (keycode == keybindings.getBinding("SPRINT")) {
      player.setSprint(true);
    } else if (keycode == keybindings.getBinding("CONSUME")) {
      player.consumeSelectedItem();
    } else if (keycode == keybindings.getBinding("TRAVEL")) {
      pKeyPressed = true;
    }
    if (keycode >= Input.Keys.NUM_1 && keycode <= Input.Keys.NUM_9) {
      player.getHud().getHotBar().setSelectedItem(keycode - Input.Keys.NUM_1);
    }

    return true;
  }

  /**
   * Handles the keyboard input according to the rules for {@link Player}'s movement.
   *
   * @param keycode the key that was lifted.
   * @return whether the input was processed.
   */
  protected boolean handleInputUp(int keycode) {
    if (keycode == keybindings.getBinding("LEFT")) {
      leftKeyPressed = false;
      if (rightKeyPressed) xDirection = 1;
      else xDirection = 0;
    } else if (keycode == keybindings.getBinding("RIGHT")) {
      rightKeyPressed = false;
      if (leftKeyPressed) xDirection = -1;
      else xDirection = 0;
    } else if (keycode == keybindings.getBinding("UP")) {
      upKeyPressed = false;
      if (downKeyPressed) yDirection = -1;
      else yDirection = 0;
    } else if (keycode == keybindings.getBinding("DOWN")) {
      downKeyPressed = false;
      if (upKeyPressed) yDirection = 1;
      else yDirection = 0;
    } else if (keycode == keybindings.getBinding("SPRINT")) {
      player.setSprint(false);
    } else if (keycode == keybindings.getBinding("TRAVEL")) {
      pKeyPressed = false;
    }
    return true;
  }

  /**
   * Handles the scroll input according to the rules for the {@link Player}'s {@link HUD}'s
   * movement.
   *
   * @param amountY the amount that was scrolled along the y-axis.
   * @return whether the input was processed.
   */
  protected boolean handleScroll(float amountY) {
    if (amountY == 0 || System.currentTimeMillis() - lastScrollTime < 100) {
      return false;
    }
    lastScrollTime = System.currentTimeMillis();

    if (amountY > 0) {
      player.getHud().getHotBar().incrementSelectedItem();
    } else {
      player.getHud().getHotBar().decrementSelectedItem();
    }
    return true;
  }

  /**
   * The x-axis direction the Player should move in.
   *
   * @return -1 if left, 0 if none, 1 if right
   */
  public int getXDirection() {
    return xDirection;
  }

  /**
   * The y-axis direction the Player should move in.
   *
   * @return -1 if down, 0 if none, 1 if up
   */
  public int getYDirection() {
    return yDirection;
  }

  /** Flushes the player inputs */
  public void flush() {
    this.leftKeyPressed = false;
    this.rightKeyPressed = false;
    this.upKeyPressed = false;
    this.downKeyPressed = false;

    this.xDirection = 0;
    this.yDirection = 0;
  }

  public boolean isPKeyPressed() {
    return pKeyPressed;
  }
}
