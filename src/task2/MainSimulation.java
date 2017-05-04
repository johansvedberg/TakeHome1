
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
		actState.bPrio = true;
		actState.delayExponetial = true;

		// The main simulation loop
		while (actState.nbrOfMeasurements < 1000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}

		System.out.println("Delay distribution is exponential: " + actState.delayExponetial);
		System.out.println("B has priority: " + actState.bPrio);
		System.out
				.println("Mean number of jobs in the buffer: " + 1.0 * actState.inBuffer / actState.nbrOfMeasurements);
		actState.W.close();
	}
}