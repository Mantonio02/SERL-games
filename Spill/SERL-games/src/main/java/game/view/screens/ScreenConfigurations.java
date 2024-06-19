package game.view.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class ScreenConfigurations {
  public static final int SCREEN_WIDTH = 1900;
  public static final int SCREEN_HEIGHT = 950;
  public static final String GAME_TITLE = "PROTOTYPE";

  public static TextButton addButton(
      TextButtonStyle tbs, Stage stage, String buttonText, int x, int y) {
    TextButton button = new TextButton(buttonText, tbs);
    stage.addActor(button);
    button.setPosition(x, y);
    return button;
  }
}
