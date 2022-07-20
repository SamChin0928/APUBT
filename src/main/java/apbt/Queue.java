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
public class Queue {

    int queueMAX = 5;
    Lock queue_lock1 = new ReentrantLock(); //Create lock.

    public void buy(RunFunction rF, Booth2 booth2Count, Booth2ToiletBreakCheck booth2ToiletBreakCheck, BoughtCheck boughtCheck, Machine machineCount, MachineMalfunctionCheck machineMalfunctionCheck, Booth1 booth1Count, Booth1ToiletBreakCheck booth1ToiletBreakCheck) {
        try {
            Random rand = new Random(); //instance of random class
            int upperbound = 3;
            //generate random values from 0-2
            int option = rand.nextInt(upperbound);

            if (option == 0) {
                if (machineMalfunctionCheck.malfunction == true) {

                    if (machineCount.count != queueMAX) {
                        synchronized (machineCount) {   //Concurrency concept - synchronization deployed to avoid race condition.
                            machineCount.count++;
                            System.out.println(rF.getName() + "         QUEUED at the MACHINE");

                        }
                    }

                    Thread.sleep(4000);
                    System.out.println(rF.getName() + "             BOUGHT a ticket from MACHINE");
                    synchronized (machineCount) {   //Concurrency concept - synchronization deployed to avoid race condition.
                        machineCount.count--;
                    }

                    synchronized (boughtCheck) {    //Concurrency concept - synchronization deployed to avoid race condition.
                        boughtCheck.hasTicket = true;
                    }
                }
            } else if (option == 1) {
                if (booth1Count.count != queueMAX) {
                    synchronized (booth1Count) {    //Concurrency concept - synchronization deployed to avoid race condition.
                        booth1Count.count++;
                        System.out.println(rF.getName() + "         QUEUED at the BOOTH 1");
                    }
                }

                if (booth1ToiletBreakCheck.inBreak == true) {
                    Thread.sleep(8000);
                    System.out.println(rF.getName() + "             BOUGHT a ticket from BOOTH 1");
                    synchronized (booth1Count) {    //Concurrency concept - synchronization deployed to avoid race condition.
                        booth1Count.count--;
                    }

                    synchronized (boughtCheck) {    //Concurrency concept - synchronization deployed to avoid race condition.
                        boughtCheck.hasTicket = true;
                    }
                }
            } else if (option == 2) {
                if (booth2Count.count != queueMAX) {
                    synchronized (booth2Count) {    //Concurrency concept - synchronization deployed to avoid race condition.
                        booth2Count.count++;
                        System.out.println(rF.getName() + "         QUEUED at the BOOTH 2");
                    }
                }
                if (booth2ToiletBreakCheck.inBreak == true) {
                    Thread.sleep(8000);
                    System.out.println(rF.getName() + "             BOUGHT a ticket from BOOTH 2");
                    synchronized (booth2Count) {    //Concurrency concept - synchronization deployed to avoid race condition.
                        booth2Count.count--;

                    }

                    synchronized (boughtCheck) {    //Concurrency concept - synchronization deployed to avoid race condition.
                        boughtCheck.hasTicket = true;
                    }
                }
            }
        } catch (Exception e) {
        }
    }
}
