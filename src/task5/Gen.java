package task5;

import java.util.*;
import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation 

class Gen extends Proc {
	Dispatcher dis;

	public Gen(Dispatcher dis) {
		this.dis = dis;

	}

	// The random number generator is started:
	Random slump = new Random();

	// There are two parameters:

	public double lambda; // How many to generate per second

	// What to do when a signal arrives
	public void TreatSignal(Signal x) {
		switch (x.signalType) {
		case READY: {
			SignalList.SendSignal(ARRIVAL, dis, time);
			double time2 = (2 / lambda) * slump.nextDouble();
			SignalList.SendSignal(READY, this, time + time2);
		}
			break;
		}
	}
}