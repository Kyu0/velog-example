/**
 * 다양한 알고리즘을 가지는 캐시 구현을 위한 인터페이스
 */
public interface Cache<K, V> {
    /**
     * 현재 캐시가 가지고 있는 키들의 사이즈를 반환하는 메소드.
     * @return 캐시 사이즈
     */
    public int size();

    /**
     * {@code key}에 {@code value}를 저장하는 메소드.
     * @param key 값을 저장하고자 하는 키
     * @param value 저장할 값
     */
    public void set(K key, V value);

    /**
     * 캐시에 저장된 값을 조회하는 메소드. {@code key}에 저장된 값이 없을 경우 {@code null}을 반환한다.
     * @param key 조회할 키
     * @return 조회된 값 또는 {@code null}
     */
    public V get(K key);

    /**
     * 캐시의 {@code key}에 저장된 값을 삭제하는 메소드. 삭제할 키가 없을 경우 {@code null}을 반환한다.
     * @param key 삭제하고자 하는 키
     * @return 삭제된 값 또는 {@code null}
     */
    public V delete(K key);
}