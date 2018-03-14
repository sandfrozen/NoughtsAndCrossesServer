/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkserver;

import interfaces.PlayerInterface;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tomek.buslowski
 */
public class Tournament {
    
    private PlayerInterface actualPlayer;
    
    private String[] field = { " ", " ", " ", " ", " ", " ", " ", " ", " " };
    
    public void setField(int i) {
        if( i<1 || i > 9 ) return;
        
        try {
            field[i-1] = actualPlayer.getSymbol();
        } catch (RemoteException ex) {
            Logger.getLogger(Tournament.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String[] getField() {
        return field;
    }
    
    public void setActualPlayer(PlayerInterface p) {
        actualPlayer = p;
    }
    
    public PlayerInterface getActualPlayer() {
        return actualPlayer;
    }
    
    public PlayerInterface checkWin() {
        return null;
    }
    
    public boolean isWinner() throws RemoteException {
//        0 1 2
//        3 4 5
//        6 7 8
        // horizontals
        String symbol = actualPlayer.getSymbol();
        //System.out.println("ACTUAL SYMBOL: " + actualPlayer.getSymbol());
        if( field[0].equals(symbol) && field[1].equals(symbol) && field[2].equals(symbol) ) {
            return true;
        } else if( field[3].equals(symbol) && field[4].equals(symbol) && field[5].equals(symbol) ) {
            return true;
        } else if( field[6].equals(symbol) && field[7].equals(symbol) && field[8].equals(symbol) ) {
            return true;
        } // verticals
        else if( field[0].equals(symbol) && field[3].equals(symbol) && field[6].equals(symbol) ) {
            return true;
        } else if( field[1].equals(symbol) && field[4].equals(symbol) && field[7].equals(symbol) ) {
            return true;
        } else if( field[2].equals(symbol) && field[5].equals(symbol) && field[8].equals(symbol) ) {
            return true;
        } // diagonals
        else if( field[0].equals(symbol) && field[4].equals(symbol) && field[8].equals(symbol) ) {
            return true;
        } else if( field[2].equals(symbol) && field[4].equals(symbol) && field[6].equals(symbol) ) {
            return true;
        }
        return false;
    }
    
}
