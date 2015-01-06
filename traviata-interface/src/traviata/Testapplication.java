/*
 * (C) by Marcus Warm
 */
package traviata;

import java.util.List;

/**
 * Traviata test application
 */
public interface Testapplication {

	/**
	 * Call this method before you start Testcases. 
	 * @param path test application execution folder
	 * @param url URL of the to be tested test application
	 * @param parameters more parameters for the test application: lines with key + "=" + value
	 */
	void init(String path, String url, String parameters);
	
	/**
	 * @return test case compilation names
	 */
	List<String> getTestcaseCompilationNames();

	/**
	 * @param tcc test case compilation name
	 * @return all test cases of test case compilation
	 */
	List<Testcase> getTestcases(String tcc);
	
	/**
	 * Cleans up resources after test run.
	 */
	void cleanup();
}
