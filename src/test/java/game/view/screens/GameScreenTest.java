package game.view.screens;

import static game.utils.Constants.MAX_MAP_HEIGHT;
import static game.utils.Constants.MAX_MAP_WIDTH;
import static game.utils.Constants.PPM;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import game.AbstractTest;
import game.entities.movingEntities.attackEntities.player.Player;
import game.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameScreenTest extends AbstractTest {
  private GameScreen gameScreen;
  private Model model;
  private OrthogonalTiledMapRenderer mapRenderer;
  private Box2DDebugRenderer b2dr;
  private OrthographicCamera camera;
  private Stage stage;
  private Player player;

  @BeforeEach
  public void setup() {
    model = new Model();
    model.setDebugMode();
    model.create();
    player = mock(Player.class);
    Body body = mock(Body.class);
    mapRenderer = mock(OrthogonalTiledMapRenderer.class);
    b2dr = mock(Box2DDebugRenderer.class);
    stage = mock(Stage.class);

    when(player.getBody()).thenReturn(body);
    gameScreen = new GameScreen(model, mapRenderer, b2dr, stage);
    camera = gameScreen.getCamera();
  }

  @Test
  void testAssetNotNull() {
    assertNotNull(gameScreen.getCamera());
  }

  @Test
  public void testCameraDoesNotShowOutsideMap() {
    when(player.getBody().getPosition())
        .thenReturn(new Vector2(MAX_MAP_WIDTH / PPM, MAX_MAP_HEIGHT / PPM));
    gameScreen.updateCamera();
    assertTrue(camera.position.x >= 0 && camera.position.x <= MAX_MAP_WIDTH);
    assertTrue(camera.position.y >= 0 && camera.position.y <= MAX_MAP_HEIGHT);
  }
}
