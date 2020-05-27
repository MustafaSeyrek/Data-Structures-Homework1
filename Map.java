public interface Map<K, V, C> {

	int size();

	boolean isEmpty();

	V get(K key, V value, C count);

	V put(K key, V value, C count);

	V remove(K key, V value, C count);

	Iterable<K> keySet();

	Iterable<V> values();

	Iterable<C> counts();

	Iterable<Entry<K, V, C>> entrySet();
}