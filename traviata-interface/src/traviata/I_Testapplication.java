/*
 * (C) by Marcus Warm
 */
package traviata;

import java.util.List;

/**
 * Traviata test application
 */
public interface I_Testapplication {

	/**
	 * @return test case compilation names
	 */
	List<String> getTestcaseCompilationNames();

	/**
	 * @param tcc test case compilation name
	 * @return all test cases of test case compilation
	 */
	List<I_Testcase> getTestcases(String tcc);
	
	/**
	 * Cleans up resources after test run.
	 */
	void cleanup();
}
