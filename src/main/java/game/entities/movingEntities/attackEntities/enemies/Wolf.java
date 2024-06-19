package game.entities.movingEntities.attackEntities.enemies;

import java.util.Arrays;
import java.util.Map;

public class Wolf extends Enemy {

  public static final int FOLLOW_DISTANCE = 5;
  public static final int MAX_HEALTH = 100;
  public static final int STRENGTH = 5;

  public Wolf() {
    super(
        FOLLOW_DISTANCE,
        Arrays.asList(
            Map.entry("sprites/enemySprites/wolf/IdleLeft.png", 1),
            Map.entry("sprites/enemySprites/wolf/IdleRight.png", 1),
            Map.entry("sprites/enemySprites/wolf/IdleUp.png", 1),
            Map.entry("sprites/enemySprites/wolf/IdleDown.png", 1),
            Map.entry("sprites/enemySprites/wolf/RunLeft.png", 4),
            Map.entry("sprites/enemySprites/wolf/RunRight.png", 4),
            Map.entry("sprites/enemySprites/wolf/RunUp.png", 4),
            Map.entry("sprites/enemySprites/wolf/RunDown.png", 4),
            Map.entry("sprites/enemySprites/wolf/AttackLeft.png", 6),
            Map.entry("sprites/enemySprites/wolf/AttackRight.png", 6),
            Map.entry("sprites/enemySprites/wolf/AttackUp.png", 6),
            Map.entry("sprites/enemySprites/wolf/AttackDown.png", 6)));

    idle();
    setWidth(getKeyFrame().getRegionWidth());
    setHeight(getKeyFrame().getRegionHeight());

    this.setAttackSound("sounds/mixkit-wolf-attack-1773.mp3");
    this.setMaxHealth(MAX_HEALTH);
    this.setHealth(MAX_HEALTH);
    this.setStrength(STRENGTH);
  }
}
