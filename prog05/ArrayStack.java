package prog05;

import java.util.EmptyStackException;

/** Implementation of the interface StackInt<E> using an array.
 *   @author vjm
 */

public class ArrayStack<E> implements StackInt<E> {
	// Data Fields
	/** Storage for stack. */
	E[] theData;

	/** Index to top of stack. */
	int top = -1; // initially -1 because there is not top

	private static final int INITIAL_CAPACITY = 100;

	/** Construct an empty stack with the default initial capacity. */
	public ArrayStack () {
		theData = (E[])new Object[INITIAL_CAPACITY];
	}

	/** Pushes an item onto the top of the stack and returns the item
      pushed.
      @param obj The object to be inserted.
      @return The object inserted.
	 */
	public E push (E obj) {
		top++;

		if (top == theData.length)
			reallocate();

		theData[top] = obj;
		return obj;
	}

	private void reallocate() {
		// TODO Auto-generated method stub

		E[] newData;
		newData = (E[])new Object[2*theData.length];
		for(int i=0; i<= newData.length; i++) {
			newData[i]= theData[i];
		}
		//make array double the length
		//use for loop to copy data
		//copy data from old array to new array
	}

	/** Returns the object at the top of the stack and removes it.
      post: The stack is one item smaller.
      @return The object at the top of the stack.
      @throws EmptyStackException if stack is empty.
	 */
	public E pop () {

		if (empty())
			throw new EmptyStackException();


		return theData[top--];



		/**** EXERCISE ****/

	}

	/** Returns the object at the top of the stack without removing it.
      post: The stack remains unchanged.
      @return The object at the top of the stack.
      @throws EmptyStackException if stack is empty.
	 */
	public E peek () {
		/**** EXERCISE ****/

		if (empty())
			throw new EmptyStackException();
		return theData[top];
	}
	public interface StackInt<E> {
		/** Pushes an item onto the top of the stack and returns the item
	      pushed.
	      @param obj The object to be inserted.
	      @return The object inserted.
		 */
		E push(E obj);


		/** Returns the object at the top of the stack without removing it.
	      post: The stack remains unchanged.
	      @return The object at the top of the stack.
	      @throws EmptyStackException if stack is empty.
		 */
		E peek();

		/** Returns the object at the top of the stack and removes it.
	      post: The stack is one item smaller.
	      @return The object at the top of the stack.
	      @throws EmptyStackException if stack is empty.
		 */
		E pop();

		/** Returns true if the stack is empty; otherwise, returns false.
	      @return true if the stack is empty.
		 */
		boolean empty();
		/**** EXERCISE ****/
	}
	@Override
	public boolean empty() {
		if(top==-1) 
			// TODO Auto-generated method stub
			return true;

		else return false;
	}
}
