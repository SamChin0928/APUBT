/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apbt;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Sam
 */
public class LeaveTerminal extends Thread {

    public void leave(EnterBus2 enterBus, Bus1 bus1Count, Bus2 bus2Count, Bus3 bus3Count, Bus1Arrive bus1Arrive, Bus2Arrive bus2Arrive, Bus3Arrive bus3Arrive, int option) {
        if (option == 0) {
            try {
                Thread.sleep(10000);
                synchronized (bus1Count) {  //Concurrency concept - synchronization deployed to avoid race condition.
                    bus1Count.count = 0;
                    System.out.println("BUS 1 HAS LEFT APBT");
                }
                synchronized (bus1Arrive) { //Concurrency concept - synchronization deployed to avoid race condition.
                    bus1Arrive.arrived = false;
                }

            } catch (Exception e) {
            }
        } else if (option == 1) {
            try {
                Thread.sleep(10000);
                synchronized (bus2Count) {  //Concurrency concept - synchronization deployed to avoid race condition.
                    bus2Count.count = 0;
                    System.out.println("BUS 2 HAS LEFT APBT");
                }
                synchronized (bus2Arrive) { //Concurrency concept - synchronization deployed to avoid race condition.
                    bus2Arrive.arrived = false;
                }
            } catch (Exception e) {
            }

        } else if (option == 2) {
            try {
                Thread.sleep(10000);
                synchronized (bus3Count) {  //Concurrency concept - synchronization deployed to avoid race condition.
                    bus3Count.count = 0;
                    System.out.println("BUS 3 HAS LEFT APBT");
                }
                synchronized (bus3Arrive) { //Concurrency concept - synchronization deployed to avoid race condition.
                    bus3Arrive.arrived = false; 
                }
            } catch (Exception e) {
            }
        }
    }
}
