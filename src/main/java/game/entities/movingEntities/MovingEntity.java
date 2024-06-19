package game.entities.movingEntities;

import static game.utils.Constants.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import game.entities.Direction;
import game.entities.Entity;
import java.util.List;
import java.util.Map;

public abstract class MovingEntity extends Entity {
  private Vector2 movementVec;
  private float movementSpeed;
  private boolean isRunning;

  /**
   * Creates a new {@link MovingEntity} with the given movement speed and a given list of
   * animations.
   *
   * @param movementSpeed pixels per delta time
   * @param animationList the animations of the entity given by path name to the sprite sheet to be
   *     * used and the number of frames.
   */
  public MovingEntity(float movementSpeed, List<Map.Entry<String, Integer>> animationList) {
    super(animationList);
    this.movementSpeed = movementSpeed;
    this.isRunning = false;
    this.movementVec = new Vector2(0, 0);
  }

  public void setMovementSpeed(float movementSpeed) {
    this.movementSpeed = movementSpeed;
  }

  public float getMovementSpeed() {
    return this.movementSpeed;
  }

  /**
   * Uses the movement vector of the entity to set the direction of the entity. The direction is set
   * along the axis the entity is moving along the quickest.
   */
  public void updateRunningDirection() {
    if (this.movementVec.len() == 0) {
      return;
    }
    if (Math.abs(this.movementVec.x) > Math.abs(this.movementVec.y)) {
      if (this.movementVec.x < 0) {
        this.startRunning(Direction.LEFT);
        return;
      }
      this.startRunning(Direction.RIGHT);
    } else {
      if (this.movementVec.y < 0) {
        this.startRunning(Direction.DOWN);
        return;
      }
      this.startRunning(Direction.UP);
    }
  }

  private void updateMovement() {
    if (this.movementVec.len() == 0 && this.isRunning) {
      this.stopRunning();
    }

    this.setPosition(
        this.getBody().getPosition().x * PPM - this.getWidth() / 2f,
        this.getBody().getPosition().y * PPM - this.getHeight() / 2f);

    this.getBody().setLinearVelocity(this.getMovementVec());
  }

  @Override
  public void act(float deltaTime) {
    super.act(deltaTime);
    updateMovement();
  }

  /**
   * Creates a {@link com.badlogic.gdx.physics.box2d.BodyDef.BodyType#DynamicBody dynamic} {@link
   * Body} without any {@link com.badlogic.gdx.physics.box2d.Fixture Fixture}s for the Entity.
   *
   * @param world the {@link World} to create the body in.
   */
  public void createBody(World world) {
    BodyDef bDef = new BodyDef();
    bDef.type = BodyDef.BodyType.DynamicBody;
    bDef.position.set(
        (this.getX() + this.getWidth() / 2f) / PPM, (this.getY() + this.getHeight() / 2f) / PPM);
    bDef.fixedRotation = true;
    this.body = world.createBody(bDef);
    this.body.setUserData(this);
  }

  public Vector2 getMovementVec() {
    return this.movementVec;
  }

  /**
   * Sets the movement vector to the given {@link Vector2} and updates the {@link Direction} of the
   * entity using {@link #updateRunningDirection()}.
   *
   * @param vec the new movement vector
   */
  public void setMovementVec(Vector2 vec) {
    this.movementVec = vec;
    this.updateRunningDirection();
  }

  public boolean isRunning() {
    return this.isRunning;
  }

  /**
   * Starts the running process of the entity.
   *
   * @param direction the {@link Direction} to run in.
   */
  protected void startRunning(Direction direction) {
    if (!isRunning || this.getCurrentDirection() != direction) {
      isRunning = true;
      this.setCurrentDirection(direction);
      setStateTime(0);
      animationHandler.setCurrentAnimation("Run", direction);
      animationHandler.setLooping(true);
    }
  }

  /** End the running process of the entity. */
  public void stopRunning() {
    isRunning = false;
    setStateTime(0);
    animationHandler.setLooping(false);
    idle();
  }

  protected float numPixelsToMove(float movementSpeed, float delta) {
    return (movementSpeed * PPM) * delta;
  }
}
