package game.entities.npc;

import static game.utils.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import game.entities.Direction;
import game.entities.Entity;
import game.entities.movingEntities.attackEntities.enemies.ITarget;
import game.entities.movingEntities.attackEntities.player.Player;
import game.model.Model;
import game.utils.Keybindings;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Represent an NPC */
public abstract class NPC extends Entity implements Json.Serializable {
  private ITarget target;
  private boolean stopTalking;
  protected Keybindings keybindings;
  protected boolean interactPressed;
  protected boolean shopButtonPressed;
  protected boolean receiveInput;

  //  private ArrayList<Body> friendDeleteList;

  /**
   * Creates a new NPC with a given list of animations.
   *
   * @param animationList the animations of the NPC given by path name to the sprite sheet to be
   *     used and the number of frames.
   */
  public NPC(List<Map.Entry<String, Integer>> animationList) {
    super(animationList);
    idle();
    setSize(getKeyFrame().getRegionWidth(), getKeyFrame().getRegionHeight());
    interactPressed = false;
    shopButtonPressed = false;
    stopTalking = true;
  }

  public void setKeybindings(Keybindings keybindings) {
    this.keybindings = keybindings;
  }

  public void setTarget(ITarget target) {
    this.target = target;
  }

  @Override
  public void act(float deltaTime) {
    super.act(deltaTime);
    float dist = this.target.getBodyPosition().dst(new Vector2(getX() / PPM, getY() / PPM));
    double distanceToPlayer = 3;
    adjustDirectionTowardsPlayer();
    if (interactPressed && dist < distanceToPlayer) {
      stopTalking = false;
    }
    if (!stopTalking) {
      speak();
    }
    if (dist >= distanceToPlayer || stopTalking) {
      idle();
    }
  }

  /**
   * Called when NPC should be speaking. NPC's classes should override this if they handle text
   * bubbles.
   */
  protected abstract void speak();

  /**
   * Should be added to stage since {@link TextArea} count as an actor.
   *
   * @return a list of all the text bubbles.
   */
  public abstract ArrayList<TextArea> getSpeakBubbles();

  public boolean handleInput(int keycode, boolean down) {
    if (receiveInput) {
      if (keycode == keybindings.getBinding("INTERACT")) {
        interactPressed = down;
      }
      if (keycode == keybindings.getBinding("OPEN SHOP")) {
        shopButtonPressed = down;
      }
      return down;
    }
    return false;
  }

  public void createBody(World world) {
    super.createBody(world);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(this.getWidth() / 2f / PPM, this.getHeight() / 2f / PPM);
    FixtureDef fDef = new FixtureDef();
    fDef.shape = shape;

    fDef.filter.categoryBits = Model.MAP_OBJECT_BIT;
    fDef.filter.maskBits = Model.DEFAULT | Model.PLAYER_BIT | Model.ENEMY_BIT;

    body.createFixture(fDef);
    shape.dispose();
  }

  /** Called when NPC should stop talking. */
  protected void stopTalking() {
    stopTalking = true;
  }

  /**
   * Create a text bubble for the NPC.
   *
   * @param text Text which should be displayed.
   * @param textPos position of the text bubble.
   * @return The text bubble.
   */
  protected TextArea createSpeakBubble(String text, Vector2 textPos) {
    Skin skin = new Skin(Gdx.files.internal("skins/plain-james-ui.json"));
    BitmapFont font = skin.getFont("font");
    float scaleFactor = 0.5f;
    font.getData().setScale(scaleFactor);
    TextField.TextFieldStyle style = skin.get("default", TextField.TextFieldStyle.class);
    TextArea newTextField = new TextArea(text, style);
    newTextField.setSize(120, 70);
    newTextField.setPosition(textPos.x, textPos.y);
    newTextField.setVisible(false);
    return newTextField;
  }

  private void adjustDirectionTowardsPlayer() {
    Vector2 npcPosition = this.body.getPosition();
    Vector2 playerPosition = target.getBodyPosition();
    Vector2 directionToPlayer = playerPosition.sub(npcPosition);
    float diffX = Math.abs(directionToPlayer.x);
    float diffY = Math.abs(directionToPlayer.y);
    if (diffX > diffY) {
      if (directionToPlayer.x > 0) {
        setCurrentDirection(Direction.RIGHT);
      } else {
        setCurrentDirection(Direction.LEFT);
      }
    } else if (diffY > diffX) {
      if (directionToPlayer.y > 0) {
        setCurrentDirection(Direction.UP);
      } else {
        setCurrentDirection(Direction.DOWN);
      }
    }
    idle();
  }

  public boolean canReceiveInput() {
    return receiveInput;
  }

  @Override
  public void onCollision(Entity otherEntity) {
    if (otherEntity instanceof Player) {
      receiveInput = true;
    }
  }

  @Override
  public void postCollision() {
    receiveInput = false;
  }

  @Override
  public void write(Json json) {}

  @Override
  public void read(Json json, JsonValue jsonValue) {
    float x = jsonValue.get("position").get("x").asFloat();
    float y = jsonValue.get("position").get("y").asFloat();
    setPosition(x, y);
  }
}
