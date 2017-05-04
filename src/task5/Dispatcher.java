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

	public Dispatcher(ArrayList<QS> queues) {
		this.queues = queues;
		randomGenerator = new Random();

	}

	@Override
	public void TreatSignal(Signal x) {
		switch (x.signalType) {
		case ARRIVAL: {

			SignalList.SendSignal(READY, this, time);

		}
			break;

		case READY: {
			//sendToRandom();
			//sendToRoundRobin();
			 sendToSmallest();

			SignalList.SendSignal(ARRIVAL, sendTo, time);

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

		QS queue = queues.get(currentIndex);
		sendTo = queue;
		currentIndex++;
		currentIndex %= 5;

	}

	private void sendToSmallest() {

		ArrayList<QS> smallest = new ArrayList<QS>();
		double small = queues.get(0).numberInSystem;

		for (QS qs : queues) {
			if (qs.numberInSystem <= small) {
				small = qs.numberInSystem;
			}
		}

		for (QS qs2 : queues) {
			if (qs2.numberInSystem == small) {
				smallest.add(qs2);

			}
		}

		int index = randomGenerator.nextInt(smallest.size());

		QS queue = smallest.get(index);
		sendTo = queue;
		smallest.clear();

	}
}
