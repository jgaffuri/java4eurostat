/**
 * 
 */
package eu.europa.ec.eurostat.java4eurostat.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.stat.StatUtils;

/**
 * Some additional utilities on statistical data
 * 
 * @author julien Gaffuri
 *
 */
public class StatsUtil {

	private static double[] getQuantiles(double[] vals, int nb) {
		double[] out = new double[nb];
		for(int quantile=0; quantile<nb; quantile++)
			out[quantile] = StatUtils.percentile(vals, 100*(quantile+1)/(nb+1));
		return out;
	}

	/**
	 * Compute the quantiles.
	 * 
	 * @param vals
	 * @param nb
	 * @return
	 */
	public static double[] getQuantiles(Double[] vals, int nb) { return getQuantiles(ArrayUtils.toPrimitive(vals), nb); }

	/**
	 * Compute the quantiles.
	 * 
	 * @param vals
	 * @param nb
	 * @return
	 */
	public static double[] getQuantiles(Collection<Double> vals, int nb) { return getQuantiles(vals.toArray(new Double[vals.size()]), nb); }


	/**
	 * Print some basic statistics of some values.
	 * @param vals
	 */
	public static void printStats(double[] vals){
		System.out.println("Max = " + StatUtils.max(vals));
		System.out.println("Min = " + StatUtils.min(vals));
		System.out.println("Mean = " + StatUtils.mean(vals));
		System.out.println("Median = " + StatUtils.percentile(vals, 50));
		System.out.println("Q1 = " + StatUtils.percentile(vals, 25));
		System.out.println("Q2 = " + StatUtils.percentile(vals, 75));
		System.out.println("Std = " + Math.sqrt(StatUtils.variance(vals)));
		System.out.println("RMS = " + Math.sqrt(StatUtils.sumSq(vals)/vals.length));
	}

	/**
	 * Print some basic statistics of some values.
	 * @param vals
	 */
	public static void printStats(Double[] vals) { printStats(ArrayUtils.toPrimitive(vals)); }

	/**
	 * Print some basic statistics of some values.
	 * @param vals
	 */
	public static void printStats(Collection<Double> vals) { printStats(vals.toArray(new Double[vals.size()])); }


	/**
	 * Write some basic statistics of some values.
	 * 
	 * @param bw
	 * @param vals
	 * @throws MathIllegalArgumentException
	 * @throws IOException
	 */
	public static void writeStats(BufferedWriter bw, double[] vals) throws MathIllegalArgumentException, IOException {
		bw.write("Max," + StatUtils.max(vals) + "\n");
		bw.write("Min," + StatUtils.min(vals) + "\n");
		bw.write("Mean," + StatUtils.mean(vals) + "\n");
		bw.write("Median," + StatUtils.percentile(vals, 50) + "\n");
		bw.write("Q1," + StatUtils.percentile(vals, 25) + "\n");
		bw.write("Q2," + StatUtils.percentile(vals, 75) + "\n");
		bw.write("Std," + Math.sqrt(StatUtils.variance(vals)) + "\n");
		bw.write("RMS," + Math.sqrt(StatUtils.sumSq(vals)/vals.length) + "\n");
	}


	/**
	 * Print quantile values.
	 * @param vals
	 * @param nb
	 */
	private static void printQuantiles(double[] vals, int nb) {
		for(int quantile=1; quantile<=nb; quantile++)
			System.out.println("Quantile " + quantile + "/" + nb + ": " + StatUtils.percentile(vals, quantile*100/nb));
	}

	/**
	 * Print quantile values.
	 * @param vals
	 * @param nb
	 */
	public static void printQuantiles(Collection<Double> vals, int nb) { printQuantiles(vals.toArray(new Double[vals.size()]), nb); }

	private static void printQuantiles(Double[] vals, int nb) { printQuantiles(ArrayUtils.toPrimitive(vals), nb); }

}
