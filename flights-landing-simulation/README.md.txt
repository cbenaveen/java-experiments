Design a flight simulator often is an interview question to test a candidate design skills. 
To elaborate, there is N no of flights and single runway. How will you design the system to ensure that only one flight using the runway at any given point of time?
Let us make it very simple.
The runway is a resource which will be shared by different user, the flights in a concurrent fashion.
Hence while only one flight should be allowed to use the runway, the other should be waiting. Once the Flight which is owning runway completes its usage, shall release the resource so that other waiting user can start using it.
