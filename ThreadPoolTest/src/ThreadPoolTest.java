import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//execute和submit的区别 
//http://blog.csdn.net/hayre/article/details/53314599
//newSingleThreadExecutor等是ThreadPoolExecutor具体实现  特殊实现
public class ThreadPoolTest {
	public static void main(String[] args) {

		// testFixThreadPool();
		// testNewSinglePool();
		// testCacheThreadPool();
		// testScheduledAtFixedRate();
		// testThreadPool();

	}

	/*
	 * 当线程数小于核心线程数时，创建线程。 当线程数大于等于核心线程数，且任务队列未满时，将任务放入任务队列。
	 * 当线程数大于等于核心线程数，且任务队列已满 若线程数小于最大线程数，创建线程 若线程数等于最大线程数，抛出异常，拒绝任务
	 * 
	 */
	private static void testThreadPool() {
		ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 8, 200, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(5));
		for (int i = 0; i < 15; i++) {
			MyTask myTask = new MyTask(i);
			pool.execute(myTask);
			System.out.println("now threadcorepoolsize is " + pool.getPoolSize() + ", cachepoolsize is"
					+ pool.getQueue().size() + ",is completedsize is" + pool.getCompletedTaskCount());
		}
		pool.shutdown();
	}

	private static void testScheduledAtFixedRate() {
		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

		exec.scheduleAtFixedRate(new Runnable() {// 每隔一段时间就触发异常

			@Override

			public void run() {

				// throw new RuntimeException();

				System.out.println("================");

			}

		}, 1000, 5000, TimeUnit.MILLISECONDS);

		exec.scheduleAtFixedRate(new Runnable() {// 每隔一段时间打印系统时间，证明两者是互不影响的

			@Override

			public void run() {

				System.out.println(System.nanoTime());

			}

		}, 1000, 2000, TimeUnit.MILLISECONDS);
	}

	private static void testCacheThreadPool() {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			final int index = i;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			cachedThreadPool.execute(new Runnable() {

				@Override
				public void run() {
					System.out.println(index);
				}
			});
		}
	}

	private static void testNewSinglePool() {
		ExecutorService service = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 10; i++) {
			service.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("start.....");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println("end....");
				}
			});

		}

		service.shutdown();
	}

	private static void testFixThreadPool() {
		ExecutorService service = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 10; i++) {
			service.execute(new Runnable() {

				@Override
				public void run() {
					System.out.println("start.....");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println("end....");

				}
			});
		}
		service.shutdown();
	}
}

class MyTask implements Runnable {
	int num;

	public MyTask(int num) {
		this.num = num;
	}

	@Override
	public void run() {
		System.out.println("Is doing..task" + num);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("task" + num + " is done.");
	}
}