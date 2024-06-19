package game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import game.model.Model;
import util.ExcludeFromGeneratedCoverageReport;

@ExcludeFromGeneratedCoverageReport
public class SettingsScreen extends ScreenConfigurations implements Screen {
  final Model model;
  OrthographicCamera camera;
  private final TextButton display;
  private final TextButton keybindings;
  private final TextButton moveBack;
  private final Stage stage;
  private final TextButtonStyle tbs;

  /**
   * Creates a new {@link Screen} to access the settings of the game.
   *
   * @param model the model
   */
  public SettingsScreen(final Model model) {
    this.model = model;

    // Set up camera
    camera = new OrthographicCamera();
    camera.setToOrtho(false, ScreenConfigurations.SCREEN_WIDTH, ScreenConfigurations.SCREEN_HEIGHT);

    // Called at startup
    stage = new Stage();
    Gdx.input.setInputProcessor(stage);
    tbs = new TextButton.TextButtonStyle();
    tbs.font = model.getFont();

    // Create buttons for Start Menu
    display = addButton(tbs, stage, "Display", 20, ScreenConfigurations.SCREEN_HEIGHT - 60);
    keybindings = addButton(tbs, stage, "Keybindings", 20, ScreenConfigurations.SCREEN_HEIGHT - 80);
    moveBack = addButton(tbs, stage, "Return", 20, ScreenConfigurations.SCREEN_HEIGHT - 100);

    tbs.font.setColor(Color.RED);
    // backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal());
    Gdx.graphics.setForegroundFPS(60);
  }

  @Override
  public void show() {
    display.addListener(
        new ClickListener() {
          @Override
          public void clicked(InputEvent event, float x, float y) {
            // code to be placed here ...
          }
        });

    keybindings.addListener(
        new ClickListener() {
          @Override
          public void clicked(InputEvent event, float x, float y) {
            model.setScreen(new KeybindScreen(model));
          }
        });

    moveBack.addListener(
        new ClickListener() {
          @Override
          public void clicked(InputEvent event, float x, float y) {
            model.setScreen(new StartMenuScreen(model));
          }
        });
  }

  @Override
  public void render(float v) {
    ScreenUtils.clear(Color.BLACK);

    SpriteBatch batch = model.getBatch();

    camera.update();
    batch.setProjectionMatrix(camera.combined);

    batch.begin();
    tbs.font.draw(batch, "Settings", 20, ScreenConfigurations.SCREEN_HEIGHT - 20);
    stage.draw();
    batch.end();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {
    stage.dispose();
  }
}
