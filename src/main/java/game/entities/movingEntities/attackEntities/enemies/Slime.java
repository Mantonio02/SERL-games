package game.entities.movingEntities.attackEntities.enemies;

import java.util.Arrays;
import java.util.Map;

/** Represent a slime monster which has low health, but hit hard. */
public class Slime extends Enemy {
  public static final int FOLLOW_DISTANCE = 4;
  public static final int MAX_HEALTH = 30;
  public static final int STRENGTH = 10;

  public Slime() {
    super(
        FOLLOW_DISTANCE,
        Arrays.asList(
            Map.entry("sprites/enemySprites/slime/IdleLeft.png", 1),
            Map.entry("sprites/enemySprites/slime/IdleRight.png", 1),
            Map.entry("sprites/enemySprites/slime/IdleUp.png", 1),
            Map.entry("sprites/enemySprites/slime/IdleDown.png", 1),
            Map.entry("sprites/enemySprites/slime/RunLeft.png", 2),
            Map.entry("sprites/enemySprites/slime/RunRight.png", 2),
            Map.entry("sprites/enemySprites/slime/RunUp.png", 2),
            Map.entry("sprites/enemySprites/slime/RunDown.png", 2)));

    idle();
    setWidth(getKeyFrame().getRegionWidth());
    setHeight(getKeyFrame().getRegionHeight());

    this.setMaxHealth(MAX_HEALTH);
    this.setHealth(MAX_HEALTH);
    this.setStrength(STRENGTH);
  }

  @Override
  public void startAttacking() {
    animationHandler.setCurrentAnimation("Run", direction);
  }
}
