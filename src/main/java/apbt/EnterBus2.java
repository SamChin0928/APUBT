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
public class EnterBus2 {

    int busMAX = 12;
    Lock bus_lock = new ReentrantLock();

    public void bus(RunFunction rF, Room1 room1Count, Room2 room2Count, Room3 room3Count,Bus2 bus2Count, Bus2Arrive bus2Arrive, CheckForTicket checkForTicket, LeaveTerminal leaveTerminal, InspectorToiletBreakCheck inspectorToiletBreakCheck, InWaitingRoom inWaitingRoom) {
        try {
            if (bus2Count.count == busMAX) {
                System.out.println("                                                                                BUS 2 FULL & LEFT");

                synchronized (bus2Arrive) { //Concurrency concept - synchronization deployed to avoid race condition.
                    bus2Arrive.arrived = false;
                    bus2Arrive.wait = false;
                }

                synchronized (bus2Count) {  //Concurrency concept - synchronization deployed to avoid race condition.
                    bus2Count.count = 0;
                }
                synchronized (this) {   //Concurrency concept - synchronization deployed to notify bus 2 arrive function.
                    this.notify();
                }
            }

            if (inspectorToiletBreakCheck.inBreak == true) {
                if (inWaitingRoom.inside == true) {
                    if (checkForTicket.hasTicket == false) {
                        Random rand = new Random(); //instance of random class
                        int upperbound = 3;
                        //generate random values from 0-2
                        int option = rand.nextInt(upperbound);

                        try {
                            if (bus2Arrive.arrived == true) {
                                if (option == 0) {
                                    if (room1Count.count != 0) {
                                        System.out.println("Ticket Inspector has checked " + rF.getName() + "'s ticket");
                                        synchronized (checkForTicket) { //Concurrency concept - synchronization deployed to avoid race condition.
                                            checkForTicket.hasTicket = true;
                                        }
                                        Thread.sleep(7000);

                                        synchronized (room1Count) { //Concurrency concept - synchronization deployed to avoid race condition.
                                            room1Count.count--;
                                            System.out.println(rF.getName() + "                                     entered BUS 2");
                                            synchronized (checkForTicket) { //Concurrency concept - synchronization deployed to avoid race condition.
                                                checkForTicket.hasTicket = false;
                                            }
                                        }

                                        synchronized (bus2Count) { //Concurrency concept - synchronization deployed to avoid race condition.
                                            bus2Count.count++;
                                        }

                                        synchronized (inWaitingRoom) {  //Concurrency concept - synchronization deployed to avoid race condition.
                                            inWaitingRoom.inside = false;
                                        }

                                    } else if (room1Count.count == 10) {
                                        System.out.println("Ticket Inspector has checked " + rF.getName() + "'s ticket");
                                        synchronized (checkForTicket) { //Concurrency concept - synchronization deployed to avoid race condition.
                                            checkForTicket.hasTicket = true;
                                        }
                                        Thread.sleep(7000);

                                        synchronized (room1Count) { //Concurrency concept - synchronization deployed to avoid race condition.
                                            room1Count.count--;
                                            System.out.println(rF.getName() + "                                     entered BUS 2");
                                            synchronized (checkForTicket) { //Concurrency concept - synchronization deployed to avoid race condition.
                                                checkForTicket.hasTicket = false;
                                            }
                                        }

                                        synchronized (bus2Count) { //Concurrency concept - synchronization deployed to avoid race condition.
                                            bus2Count.count++;
                                        }

                                        synchronized (inWaitingRoom) {  //Concurrency concept - synchronization deployed to avoid race condition.
                                            inWaitingRoom.inside = false;
                                        }

                                    }
                                } else if (option == 1) {
                                    if (room2Count.count != 0) {
                                        System.out.println("Ticket Inspector has checked " + rF.getName() + "'s ticket");
                                        synchronized (checkForTicket) { //Concurrency concept - synchronization deployed to avoid race condition.
                                            checkForTicket.hasTicket = true;
                                        }
                                        Thread.sleep(7000);

                                        synchronized (room2Count) { //Concurrency concept - synchronization deployed to avoid race condition.
                                            room2Count.count--;
                                            System.out.println(rF.getName() + "                                     entered BUS 2");
                                            synchronized (checkForTicket) { //Concurrency concept - synchronization deployed to avoid race condition.
                                                checkForTicket.hasTicket = false;
                                            }
                                        }

                                        synchronized (bus2Count) { //Concurrency concept - synchronization deployed to avoid race condition.
                                            bus2Count.count++;
                                        }

                                        synchronized (inWaitingRoom) {  //Concurrency concept - synchronization deployed to avoid race condition.
                                            inWaitingRoom.inside = false;
                                        }

                                    } else if (room2Count.count == 10) {
                                        System.out.println("Ticket Inspector has checked " + rF.getName() + "'s ticket");
                                        synchronized (checkForTicket) { //Concurrency concept - synchronization deployed to avoid race condition.
                                            checkForTicket.hasTicket = true;
                                        }
                                        Thread.sleep(7000);

                                        synchronized (room2Count) { //Concurrency concept - synchronization deployed to avoid race condition.
                                            room2Count.count--;
                                            System.out.println(rF.getName() + "                                     entered BUS 2");
                                            synchronized (checkForTicket) { //Concurrency concept - synchronization deployed to avoid race condition.
                                                checkForTicket.hasTicket = false;
                                            }
                                        }

                                        synchronized (bus2Count) { //Concurrency concept - synchronization deployed to avoid race condition.
                                            bus2Count.count++;
                                        }

                                        synchronized (inWaitingRoom) {  //Concurrency concept - synchronization deployed to avoid race condition.
                                            inWaitingRoom.inside = false;
                                        }

                                    }
                                } else if (option == 2) {
                                    if (room3Count.count != 0) {
                                        System.out.println("Ticket Inspector has checked " + rF.getName() + "'s ticket");
                                        synchronized (checkForTicket) { //Concurrency concept - synchronization deployed to avoid race condition.
                                            checkForTicket.hasTicket = true;
                                        }
                                        Thread.sleep(7000);

                                        synchronized (room3Count) { //Concurrency concept - synchronization deployed to avoid race condition.
                                            room3Count.count--;
                                            System.out.println(rF.getName() + "                                     entered BUS 2");
                                            synchronized (checkForTicket) { //Concurrency concept - synchronization deployed to avoid race condition.
                                                checkForTicket.hasTicket = false;
                                            }
                                        }

                                        synchronized (bus2Count) { //Concurrency concept - synchronization deployed to avoid race condition.
                                            bus2Count.count++;
                                        }

                                        synchronized (inWaitingRoom) {  //Concurrency concept - synchronization deployed to avoid race condition.
                                            inWaitingRoom.inside = false;
                                        }

                                    } else if (room3Count.count == 10) {
                                        System.out.println("Ticket Inspector has checked " + rF.getName() + "'s ticket");
                                        synchronized (checkForTicket) { //Concurrency concept - synchronization deployed to avoid race condition.
                                            checkForTicket.hasTicket = true;
                                        }
                                        Thread.sleep(7000);

                                        synchronized (room3Count) { //Concurrency concept - synchronization deployed to avoid race condition.
                                            room3Count.count--;
                                            System.out.println(rF.getName() + "                                     entered BUS 2");
                                            synchronized (checkForTicket) { //Concurrency concept - synchronization deployed to avoid race condition.
                                                checkForTicket.hasTicket = false;
                                            }
                                        }

                                        synchronized (bus2Count) { //Concurrency concept - synchronization deployed to avoid race condition.
                                            bus2Count.count++;
                                        }

                                        synchronized (inWaitingRoom) {  //Concurrency concept - synchronization deployed to avoid race condition.
                                            inWaitingRoom.inside = false;
                                        }
                                    }
                                }
                            }
                        } catch (Exception ex) {
                        }
                    }
                }
            }
        } catch (Exception e) {
        }

    }

}
