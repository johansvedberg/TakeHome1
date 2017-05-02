package task5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Dispatcher extends Proc {
	public int numberInQueue = 0, accumulated, noMeasurements;;
	public Proc sendTo;
	private ArrayList<QS> queues;
	private Random randomGenerator;
	private int currentIndex = 0;
	Random slump = new Random();

	public Dispatcher(ArrayList<QS> queues) {
		this.queues = queues;

		randomGenerator = new Random();

	}

	@Override
	public void TreatSignal(Signal x) {
		switch (x.signalType) {

		case ARRIVAL: {
			numberInQueue++;
			if (numberInQueue == 1) {
				SignalList.SendSignal(READY, this, time);
			}
		}
			break;

		case READY: {
			// sendToRandom();
			// sendToRoundRobin();
			sendToSmallest();
			numberInQueue--;
			if (sendTo != null) {
				SignalList.SendSignal(ARRIVAL, sendTo, time);
			}
			if (numberInQueue > 0) {
				SignalList.SendSignal(READY, sendTo, time);
			}
		}
			break;

		}
	}

	private void sendToRandom() {

		int index = randomGenerator.nextInt(queues.size());
		QS queue = queues.get(index);
		sendTo = queue;

	}

	private void sendToRoundRobin() {
		if (currentIndex > 4) {
			currentIndex = 0;
		}

		QS queue = queues.get(currentIndex);
		sendTo = queue;
		currentIndex++;

	}

	private void sendToSmallest() {

		ArrayList<QS> smallest = new ArrayList<QS>();
		int small = queues.get(0).numberInQueue;

		for (QS qs : queues) {
			if (qs.numberInQueue <= small) {
				small = qs.numberInQueue;
			}
		}

		for (QS qs2 : queues) {
			if (qs2.numberInQueue == small) {
				smallest.add(qs2);

			}
		}

		int index = randomGenerator.nextInt(smallest.size());

		QS queue = smallest.get(index);
		sendTo = queue;
		smallest.clear();

	}
}
