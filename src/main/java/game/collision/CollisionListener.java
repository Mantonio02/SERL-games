package game.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import game.entities.Entity;

public class CollisionListener implements ContactListener {

  @Override
  public void beginContact(Contact contact) {
    Object userDataA = contact.getFixtureA().getBody().getUserData();
    Object userDataB = contact.getFixtureB().getBody().getUserData();

    if (userDataA instanceof Entity entityA && userDataB instanceof Entity entityB) {
      entityA.onCollision(entityB);
      entityB.onCollision(entityA);
    }
  }

  @Override
  public void endContact(Contact contact) {
    Object userDataA = contact.getFixtureA().getBody().getUserData();
    Object userDataB = contact.getFixtureB().getBody().getUserData();

    if (userDataA instanceof Entity entityA && userDataB instanceof Entity entityB) {
      entityA.postCollision();
      entityB.postCollision();
    }
  }

  @Override
  public void preSolve(Contact contact, Manifold oldManifold) {}

  @Override
  public void postSolve(Contact contact, ContactImpulse impulse) {}
}
