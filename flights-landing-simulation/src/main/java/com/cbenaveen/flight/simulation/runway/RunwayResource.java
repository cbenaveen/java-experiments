package com.cbenaveen.flight.simulation.runway;

/**
 * Resource implementation abstracts the Runway resource.
 * 
 * @author Naveen Kumar K
 * @email cbenaveen@gmail.com 
 */
public class RunwayResource {

    /**
     * Create single instance of runway object to be shared with all the flights
     */
    private static final Runway RUNWAY = new Runway();

    /**
     * Implementation takes care of allowing only one flight to use the runway
     * at any given point of time.
     * 
     * Makes the others to be on wait till the flight who ownes the runway
     * completes its operation.
     * 
     * @param flightName
     *            Flight name which wants to use the runway
     * @return True, when the flight got the runway to use
     */
    public boolean canIUseRunway(final String flightName) {
        // synchronize on the runway object
        // so that only one request can get the runway
        synchronized (RUNWAY) {

            // if runway already in use, this flag would be set to false
            // and make the requester to wait till further notice
            while (!RUNWAY.isFreeNow()) {
                try {
                    // put the requester on wait
                    RUNWAY.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // if one flight get runway update flag and owner information
            RUNWAY.setFreeNow(false);
            RUNWAY.setOwner(flightName);
        }

        return true;
    }

    /**
     * When flight completes its landing, flight will call this api to release
     * the runway for the others to use.
     * 
     * @param flightName
     */
    public void iAmDoneWithRunway(final String flightName) {

        // synchronize on the runway object
        // so that only one request can get the runway
        synchronized (RUNWAY) {
            //is the runway being used?
            if (!RUNWAY.isFreeNow()) {
                //if so set the flags appopriatly
                RUNWAY.setFreeNow(true);
                RUNWAY.setOwner(null);
                
                //inform the waiting flights
                RUNWAY.notifyAll();
            }
        }
    }

    /**
     * Actual runway object
     * 
     * @author Naveen Kumar K
     */
    private static class Runway {
        
        /**
         * Information about the current flight who
         * is owning the runway. Null if non owns it.
         */
        private String owner;
        
        /**
         * Flag to indicate that the runway is available or not
         */
        private boolean isFreeNow = true;

        public boolean isFreeNow() {
            return isFreeNow;
        }

        public void setFreeNow(boolean isFreeNow) {
            this.isFreeNow = isFreeNow;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }
    }
}
