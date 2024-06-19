package game.entities.weapons;

import com.badlogic.gdx.physics.box2d.*;
import game.entities.Direction;
import game.entities.movingEntities.attackEntities.player.Player;
import game.utils.Constants;
import java.util.Arrays;
import java.util.Map;

public class Club extends Weapon {
  public static final float WEAPON_WIDTH = 30;
  public static final float WEAPON_HEIGHT = 48;
  private static final float ROTATION_DEGREES = 90;

  /**
   * Create new Club positioned so that the Weapon grip overlaps with the Player grip.
   *
   * @param player the Player that holds the Club
   */
  public Club(Player player) {
    super(
        Arrays.asList(
            Map.entry("sprites/weaponSprites/Club/AttackLeft.png", 5),
            Map.entry("sprites/weaponSprites/Club/AttackRight.png", 5),
            Map.entry("sprites/weaponSprites/Club/AttackUp.png", 5),
            Map.entry("sprites/weaponSprites/Club/AttackDown.png", 5)),
        player);
    animationHandler.setCurrentAnimation("Attack", getCurrentDirection());
    setSize(getKeyFrame().getRegionWidth(), getKeyFrame().getRegionHeight());
    this.weaponWidth = WEAPON_WIDTH;
    this.weaponHeight = WEAPON_HEIGHT;
    setWeaponGrip(getWidth() / 2f, getHeight() / 2f);
  }

  @Override
  public float getAngle(Direction direction) {
    if (direction.equals(Direction.LEFT)) {
      return 45;
    } else if (direction.equals(Direction.DOWN)) {
      return 135;
    } else return -45;
  }

  // Got help from Bing Copilot to design this method.
  @Override
  public void attack() {
    float currentFrameTime = Math.min(getStateTime(), getCurrentAnimation().getAnimationDuration());
    float rotation =
        currentFrameTime
            / animationHandler.getCurrentAnimation().getAnimationDuration()
            * ROTATION_DEGREES;

    if (direction == Direction.RIGHT) {
      body.setTransform(
          body.getWorldCenter(), (getAngle(direction) - rotation) * Constants.DEGREE_TO_RADIAN);
    } else {
      body.setTransform(
          body.getWorldCenter(), (getAngle(direction) + rotation) * Constants.DEGREE_TO_RADIAN);
    }
  }
}
