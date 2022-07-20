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
public class Booth1WaitFunction extends Thread {

    Booth1BreakFunction booth1BreakFunction;        //Instantiate thread.
    Booth1ToiletBreakCheck booth1ToiletBreakCheck;  //Instantiate class. A shared container that may be an area for race condition.

    public void set(Booth1ToiletBreakCheck bC,Booth1BreakFunction bF) {
        booth1BreakFunction = bF;
        booth1ToiletBreakCheck = bC;
    }

    public void run() {
        try {
            while (true) { //Loop forever
                while (booth1ToiletBreakCheck.inBreak == true) {
                    synchronized (booth1BreakFunction) {    //Concurrency concept - synchronization delayed since wait is called upon an object.
                        booth1BreakFunction.wait();         //Wait for employee in toilet.
                    }
                }

                if (booth1ToiletBreakCheck.inBreak == false) {
                    Thread.sleep(3000);
                    System.out.println("BOOTH 1 WORKING");
                    synchronized (booth1ToiletBreakCheck) { //Concurrency concept - synchronization deployed to avoid race condition.
                        booth1ToiletBreakCheck.inBreak = true;
                    }

                    synchronized (this) {   //Concurrency concept - synchronization deployed to notify booth 1 break function.
                        this.notify();
                    }
                }
            }

        } catch (Exception e) {
        }
    }
}
