package task2;

import java.util.*;
import java.io.*;

class State extends GlobalSimulation {

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int inBuffer = 0, nbrOfMeasurements = 0, nbrOfArrivals = 0;
	public boolean bPrio, delayExponetial;

	public LinkedList<Integer> buffer = new LinkedList<Integer>();

	Random slump = new Random(); // This is just a random number generator
	SimpleFileWriter W = new SimpleFileWriter("number.m", false);
	final double arrivalTime = (1.0 / 150.0);
	final double delay = 1.0;
	final double serviceA = 0.002;
	final double serviceB = 0.004;

	// The following method is called by the main program each time a new event
	// has been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {
		switch (x.eventType) {
		case ARRIVALA:
			arrivalA();
			break;
		case ARRIVALB:
			arrivalB();
			break;
		case MEASURE:
			measure();
			break;
		case READY:
			ready();
			break;
		}
	}

	// The following methods defines what should be done when an event takes
	// place. This could
	// have been placed in the case in treatEvent, but often it is simpler to
	// write a method if
	// things are getting more complicated than this.

	private void arrivalA() {
		nbrOfArrivals++;
		if (buffer.size() == 0)
			insertEvent(READY, time + serviceA);
		if (bPrio) {
			buffer.addLast(ARRIVALA);
		} else {
			buffer.addFirst(ARRIVALA);
		}
		insertEvent(ARRIVALA, time + expDistribution(arrivalTime));
	}

	private void arrivalB() {
		if (buffer.size() == 0)
			insertEvent(READY, time + serviceB);
		if (bPrio) {
			buffer.addFirst(ARRIVALB);
		} else {
			buffer.addLast(ARRIVALB);
		}

	}

	private void ready() {
		if (buffer.size() > 0) {
			int inQueue = buffer.poll();
			if (inQueue == ARRIVALA) {
				insertEvent(READY, time + serviceA);
				if (delayExponetial) {
					insertEvent(ARRIVALB, time + serviceA + expDistribution(delay));

				} else {
					insertEvent(ARRIVALB, time + serviceA + delay);
				}

			} else if (inQueue == ARRIVALB) {
				insertEvent(READY, time + serviceB);
			}
		}
	}

	private void measure() {
		inBuffer = inBuffer + buffer.size();
		nbrOfMeasurements++;
		insertEvent(MEASURE, time + 0.1);

	}

	public double expDistribution(double mean) {
		return -(mean) * Math.log(slump.nextDouble());
	}
}