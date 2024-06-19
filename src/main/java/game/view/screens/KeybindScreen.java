package game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import game.model.Model;
import game.utils.Keybindings;
import java.util.Map;
import util.ExcludeFromGeneratedCoverageReport;

@ExcludeFromGeneratedCoverageReport
public class KeybindScreen implements Screen {
  private final Stage stage;
  private final Skin skin;
  private final Model model;
  private final Keybindings keybindings;

  /**
   * Creates a new {@link Screen} to display the keybinding of the game.
   *
   * @param model the model
   */
  public KeybindScreen(Model model) {
    stage = new Stage(new ScreenViewport());
    skin = new Skin(Gdx.files.internal("skins/plain-james-ui.json"));
    this.model = model;
    keybindings = model.getKeybindings();

    Label titleLabel = new Label("Key Bindings", skin, "title");
    titleLabel.setPosition(
        Gdx.graphics.getWidth() / 2f - titleLabel.getWidth() / 2f,
        Gdx.graphics.getHeight() - titleLabel.getHeight() - 20);
    stage.addActor(titleLabel);
    setupUI();
    Label explanationLabel =
        new Label(
            "Hot bar keys is [1-9] and is non-changeable.\nMousewheel is support for this.",
            skin,
            "default");
    explanationLabel.setPosition(Gdx.graphics.getWidth() / 2f - titleLabel.getWidth() / 2, 100);
    stage.addActor(titleLabel);
    stage.addActor(explanationLabel);
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(Color.LIGHT_GRAY);
    stage.act(delta);
    stage.draw();
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
  public void hide() {
    model.updateKeyCode();
  }

  @Override
  public void dispose() {}

  private void setupUI() {
    Table table = new Table();
    table.setFillParent(true);
    stage.addActor(table);

    table.add(new Label("Movement", skin)).colspan(2);
    table.add(new Label("Interactable", skin)).colspan(2).row();

    Table movementTable = new Table();
    Table interactableTable = new Table();

    Map<String, Integer> keyBindingMap = keybindings.getKeyBindingMap();
    keyBindingMap.forEach(
        (action, keycode) -> {
          TextField keyField = new TextField(Input.Keys.toString(keycode), skin);
          keyField.setMessageText("Press a key");
          keyField.setFocusTraversal(false);

          keyField.addListener(
              new InputListener() {
                @Override
                public boolean keyDown(InputEvent event, int keycode) {
                  if (stage.getKeyboardFocus() == keyField) {
                    keyField.setText(Input.Keys.toString(keycode));
                    stage.setKeyboardFocus(null);
                    keybindings.setKeybinding(action, keycode);
                    return true;
                  }
                  return false;
                }
              });

          if (leftTableActions(action)) {
            movementTable.add(new Label(action, skin));
            movementTable.add(keyField).width(100).pad(10).row();
          } else if (rightTableActions(action)) {
            interactableTable.add(new Label(action, skin));
            interactableTable.add(keyField).width(100).pad(10).row();
          }
        });

    table.add(movementTable);
    table.add().width(100);
    table.add(interactableTable).row();

    TextButton backButton = new TextButton("Back", skin);
    backButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            model.setScreen(new SettingsScreen(model));
          }
        });
    table.add(backButton).colspan(5).padTop(10);
  }

  private boolean rightTableActions(String action) {
    return !leftTableActions(action);
  }

  private boolean leftTableActions(String action) {
    return keybindings.isMovementKeycode(action)
        && !action.equals("HEALING")
        && !action.equals("ATTACK")
        && !action.equals("CONSUME");
  }
}
