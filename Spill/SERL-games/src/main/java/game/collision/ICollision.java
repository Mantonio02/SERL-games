package game.collision;

public interface ICollision<T> {

  /**
   * How to act upon collision with another Entity.
   *
   * @param otherEntity the entity that has collided
   */
  void onCollision(T otherEntity);

  /** How to act when collision is over. */
  void postCollision();
}
