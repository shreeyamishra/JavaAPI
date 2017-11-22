package prog07;
import java.util.*;

public class SkipMap <K extends Comparable<K>, V> extends DLLMap<K, V> {
	List<DLLMap<K, Node>> skips = new ArrayList<DLLMap<K, Node>>();

	Node getValue (Node<K,Node> node) {
		if (node == null)
			return null;
		return node.getValue();
	}

	public boolean containsKey (Object keyAsObject) {
		K key = (K) keyAsObject;
		Node start = null;
		for (int i = skips.size()-1; i >= 0; i--)
			start = getValue(skips.get(i).find(key, start));
		return containsKey(key, start);
	}

	public V get (Object keyAsObject) {
		Node start = null;
		K key = (K) keyAsObject;
		if(containsKey(keyAsObject) == true) {
			for(int i = skips.size() -1; i>=0; i--)
				start = getValue(skips.get(i).find(key, start));
		}
		return get(key, start); // WRONG
	}

	public V remove (Object keyAsObject) {
		Node start = null;
		K key = (K) keyAsObject;
		Node temp = null;
		for(int i = skips.size()-1; i>= 0; i--) {
			temp = start;
			start = getValue(skips.get(i).find(key, start));
			skips.get(i).remove(key, temp);

		}
		return remove(key, start);
	}

	public V put (K key, V value) {


		// Before each update to start, push the current value onto a stack.

		Node start =null;
		Stack<Node> s = new Stack<Node>();
		for(int i = skips.size()-1; i>= 0; i--) {
			s.push(start);
			start = getValue(skips.get(i).find(key, start));

		}

		// At the bottom level, if the list contains the key, then use put
		// to update the value and return.  Make sure to use start!

		if(containsKey((Object)key)){

			return put(key, value, start);
		}
		// Now we have to deal with adding the key.
		// put it into the bottom list, and use find to get its node.


		else{
			put(key, value, start);
			Node find = find(key, start);
			int i = 0;
			while (heads()) {

				// If the stack is empty, push a null and also add a new empty
				// DLLMap to skips.

				if(s.empty()){
					s.push(null);
					skips.add(new DLLMap<K, Node>());
				}

				start= s.pop();
				skips.get(i).put(key,  find, start);
				find = skips.get(i).find(key, start);
				// pop the stack to get the start for list i.
				// put the key into list i with value = the node the the next lower list.
				// find the new node in list



				i++;


			}
			return null;
		}
	}
	Random random = new Random(2);
	boolean heads () {
		return random.nextInt() % 2 == 0;
	}

	void skipify () {
		skips.clear();
		Node node = head;
		while (node != null && node.next != null) {
			DLLMap<K, Node> skip = new DLLMap<K, Node>();
			while (node != null) {
				node = node.next;
				if (node != null) {
					skip.put((K) node.key, node);
					node = node.next;
				}
			}
			skips.add(skip);
			node = skip.head;
		}
	}

	public String toString () {
		String s = super.toString();
		for (DLLMap map : skips)
			s = map + s;
		return s;
	}

	void print () {
		for (int i = skips.size()-1; i >= 0; i--) {
			for (Node node = skips.get(i).head; node != null; node = node.next)
				System.out.print(node.key + " ");
			System.out.println();
		}
		for (Node node = head; node != null; node = node.next)
			System.out.print(node.key + " ");
		System.out.println();
		for (Node node = head; node != null; node = node.next)
			System.out.print(node.value + " ");
		System.out.println();
	}

	public static void main (String[] args) {
		SkipMap<String, Integer> map = new SkipMap<String, Integer>();
		for (int i = 1; i < 26; i++) {
			String key = "" + (char) ('A' + i);
			Integer val = i;
			map.put(key, val);
		}
		//map.skipify();
		System.out.println(map);

		System.out.println("containsKey(A)=" + map.containsKey("A"));
		System.out.println("containsKey(C)=" + map.containsKey("C"));
		System.out.println("containsKey(L)=" + map.containsKey("L"));
		System.out.println("containsKey(M)=" + map.containsKey("M"));
		System.out.println("containsKey(Z)=" + map.containsKey("Z"));
		System.out.println("containsKey(Zoe)=" + map.containsKey("Zoe"));

		System.out.println("get(A)=" + map.get("A"));
		System.out.println("get(C)=" + map.get("C"));
		System.out.println("get(L)=" + map.get("L"));
		System.out.println("get(M)=" + map.get("M"));
		System.out.println("get(Z)=" + map.get("Z"));
		System.out.println("get(Zoe)=" + map.get("Zoe"));

		System.out.println("remove(A)=" + map.remove("A"));
		System.out.println("remove(C)=" + map.remove("C"));
		System.out.println("remove(L)=" + map.remove("L"));
		System.out.println("remove(Q)=" + map.remove("Q"));
		System.out.println("remove(Z)=" + map.remove("Z"));
		System.out.println("remove(Zoe)=" + map.remove("Zoe"));

		System.out.println(map);

		System.out.println("put(A,10)=" + map.put("A",10));
		System.out.println("put(A,11)=" + map.put("A",11));
		System.out.println("put(L,20)=" + map.put("L",20));
		System.out.println("put(L,21)=" + map.put("L",21));
		System.out.println("put(Z,30)=" + map.put("Z",30));
		System.out.println("put(Z,31)=" + map.put("Z",31));

		System.out.println(map);
	}
}
