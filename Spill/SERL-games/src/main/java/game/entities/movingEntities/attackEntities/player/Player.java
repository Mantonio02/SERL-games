package game.entities.movingEntities.attackEntities.player;

import static game.utils.Constants.MAX_MAP_HEIGHT;
import static game.utils.Constants.MAX_MAP_WIDTH;
import static game.utils.Constants.PPM;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import game.entities.Direction;
import game.entities.Entity;
import game.entities.movingEntities.attackEntities.AttackEntity;
import game.entities.movingEntities.attackEntities.enemies.ITarget;
import game.entities.weapons.Weapon;
import game.items.IItem;
import game.model.Model;
import game.utils.Keybindings;
import java.util.Arrays;
import java.util.Map;
import util.ExcludeFromGeneratedCoverageReport;

/** Represents the playable character. */
public class Player extends AttackEntity implements ITarget {
  private final PlayerInputHandler inputHandler;
  private Weapon weapon;
  private HUD hud;
  private final IItem[] items;
  private final Vector2 startPosition; // This should be a Level attribute
  private boolean sprint;
  private int experience;
  private int level;
  private final float sprintSpeedBonus = 2f;
  private final Vector2 gripRight = new Vector2(4, -9);
  private final Vector2 gripLeft = new Vector2(-4, -9);
  private final Vector2 gripUp = new Vector2(0, -9);
  private final Vector2 gripDown = new Vector2(0, -9);

  public Player(Keybindings keybindings) {
    super(
        4,
        Arrays.asList(
            Map.entry("sprites/playerSprites/IdleLeft.png", 1),
            Map.entry("sprites/playerSprites/IdleRight.png", 1),
            Map.entry("sprites/playerSprites/IdleUp.png", 1),
            Map.entry("sprites/playerSprites/IdleDown.png", 1),
            Map.entry("sprites/playerSprites/RunLeft.png", 4),
            Map.entry("sprites/playerSprites/RunRight.png", 4),
            Map.entry("sprites/playerSprites/RunUp.png", 4),
            Map.entry("sprites/playerSprites/RunDown.png", 4),
            Map.entry("sprites/playerSprites/AttackLeft.png", 5),
            Map.entry("sprites/playerSprites/AttackRight.png", 5),
            Map.entry("sprites/playerSprites/AttackUp.png", 5),
            Map.entry("sprites/playerSprites/AttackDown.png", 5)));
    idle();
    setSize(getKeyFrame().getRegionWidth(), getKeyFrame().getRegionHeight());

    this.inputHandler = new PlayerInputHandler(this, keybindings);

    this.setPosition(
        MAX_MAP_WIDTH / 2f - this.getKeyFrame().getRegionWidth() / 2f,
        MAX_MAP_HEIGHT / 2f + this.getKeyFrame().getRegionHeight() / 2f);

    this.startPosition =
        new Vector2(
            MAX_MAP_WIDTH / 2f - this.getKeyFrame().getRegionWidth() / 2f,
            MAX_MAP_HEIGHT / 2f + this.getKeyFrame().getRegionHeight() / 2f);

    this.setMaxHealth(100);
    this.setHealth(this.getMaxHealth());
    this.setStrength(40);
    this.items = new IItem[9];
    this.experience = 0;
    this.level = 1;
    sprint = false;
  }

  /** Set HUD to player. */
  public void setHud() {
    this.hud = new HUD(this);
  }

  @ExcludeFromGeneratedCoverageReport
  public void drawHUD(Batch batch, OrthographicCamera camera) {
    this.hud.draw(batch, camera);
  }

  private void updateMovementVector(float deltaTime) {
    if (!isAttacking()) {
      float moveValue = numPixelsToMove(getMovementSpeed(), deltaTime);
      if (sprint) moveValue *= sprintSpeedBonus;

      float xSpeed = inputHandler.getXDirection() * moveValue;
      float ySpeed = inputHandler.getYDirection() * moveValue;
      setMovementVec(new Vector2(xSpeed, ySpeed));
    } else setMovementVec(Vector2.Zero);
  }

  @Override
  public void act(float deltaTime) {
    updateMovementVector(deltaTime);
    super.act(deltaTime);
  }

  @Override
  protected void onDeath() {
    this.loseAllXp();
    this.inputHandler.flush();
  }

  @Override
  public void createBody(World world) {
    super.createBody(world);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(this.getWidth() / 2f / PPM, this.getHeight() / 2f / PPM);
    FixtureDef fDef = new FixtureDef();
    fDef.shape = shape;

    // From tutorial:
    // https://www.youtube.com/watch?v=Br9fF4xcL94&list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt&index=14
    fDef.filter.categoryBits = Model.PLAYER_BIT;
    fDef.filter.maskBits = Model.DEFAULT | Model.MAP_OBJECT_BIT | Model.ENEMY_BIT | Model.DOOR_BIT;

    getBody().createFixture(fDef);
    shape.dispose();
  }

  /**
   * Gets the coordinates of the {@link Player}'s grip in the current {@link Direction}. These
   * coordinates can be used to place objects like a {@link Weapon} in the Player's hands.
   *
   * @return the coordinates of the {@link Player}'s grip in the current {@link Direction}.
   */
  public Vector2 getCurrentGrip() {
    return switch (this.getCurrentDirection()) {
      case RIGHT -> gripRight;
      case LEFT -> gripLeft;
      case UP -> gripUp;
      case DOWN -> gripDown;
    };
  }

  /**
   * Sets whether the Player should sprint or not.
   *
   * @param sprint whether the Player should sprint or not.
   */
  public void setSprint(boolean sprint) {
    this.sprint = sprint;
  }

  /** Respawns the Player. Can be used to continue playing after death. */
  public void respawn() {
    this.getBody().setTransform(this.startPosition.x / PPM, this.startPosition.y / PPM, 0);
    this.setHealth(this.getMaxHealth());
  }

  /**
   * Handles user input via keyboard.
   *
   * @param keycode the key that was triggered.
   * @param down whether the key is down or up.
   * @return whether the input was processed.
   */
  public boolean handleInput(int keycode, boolean down) {
    if (down) return this.inputHandler.handleInputDown(keycode);
    else return this.inputHandler.handleInputUp(keycode);
  }

  /**
   * Handles user input via scrolling.
   *
   * @param amountY the amount that was scrolled on the y-axis
   * @return whether the input was processed.
   */
  public boolean handleScroll(float amountY) {
    return this.inputHandler.handleScroll(amountY);
  }

  @Override
  public void startAttacking() {
    super.startAttacking();
    weapon.startAttacking();
  }

  /**
   * Sets the given {@link Weapon} as the Player's current weapon.
   *
   * @param newWeapon the Weapon to set as the Player's current weapon.
   */
  public void setWeapon(Weapon newWeapon) {
    this.weapon = newWeapon;
  }

  @Override
  public Vector2 getBodyPosition() {
    return body.getWorldCenter();
  }

  /**
   * Increase the players xp.
   *
   * @param xp How much xp should be increased.
   */
  public void increaseXp(int xp) {
    this.experience += xp;
    if (this.experience >= 80) {
      this.experience -= 50;
      this.levelUp(1);
    }
  }

  /**
   * Player level up
   *
   * @param levels the number of levels to level up by
   */
  public void levelUp(int levels) {
    this.level += levels;
    this.setStrength(this.getStrength() + levels);
    this.setMaxHealth(this.getMaxHealth() + levels * 3);
  }

  /**
   * Set player level
   *
   * @param level the level the player should end up at
   */
  public void setLevel(int level) {
    if (this.level < level) {
      levelUp(level - this.level);
    } else {
      this.level = level;
    }
  }

  /**
   * Spend the players XP. The amount of xp the Player has should never go below 0.
   *
   * @param xp How much xp should be used.
   */
  public void spendXP(int xp) {
    this.experience = Math.max(0, this.experience - xp);
  }

  /** Set Player's xp to 0. */
  public void loseAllXp() {
    this.experience = 0;
  }

  public int getXp() {
    return this.experience;
  }

  public void setXp(int xp) {
    this.experience = xp;
  }

  public Weapon getWeapon() {
    return weapon;
  }

  public HUD getHud() {
    return hud;
  }

  public IItem[] getItems() {
    return items;
  }

  /**
   * Consumes the item that is currently selected in the {@link HotBar} of the Player. Activates any
   * buffs the item gives.
   */
  public void consumeSelectedItem() {
    consumeItem(this.hud.getHotBar().getSelectedItem());
  }

  /**
   * Consumes a given item. Activates any buffs the item gives.
   *
   * @param index the index of the item in the Player's {@link HotBar}.
   */
  public void consumeItem(int index) {
    if (this.items[index] != null) {
      this.items[index].interact(this);
      this.items[index] = null;
    }
  }

  /**
   * Adds a given item to the {@link HotBar} of the Player.
   *
   * @param item the item to add.
   */
  public void addItem(IItem item) {
    for (int i = 0; i < this.items.length; i++) {
      if (this.items[i] == null) {
        this.items[i] = item;
        return;
      }
    }
  }

  // TODO: Apply force to Player when Monsters attack so Player is pushed back a little.
  @Override
  public void onCollision(Entity otherEntity) {}

  @Override
  public void postCollision() {}

  public int getLevel() {
    return this.level;
  }

  public boolean isPKeyPressed() {
    return this.inputHandler.isPKeyPressed();
  }
}
