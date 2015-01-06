package traviata.worker;

public interface Worker {

	void before();
	
	void after(boolean success);
	
	void afterAll();
}
