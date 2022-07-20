/*

 */
package apbt;

import java.util.Random;

/**
 *
 * @author Sam
 */
public class RunFunction extends Thread {

    Entrance entrance;  //Instantiate class.
    Queue queue;        //Instantiate class.

    BoughtCheck boughtCheck;    //Instantiate class. A shared container that may be an area for race condition.

    InWaitingRoom inWaitingRoom;    //Instantiate class. A shared container that may be an area for race condition.

    WaitingRoom waitingRoom;        //Instantiate class.
    CheckForTicket checkForTicket;  //Instantiate class. A shared container that may be an area for race condition.

    EnterBus1 enterBus1;              //Instantiate class.
    EnterBus2 enterBus2;              //Instantiate class.
    EnterBus3 enterBus3;              //Instantiate class.

    LeaveTerminal leaveTerminal;    //Instantiate class.

    Bus1ArriveFunction bus1ArriveFunc;  //Instantiate class.
    Bus2ArriveFunction bus2ArriveFunc;  //Instantiate class.
    Bus3ArriveFunction bus3ArriveFunc;  //Instantiate class.

    MachineMalfunctionCheck machineMalfunctionCheck;    //Instantiate class. A shared container that may be an area for race condition.

    InspectorToiletBreakCheck inspectorToiletBreakCheck;    //Instantiate class. A shared container that may be an area for race condition.

    Booth1ToiletBreakCheck booth1ToiletBreakCheck;  //Instantiate class. A shared container that may be an area for race condition.
    Booth2ToiletBreakCheck booth2ToiletBreakCheck;  //Instantiate class. A shared container that may be an area for race condition.

    Foyer foyerCount;       //Instantiate class. A shared container that may be an area for race condition.
    Machine machineCount;   //Instantiate class. A shared container that may be an area for race condition.
    Booth1 booth1Count;     //Instantiate class. A shared container that may be an area for race condition.
    Booth2 booth2Count;     //Instantiate class. A shared container that may be an area for race condition.
    Room1 room1Count;       //Instantiate class. A shared container that may be an area for race condition.
    Room2 room2Count;       //Instantiate class. A shared container that may be an area for race condition.
    Room3 room3Count;       //Instantiate class. A shared container that may be an area for race condition.
    Bus1 bus1Count;         //Instantiate class. A shared container that may be an area for race condition.
    Bus2 bus2Count;         //Instantiate class. A shared container that may be an area for race condition.
    Bus3 bus3Count;         //Instantiate class. A shared container that may be an area for race condition.
    Bus1Arrive bus1Arrive;  //Instantiate class. A shared container that may be an area for race condition.
    Bus2Arrive bus2Arrive;  //Instantiate class. A shared container that may be an area for race condition.
    Bus3Arrive bus3Arrive;  //Instantiate class. A shared container that may be an area for race condition.

    public RunFunction(
            String name,
            Entrance e,
            Queue q,
            BoughtCheck bC,
            InWaitingRoom iWR,
            WaitingRoom wR,
            CheckForTicket cFT,
            EnterBus1 eB1,
            EnterBus2 eB2,
            EnterBus3 eB3,
            LeaveTerminal lT,
            Bus1ArriveFunction b1AFunc,
            Bus2ArriveFunction b2AFunc,
            Bus3ArriveFunction b3AFunc,
            MachineMalfunctionCheck mMC,
            InspectorToiletBreakCheck iC,
            Booth1ToiletBreakCheck bTC1,
            Booth2ToiletBreakCheck bTC2,
            Foyer fC,
            Machine mC,
            Booth1 b1C,
            Booth2 b2C,
            Room1 r1C,
            Room2 r2C,
            Room3 r3C,
            Bus1 bus1C,
            Bus2 bus2C,
            Bus3 bus3C,
            Bus1Arrive b1A,
            Bus2Arrive b2A,
            Bus3Arrive b3A
    ) {
        super(name);
        entrance = e;
        queue = q;
        boughtCheck = bC;
        inWaitingRoom = iWR;
        waitingRoom = wR;
        checkForTicket = cFT;
        enterBus1 = eB1;
        enterBus2 = eB2;
        enterBus3 = eB3;
        leaveTerminal = lT;
        bus1ArriveFunc = b1AFunc;
        bus2ArriveFunc = b2AFunc;
        bus3ArriveFunc = b3AFunc;
        machineMalfunctionCheck = mMC;
        inspectorToiletBreakCheck = iC;
        booth1ToiletBreakCheck = bTC1;
        booth2ToiletBreakCheck = bTC2;
        foyerCount = fC;
        machineCount = mC;
        booth1Count = b1C;
        booth2Count = b2C;
        room1Count = r1C;
        room2Count = r2C;
        room3Count = r3C;
        bus1Count = bus1C;
        bus2Count = bus2C;
        bus3Count = bus3C;
        bus1Arrive = b1A;
        bus2Arrive = b2A;
        bus3Arrive = b3A;
    }

    public void run() {
////////////////////////////////////////////////////////////////////
//Concurency concept - Locks used to run the simulation with order//
////////////////////////////////////////////////////////////////////

        Random rand = new Random(); //instance of random class
        int upperbound = 3;
        //generate random values from 0-2
        int random = rand.nextInt(upperbound);

//Entrance is for customers entering APBT////////////////////////////////////////////////////
        entrance.use_lock.lock();                                                          // 
        entrance.enterFromEntrance(this, waitingRoom, foyerCount, new Random().nextInt(2));//
        entrance.use_lock.unlock();                                                        //
/////////////////////////////////////////////////////////////////////////////////////////////

//Queue is for customers lining up to buy tickets and buying the tickets////////////////////////////////////////////////////////////////////////////////
        queue.queue_lock1.lock();                                                                                                                     //
        queue.buy(this, booth2Count, booth2ToiletBreakCheck, boughtCheck, machineCount, machineMalfunctionCheck, booth1Count, booth1ToiletBreakCheck);//
        queue.queue_lock1.unlock();                                                                                                                   //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//Waiting Room is for after customers has bought theit tickets, they will enter a waiting room.////////////////////////
        waitingRoom.room_lock.lock();                                                                                //
        waitingRoom.room(this, foyerCount, room1Count, room2Count, room3Count, boughtCheck, inWaitingRoom);          //
        waitingRoom.room_lock.unlock();                                                                              //
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//Enter Bus is for customers to enter the bus, after entering and getting their tickets checked by the inspector.////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        enterBus1.bus_lock.lock();                                                                                                                                                                                                                                        //
        enterBus2.bus_lock.lock();                                                                                                                                                                                                                                        //
        enterBus3.bus_lock.lock();                                                                                                                                                                                                                                        //
        if (random == 0) {                                                                                                                                                                                                                                                //
            enterBus1.bus(this, room1Count, room2Count, room3Count, bus1Count, bus1Arrive, checkForTicket, leaveTerminal, inspectorToiletBreakCheck, inWaitingRoom);                                                                                                      //
        } else if (random == 1) {                                                                                                                                                                                                                                         //
            enterBus2.bus(this, room1Count, room2Count, room3Count, bus2Count, bus2Arrive, checkForTicket, leaveTerminal, inspectorToiletBreakCheck, inWaitingRoom);                                                                                                      //
        } else if (random == 2) {                                                                                                                                                                                                                                         //
            enterBus3.bus(this, room1Count, room2Count, room3Count, bus3Count, bus3Arrive, checkForTicket, leaveTerminal, inspectorToiletBreakCheck, inWaitingRoom);                                                                                                      //
        }                                                                                                                                                                                                                                                                 //
        enterBus2.bus_lock.unlock();                                                                                                                                                                                                                                      //
        enterBus3.bus_lock.unlock();                                                                                                                                                                                                                                      //
        enterBus1.bus_lock.unlock();                                                                                                                                                                                                                                      //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////        
    }
}
