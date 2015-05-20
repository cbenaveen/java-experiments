package com.cbenaveen.flight.simulation;

import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;

import com.cbenaveen.flight.simulation.runway.RunwayResource;

/**
 * JUnit test creates instance of:
 *  
 *      1) Flights
 *      2) Runway Resource 
 *      3) Share the runway resource with each flight
 *      4) Launch/Starts each flight
 *      
 * @author Naveen Kumar K
 * @email cbenaveen@gmail.com 
 */
public class FlightSimulatorJUnitTest {
    
    /**
     * No of flight instances.
     * 
     * Feel free to change as much as you want.
     */
    private static final int NO_OF_FLIGHTS = 10;
    
    /**
     * Runway resource abstracts the runway 
     */
    private RunwayResource runway;
    
    /**
     * A latch just to keep the main thread waiting till
     * all the flights lands successfully.
     */
    private CountDownLatch latch;
    
    /**
     * Create instance of Runway and latch
     * which are prerequsite for this test.
     */
    @Before
    public void createRunway(){
        runway = new RunwayResource();
        latch = new CountDownLatch(NO_OF_FLIGHTS);
    }

    /**
     * Starts the simulation:
     *      1) Create instance of Flight
     *      2) Start each flight as a thread
     *      3) Share the runway resource and latch with each flight
     *      4) Waits till all the flights lands 
     */
    @Test
    public void createFlights(){
        for(int i = 0; i < NO_OF_FLIGHTS; i++){
            //create a unique name
            final String name = "Flight-"+i;
            
            //create instance of flight with al the dependencies set 
            //and start a new thread
            new Thread(new Flight(name, runway, latch)).start();
        }
        
        try {
            //wait for all the flights to land safely
            latch.await();
            System.out.println("Mission completed....All flights landed successfully... (:)");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
