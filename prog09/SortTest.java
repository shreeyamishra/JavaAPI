package prog09;
import java.util.Random;

public class SortTest<E extends Comparable<E>> {
	public void test (Sorter<E> sorter, E[] array) {
		E[] copy = array.clone();
		long start = System.nanoTime();
		sorter.sort(copy);
		long end = System.nanoTime();
		System.out.println("running time is " + (end-start)/1000000.0 + " milliseconds");

		System.out.println(sorter);

		for (int i = 0; i < copy.length; i++)
			System.out.print(copy[i] + " ");
		System.out.println();
	}

	public static void main (String[] args) {
		Integer[] array = { 3, 1, 4, 1, 5, 9, 2, 6 };
		
		if (args.length <= 100) {
			// Print out command line argument if there is one.
			System.out.println("args[0] = " + args[0]);

			// Create a random object to call random.nextInt() on.
			Random random = new Random(0);

			// Make array.length equal to args[0] and fill it with random
			// integers:
//			int length = array.length;
//			  array = new Integer[length];
			String arg = args[0];
			int length = 0;
			try { 
				length = Integer.parseInt(arg); } 
			catch (NumberFormatException e) { 
				length = arg.length(); }
			array = new Integer[length];
			for(int i=0; i<array.length; i++)
				array[i] = random.nextInt();
			  }

		SortTest<Integer> tester = new SortTest<Integer>();
		tester.test(new InsertionSort<Integer>(), array);
		tester.test(new HeapSort<Integer>(), array);
		tester.test(new QuickSort<Integer>(), array);
		tester.test(new MergeSort<Integer>(), array);
		
		if (array.length <= 100000) 
			tester.test(new InsertionSort<Integer>(), array); 
		else 
			System.out.println("Skipping InsertionSort for length" + array.length);
	}
}

class InsertionSort<E extends Comparable<E>>
implements Sorter<E> {
	public void sort (E[] array) {
		for (int n = 0; n < array.length; n++) {
			E data = array[n];
			int i = n;
			// while array[i-1] > data move array[i-1] to array[i] and
			// decrement i
			while(i>0 && array[i-1].compareTo(data)>0){
				array[i] = array[i-1];
				i--;
			}
			array[i] = data;
		}
	}
}

class HeapSort<E extends Comparable<E>>
implements Sorter<E> {

	private E[] array;

	private void swap (int i, int j) {
		E data = array[i];
		array[i] = array[j];
		array[j] = data;
	}

	public void sort (E[] array) {
		this.array = array;

		for (int i = parent(array.length - 1); i >= 0; i--)
			swapDown(i, array.length - 1);

		for (int n = array.length - 1; n >= 0; n--) {
			swap(0, n);
			swapDown(0, n - 1);
		}
	}

	public void swapDown (int root, int end) {
		// Calculate the left child of root.
		int leftChild = left(root);

		int biggerChild =0;
		// while the left child is still in the array
		//   calculate the right child
		while(leftChild <= end ){
			int rightChild = right(root);
			if(rightChild <= end && array[rightChild].compareTo(array[leftChild])>0)
				biggerChild = rightChild;
			else{
				biggerChild = leftChild;
			}
			if(array[root].compareTo(array[biggerChild])>0){
				return;
			}
			swap(root,biggerChild);
			root=biggerChild;
			leftChild = left(root);


		}
		//   if the right child is in the array and 
		//      it is bigger than than the left child
		//     bigger child is right child
		//   else
		//     bigger child is left child

		//   if the root is not less than the bigger child
		//     return

		//   swap the root with the bigger child
		//   update root and calculate left child
	}

	private int left (int i) { return 2 * i + 1; }
	private int right (int i) { return 2 * i + 2; }
	private int parent (int i) { return (i - 1) / 2; }
}

class QuickSort<E extends Comparable<E>>
implements Sorter<E> {

	private E[] array;
	private void swap (int i, int j) {
		E data = array[i];
		array[i] = array[j];
		array[j] = data;
	}

	public void sort (E[] array) {
		this.array = array;
		sort(0, array.length-1);
	}

	private void sort(int left, int right) {
		if (left >= right)
			return;

		swap(left, (left + right) / 2);

		E pivot = array[left];
		int a = left + 1;
		int b = right;
		while (a <= b) {
			if(array[a].compareTo(pivot)<0)
				a++;
			else if(array[b].compareTo(pivot)>0)
				b--;
			else{ 
				swap(a, b);
				a++;
				b--;

			}

			// Move a forward if array[a] <= pivot
			// Move b backward if array[b] > pivot
			// Otherwise swap array[a] and array[b]
		}

		swap(left, b);

		sort(left, b-1);
		sort(b+1, right);
	}
}

class MergeSort<E extends Comparable<E>>
implements Sorter<E> {

	private E[] array, array2;

	public void sort (E[] array) {
		this.array = array;
		array2 = array.clone();
		sort(0, array.length-1);
	}

	private void sort(int left, int right) {
		if (left >= right)
			return;

		int middle = (left + right) / 2;
		sort(left, middle);
		sort(middle+1, right);

		int i = left;
		int a = left;
		int b = middle+1;
		while (a <= middle || b <= right) {
			if(a> middle) 
				array2[i++] = array[b++];
			else if(b > right)
				array2[i++] = array[a++];
			else if(array[a].compareTo(array[b])>0)
				array2[i++] = array[b++];
			else
				array2[i++] = array[a++];
		}

		// If both a <= middle and b <= right
		// copy the smaller of array[a] or array[b] to array2[i]
		// Otherwise just copy the remaining elements to array2
	
	System.arraycopy(array2, left, array, left, right - left + 1);
	}
}

