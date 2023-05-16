import java.time.LocalDateTime;
import java.util.*;

/**
 * 데이터를 캐싱한 후 일정 시간이 지나면 삭제하는 캐시. {@code expirationTime}을 지정하지 않고 생성하면 기본값으로 1000ms로 지정된다.
 */
public class TimerCache<K, V> implements Cache<K, V> {
    private final Map<K, V> data;
    private final Timer cleanupTimer;
    private long expirationTime;

    /**
     * {@code expirationTime}이 1000ms로 지정된다.
     */
    public TimerCache() {
        this.data = new HashMap<>();
        this.setExpirationTime(1000);
        this.cleanupTimer = new Timer();
    }

    /**
     * 
     * @param expirationTime 캐시 데이터 유지 시간
     * @throws IllegalArgumentException {@code expirationTime}이 음수일 경우 예외를 반환한다.
     */
    public TimerCache(final long expirationTime) throws IllegalArgumentException {
        this.data = new HashMap<>();
        this.setExpirationTime(expirationTime);
        this.cleanupTimer = new Timer();
    }

    public long getExpirationTime() {
        return this.expirationTime;
    }

    /**
     * 캐시 데이터 유지 시간을 설정하는 메소드.
     * @param expirationTime 캐시 데이터 유지 시간
     * @throws IllegalArgumentException {@code expirationTime}이 음수일 경우 예외를 반환한다.
     */
    public void setExpirationTime(final long expirationTime) throws IllegalArgumentException {
        if (expirationTime < 0) {
            throw new IllegalArgumentException("캐시 데이터 유지 시간은 0 이상이어야 합니다. (단위 : ms)");
        }

        this.expirationTime = expirationTime;
    }

    /**
     * 시스템 시간이 {@code scheduledTime}가 되었을 때 {@code key}에 해당하는 데이터를 삭제하는 메소드. {@code scheduledTime}은 데이터 추가 후 경과 시간이 아닌 삭제할 시각임을 주의해야 한다.
     * @param key 삭제하고자 하는 키
     * @param scheduledTime 삭제 예정 시간
     */
    private void scheduleCleanupTask(final K key, final long scheduledTime) {
        cleanupTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                V d = data.get(key);

                if (d != null) {
                    delete(key);
                }
            }
        }, expirationTime);
    }

    @Override
    public int size() {
        return this.data.size();
    }

    @Override
    public void set(final K key, final V value) {
        this.data.put(key, value);
        scheduleCleanupTask(key, System.currentTimeMillis() + expirationTime);
        System.out.println("set : " + value);
        System.out.println(LocalDateTime.now());
    }

    @Override
    public V get(final K key) {
        return this.data.get(key);
    }

    @Override
    public V delete(final K key) {
        System.out.println("removed : " + this.data.get(key));
        System.out.println(LocalDateTime.now());
        return this.data.remove(key);
    }
}
