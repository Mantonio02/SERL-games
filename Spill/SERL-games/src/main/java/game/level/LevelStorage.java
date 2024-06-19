package game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import game.entities.Door;
import game.entities.movingEntities.attackEntities.enemies.Enemy;
import game.entities.movingEntities.attackEntities.player.Player;
import game.entities.npc.NPC;
import game.utils.Constants;
import game.utils.Keybindings;
import java.util.ArrayList;

/**
 * Processes json file for level data and creates {@link Level} objects from the data (like a
 * factory :D)
 */
public class LevelStorage {
  private World world;
  private Player player;
  private Keybindings keybindings;
  private final Json json = new Json();
  private final JsonValue levelMap;

  /**
   * Creates a levelStorage for the default file path "levelData/levelData.json".
   *
   * @param world of the game
   * @param player of the game
   * @param keybindings of the game
   */
  public LevelStorage(World world, Player player, Keybindings keybindings) {
    this.world = world;
    this.player = player;
    this.keybindings = keybindings;
    JsonReader jReader = new JsonReader();
    levelMap = jReader.parse(Gdx.files.internal("levelData/levelData.json"));
  }

  /**
   * Create a LevelStorage with a given file path to the level map for testing purposes.
   *
   * @param world of the game
   * @param player of the game
   * @param keybindings of the game
   * @param levelMap path to json file with level data
   */
  public LevelStorage(World world, Player player, Keybindings keybindings, String levelMap) {
    this.world = world;
    this.player = player;
    this.keybindings = keybindings;
    JsonReader jReader = new JsonReader();
    this.levelMap = jReader.parse(Gdx.files.internal(levelMap));
  }

  /**
   * Creates a new {@link Level} by reading the level with the given levelID from the level data.
   *
   * @param levelID id of the level to load.
   * @return the loaded {@link Level}.
   */
  public Level loadLevel(int levelID) {
    JsonValue levelInfo = levelMap.get(levelID);
    String map = json.readValue(String.class, levelInfo.get("map"));

    JsonValue jsonFriends = levelInfo.get("friends");
    ArrayList<NPC> friends = new ArrayList<>();
    for (int i = 0; i < jsonFriends.size; i++) {
      NPC friend = json.readValue(NPC.class, jsonFriends.get(i));
      friend.createBody(world);
      friend.setTarget(player);
      friend.setKeybindings(keybindings);
      friends.add(friend);
    }

    JsonValue jsonFoes = levelInfo.get("foes");
    ArrayList<Enemy> foes = new ArrayList<>();
    ArrayList<Body> deleteList = new ArrayList<>();
    for (int i = 0; i < jsonFoes.size; i++) {
      Enemy enemy = json.readValue(Enemy.class, jsonFoes.get(i));
      enemy.createBody(world);
      enemy.setTarget(player);
      enemy.setDeleteList(deleteList);
      foes.add(enemy);
    }

    JsonValue jsonDoors = levelInfo.get("doors");
    ArrayList<Door> doors = new ArrayList<>();
    for (int i = 0; i < jsonDoors.size; i++) {
      Door door = json.readValue(Door.class, jsonDoors.get(i));
      door.setWidth(Constants.PPM);
      door.setHeight(Constants.PPM);
      door.createBody(world);
      doors.add(door);
    }

    return new Level(map, friends, foes, doors);
  }
}
