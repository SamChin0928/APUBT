/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apbt;

import java.util.Random;

/**
 *
 * @author Sam
 */
public class MalfunctionFunction extends Thread {

    MachineMalfunctionCheck malfunctionCheck;   //Instantiate class. A shared container that may be an area for race condition.
    MachineWaitFunction machineWaitFunction;    //Instantiate thread.

    public void set(MachineMalfunctionCheck mC, MachineWaitFunction mF) {
        malfunctionCheck = mC;
        machineWaitFunction = mF;
    }

    public void run() {
        try {
            while (true) {
                while (malfunctionCheck.malfunction == false) {
                    synchronized (machineWaitFunction) {    //Concurrency concept - synchronization delayed since wait is called upon an object.
                        machineWaitFunction.wait();         //Wait for machine to work.
                    }
                }

                if (malfunctionCheck.malfunction == true) {
                    try {
                        Thread.sleep(10000);
                        System.out.println("MACHINE MALFUNCTION - ALL CUSTOMERS QUEUED HAS SHIFTED");
                        synchronized (malfunctionCheck) {   //Concurrency concept - synchronization deployed to avoid race condition.
                            malfunctionCheck.malfunction = false;
                        }

                        synchronized (this) {   //Concurrency concept - synchronization deployed to notify machine wait function.
                            this.notify();
                        }
                    } catch (Exception e) {
                    }
                }

            }
        } catch (Exception e) {
        }
    }
}
