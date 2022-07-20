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
public class WaitingRoom {

    int roomMAX = 10, foyerMAX = 100;
    Lock room_lock = new ReentrantLock();

    public void room(RunFunction rF, Foyer foyerCount, Room1 room1Count, Room2 room2Count, Room3 room3Count, BoughtCheck boughtCheck, InWaitingRoom inWaitingRoom) {
        if (boughtCheck.hasTicket == true) {
            Random rand = new Random(); //instance of random class
            int upperbound = 3;
            //generate random values from 0-2
            int option = rand.nextInt(upperbound);

            if (option == 0) {
                if (room1Count.count != roomMAX) {
                    System.out.println(rF.getName() + "                         entered ROOM 1");
                    synchronized (foyerCount) { //Concurrency concept - synchronization deployed to avoid race condition.
                        foyerCount.count--;
                    }
                    synchronized (boughtCheck) {    //Concurrency concept - synchronization deployed to avoid race condition.
                        boughtCheck.hasTicket = false;
                    }
                    synchronized (room1Count) { //Concurrency concept - synchronization deployed to avoid race condition.
                        room1Count.count++;
                    }

                    synchronized (inWaitingRoom) {  //Concurrency concept - synchronization deployed to avoid race condition.
                        inWaitingRoom.inside = true;
                    }

                } else {
                    if (room1Count.count == roomMAX) {
                        System.out.println("ROOM 1 FULL -" + room1Count.count);
                    }
                }
            } else if (option == 1) {
                if (room2Count.count != roomMAX) {
                    System.out.println(rF.getName() + "                         entered ROOM 2");
                    synchronized (foyerCount) { //Concurrency concept - synchronization deployed to avoid race condition.
                        foyerCount.count--;
                    }
                    synchronized (boughtCheck) {    //Concurrency concept - synchronization deployed to avoid race condition.
                        boughtCheck.hasTicket = false;
                    }
                    synchronized (room2Count) { //Concurrency concept - synchronization deployed to avoid race condition.
                        room2Count.count++;
                    }

                    synchronized (inWaitingRoom) {  //Concurrency concept - synchronization deployed to avoid race condition.
                        inWaitingRoom.inside = true;
                    }

                } else {
                    System.out.println("ROOM 2 FULL -" + room2Count.count);
                }
            } else if (option == 2) {
                if (room3Count.count != roomMAX) {
                    System.out.println(rF.getName() + "                         entered ROOM 3");
                    synchronized (foyerCount) { //Concurrency concept - synchronization deployed to avoid race condition.
                        foyerCount.count--;
                    }
                    synchronized (boughtCheck) {    //Concurrency concept - synchronization deployed to avoid race condition.
                        boughtCheck.hasTicket = false;
                    }
                    synchronized (room3Count) { //Concurrency concept - synchronization deployed to avoid race condition.
                        room3Count.count++;
                    }

                    synchronized (inWaitingRoom) {  //Concurrency concept - synchronization deployed to avoid race condition.
                        inWaitingRoom.inside = true;
                    }

                } else {
                    System.out.println("ROOM 3 FULL -" + room3Count.count);
                }
            }
        }
        synchronized (this) {   //Concurrency concept - synchronization deployed to notify other threads.
            if (foyerCount.count < 70) {
                this.notify();
            }
        }
    }
}
