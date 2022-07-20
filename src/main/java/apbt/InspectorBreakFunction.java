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
public class InspectorBreakFunction extends Thread {

    InspectorToiletBreakCheck inspectorToiletBreakCheck;    //Instantiate class. A shared container that may be an area for race condition.
    InspectorWaitFunction inspectorWaitFunction;            //Instantiate thread.

    public void set(InspectorToiletBreakCheck iC, InspectorWaitFunction iF) {
        inspectorToiletBreakCheck = iC;
        inspectorWaitFunction = iF;
    }

    public void run() {
        try {
            while (true) {
                while (inspectorToiletBreakCheck.inBreak == false) {
                    synchronized (inspectorWaitFunction) {  //Concurrency concept - synchronization delayed since wait is called upon an object.
                        inspectorWaitFunction.wait();       //wait for inspector to work.
                    }
                }

                if (inspectorToiletBreakCheck.inBreak == true) {
                    Thread.sleep(15000);
                    System.out.println("INSPECTOR TOILET BREAK");
                    synchronized (inspectorToiletBreakCheck) {  //Concurrency concept - synchronization deployed to avoid race condition.
                        inspectorToiletBreakCheck.inBreak = false;
                    }
                    synchronized (this) {   //Concurrency concept - synchronization deployed to notify inspector wait function.
                        this.notify();
                    }
                }
            }
        } catch (Exception e) {
        }
    }

}
