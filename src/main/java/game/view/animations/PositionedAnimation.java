package game.view.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import util.ExcludeFromGeneratedCoverageReport;

/** An animation {@link Actor} that can be positioned and displayed with a {@link Batch}. */
public class PositionedAnimation extends Actor {
  private Animation<TextureRegion> animation;
  private float stateTime;
  private boolean looping;

  /**
   * Creates an animation placed at a given position.
   *
   * @param animation the animation to display
   * @param position position of the midpoint of the animation
   */
  public PositionedAnimation(
      Animation<TextureRegion> animation, Vector2 position, boolean looping) {
    this.animation = animation;
    this.stateTime = 0f;
    this.looping = looping;

    setSize(
        animation.getKeyFrame(0f).getRegionWidth(), animation.getKeyFrame(0f).getRegionHeight());
    setPosition(position.x, position.y);
    //    animation.setFrameDuration(0.167f);
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    stateTime += delta;
  }

  @Override
  @ExcludeFromGeneratedCoverageReport
  public void draw(Batch batch, float parentAlpha) {
    TextureRegion keyFrame = animation.getKeyFrame(stateTime, looping);
    batch.draw(keyFrame, getX(), getY(), getWidth(), getHeight());
  }

  /**
   * Whether the animation would be finished if played without looping (PlayMode#NORMAL), given the
   * current stateTime
   *
   * @return whether the animation is finished
   */
  public boolean isAnimationFinished() {
    return animation.isAnimationFinished(stateTime);
  }

  /**
   * Set x and y of the {@link PositionedAnimation} so that the midpoint matches the given position.
   *
   * @param position of the midpoint
   */
  public void setMidpoint(Vector2 position) {
    super.setPosition(position.x - getWidth() / 2f - 2, position.y - getHeight() / 2f - 2);
  }

  /**
   * Sets the animation to a given {@link Animation<TextureRegion>} and resets stateTime.
   *
   * @param animation the new animation
   * @param looping whether the animation should loop or not
   */
  public void setAnimation(Animation<TextureRegion> animation, boolean looping) {
    this.stateTime = 0f;
    this.animation = animation;
    this.looping = looping;
  }
}
