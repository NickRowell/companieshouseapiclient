package util;

/**
 * Class used to help log progress of long running loops. It provides estimates of remaining time
 * to completion.
 */
public class Counter {

	/**
	 * Time that the {@link Counter} was started.
	 */
	long startTimeNs;
	
	/**
	 * Flag to record if the {@link Counter} has been started.
	 */
	boolean started;
	
	/**
	 * Total number of objects/iterations etc to process.
	 */
	long hundredPercent;

	/**
	 * Ten percent of the total, for convenience.
	 */
    private long tenPercent;

	/**
	 * One percent of the total, for convenience.
	 */
    private long onePercent;
    
	/**
	 * Main constructor.
	 * 
	 * @param startTimeNs
	 * 	Starting time.
	 * @param total
	 * 	Total number of objects to process, iterations or whatever is being counted.
	 */
	public Counter(long total) {
		hundredPercent = total;
		tenPercent = hundredPercent/10l;
		onePercent = hundredPercent/100l;
		started = false;
	}
	
	/**
	 * Start the {@link Counter}.
	 * 
	 * @param startTimeNs
	 * 	Starting time in nanoseconds.
	 */
	public void start(long startTimeNs) {
		this.startTimeNs = startTimeNs;
		started = true;
	}
	
	/**
	 * Check if the progress has reached a [0% 10% 20% ... 100%] checkpoint, and return
	 * a helpful log message if so.
	 * @param count
	 * 	The number of objects/iterations processed so far: must lie in range [0:total]
	 * @return
	 * 	If a checkpoint has been reached then the returned array contains two elements: the first
	 * is an ascii progress bar indicating the level of completeness at ten percent resolution, like
	 * [####.......], the second provides an estimate of the remaining time assuming equal time per
	 * object/iteration. If a checkpoint has not been reached then null is returned.
	 */
	public String[] getTenPercentMessage(long count) {
		
		if(!started) {
			throw new RuntimeException("Counter has not been started!");
		}
		
		// Log eleven progress messages on [0% 10% 20% ... 100%]
    	if(count==0 || count==(hundredPercent-1) || count%tenPercent==0) {
    		
    		int nHash = (int)(count/tenPercent);
    		if(count==(hundredPercent-1)) {
    			nHash = 11;
    		}
    		StringBuilder progress = new StringBuilder();
    		progress.append("[");
    		for(int i=0; i<nHash; i++) {
    			progress.append("#");
    		}
    		for(int i=nHash; i<11; i++) {
    			progress.append(".");
    		}
    		progress.append("]\t");
    		
    		String remaining = String.format("time remaining: %s", getTimeLeftMessage(count));
    		
    		return new String[]{progress.toString(), remaining};
    	}
    	else {
    		return null;
    	}
	}
	
	/**
	 * Check if the progress has reached a [0% 1% 2% ... 100%] checkpoint, and return
	 * a helpful log message if so.
	 * @param count
	 * 	The number of objects/iterations processed so far: must lie in range [0:total]
	 * @return
	 * 	If a checkpoint has been reached then the returned array contains two elements: the first
	 * is an ascii progress bar indicating the level of completeness at one percent resolution, like
	 * [####.......], the second provides an estimate of the remaining time assuming equal time per
	 * object/iteration. If a checkpoint has not been reached then null is returned.
	 */
	public String[] getOnePercentMessage(long count) {
		
		if(!started) {
			throw new RuntimeException("Counter has not been started!");
		}
		
		// Log 101 progress messages on [0% 10% 20% ... 100%]
    	if(count==0 || count==(hundredPercent-1) || count%onePercent==0) {
    		
    		int nHash = (int)(count/onePercent);
    		if(count==(hundredPercent-1)) {
    			nHash = 101;
    		}
    		StringBuilder progress = new StringBuilder();
    		progress.append("[");
    		for(int i=0; i<nHash; i++) {
    			progress.append("#");
    		}
    		for(int i=nHash; i<101; i++) {
    			progress.append(".");
    		}
    		progress.append("]\t");
    		
    		String remaining = String.format("time remaining: %s", getTimeLeftMessage(count));
    		
    		return new String[]{progress.toString(), remaining};
    	}
    	else {
    		return null;
    	}
	}
	
	/**
	 * Estimate the remaining time, given the fraction of objects processed and the elapsed time.
	 * 
	 * @param count
	 * 	The number of objects/iterations processed so far: must lie in range [0:total]
	 * @return
	 */
	public String getTimeLeftMessage(long count) {
		
		if(!started) {
			throw new RuntimeException("Counter has not been started!");
		}
		
		// Time-per-calibrator so far computed
		String[] t = {"?","?","?","?"};
		if(count > 0) {
			long timePerCalNs = (System.nanoTime() - startTimeNs) / count;
			// Time remaining
			long timeRemainingNs = timePerCalNs * (hundredPercent - 1 - count);
			long timeRemainingMs = timeRemainingNs/1000000;
	        t = getDurationDHMS(timeRemainingMs);
		}
		
		return String.format("%sd %sh %sm %ss", t[0], t[1], t[2], t[3]);
	}
	
	/**
	 * Convert a duration in milliseconds to the equivalent duration in days, hours,
	 * minutes and seconds.
	 * @param ms
	 * 	Duration in milliseconds.
	 * @return
	 * 	Array of Strings containing the days, hours-past-the-day, mins-past-the-hour and
	 * secs-past-the-minute.
	 */
	public static String[] getDurationDHMS(long ms) {
		
		// Conversion factors
        long MS_PER_SEC = 1000;
        long MS_PER_MIN = MS_PER_SEC*60;
        long MS_PER_HOUR = MS_PER_MIN*60;
        long MS_PER_DAY = MS_PER_HOUR*24;
        
        // Time quantisation
        long residualMs = ms%MS_PER_SEC;
        long secs_past_the_minute = (ms/MS_PER_SEC)%60;
        long mins_past_the_hour   = (ms/MS_PER_MIN)%60;
        long hours_past_the_day   = (ms/MS_PER_HOUR)%24;
        long days  				  = (ms/MS_PER_DAY);
        
        return new String[]{Long.toString(days), Long.toString(hours_past_the_day),
        		Long.toString(mins_past_the_hour), String.format("%02d.%03d", secs_past_the_minute, residualMs)};
	}
}
