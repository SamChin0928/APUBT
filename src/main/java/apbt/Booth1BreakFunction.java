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
public class Booth1BreakFunction extends Thread {

    Booth1ToiletBreakCheck booth1ToiletBreakCheck;  //Instantiate class. A shared container that may be an area for race condition.
    Booth1WaitFunction booth1WaitFunction;          //Instantiate thread.

    public void set(Booth1ToiletBreakCheck bC, Booth1WaitFunction bF) {
        booth1ToiletBreakCheck = bC;
        booth1WaitFunction = bF;
    }

    public void run() {
        try {
            while (true) { //Loop forever
                while (booth1ToiletBreakCheck.inBreak == false) {
                    synchronized (booth1WaitFunction) { //Concurrency concept - synchronization delayed since wait is called upon an object.
                        booth1WaitFunction.wait();      //wait for employee to work.
                    }
                }

                if (booth1ToiletBreakCheck.inBreak == true) {
                    Thread.sleep(new Random().nextInt((3) * 4000));
                    System.out.println("BOOTH 1 TOILET BREAK");
                    synchronized (booth1ToiletBreakCheck) { //Concurrency concept - synchronization deployed to avoid race condition.
                        booth1ToiletBreakCheck.inBreak = false;
                    }
                    synchronized (this) {   //Concurrency concept - synchronization deployed to notify booth 1 wait function.
                        this.notify();
                    }
                }
            }
        } catch (Exception e) {
        }
    }

}
