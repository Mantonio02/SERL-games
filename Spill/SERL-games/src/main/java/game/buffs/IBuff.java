package game.buffs;

import game.entities.movingEntities.attackEntities.AttackEntity;

public interface IBuff {
  /**
   * Activate the buff
   *
   * @param target the {@link AttackEntity} that the buff should affect
   */
  void activate(AttackEntity target);

  /**
   * Deactivate the buff
   *
   * @param target the {@link AttackEntity} that the buff should affect
   */
  void deactivate(AttackEntity target);

  /**
   * Get the time at which the buff runs out in milliseconds since UNIX epoch
   *
   * @return the end time
   */
  long getEndTime();
}
