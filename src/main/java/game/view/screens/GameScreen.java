package game.view.screens;

import static game.utils.Constants.MAX_MAP_HEIGHT;
import static game.utils.Constants.MAX_MAP_WIDTH;
import static game.utils.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import game.entities.movingEntities.attackEntities.enemies.Boss;
import game.entities.movingEntities.attackEntities.enemies.Enemy;
import game.entities.movingEntities.attackEntities.player.Player;
import game.entities.npc.NPC;
import game.model.Model;
import game.utils.SaveManager;
import java.util.ArrayList;
import util.ExcludeFromGeneratedCoverageReport;

public class GameScreen extends ScreenAdapter {

  private final OrthogonalTiledMapRenderer mapRenderer;
  private final Box2DDebugRenderer b2dr;
  private final OrthographicCamera camera;
  private final Stage stage;
  private final Model model;
  private float minCameraX, maxCameraX;
  private float minCameraY, maxCameraY;
  private final Player player;
  private SaveManager saveManager;
  private ArrayList<Enemy> newEnemies;

  /**
   * Creates a new {@link com.badlogic.gdx.Screen} for the game.
   *
   * @param model of the game
   */
  @ExcludeFromGeneratedCoverageReport
  public GameScreen(Model model) {
    this.model = model;
    b2dr = new Box2DDebugRenderer();
    mapRenderer = new OrthogonalTiledMapRenderer(model.getMap());
    this.player = model.getPlayer();
    this.newEnemies = new ArrayList<>();

    camera = cameraSetup();
    stage =
        new Stage(
            new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera),
            mapRenderer.getBatch());
    initialize();
    saveManager = new SaveManager(model);
  }

  @Override
  public void show() {
    if (saveManager.hasSaveFile()) {
      saveManager.loadGameState(player);
    }
  }

  /**
   * Creates a new {@link com.badlogic.gdx.Screen} for the game for testing purposes where graphics
   * are mocked.
   *
   * @param model the model
   * @param mapRenderer to render TiledMap
   * @param b2dr to render Box2d
   * @param stage with Actors
   */
  public GameScreen(
      Model model, OrthogonalTiledMapRenderer mapRenderer, Box2DDebugRenderer b2dr, Stage stage) {
    this.model = model;
    this.player = model.getPlayer();
    this.b2dr = b2dr;
    this.mapRenderer = mapRenderer;
    camera = cameraSetup();
    this.stage = stage;
    initialize();
  }

  private void initialize() {
    updateCameraBoundaries();
    stage.addActor(player);
    stage.addActor(player.getWeapon());

    for (NPC npc : model.getCurrentLevel().getFriends()) {
      stage.addActor(npc);
      for (TextArea speakBubble : npc.getSpeakBubbles()) {
        stage.addActor(speakBubble);
      }
    }

    for (Enemy enemy : model.getCurrentLevel().getFoes()) {
      if (enemy instanceof Boss) {
        stage.addActor(((Boss) enemy).getBossHealthBar());
      }
      stage.addActor(enemy);
    }
  }

  private OrthographicCamera cameraSetup() {
    OrthographicCamera camera = new OrthographicCamera();
    camera.setToOrtho(false, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
    // camera.zoom = 0.2f;
    camera.update();
    return camera;
  }

  /**
   * Source: <a href=
   * https://www.youtube.com/watch?v=_y1RvNWoRFU&list=PLD_bW3UTVsElsuvyKcYXHLnWb8bD0EQNI></a>*
   */
  protected void updateCamera() {
    Vector3 position = camera.position;
    position.x = player.getBody().getPosition().x * PPM;
    position.y = player.getBody().getPosition().y * PPM;
    camera.position.set(
        MathUtils.clamp(position.x, minCameraX, maxCameraX),
        MathUtils.clamp(position.y, minCameraY, maxCameraY),
        0);
    camera.update();
  }

  protected OrthographicCamera getCamera() {
    return this.camera;
  }

  @Override
  @ExcludeFromGeneratedCoverageReport
  public void render(float v) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    for (Enemy boss : this.model.getCurrentLevel().getFoes()) {
      if (boss instanceof Boss) {
        if (boss.isVisible()
            && this.model.getCurrentLevel().getMap().equals("maps/bossFight.tmx")) {
          stage.getBatch().setColor(0.3f, 0.3f, 0.6f, 1f);
        } else {
          stage.getBatch().setColor(1f, 1f, 1f, 1f);
        }
      }
    }

    if (!this.newEnemies.isEmpty()) {
      for (Enemy enemy : newEnemies) {
        stage.addActor(enemy);
      }
    }
    newEnemies.clear();

    stage.act(Gdx.graphics.getDeltaTime());
    updateCamera();
    mapRenderer.getBatch().setProjectionMatrix(camera.combined);
    mapRenderer.setView(camera);
    mapRenderer.render();

    stage.draw();
    player.drawHUD(stage.getBatch(), camera);
  }

  @Override
  public void resize(int width, int height) {
    updateCameraBoundaries();
    camera.viewportWidth = width;
    camera.viewportHeight = height;
    camera.update();
  }

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {
    Gdx.app.log("SAVE", "Saving game...");
    saveManager.saveGameState();
    dispose();
  }

  @Override
  public void dispose() {
    b2dr.dispose();
    mapRenderer.dispose();
  }

  private void updateCameraBoundaries() {
    minCameraX = Gdx.graphics.getWidth() / 2f;
    minCameraY = Gdx.graphics.getHeight() / 2f;
    maxCameraX = MAX_MAP_WIDTH - minCameraX;
    maxCameraY = MAX_MAP_HEIGHT - minCameraY;
  }

  /** Add new enemies to gameScreen, even after {@link Model} and {@link GameScreen} is created. */
  public void addNewEnemies(Enemy enemy) {
    this.newEnemies.add(enemy);
  }
}
