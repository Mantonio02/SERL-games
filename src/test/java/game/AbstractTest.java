package game;

import static org.mockito.Mockito.*;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

/**
 * Abstrakt test klasse som alle tester må arve.
 *
 * <p>Merk at metoder som bruker grafikk som {@link setScreen()} eller {@link
 * OrthogonalTiledMapRenderer} må mockes.
 *
 * <p>Se eksempel for hver pakke; ModelTest, PlayTest, PlayerTest.
 */
public abstract class AbstractTest {

  private static Application application;

  @BeforeAll
  public static void init() {
    application = new HeadlessApplication(new ApplicationAdapter() {});
    Gdx.gl20 = mock(GL20.class);
    Gdx.gl = Gdx.gl20;
  }

  @AfterAll
  public static void cleanUp() {
    application.exit();
    application = null;
  }
}
