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
public class Bus2ArriveFunction extends Thread {

    Bus2Arrive bus2Arrive;                  //Instantiate thread.
    Bus2 bus2;                              //Instantiate class. A shared container that may be an area for race condition.
    EnterBus2 enterBus2;                      //Instantiate thread.

    public void set(Bus2Arrive b2Arrive, Bus2 b2, EnterBus2 eB) {
        bus2Arrive = b2Arrive;
        bus2 = b2;
        enterBus2 = eB;
    }

    public void run() {
        try {

            while (bus2Arrive.arrived == false) {
                int random = new Random().nextInt(2);
                int random2 = new Random().nextInt(2);
                if (random == 0) {

                    Thread.sleep(10000);
                    System.out.println("BUS 2 ARRIVED");

                    synchronized (bus2Arrive) { //Concurrency concept - synchronization deployed to avoid race condition.
                        bus2Arrive.arrived = true;
                    }

                    if (random2 == 0) {
                        try {
                            Thread.sleep(10000);
                            System.out.println("                                                                                BUS 2 LEFT");
                            synchronized (bus2Arrive) { //Concurrency concept - synchronization deployed to avoid race condition.
                                bus2Arrive.arrived = false;
                            }
                            synchronized (bus2) {   //Concurrency concept - synchronization deployed to avoid race condition.
                                bus2.count = 0;
                            }
                        } catch (Exception ex) {
                        }
                    } else if (random2 == 1) {
                        System.out.println("BUS 2 WAITING FOR ALL PASSENGERS");
                        synchronized (enterBus2) {   //Concurrency concept - synchronization delayed since wait is called upon an object.
                            enterBus2.wait();        //Wait for customers to enter bus.
                        }
                    }
                } else if (random == 1) {
                    if (bus2Arrive.arrived == false) {
                        System.out.println("BUS 2 DELAYED");

                        synchronized (bus2Arrive) {     //Concurrency concept - synchronization deployed to avoid race condition.
                            bus2Arrive.arrived = false;
                        }

                    }
                }
            }

        } catch (Exception e) {
        }
    }
}
