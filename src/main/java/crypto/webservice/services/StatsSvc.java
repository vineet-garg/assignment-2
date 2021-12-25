package crypto.webservice.services;

import crypto.webservice.api.PlainStats;

public interface StatsSvc {
	public PlainStats GetRunningStats(int num);

}
