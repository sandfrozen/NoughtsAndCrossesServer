/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author tomek.buslowski
 */
public interface Observator extends Remote {
    
    public void update() throws RemoteException;
    
}
