package game.entities;

import static game.utils.Constants.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import game.entities.movingEntities.attackEntities.player.Player;
import game.model.Model;
import java.util.ArrayList;

public class Door extends Entity implements Json.Serializable {
  private int toLevel;
  private boolean collidingWithPlayer;
  private boolean playerDependent;
  private Vector2 toPosition;

  /**
   * Creates a new Door to transport from one {@link game.level.Level} to another. Note that this
   * Entity does not have any animations in the animation map.
   */
  public Door() {
    super(new ArrayList<>());
  }

  @Override
  public void createBody(World world) {
    super.createBody(world);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(this.getWidth() / 2f / PPM, this.getHeight() / 2f / PPM);
    FixtureDef fDef = new FixtureDef();
    fDef.shape = shape;
    fDef.filter.categoryBits = Model.DOOR_BIT;
    fDef.filter.maskBits = Model.PLAYER_BIT;
    getBody().createFixture(fDef);
    this.getBody().setUserData(this);
    shape.dispose();
  }

  public int getToLevel() {
    return toLevel;
  }

  @Override
  public void write(Json json) {}

  @Override
  public void read(Json json, JsonValue jsonValue) {
    float x = jsonValue.get("position").get("x").asFloat();
    float y = jsonValue.get("position").get("y").asFloat();
    setPosition(x, y);

    toLevel = jsonValue.get("toLevel").asInt();

    x = jsonValue.get("toPosition").get("x").asFloat();
    y = jsonValue.get("toPosition").get("y").asFloat();
    toPosition = new Vector2(x, y);

    playerDependent = jsonValue.get("playerDependent").asBoolean();
  }

  public boolean isCollidingWithPlayer() {
    return collidingWithPlayer;
  }

  public boolean isPlayerDependent() {
    return playerDependent;
  }

  /**
   * Get the toPosition ArrayList.
   *
   * @return toPosition.
   */
  public Vector2 getToPosition() {
    return toPosition;
  }

  @Override
  public void onCollision(Entity otherEntity) {
    if (otherEntity instanceof Player) {
      this.collidingWithPlayer = true;
    }
  }

  @Override
  public void postCollision() {
    this.collidingWithPlayer = false;
  }
}
