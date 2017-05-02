package task7;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;

class State extends GlobalSimulation {

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public double timeOfFailure;
	public int nbrOfSystemFailures, nbrOfFailedComponents;
	private boolean comp1broken, comp2broken, comp3broken, comp4broken, comp5broken;

	//Random slump = new Random(); // This is just a random number generator
	SimpleFileWriter W = new SimpleFileWriter("number.m", false);

	// The following method is called by the main program each time a new event
	// has been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {
		switch (x.eventType) {
		case START:
			start();
			break;
		case COMPONENT1BREAK:
			break1();
			break;
		case COMPONENT2BREAK:
			break2();
			break;
		case COMPONENT3BREAK:
			break3();
			break;
		case COMPONENT4BREAK:
			break4();
			break;
		case COMPONENT5BREAK:
			break5();
			break;
		case SYSTEMFAILURE:
			systemFailure();
			break;
		}
	}

	// The following methods defines what should be done when an event takes
	// place. This could
	// have been placed in the case in treatEvent, but often it is simpler to
	// write a method if
	// things are getting more complicated than this.

	private void start() {
		
		insertEvent(COMPONENT1BREAK, time + ThreadLocalRandom.current().nextDouble(1, 5));
		insertEvent(COMPONENT2BREAK, time + ThreadLocalRandom.current().nextDouble(1, 5));
		insertEvent(COMPONENT3BREAK, time + ThreadLocalRandom.current().nextDouble(1, 5));
		insertEvent(COMPONENT4BREAK, time + ThreadLocalRandom.current().nextDouble(1, 5));
		insertEvent(COMPONENT5BREAK, time + ThreadLocalRandom.current().nextDouble(1, 5));

	}

	private void break1() {

		if (!comp1broken) {
			//System.out.println("Component 1 broken down at: " + time);
			insertEvent(COMPONENT2BREAK, time);
			insertEvent(COMPONENT5BREAK, time);
			nbrOfFailedComponents++;
			comp1broken = true;

		}

		checkForFailure();

	}

	private void break2() {

		if (!comp2broken) {
			nbrOfFailedComponents++;
			comp2broken = true;

		}

		checkForFailure();

	}

	private void break3() {

		if (!comp3broken) {
			insertEvent(COMPONENT4BREAK, time);
			nbrOfFailedComponents++;
			comp3broken = true;

		}

		checkForFailure();

	}

	private void break4() {

		if (!comp4broken) {
			nbrOfFailedComponents++;
			comp4broken = true;

		}

		checkForFailure();

	}

	private void break5() {

		if (!comp5broken) {
			nbrOfFailedComponents++;
			comp5broken = true;

		}

		checkForFailure();

	}

	private void systemFailure() {

		nbrOfSystemFailures++;
		timeOfFailure = time + timeOfFailure;
		time = 0;
		nbrOfFailedComponents = 0;
		comp1broken = false;
		comp2broken = false;
		comp3broken = false;
		comp4broken = false;
		comp5broken = false;
		insertEvent(START, time);

	}

	private void checkForFailure() {
		if (nbrOfFailedComponents == 5) {
			insertEvent(SYSTEMFAILURE, time);
		}

	}
}