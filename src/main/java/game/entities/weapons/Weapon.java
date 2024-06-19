package game.entities.weapons;

import static game.utils.Constants.PPM;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import game.entities.Direction;
import game.entities.Entity;
import game.entities.movingEntities.attackEntities.enemies.Enemy;
import game.entities.movingEntities.attackEntities.player.Player;
import game.model.Model;
import game.utils.Constants;
import java.util.List;
import java.util.Map;

/**
 * Class for Weapons held by a Player. <br>
 * <br>
 * The implementation of this class assumes that the Weapon Sprites are designed so that the
 * dimension of the weapon's sprite is square. It is also assumed that the grip of the weapon in
 * different directions is always the same. This can be achieved by positioning the grip of the
 * weapon in the center of the sprite when designing. <br>
 * This is probably not best practice, but as this is a closed project, we take the liberty to
 * create rules for the design that must be followed in order for the game to function as intended.
 */
public abstract class Weapon extends Entity {
  protected Fixture fixture;
  protected Vector2 grip = new Vector2();
  protected float weaponWidth;
  protected float weaponHeight;
  private final Player player;

  protected Weapon(List<Map.Entry<String, Integer>> animationList, Player player) {
    super(animationList);
    setVisible(false);
    this.player = player;
  }

  public boolean isAttackAnimationFinished() {
    return getCurrentAnimation().isAnimationFinished(getStateTime());
  }

  /**
   * Creates a {@link Body} for the Weapon that acts as a hit box. Positions the {@link Body} in
   * relation to a player's body based on a given player's grip. The {@link Body} of the {@link
   * Weapon} har the {@link com.badlogic.gdx.physics.box2d.BodyDef.BodyType#KinematicBody}. To move
   * the hit box along with the Player, {@link Body#setTransform(float, float, float)} should be
   * used.
   *
   * @param world the world to add the {@link Body} to.
   */
  @Override
  public void createBody(World world) {
    // Create body
    BodyDef bDef = new BodyDef();
    bDef.type = BodyDef.BodyType.KinematicBody;

    // Position worldCenter at player grip
    bDef.position.set(
        player.getBody().getWorldCenter().x + (player.getCurrentGrip().x / PPM),
        player.getBody().getWorldCenter().y + (player.getCurrentGrip().y / PPM));
    bDef.fixedRotation = false;
    this.body = world.createBody(bDef);

    // Create shape and center in relation to weapon dimension
    PolygonShape shape = new PolygonShape();
    Vector2 center = new Vector2(0, (getWeaponHeight() / 2f - 1) / PPM);
    shape.setAsBox(getWeaponWidth() / 2f / PPM, getWeaponHeight() / 2f / PPM, center, 0);

    FixtureDef fDef = new FixtureDef();
    fDef.shape = shape;

    // Set filter
    // From tutorial:
    // https://www.youtube.com/watch?v=Br9fF4xcL94&list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt&index=14
    fDef.filter.categoryBits = Model.INACTIVE_WEAPON_BIT;
    fDef.filter.maskBits = Model.ENEMY_BIT;

    fixture = body.createFixture(fDef);
    this.body.setUserData(this);
    shape.dispose();
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    setCurrentDirection(player.getCurrentDirection());
    body.setTransform(
        player.getBody().getWorldCenter().x + player.getCurrentGrip().x / PPM,
        player.getBody().getWorldCenter().y + player.getCurrentGrip().y / PPM,
        getAngle(direction) * Constants.DEGREE_TO_RADIAN);
    if (player.isAttacking()) {
      attack();
      if (isAttackAnimationFinished()) {
        stopAttacking();
      }
    }
  }

  @Override
  protected void setCurrentDirection(Direction d) {
    super.setCurrentDirection(d);
    if (d == Direction.UP) {
      setZIndex(0);
    } else {
      setZIndex(1);
    }
  }

  /**
   * Set the {@link Weapon}'s grip in relation to the lower left corner of the {@link Animation}'s
   * keyframe
   */
  public void setWeaponGrip(float x, float y) {
    grip.set(x, y);
  }

  public abstract float getAngle(Direction direction);

  /**
   * The actual width of the weapon Sprite that represents the weapon (transparent background
   * excluded)
   *
   * @return width of the weapon
   */
  public float getWeaponWidth() {
    return this.weaponWidth;
  }
  ;

  /**
   * The actual height of the weapon Sprite that represents the weapon (transparent background
   * excluded)
   *
   * @return height of the weapon
   */
  public float getWeaponHeight() {
    return this.weaponHeight;
  }

  /**
   * // From tutorial: // <a href=
   * 'https://www.youtube.com/watch?v=Br9fF4xcL94&list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt&index=14'></a>
   */
  protected void setCategoryFilter(short filterBit) {
    Filter filter = new Filter();
    filter.categoryBits = filterBit;
    fixture.setFilterData(filter);
  }

  /** Start an attack in the given direction */
  public void startAttacking() {
    setStateTime(0);
    setVisible(true);
    setCategoryFilter(Model.ACTIVE_WEAPON_BIT);
    animationHandler.setCurrentAnimation("Attack", getCurrentDirection());
  }

  public void stopAttacking() {
    setVisible(false);
    setCategoryFilter(Model.INACTIVE_WEAPON_BIT);
    player.stopAttacking();
  }

  /**
   * Performs the expected behaviour during an attack. <br>
   * For exampled used to set rotation in relation to the current animation frame.
   */
  public abstract void attack();

  @Override
  public void onCollision(Entity otherEntity) {
    if (otherEntity instanceof Enemy) {
      ((Enemy) otherEntity).takeDamage(player.getStrength());
    }
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.draw(
        getKeyFrame(),
        player.getBody().getWorldCenter().x * PPM + player.getCurrentGrip().x - grip.x,
        player.getBody().getWorldCenter().y * PPM + player.getCurrentGrip().y - grip.y,
        getWidth(),
        getHeight());
  }

  @Override
  public void postCollision() {}
}
