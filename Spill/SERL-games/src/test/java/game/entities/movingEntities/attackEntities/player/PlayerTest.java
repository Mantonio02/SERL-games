package game.entities.movingEntities.attackEntities.player;

import static game.utils.Constants.MAX_MAP_WIDTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;
import game.AbstractTest;
import game.items.RedFlask;
import game.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest extends AbstractTest {
  private Player player;
  private Model model;
  private float initialX;
  private float initialY;
  private World world;

  @BeforeEach
  public void setup() {
    this.model = new Model();
    model.setDebugMode();
    model.create();
    this.player = model.getPlayer();
    world = model.getWorld();
    initialX = player.getBody().getPosition().x;
    initialY = player.getBody().getPosition().y;
    Gdx.input = mock(Input.class);
  }

  @Test
  public void testPlayerMovesRight() {
    when(Gdx.input.isKeyPressed(Input.Keys.RIGHT)).thenReturn(true);
    player.handleInput(Input.Keys.RIGHT, true);
    player.act(Gdx.graphics.getDeltaTime());
    world.step(1 / 60f, 6, 2);
    player.handleInput(Input.Keys.RIGHT, false);
    assertTrue(player.getBody().getPosition().x > initialX);
    assertEquals(player.getBody().getPosition().y, initialY);
  }

  @Test
  public void testPlayerMovesLeft() {
    when(Gdx.input.isKeyPressed(Input.Keys.LEFT)).thenReturn(true);
    player.handleInput(Input.Keys.LEFT, true);
    player.act(Gdx.graphics.getDeltaTime());
    world.step(1 / 60f, 6, 2);
    player.handleInput(Input.Keys.LEFT, false);
    assertTrue(player.getBody().getPosition().x < initialX);
    assertEquals(player.getBody().getPosition().y, initialY);
  }

  @Test
  public void testPlayerMovesUp() {
    when(Gdx.input.isKeyPressed(Input.Keys.UP)).thenReturn(true);
    player.handleInput(Input.Keys.UP, true);
    player.act(Gdx.graphics.getDeltaTime());
    world.step(1 / 60f, 6, 2);
    player.handleInput(Input.Keys.UP, false);
    assertEquals(player.getBody().getPosition().x, initialX);
    assertTrue(player.getBody().getPosition().y > initialY);
  }

  @Test
  public void testPlayerMovesDown() {
    when(Gdx.input.isKeyPressed(Input.Keys.DOWN)).thenReturn(true);
    player.handleInput(Input.Keys.DOWN, true);
    player.act(Gdx.graphics.getDeltaTime());
    world.step(1 / 60f, 6, 2);
    player.handleInput(Input.Keys.DOWN, false);
    assertEquals(player.getBody().getPosition().x, initialX);
    assertTrue(player.getBody().getPosition().y < initialY);
  }

  @Test
  public void testPlayerNeverOutsideMap() {
    when(Gdx.input.isKeyPressed(Input.Keys.RIGHT)).thenReturn(true);
    for (int i = 0; i < 100; i++) {
      player.handleInput(Input.Keys.RIGHT, true);
      player.act(Gdx.graphics.getDeltaTime());
      world.step(1 / 60f, 6, 2);
    }
    player.handleInput(Input.Keys.RIGHT, false);
    assertTrue(player.getBody().getPosition().x <= MAX_MAP_WIDTH);
    player.getBody().setTransform(initialX, initialY, player.getBody().getAngle());
    when(Gdx.input.isKeyPressed(Input.Keys.LEFT)).thenReturn(true);
    for (int i = 0; i < 100; i++) {
      player.handleInput(Input.Keys.LEFT, true);
      player.act(Gdx.graphics.getDeltaTime());
      world.step(1 / 60f, 6, 2);
    }
    player.handleInput(Input.Keys.LEFT, false);
    assertTrue(player.getBody().getPosition().x >= 0);
  }

  @Test
  public void testSprintingIsFaster() {
    when(Gdx.input.isKeyPressed(Input.Keys.RIGHT)).thenReturn(true);

    var noSprintBefore = player.getBody().getPosition().cpy();
    player.handleInput(Input.Keys.RIGHT, true);
    for (int i = 0; i < 50; i++) {
      player.act(Gdx.graphics.getDeltaTime());
      world.step(1 / 60f, 6, 2);
    }
    player.handleInput(Input.Keys.RIGHT, false);
    var noSprintAfter = player.getBody().getPosition().cpy();

    var sprintBefore = player.getBody().getPosition().cpy();
    player.handleInput(Input.Keys.RIGHT, true);
    player.handleInput(Input.Keys.SHIFT_LEFT, true);
    for (int i = 0; i < 50; i++) {
      player.act(Gdx.graphics.getDeltaTime());
      world.step(1 / 60f, 6, 2);
    }
    player.handleInput(Input.Keys.RIGHT, false);
    player.handleInput(Input.Keys.SHIFT_LEFT, false);
    var sprintAfter = player.getBody().getPosition().cpy();

    assertTrue(noSprintAfter.sub(noSprintBefore).len() < sprintAfter.sub(sprintBefore).len());
  }

  @Test
  public void testHotbar() {

    var selectedBefore = player.getHud().getHotBar().getSelectedItem();
    player.handleScroll(100);
    var selectedAfter = player.getHud().getHotBar().getSelectedItem();

    assertTrue(selectedBefore != selectedAfter);

    // check that constantly incrementing doesn't throw an exception or anything like that
    for (int i = 0; i < 100; i++) {
      player.getHud().getHotBar().incrementSelectedItem();
      player.getHud().getHotBar().getSelectedItem();
    }

    // check that constantly decrementing doesn't throw an exception or anything like that
    for (int i = 0; i < 100; i++) {
      player.getHud().getHotBar().decrementSelectedItem();
      player.getHud().getHotBar().getSelectedItem();
    }

    player.getHud().getHotBar().setSelectedItem(0);
    var flask = new RedFlask();
    player.addItem(flask);

    assertTrue(player.getHud().getHotBar().getItem(0) == flask);
    assertTrue(player.getHud().getHotBar().getNumItems() == 1);

    player.consumeSelectedItem();

    assertTrue(player.getHud().getHotBar().getItem(0) == null);
    assertTrue(player.getHud().getHotBar().getNumSlots() == 9);

    assertThrows(
        IndexOutOfBoundsException.class, () -> player.getHud().getHotBar().setSelectedItem(1000));
    assertThrows(
        IndexOutOfBoundsException.class, () -> player.getHud().getHotBar().setSelectedItem(-1));
  }
}
