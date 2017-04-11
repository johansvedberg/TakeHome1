package task1;

import java.util.*;
import java.io.*;

class State extends GlobalSimulation {

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue1 = 0, numberInQueue2 = 0, accumulated1 = 0, accumulated2 = 0, noMeasurements1 = 0,
			noMeasurements2 = 0, noRejected = 0;

	Random slump = new Random(); // This is just a random number generator
	SimpleFileWriter W = new SimpleFileWriter("number.m", false);
	double arrivalTime = 5;

	// The following method is called by the main program each time a new event
	// has been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {
		switch (x.eventType) {
		case ARRIVAL1:
			arrival1();
			break;
		case READY1:
			ready1();
			break;
		case MEASURE1:
			measure1();
			break;
		case ARRIVAL2:
			arrival2();
			break;
		case READY2:
			ready2();
			break;
		case MEASURE2:
			measure2();
			break;
		}
	}

	// The following methods defines what should be done when an event takes
	// place. This could
	// have been placed in the case in treatEvent, but often it is simpler to
	// write a method if
	// things are getting more complicated than this.

	private void arrival1() {
		if (numberInQueue1 == 0)
			insertEvent(READY1, time + expDistribution(2.1));
		insertEvent(ARRIVAL1, time + arrivalTime);
		if (numberInQueue1 < 10) {
			numberInQueue1++;
		} else {
			noRejected++;
		}
	}

	private void ready1() {
		numberInQueue1--;
		if (numberInQueue1 > 0)
			insertEvent(READY1, time + expDistribution(2.1));
		insertEvent(ARRIVAL2, time);
	}

	private void measure1() {
		accumulated1 = accumulated1 + numberInQueue1;
		noMeasurements1++;
		insertEvent(MEASURE1, time + slump.nextDouble() * 10);
	}

	private void arrival2() {
		if (numberInQueue2 == 0)
			insertEvent(READY2, time + 2.0);
		numberInQueue2++;

	}

	private void ready2() {
		numberInQueue2--;
		if (numberInQueue2 > 0)
			insertEvent(READY2, time + 2.0);
	}

	private void measure2() {
		accumulated2 = accumulated2 + numberInQueue2;
		noMeasurements2++;
		insertEvent(MEASURE2, time + expDistribution(5));
	}

	public double expDistribution(double mean) {
		return -(mean) * Math.log(slump.nextDouble());
	}
}