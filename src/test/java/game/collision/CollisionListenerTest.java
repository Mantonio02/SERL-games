package game.collision;

import static game.utils.Constants.PPM;
import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import game.AbstractTest;
import game.entities.movingEntities.attackEntities.enemies.Enemy;
import game.entities.movingEntities.attackEntities.enemies.Wolf;
import game.entities.movingEntities.attackEntities.player.Player;
import game.entities.npc.Merchant;
import game.entities.npc.NPC;
import game.model.Model;
import game.utils.Keybindings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CollisionListenerTest extends AbstractTest {
  private Player player;
  private World world;
  private ContactListener listener;

  @BeforeEach
  public void setup() {
    world = new World(new Vector2(), false);

    player = new Player(new Keybindings());
    player.createBody(world);

    listener = new CollisionListener();
    world.setContactListener(listener);
  }

  @Test
  void beginContactEntityEntity() {
    Enemy wolf = new Wolf();
    wolf.createBody(world);
    wolf.setTarget(player);

    int healthBefore = player.getHealth();

    wolf.getBody().setTransform(player.getBody().getWorldCenter(), 0);
    world.step(0.1f, 6, 2);

    Contact contact = world.getContactList().get(0);
    listener.beginContact(contact);
    player.act(0.1f);
    wolf.act(0.1f);

    int healthAfter = player.getHealth();
    // onCollision called for Entity 1
    assertTrue(healthBefore > healthAfter);
    // onCollision called for Entity 2
    assertTrue(wolf.isAttacking());
  }

  private Body createObjectBody() {
    BodyDef bDef = new BodyDef();
    bDef.type = BodyDef.BodyType.DynamicBody;
    bDef.position.set(0, 0);
    bDef.fixedRotation = true;
    Body body = world.createBody(bDef);
    body.setUserData(new Object());

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(10 / 2f / PPM, 10 / 2f / PPM);
    FixtureDef fDef = new FixtureDef();
    fDef.shape = shape;

    fDef.filter.categoryBits = Model.DEFAULT;
    fDef.filter.maskBits = Model.DEFAULT | Model.PLAYER_BIT | Model.ENEMY_BIT;

    body.createFixture(fDef);
    shape.dispose();
    return body;
  }

  @Test
  void beginContactEntityNonEntity() {
    Body object = createObjectBody();
    // place object right in front of player
    object.setTransform(
        player.getBody().getWorldCenter().x + player.getWidth() / 2 / PPM + 5 / PPM,
        player.getBody().getWorldCenter().y,
        0);
    assertTrue(world.getContactList().isEmpty());

    Vector2 playerPosBefore = player.getBody().getWorldCenter();
    Vector2 objectPostBefore = object.getWorldCenter();

    player.getBody().setLinearVelocity(1, 0);
    world.step(0.1f, 6, 2);
    assertFalse(world.getContactList().isEmpty());

    Contact contact = world.getContactList().get(0);
    listener.beginContact(contact);
    world.step(0.1f, 6, 2);
    player.act(0.1f);

    Vector2 playerPosAfter = player.getBody().getWorldCenter();
    Vector2 objectPostAfter = object.getWorldCenter();

    assertEquals(playerPosBefore, playerPosAfter);
    assertEquals(objectPostBefore, objectPostAfter);
  }

  @Test
  void beginContactNonEntityNonEntity() {
    Body objectA = createObjectBody();
    objectA.setTransform(10 / PPM, 0, 0);
    Body objectB = createObjectBody();

    Vector2 APosBefore = objectA.getWorldCenter();
    Vector2 BPostBefore = objectB.getWorldCenter();
    assertTrue(world.getContactList().isEmpty());

    objectB.setLinearVelocity(1, 0);
    world.step(0.1f, 6, 2);
    assertFalse(world.getContactList().isEmpty());

    Contact contact = world.getContactList().get(0);
    listener.beginContact(contact);
    world.step(6, 2, 0);
    player.act(0.1f);

    Vector2 APosAfter = objectA.getWorldCenter();
    Vector2 BPostAfter = objectB.getWorldCenter();

    assertEquals(APosBefore, APosAfter);
    assertEquals(BPostBefore, BPostAfter);
  }

  @Test
  void endContactEntityEntity() {
    NPC merchant = new Merchant();
    merchant.createBody(world);
    merchant.setTarget(player);
    merchant.getBody().setTransform(player.getBody().getWorldCenter(), 0);
    world.step(6, 2, 0);

    Contact contact = world.getContactList().get(0);
    listener.beginContact(contact);
    assertTrue(merchant.canReceiveInput());

    listener.endContact(contact);
    player.act(0.1f);
    merchant.act(0.1f);

    assertFalse(merchant.canReceiveInput());
  }
}
