package game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import game.view.animations.AnimationHelperMethods;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimationMap {
  private final HashMap<String, HashMap<Direction, Animation<TextureRegion>>> animationMap;

  /** Creates an empty AnimationMap. */
  public AnimationMap() {
    this.animationMap = new HashMap<>();
  }

  /**
   * Creates an AnimationMap from the given map of paths and frames.
   *
   * @param animationList list of {@link Map.Entry}s where key is the path name of the sprite sheet
   *     used for the animation and value is the number of frames.
   */
  public AnimationMap(List<Map.Entry<String, Integer>> animationList) {
    this.animationMap = new HashMap<>();
    for (Map.Entry<String, Integer> entry : animationList) {
      insertIntoAnimationMap(entry.getKey(), entry.getValue());
    }
  }

  /**
   * @return true if this {@link AnimationMap} contains no key-value mappings.
   */
  public boolean isEmpty() {
    return animationMap.isEmpty();
  }

  /**
   * Returns true if this map contains a mapping for the specified key.
   *
   * @param key the key whose presence in this {@link AnimationMap} is to be tested
   * @return true if this map contains a mapping for the specified key
   */
  public boolean containsKey(String key) {
    return animationMap.containsKey(key);
  }

  private void insertIntoAnimationMap(String path, Integer frames) {
    Direction direction =
        switch (path.toLowerCase().replaceFirst(".*/.*(left|right|up|down).*", "$1")) {
          case "left" -> Direction.LEFT;
          case "right" -> Direction.RIGHT;
          case "up" -> Direction.UP;
          case "down" -> Direction.DOWN;
          default ->
              throw new IllegalArgumentException(
                  "Cannot recognise direction. Make sure that the filename of the animation is formatted correctly.");
        };
    String animationName = path.replaceFirst(".*/(.*)(?i)(left|right|up|down).*", "$1");

    Animation<TextureRegion> animation =
        AnimationHelperMethods.createTextureRegionAnimation(new Texture(path), frames);

    if (!animationMap.containsKey(animationName)) {
      animationMap.put(animationName, new HashMap<>());
    }

    HashMap<Direction, Animation<TextureRegion>> directionMap = animationMap.get(animationName);
    directionMap.put(direction, animation);
  }

  /**
   * Returns the animation to which the specified name and direction is mapped, or null if this
   * animation map contains no mapping for the two given keys in combination. A return value of null
   * does not necessarily indicate that the animation map contains no mapping for the name-key, just
   * that there is no animation given for the name and direction in combination. The {@link
   * #containsKey(String)} operation can be used to distinguish these two cases.
   *
   * @param name of the animation group
   * @param direction of the animation
   * @return the {@link Animation<TextureRegion>} to which the specified name and direction is
   *     mapped, or null if this animation map contains no mapping for the two given keys in
   *     combination.
   */
  public Animation<TextureRegion> getAnimation(String name, Direction direction) {
    return animationMap.get(name).get(direction);
  }

  /**
   * Adds a new animation to the animation map. If the given animation group already exists, the new
   * animation will be added into this group. If not, a new animation group will be created. If the
   * map already contains an animation in the given group and direction, this animation will be
   * replaced by the new animation.
   *
   * @param animation A {@link Map.Entry} containing the name of the animation group and the
   *     direction of the animation.
   */
  public void addAnimation(Map.Entry<String, Integer> animation) {
    insertIntoAnimationMap(animation.getKey(), animation.getValue());
  }
}
