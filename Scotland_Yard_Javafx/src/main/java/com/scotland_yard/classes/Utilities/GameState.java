package com.scotland_yard.classes.Utilities;

import com.scotland_yard.classes.Detective;
import com.scotland_yard.classes.Mr_X;
import com.scotland_yard.classes.TransportUtilities;

import java.awt.*;
import java.util.ArrayList;

public class GameState {

    public Detective blue;
    public Detective black;
    public Detective red;
    public Detective green;
    public Detective yellow;
    public Mr_X mr_x;
    public ArrayList<Detective> detectives = new ArrayList<>();
    public int round;

    public GameState(ArrayList<Detective> detectives, Mr_X mr_x, int round) {
        this.blue = new Detective(Color.BLUE, detectives.get(0).getCurrentLocation(), detectives.get(0).copyTickets());
        this.black = new Detective(Color.BLACK, detectives.get(1).getCurrentLocation(), detectives.get(1).copyTickets());
        this.red = new Detective(Color.RED, detectives.get(2).getCurrentLocation(), detectives.get(2).copyTickets());
        this.green = new Detective(Color.GREEN, detectives.get(3).getCurrentLocation(), detectives.get(3).copyTickets());
        this.yellow = new Detective(Color.YELLOW, detectives.get(4).getCurrentLocation(), detectives.get(4).copyTickets());

        this.detectives.add(blue);
        this.detectives.add(black);
        this.detectives.add(red);
        this.detectives.add(green);
        this.detectives.add(yellow);

        this.mr_x = mr_x;
        this.round = round;
    }

    public GameState(GameState gameState) {
        this.blue = new Detective(Color.BLUE, gameState.blue.getCurrentLocation(), gameState.blue.copyTickets());
        this.black = new Detective(Color.BLACK, gameState.black.getCurrentLocation(), gameState.black.copyTickets());
        this.red = new Detective(Color.RED, gameState.red.getCurrentLocation(), gameState.red.copyTickets());
        this.green = new Detective(Color.GREEN, gameState.green.getCurrentLocation(), gameState.green.copyTickets());
        this.yellow = new Detective(Color.YELLOW, gameState.yellow.getCurrentLocation(), gameState.yellow.copyTickets());

        this.detectives.add(blue);
        this.detectives.add(black);
        this.detectives.add(red);
        this.detectives.add(green);
        this.detectives.add(yellow);

        this.mr_x = new Mr_X(Color.WHITE, gameState.mr_x.getCurrentLocation());
        this.round = gameState.round;
    }

    public static GameState copyGameState(ArrayList<Detective> detectives, Mr_X mr_x, int roundCount) {
        return new GameState(detectives, mr_x, roundCount);
    }
}
