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
public class Booth2BreakFunction extends Thread {

    Booth2ToiletBreakCheck booth2ToiletBreakCheck;  //Instantiate class. A shared container that may be an area for race condition.
    Booth2WaitFunction booth2WaitFunction;          //Instantiate thread.

    public void set(Booth2ToiletBreakCheck bC, Booth2WaitFunction bF) {
        booth2ToiletBreakCheck = bC;
        booth2WaitFunction = bF;
    }

    public void run() {
        try {
            while (true) {
                while (booth2ToiletBreakCheck.inBreak == false) {
                    synchronized (booth2WaitFunction) { //Concurrency concept - synchronization delayed since wait is called upon an object.
                        booth2WaitFunction.wait();      //wait for employee to work.
                    }
                }

                if (booth2ToiletBreakCheck.inBreak == true) {
                    Thread.sleep(new Random().nextInt((3) * 4000));
                    System.out.println("BOOTH 2 TOILET BREAK");
                    synchronized (booth2ToiletBreakCheck) { //Concurrency concept - synchronization deployed to avoid race condition.
                        booth2ToiletBreakCheck.inBreak = false;
                    }
                    synchronized (this) {   //Concurrency concept - synchronization deployed to notify booth 2 wait function.
                        this.notify();
                    }
                }
            }
        } catch (Exception e) {
        }
    }

}
