import java.util.ArrayList;

public class ProbeHashMap<K, V, C> extends AbstractHashMap<K, V, C> {
	private MapEntry<K, V, C>[] table; // a fixed array of entries (all initially null)
	private MapEntry<K, V, C> DEFUNCT = new MapEntry<>(null, null, null); // sentinel
	private MapEntry<K, V, C> temp = new MapEntry<>(null, null, null);

	// provide same constructors as base class
	/** Creates a hash table with capacity 17 and prime factor 109345121. */
	public ProbeHashMap() {
		super();
	}

	/** Creates a hash table with given capacity and prime factor 109345121. */
	public ProbeHashMap(int cap) {
		super(cap);
	}

	/** Creates a hash table with the given capacity and prime factor. */
	public ProbeHashMap(int cap, int p) {
		super(cap, p);
	}

	/** Creates an empty table having length equal to current capacity. */
	@Override
	@SuppressWarnings({ "unchecked" })
	protected void createTable() {
		table = (MapEntry<K, V, C>[]) new MapEntry[capacity]; // safe cast
	}

	/** Returns true if location is either empty or the "defunct" sentinel. */
	private boolean isAvailable(int j) {
		return (table[j] == null || table[j] == DEFUNCT);
	}

	// yer bulucu
	@SuppressWarnings("unchecked")
	private int findSlot(int h, K k, V v) {
		h = ((int) k % capacity);
		int artis = (int) ((h * 10 / capacity)) % (capacity);
		if (artis == 0)
			artis++;
		int artisEski = 0;
		int sayac = 0;
		int[] array = new int[2];
		int yenininIndex = 0;// yeni gelenin yeni indexi
		int eskininIndex = 0;// eski degerin yeni indexi
		int f = 0;
		int j = h; // index while scanning table
		if (table[j] == null) {
			return j;
		} else if (table[j] != null) {
			if (table[j].getKey().equals(k)) {
				if (table[j].getValue().toString().equals(v.toString())) {
					f = Integer.parseInt(String.valueOf(table[j].getCount().toString())); // count artirma
					f++;
					table[j].setCount((C) Integer.toString(f));
					return j;
				}
			}
			temp = table[j];
			while (table[j] == null) {
				j += artis;
				sayac++;
			}
			yenininIndex = j;
			array[0] = sayac;// yeni elemanin maliyeti
			sayac = 0;
			j = h;
			artisEski = (int) (table[j].getKey()) * (10 / capacity) % capacity;
			if (artisEski == 0)
				artisEski++;
			while (table[j] == null) {
				j += artisEski;
				sayac++;
			}
			eskininIndex = j;
			array[1] = sayac;
			sayac = 0;
			j = h;
			if (array[0] <= array[1]) {
				table[eskininIndex] = temp;
				j = h;
			} else {
				j = yenininIndex;
			}
		}
		return j;

	}

	@Override
	protected V bucketGet(int h, K k, V v, C c) {
		int j = findSlot(h, k, v);
		if (j < 0)
			return null; // no match found
		return table[j].getValue();
	}

	@Override
	// elemaný ekler
	protected V bucketPut(int h, K k, V v, C c) {
		int j = findSlot(h, k, v);

		if (table[j] == null) {
			table[j] = new MapEntry<>(k, v, c);
			return table[j].setValue(v);
		}
		return table[j].getValue();
	}

	public void print() {
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null)
				System.out.println(table[i].getValue() + " " + table[i].getKey() + " " + table[i].getCount());
		}
	}

	public void control(String s) {
		int ascii = 0;
		char karakter = ' ';
		String temp = " ";
		boolean flag = true;
		for (int i = 0; i < s.length(); i++) {
			karakter = s.charAt(i);
			ascii += (int) karakter;
		}
		temp = Integer.toString(ascii);
		for (int j = 0; j < table.length; j++) {
			if (table[j] != null) {
				if (String.valueOf(table[j].getKey()).toString().equals(temp.toString())) {
					if (String.valueOf(table[j].getValue()).toString().equals(s.toString())) {
						System.out.println(table[j].getKey() + " " + table[j].getValue() + " " + table[j].getCount());
						flag = false;
						break;
					}
				}

			}
		}
		if (flag == true) {
			System.out.println(s + " -> NOT FOUND!");
		}

	}

	@Override
	protected V bucketRemove(int h, K k, V v, C c) {
		int j = findSlot(h, k, v);
		if (j < 0)
			return null; // nothing to remove
		V answer = table[j].getValue();
		table[j] = DEFUNCT; // mark this slot as deactivated
		n--;
		return answer;
	}

	@Override
	public Iterable<Entry<K, V, C>> entrySet() {
		ArrayList<Entry<K, V, C>> buffer = new ArrayList<>();
		for (int h = 0; h < capacity; h++)
			if (!isAvailable(h))
				buffer.add(table[h]);
		return buffer;
	}
}