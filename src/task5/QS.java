package task5;

import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc {
	public double numberInSystem = 0, accumulated = 0, nbrMeasurements = 0, meanTime = 0, nbrReady = 0;
	Random slump = new Random();
	private String name;

	public QS(String name) {
		this.name = name;
	}

	public void TreatSignal(Signal x) {

		double serviceTime = expDistribution(0.5);

		switch (x.signalType) {

		case ARRIVAL: {
			meanTime += numberInSystem * serviceTime;
			numberInSystem++;
			if (numberInSystem == 1) {
				SignalList.SendSignal(READY, this, time + serviceTime);

			}
		}
			break;

		case READY: {
			meanTime += serviceTime;
			nbrReady++;
			numberInSystem--;
			if (numberInSystem > 0) {
				SignalList.SendSignal(READY, this, time + serviceTime);
			}
		}
			break;

		case MEASURE: {
			nbrMeasurements++;
			//meanTime += numberInSystem * serviceTime + serviceTime;
			accumulated += numberInSystem;
			SignalList.SendSignal(MEASURE, this, time + 2 * slump.nextDouble());
		}
			break;
		}
	}

	public double expDistribution(double mean) {
		return -(mean) * Math.log(slump.nextDouble());
	}

	public String getName() {
		return name;
	}
}