package game.buffs;

import game.entities.movingEntities.attackEntities.AttackEntity;

public class SpeedBuff implements IBuff {
  private final long endTime;
  private final float multiplier;

  /**
   * Creates an {@link IBuff} that, upon activation, multiplies the movement speed of the {@link
   * AttackEntity} it is used on by a given multiplier for a given amount of time.
   *
   * @param duration of the effects of the buff.
   * @param multiplier to multiply the {@link AttackEntity}'s movement speed by.
   */
  public SpeedBuff(long duration, float multiplier) {
    this.endTime = System.currentTimeMillis() + duration;
    this.multiplier = multiplier;
  }

  @Override
  public void activate(AttackEntity target) {
    target.setMovementSpeed(multiplier * target.getMovementSpeed());
  }

  @Override
  public void deactivate(AttackEntity target) {
    target.setMovementSpeed(target.getMovementSpeed() / multiplier);
  }
  ;

  @Override
  public long getEndTime() {
    return this.endTime;
  }

  @Override
  public String toString() {
    return String.format("Speed x %f", this.multiplier);
  }
}
