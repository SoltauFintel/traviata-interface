package traviata.worker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreadLocalWorker extends ThreadLocal<Worker> {
	private final Class<? extends Worker> cls;
	private final List<Worker> all = Collections.synchronizedList(new ArrayList<Worker>());
	
	public ThreadLocalWorker(Class<? extends Worker> classOfWorker) {
		cls = classOfWorker;
	}
	
	protected Worker initialValue() {
		try {
			Worker w = cls.newInstance();
			all.add(w);
			return w;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Must be called after all tasks are completed to clean up the workers and to free memory.
	 */
	@Override
	public void remove() {
		for (Worker w : all) {
			w.afterAll();
		}
		all.clear();
		super.remove();
	}
}
