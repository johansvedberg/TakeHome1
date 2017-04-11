
package event_scheduling;

import java.util.*;
import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
		Event actEvent;
		State actState = new State(); // The state that shoud be used
		// Some events must be put in the event list at the beginning
		insertEvent(ARRIVAL, 0);
		insertEvent(MEASURE, 5);

		// The main simulation loop
		while (time < 10000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}

		// Printing the result of the simulation, in this case a mean value
		System.out.println("Mean value: " +1.0 * actState.accumulated / actState.noMeasurements);
		System.out.println(actState.accumulated);
    	System.out.println(actState.noMeasurements);
		actState.W.close();
	}
}