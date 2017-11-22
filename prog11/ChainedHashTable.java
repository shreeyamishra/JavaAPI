package prog11;

import java.util.*;

public class ChainedHashTable<K, V> extends AbstractMap<K, V> {
	public static class Entry<K, V> implements Map.Entry<K, V> {
		K key;
		V value;
		Entry<K,V> next;
		Entry<K,V> previous;


		public K getKey () { return key; }
		public V getValue () { return value; }
		public V setValue (V value) { return this.value = value; }

		Entry (K key, V value, Entry<K,V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
			this.previous = previous;
		}
	}

	private final static int DEFAULT_CAPACITY = 5;
	private Entry<K,V>[] table = new Entry[DEFAULT_CAPACITY];
	private int size;

	private int hashIndex (Object key) {
		int index = key.hashCode() % table.length;
		if (index < 0)
			index += table.length;
		return index;
	}

	private Entry<K,V> find (Object key) {
		int index = hashIndex(key);
		for (Entry<K,V> node = table[index]; node != null; node = node.next)
			if (key.equals(node.key))
				return node;
		return null;
	}

	public boolean containsKey (Object key) {
		return find(key) != null;
	}

	public V get (Object key) {
		Entry<K,V> node = find(key);
		if (node == null)
			return null;
		return node.value;
	}

	public V put (K key, V value) {
		Entry<K,V> node = find(key);
		if (node != null) {
			V old = node.value;
			node.value = value;
			return old;
		}
		if (size == table.length)
			rehash(2 * table.length);
		int index = hashIndex(key);
		table[index] = new Entry<K,V>(key, value, table[index]);
		size++;
		return null;
	}

	public V remove (Object key) {
		// IMPLEMENT
		// Get the index for the key.

		Entry<K,V> previous;

		int index = hashIndex(key);

		// What if the linked list at that index is empty?

		if(table[index] == null)
			return null;
		if(table[index].key.equals(key)){
			V temp = table[index].value;
			table[index] = table[index].next;
			size--;
			return temp;
		}
		previous = table[index];
		for (Entry<K,V> node = previous.next; node != null; previous = node, node = node.next){
			if (key.equals(node.key)){
				V temp = node.value;
				previous.next = node.next;
				size--;
				return temp;
			}
		}



		// What if the first element of the list has this key?


		// If it is further down the list, make sure you keep track of
		// the pointer to the previous entry, because you will need to
		// change its next variable.


		// Return null otherwise.
		return null;
	}


	private void rehash (int newCapacity) {
		// IMPLEMENT
		size = 0;
		Entry<K,V>[] allTable = table;
		table = new Entry[newCapacity];
		for(int i =0; i<= allTable.length-1; i++){
			if(table != null){

				for (Entry<K,V> node = allTable[i]; node != null; node = node.next){
					int index = hashIndex(node.key);
					table[index] = new Entry<K,V>(node.key, node.value, table[index]);
					size++;
				}
			}
		}
	}

	private Iterator<Map.Entry<K, V>> entryIterator () {
		return new EntryIterator();
	}

	private class EntryIterator implements Iterator<Map.Entry<K, V>> {
		// EXERCISE
		int index;
		Entry current, first;
		
		public EntryIterator () {
			//for every index in the array
			for(index = 0; index<= table.length - 1; index++){
				if(table[index] != null){
					current = table[index];
					first = table[index];
					index++;
					break;
				}
			}
				//if the array isn't null at that index
					//assign the first entry you find to current and first
					//break
		}

		public boolean hasNext () {
			// EXERCISE
			//if it's the first element (first)
			if(first != null)
				return true;
			//if the current entry has a .next
			if(current.next != null)
				return true;
			//for every index in the array (starting at "index")
			for(int i = index; i<= table.length - 1; i++){
				if(table[i] != null){
				//if the array isn't null at index
					return true;
				}
			}
			return false;
		}

		public Map.Entry<K, V> next () {
			// EXERCISE
			//if it's the first element (first isn't null)
			if(first != null){
				Entry variable = first; 
				//assign another Entry variable first
				first = null;
				//first is null
				return variable;
				//return the new variable
			}
			//if current has a chained (.next) element
			if(current.next !=  null){
				//make current that element
				current = current.next;
				//return current
				return current;
			}
			//for each index in the array (starting at "index)
			for( ; index<= table.length - 1; index++){
				//if the array isn't null at index
				if(table[index] != null){
					return current = table[index++];
				}
			}


			return null;
		}

		public void remove () {}
	}

	public Set<Map.Entry<K,V>> entrySet() { return new EntrySet(); }

	private class EntrySet extends AbstractSet<Map.Entry<K, V>> {
		public int size() { return size; }

		public Iterator<Map.Entry<K, V>> iterator () {
			return entryIterator();
		}

		public void remove () {}
	}

	public String toString () {
		String ret = "------------------------------\n";
		for (int i = 0; i < table.length; i++) {
			ret = ret + i + ":";
			for (Entry<K,V> node = table[i]; node != null; node = node.next)
				ret = ret + " " + node.key + " " + node.value;
			ret = ret + "\n";
		}
		return ret;
	}

	public static void main (String[] args) {
		ChainedHashTable<String, Integer> table =
				new ChainedHashTable<String, Integer>();

		table.put("Brad", 46);
		System.out.println(table);
		table.put("Hal", 10);
		System.out.println(table);
		table.put("Kyle", 6);
		System.out.println(table);
		table.put("Lisa", 43);
		System.out.println(table);
		table.put("Lynne", 43);
		System.out.println(table);
		table.put("Victor", 46);
		System.out.println(table);
		table.put("Zoe", 6);
		System.out.println(table);
		table.put("Zoran", 76);
		System.out.println(table);

		for (String key : table.keySet())
			System.out.print(key + " ");
		System.out.println();

		table.remove("Zoe");
		System.out.println(table);
		table.remove("Kyle");
		System.out.println(table);
		table.remove("Brad");
		System.out.println(table);
		table.remove("Zoran");
		System.out.println(table);
		table.remove("Lisa");
		System.out.println(table);
	}
}

