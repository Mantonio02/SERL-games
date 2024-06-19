package game.buffs;

import game.entities.movingEntities.attackEntities.AttackEntity;

public class DamageBuff implements IBuff {
  private final long endTime;
  private final int damageBonus;

  /**
   * Creates an {@link IBuff} that, upon activation, grants a given bonus for a given amount of time
   * to the {@link AttackEntity} it is activated on.
   *
   * @param duration of the effects of the buff.
   * @param damageBonus to add to the {@link AttackEntity}'s strength.
   */
  public DamageBuff(long duration, int damageBonus) {
    this.endTime = System.currentTimeMillis() + duration;
    this.damageBonus = damageBonus;
  }

  @Override
  public void activate(AttackEntity target) {
    target.setStrength(this.damageBonus + target.getStrength());
  }

  @Override
  public void deactivate(AttackEntity target) {
    target.setStrength(target.getStrength() - this.damageBonus);
  }
  ;

  @Override
  public long getEndTime() {
    return this.endTime;
  }

  @Override
  public String toString() {
    return String.format("Damage + %d", this.damageBonus);
  }
}
