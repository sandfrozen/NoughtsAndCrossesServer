/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkserver;

import interfaces.GameInterface;
import java.util.ArrayList;
import java.util.List;
import interfaces.PlayerInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author tomek.buslowski
 */
public class Game extends UnicastRemoteObject implements GameInterface {
    
    private Tournament tournament;
    public List<PlayerInterface> players = new ArrayList<>();
    
    static private Game instance;
    static public Game getInstance() throws RemoteException {
        if( instance == null ) {
            instance = new Game();
        }
        return instance;
    }
    
    private Game() throws RemoteException { /* Singleton */ }
    

    @Override
    public boolean addPlayer(PlayerInterface player) throws RemoteException {
        if( player != null && players.size() < 2 ) {
            players.add(player);
            System.out.println("Dodano: " + player.getName() + " " + player.hashCode());
            notifyObservers();
            return true;
        } else {
            System.out.println("Nie Dodano: " + player.getName());
            return false;
        }
    }

    @Override
    public void removePlayer(PlayerInterface player) throws RemoteException {
        if( player != null && findPlayer(player) != null ) {
            System.out.println("Usunięto: " + player.getName() + " " + player.hashCode());
            players.remove(player);
            if( players.size() == 1 && tournament != null){ 
                players.get(0).informWinner("Wygrałeś walkowerem.");
                tournament = null;
            }
            else notifyObservers();
        }
    }

    @Override
    public PlayerInterface findPlayer(PlayerInterface player) throws RemoteException {
        if( player != null ) {
            for (PlayerInterface p : players) {
                if( p.equals(player) ) {
                    return p;
                }
            }
        }
        return null;
    }
    
    @Override
    public String getFreeSymbol() throws RemoteException {
        if( players.isEmpty() ) {
            return "O";
        } else if( players.size() == 1 ) {
            return players.get(0).getSymbol().equals("O") ? "X" : "O";
        } else {
            return "";
        }
    }
    
    public PlayerInterface getNextPlayer(PlayerInterface actualPlayer) throws RemoteException {
        if( players.size() != 2 ) return null;
        
        return players.get(0).equals(actualPlayer) ? players.get(1) : players.get(0);
    }
    
    
   public void newGame() {
       tournament = new Tournament();
       tournament.setActualPlayer(players.get(0));
   }

    @Override
    public String getGameInfo() throws RemoteException {
        if( players.size() == 0 ) {
            return "Brak podłączonych graczy.";
        } else if ( players.size() == 1 ) {
            return "Oczekiwanie na drugiego gracza.";
        } else {
            if( tournament == null) return "Gracze gotowi go gry.";
            else {
                return "Gracze grają.";
            }
        }
    }

    @Override
    public void notifyObservers() throws RemoteException {
        if( players.size() == 2 && tournament == null ) {
            newGame();
        }
        
        for(PlayerInterface player : players) {
            player.update();
        }
    }
    
    public void setNoOneTurnObservers() throws RemoteException {
        for(PlayerInterface player : players) {
            player.setMyTurn(false);
        }
    }
    
    @Override
    public boolean WAITING() throws RemoteException {
        return players.size() < 2;
    }
    
    @Override
    public boolean PLAYING() throws RemoteException {
        return players.size() == 2 && tournament!=null;
    }

    @Override
    public PlayerInterface getActualPlayer() throws RemoteException {
        return tournament.getActualPlayer();
    }

    @Override
    public String[] getField() throws RemoteException {
        return tournament.getField();
    }

    @Override
    public void setField(int i) throws RemoteException {
        tournament.setField(i);
        if( tournament.isWinner() ) {
            setNoOneTurnObservers();
            if( tournament.getActualPlayer().equals(players.get(0)) ){
                players.get(0).informWinner("Wygrałeś !");
                players.get(1).informWinner("Przegrałeś.");
            } else {
                players.get(1).informWinner("Wygrałeś !");
                players.get(0).informWinner("Przegrałeś.");
            }
            tournament = null;
            
        } else {
            setNextPlayer();
            notifyObservers();
        }
    }

    @Override
    public void setNextPlayer() throws RemoteException {
        PlayerInterface p = players.get(0).equals(tournament.getActualPlayer()) ? players.get(1) : players.get(0);
        tournament.setActualPlayer(p);
    }
   
}
