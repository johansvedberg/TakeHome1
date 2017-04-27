
package task2;

import java.util.*;
import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
		Event actEvent;
		State actState = new State(); // The state that should be used
		// Some events must be put in the event list at the beginning
		insertEvent(ARRIVALA, 0);
		insertEvent(MEASURE, 0);

		// The main simulation loop
		while (actState.nbrOfMeasurements < 1000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
		
			actState.treatEvent(actEvent);
		}

		// Printing the result of the simulation, in this case a mean value
		// System.out.println("Mean value in Q2: " + 1.0 * actState.accumulated2
		// / actState.noMeasurements2);
		// System.out.println("Chance of rejection: " + 1.0 *
		// actState.noRejected / actState.accumulated1);
		System.out
				.println("Mean number of jobs in the buffer: " + 1.0 * actState.inBuffer / actState.nbrOfMeasurements);
		System.out.println(actState.nbrOfArrivals);
		actState.W.close();
	}
}