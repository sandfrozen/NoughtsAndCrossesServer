/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkserver;

import interfaces.GameInterface;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author tomek.buslowski
 */

// KÓŁKO I KRZYŻYK
public class KKServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.setProperty("java.security.policy", "security.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        
        try {
            System.out.println("Server startuje.. czekaj.");
            
            LocateRegistry.createRegistry(1099);
            GameInterface game = Game.getInstance();
            Naming.rebind("//localhost/Game", game);
            
            System.out.println("Server jest gotowy.");
        } catch (MalformedURLException | RemoteException ex) {
            ex.printStackTrace();
        }
    }
    
}
