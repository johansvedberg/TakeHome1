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

		QS Q1 = new QS("Q1");
		QS Q2 = new QS("Q2");
		QS Q3 = new QS("Q3");
		QS Q4 = new QS("Q4");
		QS Q5 = new QS("Q5");

		ArrayList<QS> queues = new ArrayList<QS>();

		queues.add(Q1);
		queues.add(Q2);
		queues.add(Q3);
		queues.add(Q4);
		queues.add(Q5);

		Dispatcher dispatcher = new Dispatcher(queues);
		Gen Generator = new Gen(dispatcher);
		double generalLambda = (1.0 / 2.00);

		Generator.lambda = generalLambda;

		// To start the simulation the first signals are put in the signal list

		SignalList.SendSignal(MEASURE, Q1, 0);
		SignalList.SendSignal(MEASURE, Q2, 0);
		SignalList.SendSignal(MEASURE, Q3, 0);
		SignalList.SendSignal(MEASURE, Q4, 0);
		SignalList.SendSignal(MEASURE, Q5, 0);
		SignalList.SendSignal(READY, Generator, 0);

		while (time < 100000) {
			actSignal = SignalList.FetchSignal();
			time = actSignal.arrivalTime;
			actSignal.destination.TreatSignal(actSignal);
		}

		double nbrInSystem = 0;
		double timeInSystem = 0;

		for (QS queue : queues) {
			nbrInSystem += queue.accumulated / queue.nbrMeasurements;
			timeInSystem += (queue.meanTime / queue.nbrReady) / 5;
			System.out.println("Mean number of customers in " + queue.getName() + ": "
					+ queue.accumulated / queue.nbrMeasurements);
		}

		System.out.println("Mean number of customers in the system: " + nbrInSystem);

		System.out.println("Lambda: " + nbrInSystem / timeInSystem);

	}
}