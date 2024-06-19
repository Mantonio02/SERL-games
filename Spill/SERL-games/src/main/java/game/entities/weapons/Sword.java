package game.entities.weapons;

import com.badlogic.gdx.physics.box2d.*;
import game.entities.Direction;
import game.entities.movingEntities.attackEntities.player.Player;
import game.utils.Constants;
import java.util.Arrays;
import java.util.Map;

public class Sword extends Weapon {
  public static final float WEAPON_WIDTH = 4;
  public static final float WEAPON_HEIGHT = 30;
  private static final float ROTATION_DEGREES = 90;

  /**
   * Create new Sword positioned so that the Weapon grip overlaps with the Player grip.
   *
   * @param world the {@link World} to add the Weapon's body to
   */
  public Sword(Player player, World world) {
    super(
        Arrays.asList(
            Map.entry("sprites/weaponSprites/Sword/AttackRight.png", 5),
            Map.entry("sprites/weaponSprites/Sword/AttackLeft.png", 5),
            Map.entry("sprites/weaponSprites/Sword/AttackUp.png", 5),
            Map.entry("sprites/weaponSprites/Sword/AttackDown.png", 5)),
        player);
    animationHandler.setCurrentAnimation("Attack", getCurrentDirection());
    setSize(getKeyFrame().getRegionWidth(), getKeyFrame().getRegionHeight());
    this.weaponWidth = WEAPON_WIDTH;
    this.weaponHeight = WEAPON_HEIGHT;
    setWeaponGrip(getWidth() / 2f, getHeight() / 2f);
    createBody(world);
  }

  @Override
  public float getAngle(Direction direction) {
    if (direction.equals(Direction.LEFT)) {
      return 45;
    } else if (direction.equals(Direction.DOWN)) {
      return 135;
    } else return -45;
  }

  // Currently, we only have swinging weapons. Thus, attack() for Club and Sword are identical. But
  // having the weapon itself decide how the attack goes, allows later for other types of attacks,
  // like stabbing or throwing.
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
