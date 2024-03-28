package com.scotland_yard.controllers;

import com.scotland_yard.GameEngine;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class GameScreen {
    public Button moveButton;
    public ComboBox comboBox;
    public Label detectiveLbl;
    public Label Mr_X_Locationlbl;
    public TextArea mrXTicketLog;
    public TextArea historyLog;
    public Button exitBtn;
    public Label blueTicketslbl;
    public Label blackTicketslbl;
    public Label redTicketsLbl;
    public Label greenTicketsLbl;
    public Label yellowTicketsLbl;

    public void onMoveButtonPressed(ActionEvent actionEvent) {
        if (comboBox.getValue() != null) {
            GameEngine.moveLatch.countDown();
            GameEngine.detectiveLatch.countDown();
        }
    }

    public void onExitBtnPressed(ActionEvent actionEvent) {
        GameEngine.setUserClosedGame();
        GameEngine.detectiveLatch.countDown();
        GameEngine.moveLatch.countDown();
    }
}
