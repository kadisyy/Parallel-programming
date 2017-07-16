import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//http://blog.csdn.net/ghsau/article/details/7451464
public class ThreadExceptionTest {
	public static void main(String[] args) {
		testFutureExceptionTest(); // ���Է����쳣

		// testRunnableException();
	}

	private static void testRunnableException() {
		ExecutorService executor = Executors.newCachedThreadPool();

		executor.submit(new RunnableExceptionTask());

		executor.shutdown();// ���벻�����������һ�¿�������
	}

	private static void testFutureExceptionTest() {
		ExecutorService executor = Executors.newCachedThreadPool();

		Future<Integer> future = executor.submit(new CallableExceptionTask());
		try {
			System.out.println(future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		executor.shutdown();// ���벻�����������һ�¿�������
	}
}

class RunnableExceptionTask implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int i = 10 / 0;
		System.out.println(100);
	}

}

class CallableExceptionTask implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		int i = 10 / 0;

		return i;
	}

}