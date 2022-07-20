/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apbt;

/**
 *
 * @author Sam
 */
public class MachineWaitFunction extends Thread {

    MalfunctionFunction malfunctionFunction;            //Instantiate thread.
    MachineMalfunctionCheck machineMalfunctionCheck;    //Instantiate class. A shared container that may be an area for race condition.

    public void set(MachineMalfunctionCheck mC, MalfunctionFunction mF) {
        machineMalfunctionCheck = mC;
        malfunctionFunction = mF;
    }

    public void run() {
        try {
            while (true) {
                while (machineMalfunctionCheck.malfunction == true) {
                    synchronized (malfunctionFunction) {    //Concurrency concept - synchronization delayed since wait is called upon an object.
                        malfunctionFunction.wait();         //Wait for machine to malfunction.
                    }
                }

                if (machineMalfunctionCheck.malfunction == false) {
                    Thread.sleep(2000);

                    System.out.println("MACHINE BACK UP");
                    synchronized (machineMalfunctionCheck) {    //Concurrency concept - synchronization deployed to avoid race condition.
                        machineMalfunctionCheck.malfunction = true;
                    }
                    synchronized (this) {   //Concurrency concept - synchronization deployed to notify machine malfunction function.
                        this.notify();
                    }
                }
            }
        } catch (Exception e) {
        }
    }
}
