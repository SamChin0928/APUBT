/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apbt;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Sam
 */
public class Entrance {

    int foyerMAX = 100;
    Lock use_lock = new ReentrantLock(); //Create lock.

    public void enterFromEntrance(RunFunction rF, WaitingRoom wR, Foyer f, int option) {
        try {
 
                while (f.count == foyerMAX) {
                    System.out.println("FOYER FULL");
                    synchronized (wR) { //Concurrency concept - synchronization delayed since wait is called upon an object.
                        wR.wait();      //Wait for waiting room to consume customers.
                    }
                }

                Thread.sleep(new Random().nextInt(4) * 1000);
                synchronized (f) {  //Concurrency concept - synchronization deployed to avoid race condition.
                    f.count++;
                    if (option == 0) {
                        System.out.println(rF.getName() + " has entered throught the EAST");
                        System.out.println(rF.getName() + "     is in the FOYER");
                    } else if (option == 1) {
                        System.out.println(rF.getName() + " has entered throught the WEST");
                        System.out.println(rF.getName() + "     is in the FOYER");
                    }
                }
            
        } catch (Exception e) {
        }
    }
}
