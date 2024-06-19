package game.view.animations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationHelperMethods {

  /**
   * Creates an {@link Animation<TextureRegion> TextureRegion Animation} by dividing the given
   * {@link Texture} into the given amount of frames.
   *
   * @param sheet the {@link Texture} to divide into frames
   * @param frames the number of frames
   * @return the finished {@link Animation<TextureRegion>}
   */
  public static Animation<TextureRegion> createTextureRegionAnimation(
      Texture sheet, Integer frames) {
    TextureRegion[][] tmpFrames =
        TextureRegion.split(sheet, sheet.getWidth() / frames, sheet.getHeight());
    TextureRegion[] animationFrames = new TextureRegion[frames];
    System.arraycopy(tmpFrames[0], 0, animationFrames, 0, frames);
    return new Animation<TextureRegion>(0.1f, animationFrames);
  }
}
