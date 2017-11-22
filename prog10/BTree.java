package prog10;
import java.util.*;

public class BTree<K extends Comparable<K>, V>
extends AbstractMap<K, V> implements Map<K, V> {

	/**
	 * At the bottom of the tree, this is an ordinary (prog02) directory
	 * entry in the directory list.  It contains the V value in
	 * listOrValue.  Inside the tree, this is a list entry.  Its
	 * listOrValue points to list the next level down.
	 */
	private class Entry {
		private K key;
		private Object /* V or List<Entry> */ listOrValue;

		private Entry (K key, V value) {
			this.key = key;
			listOrValue = value;
		}

		private Entry (List<Entry> list) {
			key = list.get(0).key;
			listOrValue = list;
		}

		private boolean bottom () {
			return !(listOrValue != null &&
					listOrValue instanceof ArrayList &&
					getList().size() > 0 &&
					getList().get(0) instanceof BTree.Entry);
		}

		public K getKey () {
			return key;
		}

		public V getValue () {
			return (V) listOrValue;
		}

		public V setValue (V value) {
			V old = getValue();
			listOrValue = value;
			return old;
		}

		public List<Entry> getList () {
			return (List<Entry>) listOrValue;
		}

		public String toString () {
			return "" + getKey() + " " + getValue();
		}
	}

	// (4) means give it a capacity (length) of 4.  size is set to 0.
	private List<Entry> list = new ArrayList<Entry>(4);

	/**
	 * Find the index of the entry that contains key or the index at
	 * which it should be inserted.  This is like the find method in
	 * prog02 except you should not bother with binary search since n is
	 * so small.
	 * For example if
	 *   list.get(0).key equals "Alex"
	 *   list.get(1).key equals "Lisa"
	 *   list.get(2).key equals "Reid"
	 * findInList("Lisa", list) should return 1 because that's Lisa's
	 * index, but findInList("Ivy", list) should also return 1 because
	 * that's where Ivy should go, and findInList("Zoe", list) should
	 * return 3, right?  What about findInList("Aaron", list)?
	 */
	private int findInList (K key, List<Entry> list) {
		// EXERCISE
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).key.compareTo(key) >=0){
				return i;
			}
		}
		//return -1; // Comment out this line.
		return list.size();
	}

	/**
	 * The previous find method was like the one in prog02.  This
	 * findInListOfLists method is for finding which list, in a list of
	 * lists, contains the key we are looking for, or if it is not
	 * there, where it belongs.
	 * If the keys are as stated in the previous example,
	 *   list.get(0).getList() contains "Alex" up to (but not including) "Lisa"
	 *   list.get(1).getList() contains "Lisa" up to (but not including) "Reid"
	 *   list.get(2).getList() contains "Reid" and beyond.
	 * findInListOfLists("Lisa") should still be 1, but
	 * findInListOfLists("Ivy") should be 0 now, and
	 * findInListOfLists("Zoe") should be 2, and
	 * findInListOfLists("Aaron") should be 0.
	 */
	private int findInListOfLists (K key, List<Entry> list) {
		// EXERCISE
		//		while(!list.isEmpty()){

		for(int i = 1; i< list.size();i++)
			if(key.compareTo(list.get(i).key)<0)
				return i-1;

		return list.size()-1;


		// Comment out this line.
	}


	/**
	 * Recursively find the leaf entry for the key in the tree.
	 * Return null if not there.
	 */
	private Entry find (K key, List<Entry> list) {
		// If the list is empty, it is not there.
		//		if (list.size() == 0)
		//			return null;

		// If the list entries are leaves (contain data not lists):
		//else{
		if (list.get(0).bottom()) {

			// EXERCISE
			// Use findInList to get the index of the key, if it is there.

			int i = findInList(key, list);
			if (i < list.size() && key.equals(list.get(i).key))
				return list.get(i);

			// If the entry at the index equals the key, return the entry.
			// (Be careful looking for "zero".)

		}

		// List entries are lists.
		else {
			// EXERCISE
			// Use findInListOfLists to get the index of the list that has
			// the key in it, if it is there.

			// Recursively find the key in the list at i.
			int i = findInListOfLists(key, list);
			return find(key, list.get(i).getList() /* Change null to the list at i. */);
		}
		//}
		return null;
	}

	public boolean containsKey (Object key) {
		return find((K) key, list) != null;
	}

	public V get (Object obj) {
		K key = (K) obj;
		// EXERCISE
		Entry entry = find(key, list);
		if(entry == null)
			return null;
		else
			return entry.getValue();
		//		return null; // Comment out this line.
	}

	/**
	 * Split off a right hand list for a list containing four elements.
	 * Before this call, original list contains [0], [1], [2], [3].
	 * After the call, original list contains [0] and [1], and
	 * returned list contains what used to be [2] and [3].
	 * As always, new list capacity should be 4.
	 */
	private List<Entry> split (List<Entry> list) {
		List<Entry> right = new ArrayList<Entry>(4);
		right.add(list.get(2));
		right.add(list.get(3));
		list.remove(3);
		list.remove(2);
		return right;
	}

	public V put (K key, V value) {
		Entry entry = find(key, list);
		if (entry != null) {
			V old = entry.getValue();
			entry.setValue(value);
			return old;
		}

		insert(key, value, list);

		// Look at this!!!
		if (list.size() == 4) {
			List<Entry> left = list;
			List<Entry> right = split(list);
			list = new ArrayList<Entry>(4);
			list.add(new Entry(left));
			list.add(new Entry(right));
		}

		return null;
	}

	/**
	 * Recursively insert an entry with key and value into list.
	 * Precondition:  entry must not be there.
	 */
	private void insert (K key, V value, List<Entry> list) {
		// If we are at the bottom level:
		if (list.size() == 0 || list.get(0).bottom()) {

			// EXERCISE

			// Create a new Entry.

			Entry entry = new Entry(key, value);
			// Remember:  this is an ordinary list of entries (leaf not internal).
			// How do you get the index where to insert it?

			int i = findInList(key, list);
			// Insert it using a method in the List API.
			// Just one line.  No loop necessary.


			list.add(i, entry);
		}
		// Not at the bottom level -- list of lists.
		else {
			// EXERCISE

			// Get the index at which it should be inserted.
			//			int i = 0; // Incorrect.  Fix this.

			int i = findInListOfLists(key, list);
			// Get the entry at index i.
			Entry entry = list.get(i);
			// Get its (sub)list.
			List<Entry> sublist = entry.getList();

			// Recursively insert the key and value into this sublist.
			insert(key, value, sublist);
			// Update entry's key to be the first key in the sublist.
			entry.key = sublist.get(0).key;

			// Sublist is too big, needs to be split.
			if (sublist.size() == 4) {
				// Split off the right half of the list.
				List<Entry> rightlist = split(sublist);

				// Create a new Entry with rightlist.
				Entry right = new Entry(rightlist);

				// EXERCISE
				// Insert it into list. What index?  (Hint: to the right of index i.)

				list.add(i+1, right);
			}
		}
	}

	public V remove (K key) {
		Entry entry = find(key, list);

		if (entry == null)
			return null;

		remove(key, list);


		// EXERCISE
		// If the list has only one element and that element is not a
		// leaf, then throw away the list and that entry and set list
		// equal to that entry's list.



		if(list.size() == 1 && !list.get(0).bottom()){
			list = list.get(0).getList();
		}
		
		return entry.getValue();
	}


	/**
	 * Recursively remove the entry with key from the list.
	 * Precondition:  entry must be there.


	/**
	 * Recursively remove the entry with key from the list.
	 * Precondition:  entry must be there.
	 */
	private void remove (K key, List<Entry> list) {
		// EXERCISE

		// If we are at the bottom level:

		// Find the index of the key and remove the entry at that index.
		// The List API has a remove method you should use.

		Entry entry = find(key, list);


		if(list.get(0).bottom())

			list.remove(findInList(key,list));

		// Else:

		else{
			int index = findInListOfLists(key, list);
			Entry otherEntry = list.get(index);
			List sublist = otherEntry.getList();
			remove(key, sublist);
			otherEntry.key = ((Entry)sublist.get(0)).key;
			// First find the index of the sublist which contains the key.

			// Get the entry at that index.

			// Get its list, call it the sublist.

			// Recursively remove the key from that sublist.

			// Make sure to update the key field of the entry because the
			// sublist may have a new key at index 0 now.
			int i = index;

			if (sublist.size() == 1) {
				// We need to merge list i with one of its neighbors.
				// If i > 0, set leftIndex=i-1 and rightIndex=i.
				// If i == 0, set leftIndex=0 and rightIndex=1.
				// We are going to merge the entries at leftIndex and
				// rightIndex in list.

				int leftIndex, rightIndex;
				if (i > 0) {
					leftIndex = i-1;
					rightIndex = i;
				}
				else {
					leftIndex = 0;
					rightIndex = 1;
				}

				// Get the left entry and the right entry.
				Entry leftEntry = list.get(leftIndex);
				Entry rightEntry = list.get(rightIndex);

				// Get the left sublist and the right sublist.
				List<Entry> leftList = leftEntry.getList();
				List<Entry> rightList = rightEntry.getList();

				// Use the addAll method of ArrayList to add all the entries
				// of the right sublist to the left sublist.
				leftList.addAll(rightList);

				// Use the remove method of ArrayList to remove the right entry.
				list.remove(rightIndex);

				// If the size of the left sublist is now 4:
				if (leftList.size() == 4) {
					// Split the sublist.  Look at insert to see how this is
					// done (but don't use i because we are using leftIndex and
					// rightIndex now.)
					rightList = split(leftList);
					rightEntry = new Entry(rightList);
					list.add(rightIndex, rightEntry);
				}
			}
		}
	}


	private void initTree () {
		// Entry[] nodes = { 
		BTree.Entry[] entries = { 
				new Entry((K) "eight", (V) (Integer) 8),
				new Entry((K) "five", (V) (Integer) 5),
				new Entry((K) "four", (V) (Integer) 4),
				new Entry((K) "nine", (V) (Integer) 9),
				new Entry((K) "one", (V) (Integer) 1),
				new Entry((K) "seven", (V) (Integer) 7),
				new Entry((K) "six", (V) (Integer) 6),
				new Entry((K) "three", (V) (Integer) 3),
				new Entry((K) "two", (V) (Integer) 2),
				new Entry((K) "zero", (V) (Integer) 0)
		};
		Integer[][][] levels = {{{0, 1}, {2, 3}, {4, 5}, {6, 7}, {8, 9}},
				{{0, 1}, {2, 3, 4}},
				{{0, 1}}};
		List<Entry>[][] lists = (List<Entry>[][]) new List[levels.length][];

		lists[0] = (List<Entry>[]) new List[levels[0].length];
		for (int i = 0; i < levels[0].length; i++) {
			List<Entry> list = new ArrayList<Entry>();
			for (int j = 0; j < levels[0][i].length; j++)
				list.add(entries[levels[0][i][j]]);
			lists[0][i] = list;
		}

		for (int h = 1; h < levels.length; h++) {
			lists[h] = (List<Entry>[]) new List[levels[h].length];
			for (int i = 0; i < levels[h].length; i++) {
				List<Entry> list = new ArrayList<Entry>();
				for (int j = 0; j < levels[h][i].length; j++) {
					list.add(new Entry(lists[h-1][levels[h][i][j]]));
					lists[h][i] = list;
				}
			}
		}

		list = lists[lists.length-1][0];
	}

	// Required to implement Map<K, V>.
	public Set<Map.Entry<K,V>> entrySet () { return null; }

	int maxDepth;

	private boolean test () {
		maxDepth = -1;
		if (list.size() > 3)
			return false;
		for (int i = 0; i < list.size(); i++) {
			K up = null;
			if (i < list.size()-1) {
				up = list.get(i+1).key;
				if (list.get(i).key.compareTo(up) >= 0)
					return false;
				if (list.get(i).bottom() != list.get(i+1).bottom())
					return false;
			}
			if (list.get(i).bottom())
				maxDepth = 0;
			else if (!test(list.get(i).getList(), list.get(i).key, up, 1))
				return false;
		}
		return true;
	}

	private boolean test (List<Entry> list, K lo, K up, int depth) {
		if (list.size() < 2 || list.size() > 3)
			return false;
		if (!list.get(0).key.equals(lo))
			return false;
		if (up != null && list.get(list.size()-1).key.compareTo(up) >= 0)
			return false;
		for (int i = 0; i < list.size(); i++) {
			K up2 = null;
			if (i < list.size()-1) {
				up2 = list.get(i+1).key;
				if (list.get(i).key.compareTo(up2) >= 0)
					return false;
				if (list.get(i).bottom() != list.get(i+1).bottom())
					return false;
			}
			if (list.get(i).bottom()) {
				if (maxDepth != -1 && maxDepth != depth)
					return false;
				maxDepth = depth;
			}
			else if (!test(list.get(i).getList(), list.get(i).key, up, depth+1))
				return false;
		}
		return true;
	}


	public String toString () {
		if (test())
			return "-------------------------\n" + toString(list, 0);
		else
			return "------!!BAD TREE!!-------\n" + toString(list, 0);
	}

	private String toString (List<Entry> list, int indent) {
		String ret = "";
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < indent; j++)
				ret = ret + "    ";
			ret = ret + list.get(i).key;
			if (list.get(i).bottom())
				ret = ret + " " + (V) list.get(i).getValue() + "\n";
			else
				ret = ret + "\n" + toString(list.get(i).getList(), indent+4);
		}
		return ret;
	}

	public static void main (String[] args) {
		String[] nums = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
		BTree<String, Integer> tree = new BTree<String, Integer>();
		tree.initTree();
		System.out.println(tree);

		for (String num: nums)
			System.out.println("containsKey(" + num + ") = " + tree.containsKey(num));

		System.out.println("containsKey(" + "apple" + ") = " + tree.containsKey("apple"));
		System.out.println("containsKey(" + "google" + ") = " + tree.containsKey("google"));
		System.out.println("containsKey(" + "zzz" + ") = " + tree.containsKey("zzz"));

		for (String num: nums)
			System.out.println("get(" + num + ") = " + tree.get(num));

		System.out.println("get(" + "apple" + ") = " + tree.get("apple"));
		System.out.println("get(" + "google" + ") = " + tree.get("google"));
		System.out.println("get(" + "zzz" + ") = " + tree.get("zzz"));

		String[] nums2 = { "ten", "eleven", "twelve", "thirteen", "fourteen",
				"fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };

		int n = 10;
		for (String num2: nums2) {
			System.out.println("put(" + num2 + ", " + n + ") = " + tree.put(num2, n));
			n++;
			System.out.println(tree);
		}

		for (int i = 0; i < 10; i++) {
			System.out.println("put(" + nums[i] + ", " + -i + ") = " + tree.put(nums[i], -i));
			System.out.println(tree);
		}

		for (int i = 0; i < 10; i++) {
			System.out.println("remove(" + nums[i] + ") = " + tree.remove(nums[i]));
			System.out.println(tree);
		}
	}
}
