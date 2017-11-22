package prog06;

import java.util.*;

/**
 * Implements the Queue interface using a circular array.
 **/
public class ArrayQueue<E> extends AbstractQueue<E>
implements Queue<E> {

	// Data Fields
	/** Index of the first item in the queue. */
	private int first;
	/** Index of the last item in the queue. */
	private int last;
	/** Current size of the queue. */
	private int size;
	/** Default capacity of the queue. */
	private static final int DEFAULT_CAPACITY = 10;
	/** Array to hold the items. */
	private E[] theItems;

	// Constructors
	/**
	 * Construct a queue with the default initial capacity.
	 */
	public ArrayQueue () {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Construct a queue with the specified initial capacity.
	 * @param initCapacity The initial capacity
	 */
	@SuppressWarnings("unchecked")
	public ArrayQueue (int initCapacity) {
		theItems = (E[]) new Object[initCapacity];
		first = 0;
		last = theItems.length - 1;
		size = 0;
	}

	// Public Methods
	/**
	 * Inserts an item at the last item in the queue.
	 * @post item is added to the last item in the queue.
	 * @param item The element to add
	 * @return true (always successful)
	 */
	@Override
	public boolean offer (E item) {
		if (size == theItems.length)
			reallocate();
		// Move last forward one, but if it goes past the end of the
		// array, go last to zero.
		last = (last + 1) % theItems.length;
		// Store the new item at last.
		theItems[last] = item;
		size++;
		return true;
	}

	/**
	 * Returns the item at the first item in the queue without removing it.
	 * @return The item at the first item in the queue if successful;
	 * return null if the queue is empty
	 */
	@Override
	public E peek () {
		if (size == 0)
			return null;
		return theItems[first];
	}

	/**
	 * Removes the entry at the first item in the queue and returns it
	 * if the queue is not empty.
	 * @post first references item that was second in the queue.
	 * @return The item removed if successful or null if not
	 */
	@Override
	public E poll () {
		// EXERCISE 3
		if(size!= 0){
			size--;
			E item = theItems[first];
			first = (last + 1) % theItems.length;	  
			return item;
		}
		else return  null;
	}

	/**
	 * Return the size of the queue
	 * @return The number of items in the queue
	 */
	@Override
	public int size () {
		return size;
	}

	/**
	 * Returns an iterator to the elements in the queue
	 * @return an iterator to the elements in the queue
	 */
	@Override
	public Iterator<E> iterator () {
		return new Iter();
	}

	private boolean labReallocate = true;

	// Private Methods
	/**
	 * Double the capacity and reallocate the items.
	 * @pre The array is filled to capacity.
	 * @post The capacity is doubled and the first half of the
	 *       expanded array is filled with items.
	 */
	@SuppressWarnings("unchecked")
	private void reallocate () {
		int newCapacity = 2 * theItems.length;
		E[] newItems = (E[]) new Object[newCapacity];
		if (labReallocate) {
			int j = first;
			for (int i = 0; i < size; i++) {
				newItems[i] = theItems[j];
				j = (j + 1) % theItems.length;
			}
		}
		else {
			if(first<=last)
				System.arraycopy(theItems, first, newItems, 0, last-first+1);
			else{
				System.arraycopy(theItems, first, newItems, 0, theItems.length-first);
				System.arraycopy(theItems, 0, newItems, theItems.length-first, last+1);
			}
			
			// EXERCISE 8
		}
		first = 0;
		last = size - 1;
		theItems = newItems;
	}

	private boolean labIterator = true;

	/** Inner class to implement the Iterator<E> interface. */
	private class Iter implements Iterator<E> {
		// Data Fields

		// Index of next element */
		private int index;

		// Count of elements accessed so far */
		private int count = 0;

		// Methods
		// Constructor
		/**
		 * Initializes the Iter object to reference the
		 * first queue element.
		 */
		public Iter () {
			if (labIterator) {
				index = first;
			}
			else {
				if(size>0)
					index=first;
				else
					index=-1;
				// EXERCISE 9
			}
		}

		/**
		 * Returns true if there are more elements in the queue to access.
		 * @return true if there are more elements in the queue to access.
		 */
		@Override
		public boolean hasNext () {
			if (labIterator)
				return count < size;
			else {
				// EXERCISE 9
				return index!= -1;
			}
		}

		/**
		 * Returns the next element in the queue.
		 * @pre index references the next element to access.
		 * @post index and count are incremented.
		 * @return The element with subscript index
		 */
		@Override
		public E next () {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			E returnValue = theItems[index];
			if (labIterator) {
				index = (index + 1) % theItems.length;
				count++;
			}
			else {
				if(index==last)
					index=-1;
				else index = (index+1) % theItems.length;
				// EXERCISE 9??
			}
			return returnValue;
		}

		/**
		 * Remove the item accessed by the Iter object -- not implemented.
		 * @throws UnsupportedOperationException when called
		 */
		@Override
		public void remove () {
			throw new UnsupportedOperationException();
		}
	}
}
