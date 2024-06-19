package game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import game.controller.Controller;
import game.model.Model;
import util.ExcludeFromGeneratedCoverageReport;

public class GameOver implements Screen {
  private final SpriteBatch spriteBatch;
  private final Stage stage;
  private final Viewport viewport;
  private final Model model;
  private final BitmapFont deathFont;

  /**
   * Creates a new {@link Screen} to display when the game is over.
   *
   * @param model the model
   */
  @ExcludeFromGeneratedCoverageReport
  public GameOver(Model model) {
    this.model = model;
    viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    spriteBatch = model.getBatch();
    deathFont = new BitmapFont();
    deathFont.setColor(Color.RED);
    deathFont.getData().setScale(2f);

    stage = new Stage();
    spriteBatch.setProjectionMatrix(stage.getCamera().combined);
  }

  @Override
  @ExcludeFromGeneratedCoverageReport
  public void show() {
    Gdx.input.setInputProcessor(
        new InputAdapter() {
          @Override
          public boolean keyUp(int keycode) {
            if (keycode == Input.Keys.R) {
              restartGame();
              return true;
            }
            return false;
          }
        });
  }

  @Override
  @ExcludeFromGeneratedCoverageReport
  public void render(float delta) {
    ScreenUtils.clear(Color.BLACK);
    stage.getCamera().update();

    stage.act(delta);
    stage.draw();
    spriteBatch.begin();
    model
        .getFont()
        .draw(
            spriteBatch,
            "Press R to start again",
            viewport.getWorldWidth() / 2 - 100,
            viewport.getWorldHeight() / 2);
    deathFont.draw(
        spriteBatch,
        "YOU DIED",
        viewport.getWorldWidth() / 2 - 100,
        viewport.getWorldHeight() / 2 + 100);
    spriteBatch.end();
  }

  @Override
  @ExcludeFromGeneratedCoverageReport
  public void resize(int width, int height) {
    viewport.update(width, height, true);
  }

  @Override
  @ExcludeFromGeneratedCoverageReport
  public void dispose() {
    spriteBatch.dispose();
    stage.dispose();
  }

  @Override
  @ExcludeFromGeneratedCoverageReport
  public void pause() {}

  @Override
  @ExcludeFromGeneratedCoverageReport
  public void resume() {}

  @Override
  @ExcludeFromGeneratedCoverageReport
  public void hide() {}

  private void restartGame() {
    Gdx.input.setInputProcessor(new Controller(model));
    model.loadNewLevel(0);
    model.goToGameScreen();
    model.getPlayer().respawn();
    model.resetDeathFlag();
  }
}
