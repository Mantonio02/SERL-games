package game.entities.movingEntities.attackEntities.enemies;

import com.badlogic.gdx.math.Vector2;
import game.entities.Entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class Boss extends Enemy {
  private static final int FOLLOW_DISTANCE = 5;
  private static final float ATTACK_COOLDOWN_UPPER = 4f;
  private static final int MAX_HEALTH = 300;
  private boolean wolfSpawned = false;
  private boolean canAttack = false;
  private float attackCoolDownCounter = 0f;
  private final ArrayList<Enemy> addList = new ArrayList<>();

  public Boss() {
    super(
        FOLLOW_DISTANCE,
        Arrays.asList(
            Map.entry("sprites/enemySprites/bossSprites/IdleLeft.png", 1),
            Map.entry("sprites/enemySprites/bossSprites/IdleRight.png", 1),
            Map.entry("sprites/enemySprites/bossSprites/IdleUp.png", 1),
            Map.entry("sprites/enemySprites/bossSprites/IdleDown.png", 1),
            Map.entry("sprites/enemySprites/bossSprites/RunLeft.png", 2),
            Map.entry("sprites/enemySprites/bossSprites/RunRight.png", 2),
            Map.entry("sprites/enemySprites/bossSprites/RunUp.png", 2),
            Map.entry("sprites/enemySprites/bossSprites/RunDown.png", 2),
            Map.entry("sprites/enemySprites/bossSprites/AttackLeft.png", 9),
            Map.entry("sprites/enemySprites/bossSprites/AttackRight.png", 9),
            Map.entry("sprites/enemySprites/bossSprites/AttackUp.png", 9),
            Map.entry("sprites/enemySprites/bossSprites/AttackDown.png", 9)));
    idle();
    setSize(getKeyFrame().getRegionWidth(), getKeyFrame().getRegionHeight());
    this.setMaxHealth(MAX_HEALTH);
    this.setHealth(MAX_HEALTH);
    this.setStrength(0);
    this.setMovementSpeed(1f);
    this.setAttackSound("sounds/leshen_scream.mp3");
  }

  @Override
  public void act(float deltaTime) {
    if (getTarget() != null) {
      Vector2 targetVec = getTarget().getBodyPosition();
      float speed = this.numPixelsToMove(this.getMovementSpeed(), deltaTime);
      float dist = targetVec.dst(this.getBody().getPosition());
      setMovementVec(targetVec.sub(this.getBody().getPosition()).nor().scl(speed));

      if (canAttack && attackCoolDownCounter <= 0) {
        if (!isAttacking()) {
          super.startAttacking();
        }
        setStateTime(getStateTime() + deltaTime);

        if (!wolfSpawned) {
          Random rand = new Random();
          int range = 50;
          int x = rand.nextInt(range) + range;
          int y = rand.nextInt(range) + range;
          Wolf wolf = new Wolf();
          wolf.setPosition((getTarget().getX() - x), (getTarget().getY() - y));
          addList.add(wolf);
          wolfSpawned = true;
        }
        if (this.getCurrentAnimation().isAnimationFinished(this.getStateTime())) {
          super.stopAttacking();
          attackCoolDownCounter = ATTACK_COOLDOWN_UPPER;
          wolfSpawned = false;
        }
        return;
      }
      handleWalking(dist);
      if (attackCoolDownCounter > 0) {
        attackCoolDownCounter -= deltaTime;
      }
    } else {
      this.idle();
    }
    super.act(deltaTime);
  }

  private void handleWalking(float dist) {
    if (dist > FOLLOW_DISTANCE && dist < FOLLOW_DISTANCE * 1.5) {
      startRunning(getTarget().getCurrentDirection());
      this.canAttack = false;
    } else {
      this.canAttack = true;
      this.setMovementVec(Vector2.Zero);
      this.stopRunning();
      this.idle();
    }
  }

  @Override
  public void onCollision(Entity otherEntity) {}

  @Override
  public void postCollision() {}

  public EnemyHealthBar getBossHealthBar() {
    return new EnemyHealthBar(this, 300, 20, "Ancient Leshen");
  }

  public ArrayList<Enemy> getAddList() {
    return addList;
  }
}
