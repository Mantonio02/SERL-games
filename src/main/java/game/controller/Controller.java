package game.controller;

import com.badlogic.gdx.InputAdapter;
import game.entities.npc.NPC;

public class Controller extends InputAdapter {
  ControllableModel model;

  /**
   * Creates a {@link Controller} to handle user input for a given {@link ControllableModel}.
   *
   * @param model the given model
   */
  public Controller(ControllableModel model) {
    super();
    this.model = model;
  }

  @Override
  public boolean keyDown(int keycode) {
    if (model.getPlayerKeyCodes().contains(keycode)) {
      return model.handlePlayerInput(keycode, true);
    } else if (model.getInteractionKeys().contains(keycode)) {
      for (NPC friend : model.getCurrentLevel().getFriends()) {
        model.handleNPCInput(friend, keycode, true);
      }
    }
    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    if (model.getPlayerKeyCodes().contains(keycode)) {
      return model.handlePlayerInput(keycode, false);
    } else if (model.getInteractionKeys().contains(keycode)) {
      for (NPC friend : model.getCurrentLevel().getFriends()) {
        model.handleNPCInput(friend, keycode, false);
      }
    }
    return false;
  }

  @Override
  public boolean scrolled(float amountX, float amountY) {
    return model.handlePlayerScroll(amountY);
  }
}
