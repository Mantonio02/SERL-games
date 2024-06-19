package game.entities;

import static game.utils.Constants.PPM;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import game.collision.ICollision;
import java.util.List;
import java.util.Map;
import util.ExcludeFromGeneratedCoverageReport;

/** A class to combine Actor with Body and AnimationHandler. */
public abstract class Entity extends Actor implements ICollision<Entity> {
  protected AnimationHandler animationHandler;
  protected Body body;
  private float stateTime;
  protected Direction direction;

  /**
   * Creates a new entity with a given list of animations. The entity's {@link Body} is {@code null}
   * at creation.
   *
   * @param animationList the animations of the entity given by path name to the sprite sheet to be
   *     used and the number of frames.
   */
  public Entity(List<Map.Entry<String, Integer>> animationList) {
    this.animationHandler = new AnimationHandler(animationList);
    this.stateTime = 0f;
    this.direction = Direction.RIGHT;
  }

  @Override
  @ExcludeFromGeneratedCoverageReport
  public void draw(Batch batch, float parentAlpha) {
    float width = getKeyFrame().getRegionWidth() * getScaleX();
    float height = getKeyFrame().getRegionHeight() * getScaleY();
    batch.draw(getKeyFrame(), getX(), getY(), width, height);
  }

  @Override
  public void act(float deltaTime) {
    stateTime += deltaTime;
  }

  public Body getBody() {
    return body;
  }

  /**
   * Creates a {@link com.badlogic.gdx.physics.box2d.BodyDef.BodyType#StaticBody static} {@link
   * Body} without any {@link com.badlogic.gdx.physics.box2d.Fixture Fixture}s for the Entity.
   *
   * @param world the {@link World} to create the body in.
   */
  public void createBody(World world) {
    BodyDef bDef = new BodyDef();
    bDef.type = BodyDef.BodyType.StaticBody;
    bDef.position.set(
        (this.getX() + this.getWidth() / 2f) / PPM, (this.getY() + this.getHeight() / 2f) / PPM);
    bDef.fixedRotation = true;
    this.body = world.createBody(bDef);
    this.body.setUserData(this);
  }

  public Direction getCurrentDirection() {
    return this.direction;
  }

  protected void setCurrentDirection(Direction d) {
    this.direction = d;
  }

  public float getStateTime() {
    return stateTime;
  }

  public void setStateTime(float stateTime) {
    this.stateTime = stateTime;
  }

  public AnimationHandler getAnimationHandler() {
    return animationHandler;
  }

  /**
   * @return the key frame for the current animation given the current state time of the Entity.
   */
  public TextureRegion getKeyFrame() {
    return getCurrentAnimation().getKeyFrame(stateTime, animationHandler.isLooping());
  }

  /**
   * @return the {@link Animation<TextureRegion>} currently chosen by the Entity's {@link
   *     AnimationHandler}.
   */
  public Animation<TextureRegion> getCurrentAnimation() {
    return this.animationHandler.getCurrentAnimation();
  }

  /**
   * Sets the current {@link Animation<TextureRegion>} of the {@link Entity} to "Idle" in the
   * current {@link Direction}. Assumes that the Entity has been created to contain such an
   * animation.
   */
  protected void idle() {
    animationHandler.setCurrentAnimation("Idle", direction);
  }
}
