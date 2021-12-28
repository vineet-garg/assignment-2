package crypto.webservice.services;

import crypto.webservice.api.PlainFloat;
import crypto.webservice.api.PlainStats;

public class StatsSvcImpl implements StatsSvc {
	
	private long count = 0;
	private float dSquared = 0;
	private float mean = 0;

	public synchronized PlainStats GetRunningStats(int num) {
		// Welford's online algorithm
		if (count == Long.MAX_VALUE) {
			throw new RuntimeException("Server limit exceeded");
		}
		
		count ++;
		float meanDiff = (num - mean)/count;
		float newMean = mean + meanDiff;
		float dSquaredIncrement = (num- newMean)*(num - mean);
		float newDSquared = dSquared + dSquaredIncrement;
		mean = newMean;
		dSquared = newDSquared;
		float dev = (float) Math.sqrt(dSquared/count);
				
		PlainFloat avg = new PlainFloat(mean);
		PlainFloat sd = new PlainFloat(dev);
		return new PlainStats(avg, sd);
	}
}
