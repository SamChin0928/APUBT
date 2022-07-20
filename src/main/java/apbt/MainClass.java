/*
This is an implemntation of the APBT simulation to the given problem using concurrency concepts. 
 */
package apbt;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sam
 */
//The MainClass to start the execution from.
public class MainClass {

    public static void main(String args[]) {
        Entrance entrance = new Entrance(); //A thread. Customers enter the foyer.
        Queue queue = new Queue(); //A thread. Customers queue at the booths.

        BoughtCheck boughtCheck = new BoughtCheck();    //A shared container that may be an area for race condition.

        InWaitingRoom inWaitingRoom = new InWaitingRoom(); //A shared container that may be an area for race condition.

        WaitingRoom waitingRoom = new WaitingRoom(); //A thread.
        CheckForTicket checkForTicket = new CheckForTicket(); //A shared container that may be an area for race condition.
        
        EnterBus1 enterBus1 = new EnterBus1(); //A thread. Customer enter bus 1 through any room.
        EnterBus2 enterBus2 = new EnterBus2(); //A thread. Customer enter bus 2 through any room.
        EnterBus3 enterBus3 = new EnterBus3(); //A thread. Customer enter bus 3 through any room.
        
        LeaveTerminal leaveTerminal = new LeaveTerminal(); //A thread. The bus leave APBT.

        Bus1ArriveFunction bus1ArriveFunction = new Bus1ArriveFunction(); //A thread. Bus 1 arrives at APBT, and either leaves after 10 seconds or wait until 12 customers board.
        Bus2ArriveFunction bus2ArriveFunction = new Bus2ArriveFunction(); //A thread. Bus 2 arrives at APBT, and either leaves after 10 seconds or wait until 12 customers board.
        Bus3ArriveFunction bus3ArriveFunction = new Bus3ArriveFunction(); //A thread. Bus 3 arrives at APBT, and either leaves after 10 seconds or wait until 12 customers board.

        MalfunctionFunction malfunctionFunction = new MalfunctionFunction(); //A thread. The ticket machine malfunctions.
        MachineWaitFunction machineWaitFunction = new MachineWaitFunction(); //A thread. The ticket machine gets fixed.
        MachineMalfunctionCheck machineMalfunctionCheck = new MachineMalfunctionCheck(); //A shared container that may be an area for race condition.

        InspectorBreakFunction inspectorBreakFunction = new InspectorBreakFunction(); //A thread. Inspector takes a break.
        InspectorWaitFunction inspectorWaitFunction = new InspectorWaitFunction(); //A thread. Inspector returns from the break and continues working.
        InspectorToiletBreakCheck inspectorToiletBreakCheck = new InspectorToiletBreakCheck(); //A shared container that may be an area for race condition.

        Booth1BreakFunction booth1BreakFunction = new Booth1BreakFunction(); //A thread. Booth 1 employee takes a break.
        Booth1WaitFunction booth1WaitFunction = new Booth1WaitFunction(); //A thread. Booth 1 employee returns from the break and continues working.
        Booth1ToiletBreakCheck booth1ToiletBreakCheck = new Booth1ToiletBreakCheck(); //A shared container that may be an area for race condition.

        Booth2BreakFunction booth2BreakFunction = new Booth2BreakFunction(); //A thread. Booth 2 employee takes a break.
        Booth2WaitFunction booth2WaitFunction = new Booth2WaitFunction(); //A thread. Booth 2 employee returns from the break and continues working.
        Booth2ToiletBreakCheck booth2ToiletBreakCheck = new Booth2ToiletBreakCheck(); //A shared container that may be an area for race condition.

        Foyer f = new Foyer();  //A shared container that may be an area for race condition.
        Machine m = new Machine();  //A shared container that may be an area for race condition.
        Booth1 b1 = new Booth1();   //A shared container that may be an area for race condition.
        Booth2 b2 = new Booth2();   //A shared container that may be an area for race condition. 
        Room1 r1 = new Room1(); //A shared container that may be a area for race condition.
        Room2 r2 = new Room2(); //A shared container that may be a area for race condition.
        Room3 r3 = new Room3(); //A shared container that may be a area for race condition.
        Bus1 bus1 = new Bus1(); //A shared container that may be a area for race condition.
        Bus2 bus2 = new Bus2(); //A shared container that may be a area for race condition.
        Bus3 bus3 = new Bus3(); //A shared container that may be a area for race condition.
        Bus1Arrive bus1Arrive = new Bus1Arrive();   //A shared container that may be an area for race condition.
        Bus2Arrive bus2Arrive = new Bus2Arrive();   //A shared container that may be an area for race condition.
        Bus3Arrive bus3Arrive = new Bus3Arrive();   //A shared container that may be an area for race condition.

        RunFunction rF[] = new RunFunction[150]; //A thread. Customers start from this thread.

        bus1ArriveFunction.set(bus1Arrive, bus1, enterBus1);  //Setting parameters for thread.
        bus2ArriveFunction.set(bus2Arrive, bus2, enterBus2);  //Setting parameters for thread.
        bus3ArriveFunction.set(bus3Arrive, bus3, enterBus3);  //Setting parameters for thread.

        malfunctionFunction.set(machineMalfunctionCheck, machineWaitFunction);  //Setting parameters for thread.
        machineWaitFunction.set(machineMalfunctionCheck, malfunctionFunction);  //Setting parameters for thread.

        inspectorBreakFunction.set(inspectorToiletBreakCheck, inspectorWaitFunction);   //Setting parameters for thread.
        inspectorWaitFunction.set(inspectorToiletBreakCheck, inspectorBreakFunction);   //Setting parameters for thread.

        booth1WaitFunction.set(booth1ToiletBreakCheck, booth1BreakFunction);    //Setting parameters for thread.
        booth1BreakFunction.set(booth1ToiletBreakCheck, booth1WaitFunction);    //Setting parameters for thread.

        booth2WaitFunction.set(booth2ToiletBreakCheck, booth2BreakFunction);    //Setting parameters for thread.
        booth2BreakFunction.set(booth2ToiletBreakCheck, booth2WaitFunction);    //Setting parameters for thread.

        bus1ArriveFunction.start(); //Starting thread. Bus enters APBT.
        bus2ArriveFunction.start(); //Starting thread. Bus enters APBT.
        bus3ArriveFunction.start(); //Starting thread. Bus enters APBT.

        machineWaitFunction.start();    //Starting thread. Machine is fixed.
        malfunctionFunction.start();    //Starting thread. Machine malfunctions.

        inspectorWaitFunction.start();  //Starting thread. Inspector comes back to work.
        inspectorBreakFunction.start(); //Starting thread. Inspector goes to the toilet.

        booth1WaitFunction.start();     //Starting thread. Booth 1 employee comes back to work.
        booth1BreakFunction.start();    //Starting thread. Booth 1 employee goes to toilet.

        booth2WaitFunction.start();     //Starting thread. Booth 2 employee comes back to work.
        booth2BreakFunction.start();    //Starting thread. Booth 2 employee goes to toilet.

        for (int i = 0; i < rF.length; i++) {
            rF[i] = new RunFunction( //Setting parameters for thread.
                    ("       Customer " + Integer.toString(i + 1)),
                    entrance,
                    queue,
                    boughtCheck,
                    inWaitingRoom,
                    waitingRoom,
                    checkForTicket,
                    enterBus1,
                    enterBus2,
                    enterBus3,
                    leaveTerminal,
                    bus1ArriveFunction,
                    bus2ArriveFunction,
                    bus3ArriveFunction,
                    machineMalfunctionCheck,
                    inspectorToiletBreakCheck,
                    booth1ToiletBreakCheck,
                    booth2ToiletBreakCheck,
                    f,
                    m,
                    b1,
                    b2,
                    r1,
                    r2,
                    r3,
                    bus1,
                    bus2,
                    bus3,
                    bus1Arrive,
                    bus2Arrive,
                    bus3Arrive
            );
            rF[i].start();  //Starting thread. Start the event of whatever a customer does in APBT.
////////////////////////////////////////////////////////////
//Seeing which thread is running - realtime.              //
//                Thread t = Thread.currentThread();      //
//                String name = t.getName();              //
//                System.out.println("name = " + name);   //
//System.out.println(inspectorToiletBreakCheck.inBreak);  //
////////////////////////////////////////////////////////////
        }

        for (int i = 0; i < rF.length; i++) {
            try {
                rF[i].join(); //Wait for thread to die.
            } catch (InterruptedException ex) {
                Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
