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
public class InspectorWaitFunction extends Thread {

    InspectorBreakFunction inspectorBreakFunction;          //Instantiate thread.
    InspectorToiletBreakCheck inspectorToiletBreakCheck;    //Instantiate class. A shared container that may be an area for race condition.

    public void set(InspectorToiletBreakCheck iC, InspectorBreakFunction iF) {
        inspectorToiletBreakCheck = iC;
        inspectorBreakFunction = iF;
    }

    public void run() {
        try {
            while (true) { //Loop forever
                while (inspectorToiletBreakCheck.inBreak == true) {
                    synchronized (inspectorBreakFunction) { //Concurrency concept - synchronization delayed since wait is called upon an object.
                        inspectorBreakFunction.wait();      //Wait for inspector in toilet.
                    }
                }

                if (inspectorToiletBreakCheck.inBreak == false) {
                    Thread.sleep(5000);
                    System.out.println("INSPECTOR WORKING");
                    synchronized (inspectorToiletBreakCheck) {  //Concurrency concept - synchronization deployed to avoid race condition.
                        inspectorToiletBreakCheck.inBreak = true;
                    }

                    synchronized (this) {   //Concurrency concept - synchronization deployed to notify inspector break function.
                        this.notify();
                    }
                }
            }

        } catch (Exception e) {
        }
    }
}
