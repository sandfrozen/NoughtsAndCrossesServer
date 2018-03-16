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
    
    private String[] fields = { " ", " ", " ", " ", " ", " ", " ", " ", " " };
    private int count = 0;
    
    public void setField(int i) {
        if( i<0 || i > 8 ) return;
        
        try {
            fields[i] = actualPlayer.getSymbol();
            ++count;
        } catch (RemoteException ex) {
            Logger.getLogger(Tournament.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isFreeField(int i) {
        return fields[i] == " " ? true : false;
    }
    
    public String[] getFields() {
        return fields;
    }
    
    public void setActualPlayer(PlayerInterface p) {
        actualPlayer = p;
    }
    
    public PlayerInterface getActualPlayer() {
        return actualPlayer;
    }
    
    public boolean isDraw()  {
        return count == 9;
    }
    
    public boolean isWinner() throws RemoteException {
//        0 1 2
//        3 4 5
//        6 7 8
        // horizontals
        String symbol = actualPlayer.getSymbol();
        //System.out.println("ACTUAL SYMBOL: " + actualPlayer.getSymbol());
        if( fields[0].equals(symbol) && fields[1].equals(symbol) && fields[2].equals(symbol) ) {
            return true;
        } else if( fields[3].equals(symbol) && fields[4].equals(symbol) && fields[5].equals(symbol) ) {
            return true;
        } else if( fields[6].equals(symbol) && fields[7].equals(symbol) && fields[8].equals(symbol) ) {
            return true;
        } // verticals
        else if( fields[0].equals(symbol) && fields[3].equals(symbol) && fields[6].equals(symbol) ) {
            return true;
        } else if( fields[1].equals(symbol) && fields[4].equals(symbol) && fields[7].equals(symbol) ) {
            return true;
        } else if( fields[2].equals(symbol) && fields[5].equals(symbol) && fields[8].equals(symbol) ) {
            return true;
        } // diagonals
        else if( fields[0].equals(symbol) && fields[4].equals(symbol) && fields[8].equals(symbol) ) {
            return true;
        } else if( fields[2].equals(symbol) && fields[4].equals(symbol) && fields[6].equals(symbol) ) {
            return true;
        }
        return false;
    }
    
}
