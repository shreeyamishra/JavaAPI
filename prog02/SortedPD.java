package prog02;

import java.io.*;

/**
 *
 * @author vjm
 */
public class SortedPD extends ArrayBasedPD {
	public String addOrChangeEntry (String name, String number) {
	    int index = find(name);
	    modified = true;
	    if (index >= 0) {
	      String oldNumber = theDirectory[index].getNumber();
	      theDirectory[index].setNumber(number);
	      return oldNumber;
	      //changing a entry
	       } 
	    else {
	      if (size >= theDirectory.length)
	        reallocate();

	      int insert_index = -index-1;
	      for(int i = size-1; i>= insert_index; i--){
	    	  theDirectory[i+1] = theDirectory[i];
	      }
	    	  
	      //adding an entry
	      theDirectory[insert_index] = new DirectoryEntry(name, number);
	      //no longer adding an entry to the end of the sequence, making it alphabetical
	      size++;
	      
	     
	      return null;
	    }
	  }
	protected int find (String name) {
		int first = 0;
		int last = size-1;
		int insert = 0;
		String nameOne = "";
		String nameTwo = "";
		while(first<=last) {
			int middle = (first+last)/2;
			nameOne=name;
			nameTwo = theDirectory[middle].getName();
			if(nameTwo.compareTo(nameOne)<0) {
				first = middle + 1;
			}
			else if(nameTwo.compareTo(nameOne)>0){
				last = middle-1;
			}
			else{
				return middle;
			}
		}
		return insert = -(first+1);
	    
	  }
	public String removeEntry (String name) {
	    int index = find(name);
	    if (index >= 0) {
	      DirectoryEntry entry = theDirectory[index];
	      for(int i= index; i< size - 1; i++) {
	      theDirectory[i] = theDirectory[i+1];
	      }
	      size--;
	      modified = true;
	      return entry.getNumber();
	    }
	    
	    else
	      return null;
	  }
}
