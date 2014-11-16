package traviata.worker;

public interface I_Worker {

	void before();
	
	void after(boolean success);
	
	void afterAll();
}
