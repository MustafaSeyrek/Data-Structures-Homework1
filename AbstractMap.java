import java.util.Iterator;

public abstract class AbstractMap<K, V, C> implements Map<K, V, C> {

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	// ---------------- nested MapEntry class ----------------

	protected static class MapEntry<K, V, C> implements Entry<K, V, C> {
		private K k; // key
		private V v; // value
		private C c; // count

		public MapEntry(K key, V value, C count) {
			k = key;
			v = value;
			c = count;
		}

		// public methods of the Entry interface
		public K getKey() {
			return k;
		}

		public V getValue() {
			return v;
		}

		public C getCount() {
			return c;
		}

		// utilities not exposed as part of the Entry interface
		protected void setKey(K key) {
			k = key;
		}

		protected V setValue(V value) {
			V old = v;
			v = value;
			return old;

		}

		public C setCount(C count) {
			C old = c;
			c = count;
			return old;
		}

		/** Returns string representation (for debugging only) */
		/*
		 * public String toString() { return "<" + k + ", " + v + "," + c + ">"; }
		 */
	} // ----------- end of nested MapEntry class -----------

	// Provides support for keySet() and values() methods, based upon
	// the entrySet() method that must be provided by subclasses

	// ---------------- nested KeyIterator class ----------------
	private class KeyIterator implements Iterator<K> {
		private Iterator<Entry<K, V, C>> entries = entrySet().iterator(); // reuse entrySet

		public boolean hasNext() {
			return entries.hasNext();
		}

		public K next() {
			return entries.next().getKey();
		} // return key!

		public void remove() {
			throw new UnsupportedOperationException("remove not supported");
		}
	} // ----------- end of nested KeyIterator class -----------

	// ---------------- nested KeyIterable class ----------------
	private class KeyIterable implements Iterable<K> {
		public Iterator<K> iterator() {
			return new KeyIterator();
		}
	} // ----------- end of nested KeyIterable class -----------

	@Override
	public Iterable<K> keySet() {
		return new KeyIterable();
	}

	// ---------------- nested ValueIterator class ----------------
	private class ValueIterator implements Iterator<V> {
		private Iterator<Entry<K, V, C>> entries = entrySet().iterator(); // reuse entrySet

		public boolean hasNext() {
			return entries.hasNext();
		}

		public V next() {
			return entries.next().getValue();
		} // return value!

		public void remove() {
			throw new UnsupportedOperationException("remove not supported");
		}
	} // ----------- end of nested ValueIterator class -----------

	// ---------------- nested ValueIterable class ----------------
	private class ValueIterable implements Iterable<V> {
		public Iterator<V> iterator() {
			return new ValueIterator();
		}
	} // ----------- end of nested ValueIterable class -----------

	@Override
	public Iterable<V> values() {
		return new ValueIterable();
	}

	// *******************************************************Count
	private class CountIterator implements Iterator<C> {
		private Iterator<Entry<K, V, C>> entries = entrySet().iterator(); // reuse entrySet

		public boolean hasNext() {
			return entries.hasNext();
		}

		public C next() {
			return entries.next().getCount();
		} // return count!

		public void remove() {
			throw new UnsupportedOperationException("remove not supported");
		}
	}

	private class CountIterable implements Iterable<C> {
		public Iterator<C> iterator() {
			return new CountIterator();
		}
	}

	public Iterable<C> counts() {
		return new CountIterable();
	}

}