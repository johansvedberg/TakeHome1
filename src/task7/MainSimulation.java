
package task7;

import java.util.*;
import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
		Event actEvent;
		State actState = new State(); // The state that shoud be used
		// Some events must be put in the event list at the beginning
		insertEvent(START, 0);

		// The main simulation loop
		while (actState.nbrOfSystemFailures < 1000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}

		actState.W.close();
		System.out.println("The system broke down after: " + actState.timeOfFailure / actState.nbrOfSystemFailures);
	}
}