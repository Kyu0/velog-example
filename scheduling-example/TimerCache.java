import java.time.*;
import java.util.*;

/**
 * 데이터를 캐싱한 후 일정 시간이 지나면 삭제하는 캐시. {@code expirationTime}을 지정하지 않고 생성하면 기본값으로 1000ms로 지정된다.
 */
public class TimerCache<K, V> implements Cache<K, V> {
    private final Map<K, DataObject> data;
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
                final DataObject d = data.get(key);

                if (d != null && d.isRemovable()) {
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
        final long insertedTime = System.currentTimeMillis();
        this.data.put(key, new DataObject(value, insertedTime));
        scheduleCleanupTask(key, insertedTime + expirationTime);

        System.out.println("set : " + value);
        System.out.println(millisToLocalDateTime(insertedTime));
    }

    @Override
    public V get(final K key) {
        return this.data.get(key).getValue();
    }

    @Override
    public V delete(final K key) {
        System.out.println("removed : " + this.data.get(key));
        System.out.println(LocalDateTime.now());

        return this.data.remove(key).getValue();
    }

    /**
     * {@link TimerCache} 클래스에 동일한 키 값이 여러 번 입력되었을 때 올바른 시간에 삭제되기 위해 사용하는 Wrapper Class
     */
    private class DataObject {
        private final V value;
        private final long insertedTime;

        public DataObject(V value, long insertedTime) {
            this.value = value;
            this.insertedTime = insertedTime;
        }

        public V getValue() {
            return this.value;
        }

        public long getInsertedTime() {
            return this.insertedTime;
        }

        /**
         * 이 메소드를 호출한 시점의 시스템 시간을 확인하여 {@code insertedTime}으로부터 @{code expirationTime} 만큼의 시간이 지났는지(삭제할 수 있는지) 확인하는 메소드.
         * @return 삭제 대상인 데이터이면 true, 아니면 false
         */
        public boolean isRemovable() {
            long currentTime = System.currentTimeMillis();

            return currentTime - this.insertedTime >= expirationTime;
        }

        @Override
        public String toString() {
            return "[Inserted Time : " + millisToLocalDateTime(this.insertedTime) + ", Value : " + value.toString() + "]";
        }
    }

    private LocalDateTime millisToLocalDateTime(long millis) {
        return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
