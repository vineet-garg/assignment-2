package crypto.webservice.services;

import static org.junit.Assert.*;

import org.junit.Test;

public class StatsSvcImplTest {

	@Test
	public void testGetRunningStats_avg() {
		StatsSvc statsSvc = new StatsSvcImpl();
		int[] input = new int[]{1, 10, 100, 3, 90, 0, 2000, 5000000};
		float[] averages = new float[]{1, 5.5f, 37f, 28.5f, 40.8f, 34f, 314.85f, 625275.5f};
		for (int i = 0; i < input.length; i++){
			assertEquals(averages[i], statsSvc.GetRunningStats(input[i]).getAvg().getNum(), 0.009f);
		}
	}
	
	@Test
	public void testGetRunningStats_sd() {
		StatsSvc statsSvc = new StatsSvcImpl();
		int[] input = new int[]{1, 10, 100, 3, 90, 0, 2000, 5000000};
		float[] sds = new float[]{0, 4.5f, 44.69f, 41.41f, 44.46f, 43.34f, 689.12f, 1653490.56f};
		for (int i = 0; i < input.length; i++){
			assertEquals(sds[i], statsSvc.GetRunningStats(input[i]).getSd().getNum(), 0.9f);
		}
	}
	
}
