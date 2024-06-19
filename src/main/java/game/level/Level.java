package game.level;

import game.entities.Door;
import game.entities.movingEntities.attackEntities.enemies.Enemy;
import game.entities.npc.NPC;
import java.util.ArrayList;

public class Level {
  private final String map;
  private final ArrayList<NPC> friends;
  private final ArrayList<Enemy> foes;
  private final ArrayList<Door> doors;

  /**
   * Creates a new Level with a map, NPCs, enemies and doors to other levels.
   *
   * @param map internal path to map file.
   * @param friends list of {@link NPC}s of the Level.
   * @param foes list of {@link Enemy Enemies} of the Level.
   * @param doors list of {@link Door}s of the Level.
   */
  protected Level(
      String map, ArrayList<NPC> friends, ArrayList<Enemy> foes, ArrayList<Door> doors) {
    this.map = map;
    this.friends = friends;
    this.foes = foes;
    this.doors = doors;
  }

  public String getMap() {
    return map;
  }

  public ArrayList<NPC> getFriends() {
    return friends;
  }

  public ArrayList<Enemy> getFoes() {
    return foes;
  }

  public ArrayList<Door> getDoors() {
    return doors;
  }
}
