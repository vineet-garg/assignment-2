package crypto.webservice.services;

import crypto.webservice.api.PlainStats;

/**
 * Service that calculates statistics.
 *
 */
public interface StatsSvc {
	/**
	 * Calulates new average and standard deviation incorporating the new number given.
	 * @param num int value of new number. Support -ve numbers as well.
	 * @return PlainStats value composing of PlainFloat values of new average and standard deviation.
	 */
	public PlainStats getRunningStats(int num);
}
