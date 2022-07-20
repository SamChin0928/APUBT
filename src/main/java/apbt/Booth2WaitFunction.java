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
public class Booth2WaitFunction extends Thread {

    Booth2BreakFunction booth2BreakFunction;        //Instantiate thread.
    Booth2ToiletBreakCheck booth2ToiletBreakCheck;  //Instantiate class. A shared container that may be an area for race condition.

    public void set(Booth2ToiletBreakCheck bC, Booth2BreakFunction bF) {
        booth2BreakFunction = bF;
        booth2ToiletBreakCheck = bC;
    }

    public void run() {
        try {
            while (true) {
                while (booth2ToiletBreakCheck.inBreak == true) {
                    synchronized (booth2BreakFunction) {    //Concurrency concept - synchronization delayed since wait is called upon an object.
                        booth2BreakFunction.wait();         //Wait for employee in toilet.
                    }
                }

                if (booth2ToiletBreakCheck.inBreak == false) {
                    Thread.sleep(3000);
                    System.out.println("BOOTH 2 WORKING");
                    synchronized (booth2ToiletBreakCheck) { //Concurrency concept - synchronization deployed to avoid race condition.
                        booth2ToiletBreakCheck.inBreak = true;
                    }

                    synchronized (this) {   //Concurrency concept - synchronization deployed to notify booth 2 break function.
                        this.notify();
                    }
                }
            }

        } catch (Exception e) {
        }
    }
}
