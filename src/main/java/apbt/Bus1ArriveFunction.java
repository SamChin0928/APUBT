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
public class Bus1ArriveFunction extends Thread {

    Bus1Arrive bus1Arrive;                  //Instantiate thread.
    Bus1 bus1;                              //Instantiate class. A shared container that may be an area for race condition.
    EnterBus1 enterBus1;                      //Instantiate thread.

    public void set(Bus1Arrive b1Arrive, Bus1 b1, EnterBus1 eB) {
        bus1Arrive = b1Arrive;
        bus1 = b1;
        enterBus1 = eB;
    }

    public void run() {
        try {

            while (bus1Arrive.arrived == false) {
                int random = new Random().nextInt(2);
                int random2 = new Random().nextInt(2);
                if (random == 0) {

                    Thread.sleep(10000);
                    System.out.println("BUS 1 ARRIVED");

                    synchronized (bus1Arrive) {     //Concurrency concept - synchronization deployed to avoid race condition.
                        bus1Arrive.arrived = true;
                    }

                    if (random2 == 0) {
                        try {
                            Thread.sleep(10000);
                            System.out.println("                                                                                BUS 1 LEFT");
                            synchronized (bus1Arrive) {     //Concurrency concept - synchronization deployed to avoid race condition.
                                bus1Arrive.arrived = false;
                            }
                            synchronized (bus1) {   //Concurrency concept - synchronization deployed to avoid race condition.
                                bus1.count = 0;
                            }
                        } catch (Exception ex) {
                        }
                    } else if (random2 == 1) {
                        System.out.println("BUS 1 WAITING FOR ALL PASSENGERS");
                        synchronized (enterBus1) {   //Concurrency concept - synchronization delayed since wait is called upon an object.
                            enterBus1.wait();        //Wait for customers to enter bus.
                        }

                    }
                } else if (random == 1) {
                    if (bus1Arrive.arrived == false) {
                        System.out.println("BUS 1 DELAYED");

                        synchronized (bus1Arrive) {     //Concurrency concept - synchronization deployed to avoid race condition.
                            bus1Arrive.arrived = false;
                        }

                    }
                }
            }

        } catch (Exception e) {
        }
    }
}
