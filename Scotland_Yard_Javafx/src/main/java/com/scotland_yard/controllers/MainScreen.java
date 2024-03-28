package com.scotland_yard.controllers;

import com.scotland_yard.Main;
import com.scotland_yard.ScotlandYard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainScreen {

    private static final int NUMBER_OF_ROUNDS = 1000;

    @FXML
    public Button btnPlayers;
    @FXML
    public Button btnAI;
    @FXML
    public Button btnExit;

    @FXML
    public void onBtnPlayersClicked(ActionEvent actionEvent) {
        ScotlandYard.startRound(1, false);
    }

    @FXML
    public void onBtnAIClicked(ActionEvent actionEvent) {
        ScotlandYard.startRound(NUMBER_OF_ROUNDS, true);        // num of rounds hard coded
    }

    @FXML
    public void onBtnExitClicked(ActionEvent actionEvent) {
        Main.quit();
    }
}
