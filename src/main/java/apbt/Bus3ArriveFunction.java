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
public class Bus3ArriveFunction extends Thread {

    Bus3Arrive bus3Arrive;                  //Instantiate thread.
    Bus3 bus3;                              //Instantiate class. A shared container that may be an area for race condition.
    EnterBus3 enterBus3;                      //Instantiate thread.

    public void set(Bus3Arrive b3Arrive, Bus3 b3, EnterBus3 eB) {
        bus3Arrive = b3Arrive;
        bus3 = b3;
        enterBus3 = eB;
    }

    public void run() {
        try {

            while (bus3Arrive.arrived == false) {
                int random = new Random().nextInt(2);
                int random2 = new Random().nextInt(2);
                if (random == 0) {

                    Thread.sleep(10000);
                    System.out.println("BUS 3 ARRIVED");

                    synchronized (bus3Arrive) { //Concurrency concept - synchronization deployed to avoid race condition.
                        bus3Arrive.arrived = true;
                    }

                    if (random2 == 0) {
                        try {
                            Thread.sleep(10000);
                            System.out.println("                                                                                BUS 3 LEFT");

                            synchronized (bus3Arrive) { //Concurrency concept - synchronization deployed to avoid race condition.
                                bus3Arrive.arrived = false;
                            }
                            synchronized (bus3) {   //Concurrency concept - synchronization deployed to avoid race condition.
                                bus3.count = 0;
                            }
                        } catch (Exception ex) {
                        }
                    } else if (random2 == 1) {
                        System.out.println("BUS 3 WAITING FOR ALL PASSENGERS");
                        synchronized (enterBus3) {   //Concurrency concept - synchronization delayed since wait is called upon an object.
                            enterBus3.wait();        //Wait for customers to enter bus.
                        }
                    }
                } else if (random == 1) {
                    if (bus3Arrive.arrived == false) {
                        System.out.println("BUS 3 DELAYED");

                        synchronized (bus3Arrive) {     //Concurrency concept - synchronization deployed to avoid race condition.
                            bus3Arrive.arrived = false;
                        }
                    }
                }

            }
        } catch (Exception e) {
        }
    }
}
