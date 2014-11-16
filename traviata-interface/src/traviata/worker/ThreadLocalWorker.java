package traviata.worker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreadLocalWorker extends ThreadLocal<I_Worker> {
	private final Class<? extends I_Worker> cls;
	private final List<I_Worker> all = Collections.synchronizedList(new ArrayList<I_Worker>());
	
	public ThreadLocalWorker(Class<? extends I_Worker> classOfWorker) {
		cls = classOfWorker;
	}
	
	protected I_Worker initialValue() {
		try {
			I_Worker w = cls.newInstance();
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
		for (I_Worker w : all) {
			w.afterAll();
		}
		all.clear();
		super.remove();
	}
}
