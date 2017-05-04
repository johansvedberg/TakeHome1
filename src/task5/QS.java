package task5;

import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc {
	public double numberInSystem = 0, accumulated = 0, noMeasurements = 0;
	Random slump = new Random();

	public void TreatSignal(Signal x) {
		switch (x.signalType) {

		case ARRIVAL: {
			numberInSystem++;
			if (numberInSystem == 1) {
				SignalList.SendSignal(READY, this, time + expDistribution(0.5));

			}
		}
			break;

		case READY: {
			numberInSystem--;
			if (numberInSystem > 0) {
				SignalList.SendSignal(READY, this, time + expDistribution(0.5));
			}
		}
			break;

		case MEASURE: {
			noMeasurements++;
			accumulated = accumulated + numberInSystem;
			SignalList.SendSignal(MEASURE, this, time + 2 * slump.nextDouble());
		}
			break;
		}
	}

	public double expDistribution(double mean) {
		return -(mean) * Math.log(slump.nextDouble());
	}
}