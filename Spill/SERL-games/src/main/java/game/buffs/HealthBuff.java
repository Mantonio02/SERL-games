package game.buffs;

import game.entities.movingEntities.attackEntities.AttackEntity;

public class HealthBuff implements IBuff {
  private long endTime;
  private int healthBonus;

  /**
   * Creates an {@link IBuff} that, upon activation, grant a given health bonus for a given amount
   * of time to the {@link AttackEntity} it is activated on.
   *
   * @param duration of the effects of the buff.
   * @param healthBonus to add to the {@link AttackEntity}'s health.
   */
  public HealthBuff(long duration, int healthBonus) {
    this.endTime = System.currentTimeMillis() + duration;
    this.healthBonus = healthBonus;
  }

  @Override
  public void activate(AttackEntity target) {
    target.setHealth(target.getHealth() + healthBonus);
  }

  @Override
  public void deactivate(AttackEntity target) {
    target.setHealth(target.getHealth() - healthBonus);
  }

  @Override
  public long getEndTime() {
    return this.endTime;
  }

  @Override
  public String toString() {
    return "Health increased by " + healthBonus;
  }
}
