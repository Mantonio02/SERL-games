package game.model;

import static game.utils.Constants.PPM;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import game.collision.CollisionListener;
import game.controller.ControllableModel;
import game.entities.Door;
import game.entities.movingEntities.attackEntities.enemies.Boss;
import game.entities.movingEntities.attackEntities.enemies.Enemy;
import game.entities.movingEntities.attackEntities.player.Player;
import game.entities.npc.Merchant;
import game.entities.npc.NPC;
import game.entities.weapons.Club;
import game.entities.weapons.Weapon;
import game.level.*;
import game.utils.Keybindings;
import game.view.screens.GameOver;
import game.view.screens.GameScreen;
import game.view.screens.ShopMenu;
import game.view.screens.StartMenuScreen;
import java.util.HashSet;

public class Model extends Game implements ControllableModel {
  private Player player;
  private LevelStorage levelStorage;
  private Level currentLevel;
  private TmxMapLoader mapLoader;
  private TiledMap map;
  private World world;
  private boolean debugMode;
  private BitmapFont font;
  private SpriteBatch batch;
  private boolean isGameOver = false;
  // TODO: Change playerKeyCodes and interactionKeyCodes to cooperate with Keybindings?
  private HashSet<Integer> playerKeyCodes;
  private HashSet<Integer> interactionKeyCodes;
  private boolean shopOpened = false;
  private Keybindings keybindings;

  // BIT, as per this tutorial:
  // https://www.youtube.com/watch?v=Br9fF4xcL94&list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt&index=14
  public static final short DEFAULT = 1;
  public static final short PLAYER_BIT = 1 << 1;
  public static final short INACTIVE_WEAPON_BIT = 1 << 2;
  public static final short ACTIVE_WEAPON_BIT = 1 << 3;
  public static final short MAP_OBJECT_BIT = 1 << 4;
  public static final short ENEMY_BIT = 1 << 5;
  public static final short DOOR_BIT = 1 << 6;
  private GameScreen gameScreen;

  public Model() {
    this.debugMode = false;
  }

  @Override
  public void create() {
    // Would have liked to make a startGame method so the game doesn't run in the background of the
    // main menu.
    // But it caused so much issue with testing that it wasn't worth it. Worth noting that the stage
    // stops acting when another screen is active, so you can't for example be killed by an enemy
    // while in a shop.
    CollisionListener collisionListener = new CollisionListener();
    world = new World(new Vector2(0, 0), true);
    world.setContactListener(collisionListener);
    keybindings = new Keybindings();

    player = new Player(keybindings);
    player.createBody(world);
    player.setHud();
    player.setWeapon(new Club(player));
    player.getWeapon().createBody(world);

    playerKeyCodes = new HashSet<>();
    interactionKeyCodes = new HashSet<>();
    updateKeyCode();

    levelStorage = new LevelStorage(world, player, keybindings);
    mapLoader = new TmxMapLoader();
    loadNewLevel(0);

    if (!debugMode) {
      batch = new SpriteBatch();
      font = new BitmapFont();
      setScreen(new StartMenuScreen(this));
    }
  }

  public void loadNewLevel(int level) {
    currentLevel = levelStorage.loadLevel(level);
    map = mapLoader.load(currentLevel.getMap());
    parseMap();
  }

  /** Updated key codes, should be called if keycode is changed. */
  public void updateKeyCode() {
    for (String keycode : keybindings.getKeyBindingMap().keySet()) {
      if (keybindings.isMovementKeycode(keycode)) {
        playerKeyCodes.add(keybindings.getBinding(keycode));
      } else {
        interactionKeyCodes.add(keybindings.getBinding(keycode));
      }
      for (int i = 0; i < 9; i++) {
        playerKeyCodes.add(Input.Keys.NUM_1 + i);
      }
    }
  }

  @Override
  public void resize(int i, int i1) {
    super.resize(i, i1);
  }

  private void removeDeadEnemies() {
    var enemies = currentLevel.getFoes();
    if (!enemies.isEmpty() && !enemies.get(0).getDeleteList().isEmpty()) {
      for (var body : enemies.get(0).getDeleteList()) {
        world.destroyBody(body);
      }
      enemies.get(0).getDeleteList().clear();
    }
  }

  private void addSpawnedEnemies() {
    var enemies = currentLevel.getFoes();
    for (Enemy enemy : enemies) {
      if (enemy instanceof Boss) {
        for (Enemy newEnemy : ((Boss) enemy).getAddList()) {
          newEnemy.createBody(world);
          newEnemy.setTarget(player);
          getGameScreen().addNewEnemies(newEnemy);
        }
        ((Boss) enemy).getAddList().clear();
      }
    }
  }

  private void checkIfShopOpened() {
    for (NPC friend : currentLevel.getFriends()) {
      if (friend instanceof Merchant) {
        if (((Merchant) friend).playerIsBuying() && !shopOpened) {
          setScreen(new ShopMenu(this, (Merchant) friend));
          shopOpened = true;
        }
      }
    }
  }

  private void checkIfTravel() {
    for (Door door : currentLevel.getDoors()) {

      // ============ Variables for sake of readability. ============ //
      boolean playerDependent = door.isPlayerDependent();
      boolean collidingWithPlayer = door.isCollidingWithPlayer();
      // ============================================================ //

      if (playerDependent) {
        boolean pKeyPressed = player.isPKeyPressed();
        if (collidingWithPlayer && pKeyPressed) {
          handleLevelTransfer(door);
        }
      } else {
        if (collidingWithPlayer) {
          handleLevelTransfer(door);
        }
      }
    }
  }

  private void update(float deltaTime) {
    world.step(deltaTime, 6, 2);
    removeDeadEnemies();
    addSpawnedEnemies();
    checkIfShopOpened();
    checkIfTravel();

    if (isDead() && !isGameOver) {
      isGameOver = true;
      setScreen(new GameOver(this));
    }
  }

  /**
   * Based off body removal code from <a
   * href="https://stackoverflow.com/questions/33063231/how-to-remove-just-one-body-exactly-in-array-when-collision-libgdx">StackOverflow</a>
   */
  private void removeNonPlayerBodies() { // Called upon prior to loadNewLevel.
    Array<Body> bodies = new Array<>(world.getBodyCount());
    world.getBodies(bodies);
    for (Body body : bodies) {
      if (body.getUserData() instanceof Player || body.getUserData() instanceof Weapon) {
        continue;
      }
      world.destroyBody(body);
    }
  }

  private void setNewPlayerPosition(Door door) {
    float x = door.getToPosition().x;
    float y = door.getToPosition().y;

    this.player.getBody().setTransform(x / PPM, y / PPM, 0);
  }

  /**
   * Called upon whenever certain criteria are met for Player and Door in render().
   *
   * @param door > the door to get level ID and toPosition from.
   */
  private void handleLevelTransfer(Door door) {
    player.setMovementVec(new Vector2());
    setNewPlayerPosition(door);
    removeNonPlayerBodies();
    loadNewLevel(door.getToLevel());
    goToGameScreen(); // Called to update view.
  }

  @Override
  public void render() {
    update(Gdx.graphics.getDeltaTime());
    super.render();
  }

  @Override
  public void pause() {
    super.pause();
  }

  @Override
  public void resume() {
    super.resume();
  }

  @Override
  public void dispose() {
    super.dispose();
    world.dispose();
    map.dispose();
    font.dispose();
    Gdx.input.setInputProcessor(null);
  }

  public Player getPlayer() {
    return player;
  }

  @Override
  public Level getCurrentLevel() {
    return currentLevel;
  }

  public TiledMap getMap() {
    return map;
  }

  public World getWorld() {
    return world;
  }

  public SpriteBatch getBatch() {
    return batch;
  }

  public BitmapFont getFont() {
    return font;
  }

  /** Source: <a href="https://lyze.dev/2021/03/25/libGDX-Tiled-Box2D-example/">...</a> * */
  private BodyDef getBodyDef(float x, float y) {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.StaticBody;
    bodyDef.position.set(x / PPM, y / PPM);

    return bodyDef;
  }

  /** Source: <a href= "https://lyze.dev/2021/03/25/libGDX-Tiled-Box2D-example-objects/">...</a> */
  private void parseMap() {
    var collisionLayer = map.getLayers().get("Collision");
    if (collisionLayer == null) {
      return;
    }
    MapObjects collisions = map.getLayers().get("Collision").getObjects();

    for (int i = 0; i < collisions.getCount(); i++) {
      MapObject mapObject = collisions.get(i);

      RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
      Rectangle rectangle = rectangleMapObject.getRectangle();

      BodyDef bodyDef =
          getBodyDef(
              rectangle.getX() + rectangle.getWidth() / 2f,
              rectangle.getY() + rectangle.getHeight() / 2f);

      Body body = world.createBody(bodyDef);
      PolygonShape polygonShape = new PolygonShape();
      polygonShape.setAsBox(rectangle.getWidth() / 2f / PPM, rectangle.getHeight() / 2f / PPM);
      FixtureDef fDef = new FixtureDef();
      fDef.shape = polygonShape;
      fDef.filter.categoryBits = Model.MAP_OBJECT_BIT;
      fDef.filter.maskBits = Model.PLAYER_BIT | Model.ENEMY_BIT;
      body.createFixture(fDef);
      polygonShape.dispose();
    }
  }

  public void setDebugMode() {
    this.debugMode = true;
  }

  private boolean isDead() {
    return player.getHealth() <= 0;
  }

  public void resetDeathFlag() {
    isGameOver = false;
  }

  @Override
  public boolean handlePlayerInput(int keycode, boolean down) {
    return player.handleInput(keycode, down);
  }

  @Override
  public boolean handlePlayerScroll(float amountY) {
    return player.handleScroll(amountY);
  }

  @Override
  public boolean handleNPCInput(NPC npc, int keycode, boolean down) {
    return npc.handleInput(keycode, down);
  }

  public Keybindings getKeybindings() {
    return keybindings;
  }

  @Override
  public HashSet<Integer> getPlayerKeyCodes() {
    return this.playerKeyCodes;
  }

  @Override
  public HashSet<Integer> getInteractionKeys() {
    return this.interactionKeyCodes;
  }

  public void goToGameScreen() {
    gameScreen = new GameScreen(this);
    setScreen(gameScreen);
    shopOpened = false;
  }

  public GameScreen getGameScreen() {
    return gameScreen;
  }
}
