package game.entities.movingEntities.attackEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import game.buffs.IBuff;
import game.entities.movingEntities.MovingEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AttackEntity extends MovingEntity {
  private int maxHealth;
  private int health;
  private int strength;
  private Sound attackSound;
  private long lastAttacked; // TODO: Very misleading naming of the variable. Should be changed.
  private long immunityTime;
  private boolean isAttacking;
  private ArrayList<IBuff> activeBuffs;

  /**
   * Creates a new {@link AttackEntity} with the given movement speed and a given list of
   * animations.
   *
   * @param movementSpeed pixels per delta time
   * @param animationList the animations of the entity given by path name to the sprite sheet to be
   *     used and the number of frames.
   */
  public AttackEntity(float movementSpeed, List<Map.Entry<String, Integer>> animationList) {
    super(movementSpeed, animationList);
    this.isAttacking = false;
    this.lastAttacked = 0;
    this.immunityTime = 500;
    this.activeBuffs = new ArrayList<>();
  }

  @Override
  public void act(float deltaTime) {
    super.act(deltaTime);
    this.updateBuffs();
  }

  public void setStrength(int strength) {
    this.strength = strength;
  }

  public int getStrength() {
    return this.strength;
  }

  /**
   * @return the current amount of health.
   */
  public int getHealth() {
    return this.health;
  }

  /**
   * @return the maximum amount of health.
   */
  public int getMaxHealth() {
    return this.maxHealth;
  }

  /**
   * Sets the current amount of health to a given value.
   *
   * @param health the value to set the current health to.
   */
  public void setHealth(int health) {
    this.health = health;
  }

  /**
   * Sets the maximum amount of health to a given value.
   *
   * @param health the value to set the maximum health to.
   */
  protected void setMaxHealth(int health) {
    this.maxHealth = health;
  }

  /**
   * Sets the sound that plays on attack.
   *
   * @param path internal path of the sound file.
   */
  protected void setAttackSound(String path) {
    this.attackSound = Gdx.audio.newSound(Gdx.files.internal(path));
  }

  /** Stop the sound from playing. */
  protected void stopSound() {
    if (this.attackSound != null) {
      this.attackSound.stop();
    }
  }

  /**
   * Deducts a given amount of damage from the entity if the entity's immunity time has passed. If
   * the amount of damage is greater than the entity's health, the health is just reduced to 0. If
   * the health reaches 0, the entity is considered dead.
   *
   * @param damage the amount of points to deduct from the entity's current health.
   */
  public void takeDamage(int damage) {
    if (System.currentTimeMillis() - this.lastAttacked < this.immunityTime) {
      return;
    }
    // TODO: Is this necessary if we remove the body in the next rendering after death?
    if (this.health == 0) {
      return;
    }
    this.health -= Math.min(damage, this.health);
    this.lastAttacked = System.currentTimeMillis();

    if (this.health == 0) {
      this.onDeath();
    }
  }

  protected abstract void onDeath();

  /** Starts the attack process of the entity. */
  public void startAttacking() {
    if (this.attackSound != null) {
      this.attackSound.play(0.2f);
    }
    isAttacking = true;
    setStateTime(0);
    animationHandler.setCurrentAnimation("Attack", direction);
  }

  /** Ends the attack process of the entity. */
  public void stopAttacking() {
    isAttacking = false;
    setStateTime(0);
    idle();
  }

  public boolean isAttacking() {
    return this.isAttacking;
  }

  public ArrayList<IBuff> getActiveBuffs() {
    return this.activeBuffs;
  }

  /**
   * Adds a new {@link IBuff} to the list of {@link #activeBuffs}.
   *
   * @param buff the {@link IBuff} to add to {@link #activeBuffs}.
   */
  public void appendBuff(IBuff buff) {
    this.activeBuffs.add(buff);
    buff.activate(this);
  }

  private void updateBuffs() {
    long now = System.currentTimeMillis();
    for (int i = 0; i < activeBuffs.size(); i++) {
      if (activeBuffs.get(i).getEndTime() <= now) {
        activeBuffs.get(i).deactivate(this);
        activeBuffs.remove(i--);
      }
    }
  }
}
