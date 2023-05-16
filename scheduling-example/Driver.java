public class Driver {
    
    public static void main(String[] args) throws InterruptedException {
        Cache<Integer, String> cache = new TimerCache<>(2000);

        cache.set(1, "hello");
        Thread.sleep(1000);
        cache.set(1, "hello2");
        cache.set(1, "hello3");
        cache.set(1, "hello4");
    }
}
