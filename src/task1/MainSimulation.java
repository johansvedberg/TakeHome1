
package task1;

import java.util.*;
import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
		Event actEvent;
		State actState = new State(); // The state that shoud be used
		// Some events must be put in the event list at the beginning
		insertEvent(ARRIVAL1, 0);
		//insertEvent(MEASURE1, 5);
		insertEvent(MEASURE2, 5);

		// The main simulation loop
		while (time < 1000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}

		// Printing the result of the simulation, in this case a mean value
		System.out.println("Mean value in Q2: " + 1.0 * actState.accumulated2 / actState.noMeasurements2);

		actState.W.close();
	}
}