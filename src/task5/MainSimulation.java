package task5;

import java.util.*;
import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation

public class MainSimulation extends Global {

	public static void main(String[] args) throws IOException {

		// The signal list is started and actSignal is declared. actSignal is
		// the latest signal that has been fetched from the
		// signal list in the main loop below.

		Signal actSignal;
		new SignalList();

		// Here process instances are created (two queues and one generator) and
		// their parameters are given values.

		QS Q1 = new QS();
		QS Q2 = new QS();
		QS Q3 = new QS();
		QS Q4 = new QS();
		QS Q5 = new QS();

		ArrayList<QS> queues = new ArrayList<QS>();

		queues.add(Q1);
		queues.add(Q2);
		queues.add(Q3);
		queues.add(Q4); 
		queues.add(Q5);

		Dispatcher dispatcher = new Dispatcher(queues);
		Gen Generator = new Gen(dispatcher);
		double generalLambda = (1.0 / 0.12);
		
		Generator.lambda = generalLambda;
		

		// To start the simulation the first signals are put in the signal list

		SignalList.SendSignal(READY, Generator, 0);
		SignalList.SendSignal(MEASURE, Q1, 0);
		SignalList.SendSignal(MEASURE, Q2, 0);
		SignalList.SendSignal(MEASURE, Q3, 0);
		SignalList.SendSignal(MEASURE, Q4, 0);
		SignalList.SendSignal(MEASURE, Q5, 0);

		while (time < 100000) {
			actSignal = SignalList.FetchSignal();
			time = actSignal.arrivalTime;
			actSignal.destination.TreatSignal(actSignal);
		}

		double meanInSystem = ((1.0 * Q1.accumulated / Q1.noMeasurements) + (1.0 * Q2.accumulated / Q2.noMeasurements)
				+ (1.0 * Q3.accumulated / Q3.noMeasurements) + (1.0 * Q4.accumulated / Q4.noMeasurements)
				+ (1.0 * Q5.accumulated / Q5.noMeasurements));

		System.out.println("Mean number of customers in Q1: " + 1.0 * Q1.accumulated / Q1.noMeasurements);
		// System.out.println("Mean time in Q1: " + (generalLambda / 5.0) / (1.0
		// * Q1.accumulated / Q1.noMeasurements));
		System.out.println("Mean number of customers in Q2: " + 1.0 * Q2.accumulated / Q2.noMeasurements);
		System.out.println("Mean number of customers in Q3: " + 1.0 * Q3.accumulated / Q3.noMeasurements);
		System.out.println("Mean number of customers in Q4: " + 1.0 * Q4.accumulated / Q4.noMeasurements);
		System.out.println("Mean number of customers in Q5: " + 1.0 * Q5.accumulated / Q5.noMeasurements);
		System.out.println("Mean number of customers in the system: " + meanInSystem);
		System.out.println("Average time in system: " + meanInSystem / generalLambda + " (Littles Law)");
		System.out.println("Average time in system: " + (meanInSystem / 5.0) * 0.5);

	}
}