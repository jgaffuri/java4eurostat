package eu.europa.ec.eurostat.java4eurostat.io;

import eu.europa.ec.eurostat.java4eurostat.base.StatsHypercube;
import junit.framework.TestCase;

/**
 * @author julien Gaffuri
 *
 */
public class CSVTest extends TestCase {

	/*public static void main(String[] args) throws Exception {
		junit.textui.TestRunner.run(CSVTest.class);
	}*/

	public void test() throws Exception {
		String path = "./src/test/resources/";
		StatsHypercube hc = CSV.load(path+"ex.csv", "population");
		StatsHypercube hcNc = CSV.load(path+"ex_non_compact.csv", "population");
		StatsHypercube hcOv = CSV.load(path+"ex_overlap.csv", "population");
		StatsHypercube hcDirty = CSV.load(path+"ex_dirty.csv", "population");
	}

	public void testMulti() throws Exception {
		String path = "./src/test/resources/";
		StatsHypercube hc = CSV.loadMultiValues(path+"ex_multi.csv", "year", "2010", "2015", "2020");
		//hc.printInfo(true);
	}

}