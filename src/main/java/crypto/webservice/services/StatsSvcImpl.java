package crypto.webservice.services;

import crypto.webservice.api.PlainFloat;
import crypto.webservice.api.PlainStats;

public class StatsSvcImpl implements StatsSvc {
	
	private int count = 0;
	private int sum = 0;
	private float dSquared = 0;
	private float mean = 0;

	public synchronized PlainStats GetRunningStats(int num) {
		count ++;
		sum +=num;
		float meanDiff = (num - mean)/count;
		float newMean = mean + meanDiff;
		float dSquaredIncrement = (num- newMean)*(num - mean);
		float newDSquared = dSquared + dSquaredIncrement;
		mean = newMean;
		dSquared = newDSquared;
		float dev = (float) Math.sqrt(dSquared/count);
				
		PlainFloat avg = new PlainFloat(((float)sum)/count);
		PlainFloat sd = new PlainFloat(dev);
		return new PlainStats(avg, sd);
	}

}
