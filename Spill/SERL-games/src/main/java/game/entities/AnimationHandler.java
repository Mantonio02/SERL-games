package game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.List;
import java.util.Map;

public class AnimationHandler {

  private final AnimationMap animationMap;
  private Animation<TextureRegion> currentAnimation;
  private boolean looping;

  /**
   * Handles the list of animations for an animated object with a direction and states. Current
   * animation is null on creation.
   *
   * @param animationList the list of all animations given by path name and number of frames. Path
   *     name must be formatted as ModeDirection.fileExtension.
   */
  public AnimationHandler(List<Map.Entry<String, Integer>> animationList) {
    this.animationMap = new AnimationMap(animationList);
    looping = false;
  }

  /**
   * @return the animation currently chosen by the handler.
   */
  public Animation<TextureRegion> getCurrentAnimation() {
    return currentAnimation;
  }

  /**
   * Chooses an animation from the {@link AnimationMap} to display as the current animation.
   *
   * @param name of the animation group (f.ex Run, Attack, etc.)
   * @param direction of the animation
   */
  public void setCurrentAnimation(String name, Direction direction) {
    this.currentAnimation = animationMap.getAnimation(name, direction);
  }

  /**
   * Adds a new animation to the {@link AnimationMap}.
   *
   * @param animation to add
   */
  public void addAnimation(Map.Entry<String, Integer> animation) {
    animationMap.addAnimation(animation);
  }

  public AnimationMap getAnimationMap() {
    return animationMap;
  }

  /**
   * Sets whether the current animation should be looping or not.
   *
   * @param looping true for loop, false if not
   */
  public void setLooping(boolean looping) {
    this.looping = looping;
  }

  /**
   * @return Whether the current animation is looping or not.
   */
  public boolean isLooping() {
    return looping;
  }
}
