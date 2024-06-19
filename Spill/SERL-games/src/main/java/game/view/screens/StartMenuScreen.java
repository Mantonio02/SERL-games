package game.view.screens;

import static game.view.animations.AnimationHelperMethods.createTextureRegionAnimation;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import game.controller.Controller;
import game.model.Model;
import game.utils.SaveManager;
import game.view.animations.PositionedAnimation;
import util.ExcludeFromGeneratedCoverageReport;

@ExcludeFromGeneratedCoverageReport
public class StartMenuScreen implements Screen {
  private final Model model;
  private OrthographicCamera camera;
  private final Label.LabelStyle labelStyle;
  private final Stage stage;
  private Table table;
  private boolean continueButtonVisible;
  private final SaveManager saveManager;

  // Buttons
  ImageButton continueButton;
  ImageButton startButton;
  ImageButton settingsButton;
  ImageButton quitButton;

  PositionedAnimation continueButtonActor;
  PositionedAnimation startButtonActor;
  PositionedAnimation settingsButtonActor;
  PositionedAnimation quitButtonActor;

  // Keeping track of start game animation
  boolean startAnimationStarted = false;

  /**
   * Creates a {@link Screen} with three themed buttons for Continue, Start, Settings and Quit.
   *
   * @param model the {@link Model} that displays the {@link Screen}
   */
  public StartMenuScreen(Model model) {
    this.model = model;
    saveManager = new SaveManager(model);
    continueButtonVisible = saveManager.hasSaveFile();
    cameraSetup();

    labelStyle = new Label.LabelStyle();
    labelStyle.font = model.getFont();
    labelStyle.fontColor = Color.RED;

    stage = new Stage();
    // Create table without adding to stage
    tableSetup();
    // Create animations and add to stage
    buttonAnimationSetup();
    // Add table to stage so buttons go on top of animation actors
    stage.addActor(table);
    // Render to place buttons in stage
    render(Gdx.graphics.getDeltaTime());
    // Reposition animation actors in accordance with buttons
    continueButtonActor.setMidpoint(getStageCoordinatesOfCenter(continueButton));
    startButtonActor.setMidpoint(getStageCoordinatesOfCenter(startButton));
    settingsButtonActor.setMidpoint(getStageCoordinatesOfCenter(settingsButton));
    quitButtonActor.setMidpoint(getStageCoordinatesOfCenter(quitButton));

    Gdx.input.setInputProcessor(stage);
    // backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal());
    Gdx.graphics.setForegroundFPS(60);
  }

  private void cameraSetup() {
    camera = new OrthographicCamera();
    camera.setToOrtho(false);
    camera.update();
  }

  private void tableSetup() {
    table = new Table();
    // table.debug();
    table.setFillParent(true);
    // private Music backgroundMusic;
    float PADDING = 50;
    table.defaults().pad(PADDING);

    Label title = new Label("PROTOTYPE", labelStyle);
    table.add(title).colspan(2).center();
    table.row();

    if (continueButtonVisible) {
      continueButton =
          new ImageButton(
              imageButtonStyleSetup(
                  "sprites/menuButtons/ContinueButtonUp.png",
                  "sprites/menuButtons/ContinueButtonOver.png",
                  "sprites/menuButtons/ContinueButtonOver.png"));
    } else {
      continueButton =
          new ImageButton(
              imageButtonStyleSetup(
                  "sprites/menuButtons/ContinueButtonInactive.png",
                  "sprites/menuButtons/ContinueButtonInactive.png",
                  "sprites/menuButtons/ContinueButtonInactive.png"));
    }

    table.add(continueButton);
    table.row();

    startButton =
        new ImageButton(
            imageButtonStyleSetup(
                "sprites/menuButtons/StartButtonUp.png",
                "sprites/menuButtons/StartButtonOver.png",
                "sprites/menuButtons/StartButtonOver.png"));
    table.add(startButton);
    table.row();

    settingsButton =
        new ImageButton(
            imageButtonStyleSetup(
                "sprites/menuButtons/SettingsButtonUp.png",
                "sprites/menuButtons/SettingsButtonOver.png",
                "sprites/menuButtons/SettingsButtonDown.png"));
    table.add(settingsButton).colspan(2).center();
    table.row();

    quitButton =
        new ImageButton(
            imageButtonStyleSetup(
                "sprites/menuButtons/QuitButtonUp.png",
                "sprites/menuButtons/QuitButtonOver.png",
                "sprites/menuButtons/QuitButtonDown.png"));
    table.add(quitButton).colspan(2).center();
  }

  private Vector2 getStageCoordinatesOfCenter(Button button) {
    Vector2 localCenter = new Vector2(button.getWidth() / 2f, button.getHeight() / 2f);

    return button.localToStageCoordinates(localCenter);
  }

  private void buttonAnimationSetup() {
    Animation<TextureRegion> startButtonAnimation =
        createTextureRegionAnimation(
            new Texture("sprites/menuButtons/animations/StartButtonAnimation.png"), 11);
    Animation<TextureRegion> settingsButtonAnimation =
        createTextureRegionAnimation(
            new Texture("sprites/menuButtons/animations/SettingsButtonAnimation.png"), 16);
    Animation<TextureRegion> quitButtonAnimation =
        createTextureRegionAnimation(
            new Texture("sprites/menuButtons/animations/QuitButtonAnimation.png"), 12);

    continueButtonActor = new PositionedAnimation(startButtonAnimation, Vector2.Zero, true);
    startButtonActor = new PositionedAnimation(startButtonAnimation, Vector2.Zero, true);
    settingsButtonActor = new PositionedAnimation(settingsButtonAnimation, Vector2.Zero, true);
    quitButtonActor = new PositionedAnimation(quitButtonAnimation, Vector2.Zero, true);

    stage.addActor(continueButtonActor);
    stage.addActor(startButtonActor);
    stage.addActor(settingsButtonActor);
    stage.addActor(quitButtonActor);
  }

  private ImageButton.ImageButtonStyle imageButtonStyleSetup(
      String pathUp, String pathOver, String pathDown) {
    ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
    style.up = new TextureRegionDrawable(new Texture(pathUp));
    style.over = new TextureRegionDrawable(new Texture(pathOver));
    style.down = new TextureRegionDrawable(new Texture(pathDown));

    return style;
  }

  @Override
  public void dispose() {
    stage.dispose();
  }

  @Override
  public void render(float delta) {
    // Called when the application should draw a new frame
    // Start with a blank screen
    ScreenUtils.clear(Color.BLACK);
    SpriteBatch batch = model.getBatch();

    // Update camera
    camera.update();
    batch.setProjectionMatrix(camera.combined);

    stage.act();

    // Draw calls should be wrapped in batch.begin() ... batch.end()
    batch.begin();
    stage.draw();
    batch.end();

    if (startAnimationStarted) {
      if (startButtonActor.isAnimationFinished()
          && settingsButtonActor.isAnimationFinished()
          && quitButtonActor.isAnimationFinished()) {
        model.setScreen(new GameScreen(model));
        Gdx.input.setInputProcessor(new Controller(model));
      }
    }
  }

  private void startAnimation() {
    startAnimationStarted = true;

    continueButtonActor.setAnimation(
        createTextureRegionAnimation(
            new Texture("sprites/menuButtons/animations/StartButtonStartAnimation.png"), 13),
        false);
    startButtonActor.setAnimation(
        createTextureRegionAnimation(
            new Texture("sprites/menuButtons/animations/StartButtonStartAnimation.png"), 13),
        false);
    settingsButtonActor.setAnimation(
        createTextureRegionAnimation(
            new Texture("sprites/menuButtons/animations/SettingsButtonStartAnimation.png"), 13),
        false);
    quitButtonActor.setAnimation(
        createTextureRegionAnimation(
            new Texture("sprites/menuButtons/animations/QuitButtonStartAnimation.png"), 13),
        false);

    continueButton.setStyle(
        imageButtonStyleSetup(
            "sprites/menuButtons/ContinueButtonRed.png",
            "sprites/menuButtons/ContinueButtonRed.png",
            "sprites/menuButtons/ContinueButtonRed.png"));
    startButton.setStyle(
        imageButtonStyleSetup(
            "sprites/menuButtons/StartButtonRed.png",
            "sprites/menuButtons/StartButtonRed.png",
            "sprites/menuButtons/StartButtonRed.png"));
    settingsButton.setStyle(
        imageButtonStyleSetup(
            "sprites/menuButtons/SettingsButtonRed.png",
            "sprites/menuButtons/SettingsButtonRed.png",
            "sprites/menuButtons/SettingsButtonRed.png"));
    quitButton.setStyle(
        imageButtonStyleSetup(
            "sprites/menuButtons/QuitButtonRed.png",
            "sprites/menuButtons/QuitButtonRed.png",
            "sprites/menuButtons/QuitButtonRed.png"));

    continueButton.clearListeners();
    startButton.clearListeners();
    settingsButton.clearListeners();
    quitButton.clearListeners();
  }

  @Override
  public void show() {
    continueButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            if (continueButtonVisible) {
              startAnimation();
            }
          }
        });
    startButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            startAnimation();
            saveManager.deleteSave();
          }
        });

    settingsButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            model.setScreen(new SettingsScreen(model));
          }
        });

    quitButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            Gdx.app.exit();
          }
        });
  }

  @Override
  public void resize(int width, int height) {
    // Called whenever the window is resized (including with its original site at
    // startup)
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}
}
