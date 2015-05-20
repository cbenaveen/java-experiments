package com.cbenaveen.flight.simulation;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import com.cbenaveen.flight.simulation.runway.RunwayResource;

/**
 * Models a flight object a runnable unit
 * 
 * @author Naveen Kumar K
 * @email cbenaveen@gmail.com 
 */
public class Flight implements Runnable {
    
    /**
     * Random generator to be used to generate random delay
     */
    private static final Random RANDOM = new Random();
    
    /**
     * Runway resource
     */
    private RunwayResource runway;
    
    /**
     * Name of the flight
     */
    private String name;
    
    /**
     * Latch object to notify the landing completion
     */
    private CountDownLatch latch;
    
    /**
     * Creates a flight instance 
     * 
     * @param name Flight name
     * @param runway Runway resource to be used
     * @param latch Latch to inform the completion
     */
    public Flight(final String name, final RunwayResource runway, final CountDownLatch latch) {
        this.name = name;
        this.runway = runway;
        this.latch = latch;
    }
    
    @Override
    public void run() {
        //Ask fo the runway
        //waits if the runway not available
        if(runway.canIUseRunway(name)){
            System.out.println("Flight " + name + " got the permission to use the runway....");
            try {
                //just to simulate landing operation
                long sleepTime = RANDOM.nextInt(5000);
                System.out.println("Flight " + name + " will be using the runway for next " + sleepTime + " milliseconds");
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Flight " + name + " landed successfully....");
                runway.iAmDoneWithRunway(name);
                latch.countDown();
            }
        }
    }
}
