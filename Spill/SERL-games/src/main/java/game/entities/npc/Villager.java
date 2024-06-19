package game.entities.npc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Villager extends NPC {
  private ArrayList<TextArea> textFields = new ArrayList<>();
  private Vector2 textPos;
  private int state = 0;
  private float textDisplayTime = 0;
  private float minDisplayTime = 1.0f;

  public Villager() {
    super(
        Arrays.asList(
            Map.entry("sprites/npc/villager/IdleLeft.png", 1),
            Map.entry("sprites/npc/villager/IdleRight.png", 1),
            Map.entry("sprites/npc/villager/IdleUp.png", 1),
            Map.entry("sprites/npc/villager/IdleDown.png", 1)));
  }

  protected void speak() {
    textDisplayTime += Gdx.graphics.getDeltaTime();
    switch (state) {
      case 0 -> {
        textFields.get(0).setVisible(true);
        goToNextState();
      }
      case 1 -> {
        textFields.get(0).setVisible(false);
        textFields.get(1).setVisible(true);
        goToNextState();
      }
      case 2 -> {
        textFields.get(1).setVisible(false);
        textFields.get(2).setVisible(true);
        goToNextState();
      }
      case 3 -> {
        textFields.get(2).setVisible(false);
        textFields.get(3).setVisible(true);
        goToNextState();
      }
      case 4 -> {
        for (TextArea textField : textFields) {
          textField.setVisible(false);
        }
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
    return textFields;
  }

  private void goToNextState() {
    if (interactPressed && textDisplayTime > minDisplayTime) {
      state += 1;
      textDisplayTime = 0;
    }
  }

  @Override
  public void read(Json json, JsonValue jsonValue) {
    super.read(json, jsonValue);
    textPos = new Vector2(getX(), getY() + 60);
    TextArea field1 =
        createSpeakBubble(
            "Ever since that virus came to our town, it hasn't been the same.", textPos);
    TextArea field2 = createSpeakBubble("The animals are different.", textPos);
    TextArea field3 =
        createSpeakBubble("And the keeper of the forest up North... He seems possessed.", textPos);
    TextArea field4 = createSpeakBubble("Can your help us?", textPos);
    textFields.addAll(Arrays.asList(field1, field2, field3, field4));
  }
}
