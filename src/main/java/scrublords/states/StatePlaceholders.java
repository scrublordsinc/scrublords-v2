package scrublords.states;

import scrublords.main.State;

import java.util.ArrayList;

/**
 * @author Nikolay Zahariev <nikolay.g.zahariev@gmail.com>.
 */
public class StatePlaceholders {
    public ArrayList<State> states = new ArrayList<>();
    private MenuState menuState;
    private CharState charState;
    private SettingsState settingsState;
    private HelpState helpState;
    private Stage stage;

    public StatePlaceholders(MenuState menuState, CharState charState, HelpState helpState, SettingsState settingsState, Stage stage) {
        this.menuState = menuState;
        this.charState = charState;
        this.settingsState = settingsState;
        this.helpState = helpState;
        this.stage = stage;
        populateStatesArray();
    }

    private void populateStatesArray() {
        states.add(menuState);
        states.add(charState);
        states.add(settingsState);
        states.add(helpState);
        states.add(stage);
    }
}
