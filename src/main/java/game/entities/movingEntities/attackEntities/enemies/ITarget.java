package game.entities.movingEntities.attackEntities.enemies;

import com.badlogic.gdx.math.Vector2;
import game.entities.Direction;

public interface ITarget {

  float getX();

  float getY();

  Vector2 getBodyPosition();

  void takeDamage(int strength);

  void increaseXp(int i);

  Direction getCurrentDirection();
}
