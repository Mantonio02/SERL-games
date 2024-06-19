package game.entities.movingEntities.attackEntities.enemies;

import static game.utils.Constants.PPM;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import game.entities.Entity;
import game.entities.movingEntities.attackEntities.AttackEntity;
import game.entities.movingEntities.attackEntities.player.Player;
import game.model.Model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import util.ExcludeFromGeneratedCoverageReport;

public abstract class Enemy extends AttackEntity implements Json.Serializable {
  private double followDistance;
  private Vector2 targetVec;
  private ITarget target;
  private boolean canAttack;
  private ArrayList<Body> deleteList;

  /**
   * Creates a new Enemy with a given follow distance and a given list of animations.
   *
   * @param followDistance if the Enemy's target gets within this distance, the Enemy should go
   *     towards the target.
   * @param animationList the animations of the Enemy given by path name to the sprite sheet to be *
   *     used and the number of frames.
   */
  public Enemy(double followDistance, List<Map.Entry<String, Integer>> animationList) {
    super(4, animationList);
    this.followDistance = followDistance;
    this.targetVec = new Vector2(0, 0);
    canAttack = false;
  }

  private void updateTargetVec() {
    this.targetVec = this.target.getBodyPosition();
  }

  @Override
  @ExcludeFromGeneratedCoverageReport
  public void draw(Batch batch, float parentAlpha) {
    float width = this.getKeyFrame().getRegionWidth() * getScaleX();
    float height = this.getKeyFrame().getRegionHeight() * getScaleY();

    batch.draw(this.getKeyFrame(), getX(), getY(), width, height);
  }

  @Override
  public void act(float deltaTime) {
    if (this.target != null) {
      updateTargetVec();
      float speed = this.numPixelsToMove(this.getMovementSpeed(), deltaTime);
      float dist = this.targetVec.dst(this.getBody().getPosition());

      this.setMovementVec(this.targetVec.sub(this.getBody().getPosition()).nor().scl(speed));

      if (canAttack) {
        if (!this.isAttacking()) {
          this.startAttacking();
        }
        this.target.takeDamage(getStrength());
        if (this.getCurrentAnimation().isAnimationFinished(this.getStateTime())) {
          stopAttacking();
        }
        super.act(deltaTime);
        return;
      }

      if (dist > this.followDistance) {
        this.setMovementVec(Vector2.Zero);
        this.stopRunning();
        this.idle();
      }
    } else {
      this.idle();
    }

    super.act(deltaTime);
  }

  @Override
  protected void onDeath() {
    this.remove();
    this.setVisible(false);
    if (this.target != null) {
      this.target.increaseXp(15);
    }
    if (this.deleteList != null) {
      this.deleteList.add(this.getBody());
    }
    this.stopSound();
  }

  /**
   * Sets a {@link ITarget} to be the target of the Enemy.
   *
   * @param target the target.
   */
  public void setTarget(ITarget target) {
    this.target = target;
  }

  public ITarget getTarget() {
    return this.target;
  }

  @Override
  public void onCollision(Entity otherEntity) {
    if (otherEntity instanceof Player) {
      this.canAttack = true;
    }
  }

  @Override
  public void postCollision() {
    this.canAttack = false;
    if (this.isAttacking()) {
      stopAttacking();
    }
  }

  @Override
  public void write(Json json) {}

  @Override
  public void read(Json json, JsonValue jsonValue) {
    float x = jsonValue.get("position").get("x").asFloat();
    float y = jsonValue.get("position").get("y").asFloat();
    setPosition(x, y);
  }

  @Override
  public void createBody(World world) {
    super.createBody(world);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(this.getWidth() / 2f / PPM, this.getHeight() / 2f / PPM);
    FixtureDef fDef = new FixtureDef();
    fDef.shape = shape;
    fDef.filter.categoryBits = Model.ENEMY_BIT;
    fDef.filter.maskBits =
        Model.DEFAULT | Model.PLAYER_BIT | Model.ACTIVE_WEAPON_BIT | Model.MAP_OBJECT_BIT;
    getBody().createFixture(fDef);
    this.getBody().setUserData(this);
    shape.dispose();
  }

  public void setDeleteList(ArrayList<Body> deleteList) {
    this.deleteList = deleteList;
  }

  public ArrayList<Body> getDeleteList() {
    return this.deleteList;
  }
}
