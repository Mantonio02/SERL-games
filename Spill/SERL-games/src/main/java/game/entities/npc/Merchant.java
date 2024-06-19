package game.entities.npc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Class for an {@link NPC} whom the {@link
 * game.entities.movingEntities.attackEntities.player.Player} of the game can buy Items from.
 */
public class Merchant extends NPC {
  private TextArea textField;
  private TextArea textField2;
  private Vector2 textPos;
  private int state = 0;
  private float textDisplayTime = 0;
  private float minDisplayTime = 1.0f;
  private boolean playerIsBuying;
  private int shopKey;

  public Merchant() {
    super(
        Arrays.asList(
            Map.entry("sprites/npc/merchant/IdleLeft.png", 1),
            Map.entry("sprites/npc/merchant/IdleRight.png", 1),
            Map.entry("sprites/npc/merchant/IdleUp.png", 1),
            Map.entry("sprites/npc/merchant/IdleDown.png", 1)));
    playerIsBuying = false;
  }

  @Override
  protected void speak() {
    textDisplayTime += Gdx.graphics.getDeltaTime();
    int currentShopKey = keybindings.getBinding("OPEN SHOP");

    if (shopKey != currentShopKey) {
      this.shopKey = currentShopKey;
      textField2.setText(
          String.format("I have many items!\n\t[Press %s]", Input.Keys.toString(shopKey)));
    }
    switch (state) {
      case 0 -> {
        textField.setVisible(true);
        textField2.setVisible(false);
        goToNextState();
      }
      case 1 -> {
        textField.setVisible(false);
        textField2.setVisible(true);
        goToNextState();
        if (shopButtonPressed) {
          state = 2;
          playerIsBuying = true;
          this.shopButtonPressed = false;
        }
      }
      case 2 -> {
        textField.setVisible(false);
        textField2.setVisible(false);
        stopTalking();
        goToNextState();
      }
      default -> {
        textDisplayTime = 0;
        state = 0;
      }
    }
  }

  protected void idle() {
    super.idle();
  }

  @Override
  public ArrayList<TextArea> getSpeakBubbles() {
    ArrayList<TextArea> bubbles = new ArrayList<>();
    bubbles.add(textField);
    bubbles.add(textField2);
    return bubbles;
  }

  private void goToNextState() {
    if (interactPressed && textDisplayTime > minDisplayTime) {
      state += 1;
      textDisplayTime = 0;
    }
  }

  /** Return flag for if player want to open/close merchant shop. */
  public boolean playerIsBuying() {
    return playerIsBuying;
  }

  /** Set flag so player is done using merchant shop. */
  public void playerFinishedBuying() {
    playerIsBuying = false;
    state = 2;
  }

  @Override
  public void read(Json json, JsonValue jsonValue) {
    super.read(json, jsonValue);
    textPos = new Vector2(getX(), getY() + 60);
    textField = createSpeakBubble("You want to buy something?", textPos);
    textField2 =
        createSpeakBubble(
            String.format("I have many wares!\n\t[Press %s]", Input.Keys.toString(shopKey)),
            textPos);
  }
}
