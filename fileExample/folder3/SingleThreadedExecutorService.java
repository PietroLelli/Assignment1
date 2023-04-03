package sd.lab.concurrency.exercise;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class SingleThreadedExecutorService implements ExecutorService {

    private volatile boolean shutdown = false;
    private final CompletableFuture<?> termination = new CompletableFuture<>();
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingDeque<>();
    private final Thread backgroundThread = new Thread(this::backgroundThreadMain);

    public SingleThreadedExecutorService() {
        backgroundThread.start();
    }

    private void backgroundThreadMain() {
        while(true){
            try {
                Runnable toExec = tasks.poll();
                if (toExec == null && shutdown){
                    termination.complete(null);
                    break;
                }
                toExec.run();
            } catch (Exception ex) {
            // do nothing
            }  finally {

            }
        }
    }

    @Override
    public void shutdown() {
        shutdown = true;
    }

    @Override
    public List<Runnable> shutdownNow() {
        shutdown();
        backgroundThread.interrupt();
        var runnables = new ArrayList<Runnable>();
        tasks.drainTo(runnables);
        return runnables;
    }

    @Override
    public boolean isShutdown() {
        return shutdown;
    }

    @Override
    public boolean isTerminated() {
        return termination.isDone();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        try {
            termination.get(timeout,unit);
        } catch (ExecutionException e) {
            return false;
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        FutureTask<T> futureTask = new FutureTask<>(task);
        tasks.add(futureTask);
        return futureTask;
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        Callable<T> callable = new Callable<T>() {
            @Override
            public T call() throws Exception {
                task.run();
                return result;
            }
        };
        FutureTask<T> futureTask = new FutureTask<>(callable);
        tasks.add(futureTask);
        return futureTask;
    }

    @Override
    public Future<?> submit(Runnable task) {
        Callable<?> callable = new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                task.run();
                return null;
            }

        };
        FutureTask<?> futureTask = new FutureTask<>(callable);
        tasks.add(futureTask);
        return futureTask;
    }

    @Override
    public void execute(Runnable command) {
        if(shutdown){
            throw new RejectedExecutionException();
        }
        tasks.add(command);
    }

    // ignore the following methods: they are not tested

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        throw new Error("this must not be implemented");
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        throw new Error("this must not be implemented");
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        throw new Error("this must not be implemented");
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        throw new Error("this must not be implemented");
    }
}
