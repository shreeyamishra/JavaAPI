package prog05;

import java.util.EmptyStackException;
import java.util.List;
import java.util.ArrayList;

/** Implementation of the interface StackInt<E> using a List.
 *   @author vjm
 */

public class ListStack<E> implements StackInt<E> {
	// Data Fields
	/** Storage for stack. */
	List<E> theData;

	/** Initialize theData to an empty List. */
	public ListStack() {
		theData = new ArrayList<E>();
	}

	/** Pushes an item onto the top of the stack and returns the item
      pushed.
      @param obj The object to be inserted.
      @return The object inserted.
	 */
	public E push (E obj) {
		theData.add(obj);
		return obj;
	}

	@Override
	public E peek() {
		return theData.get(theData.size()-1);
		// TODO Auto-generated method stub
	}

	@Override
	public E pop() {
		// TODO Auto-generated method stub

		E temp = theData.get(theData.size()-1);
		theData.remove(theData.size()-1);

		return temp;
	}

	@Override
	public boolean empty() {
		if(theData.contains(null))
			// TODO Auto-generated method stub
			return true;
		else return false;
	}

	/**** EXERCISE ****/
}
