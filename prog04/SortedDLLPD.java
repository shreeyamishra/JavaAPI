package prog04;

import prog02.PhoneDirectory;

public class SortedDLLPD extends DLLBasedPD {

	protected DLLEntry find (String name)  {
		// EXERCISE
		// For each entry in the directory.
		// What is the first?  What is the next?  How do you know you got them all?

		// Write each directory entry to the file.
		// EXERCISE
		for (DLLEntry entry = head   ;entry != null    ;entry = entry.getNext()   ) {
			// Write the name.
			if(entry.getName().compareTo(name)>=0)  {
				return entry;
			}
		}
		// If this is the entry you want

		// return it.
		return null;

		// Name not found.
	}
	public String addOrChangeEntry (String name, String number) {
		modified = true;
		DLLEntry entry = find(name);
		if (entry != null &&  entry.getName().equals(name)) {
			String oldNumber = entry.getNumber();
			entry.setNumber(number);
			return oldNumber;

			//change
		} 
		else{ 
			DLLEntry next = entry;
			DLLEntry previous;

			if(next != null ){
				previous = next.getPrevious();
				// Add entry alphabetically if the tail is not null
			}
			else{
				previous = tail;
				//add if tail is null
			}
			entry = new DLLEntry(name, number);
			
			entry.setNext(next);
			entry.setPrevious(previous);
			
			if(next!= null)
				next.setPrevious(entry);
			else
				tail = entry;
			//no entries after
			if(previous != null)
				previous.setNext(entry);
			else
				head = entry;
			//no entries before




			return null;
		}

		}       



		public String removeEntry (String name) {
			// Call find to find the entry to remove.
			DLLEntry entry = find(name);
			// If it is not there, forget it!
			if (entry == null || !entry.getName().equals(name))
				return null;

			// Get the next entry and the previous entry.
			// EXERCISE
			DLLEntry next = entry.getNext();
			DLLEntry previous = entry.getPrevious();


			// Two cases:  previous is null (entry is head) or not
			// EXERCISE
			if(previous != null) {
				previous.setNext(next);

			}
			else head = next;
			// Two cases:  next is null (entry is tail) or not
			// EXERCISE

			if(next != null) {
				next.setPrevious(previous);

			}
			else tail = previous;
			modified = true;
			return entry.getNumber();
		}

	}
