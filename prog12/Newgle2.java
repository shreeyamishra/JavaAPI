package prog12;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Newgle2 implements SearchEngine {

	HardDisk<PageFile> pageDisk = new HardDisk<PageFile>();
	PageTrie page2index= new PageTrie();
	HardDisk<List<Long>> wordDisk = new HardDisk<List<Long>>();
	WordTable word2index= new WordTable();
	PageComparator pageComparator = new PageComparator();


	public void gather(Browser browser, List<String> startingURLs) {
		page2index.read(pageDisk);
		word2index.read(wordDisk);

	}

	public String[] search(List<String> keyWords, int numResults) {
		// Iterator into list of page ids for each key word.

		// Current page index in each list, just ``behind'' the iterator.

		// LEAST popular page is at top of heap so if heap has numResults
		// elements and the next match is better than the least popular page
		// in the queue, the least popular page can be thrown away.

		List<Long> keyWordIndices = new LinkedList<Long>();
		for (String word : keyWords){	//remove words not found online
			Long index = word2index.get(word);
			if (index != null)
				keyWordIndices.add(index);
		}
		
		if (keyWordIndices.size() == 0)
			return new String[]{"Search not found"};

		Iterator<Long>[] pageIndexIterators = (Iterator<Long>[]) new Iterator[keyWordIndices.size()];
		long[] currentPageIndices = new long[keyWordIndices.size()];
		PriorityQueue<Long> bestPageIndices = new PriorityQueue<Long>(numResults, pageComparator);
		for(int i=0; i<= keyWords.size()-1; i++){
			//			String word = keyWords.get(i);
			//			Long wordIndex = word2index.get(word);
			pageIndexIterators[i] = wordDisk.get(keyWords.get(i)).iterator();
		}

		while(moveForward(currentPageIndices, pageIndexIterators) ){

			long pageIndex = currentPageIndices[0];

			if(allEqual(currentPageIndices)){
				if(bestPageIndices.size() < numResults || pageDisk.get(pageIndexIterators).getRefCount() > pageDisk.get(bestPageIndices.peek()).getRefCount()){
					if(bestPageIndices.size() >= numResults)
						bestPageIndices.poll();
					bestPageIndices.offer(pageIndex);
				}




			}
		}


		String[] print = new String[bestPageIndices.size()];

		for(int i = bestPageIndices.size()-1; i>= 0; i--){
			print[i] = pageDisk.get(bestPageIndices.poll()).url;
		}
		return print;



		//		System.out.println("pageDisk" + pageDisk.toString() + "\n");
		//		page2index.write(pageDisk);
		//		System.out.println("page2index"+ "\n" +  page2index.toString());
		//		System.out.println("wordDisk"+ "\n" +  wordDisk.toString());
		//		word2index.write(wordDisk);
		//		System.out.println("word2index"+ "\n" +  word2index.toString());
		//		return print;
	}
	class PageComparator implements Comparator<Long> {
		public int compare (Long a, Long b){
			int comp = pageDisk.get(a).getRefCount() - pageDisk.get(b).getRefCount();
			return comp;
		}
	}

	/** If all the currentPageIndices are the same (because are just
	      starting or just found a match), move them all forward: call
	      next() for each page index iterator and put the result into
	      current page indices.

	      If they are not all the same, don't move the largest one(s)
	      forward.  (There may be more than one equal to the largest index
	      in current page indices.)

	      Return false if hasNext() is false for any iterator.
	@param currentPageIndices array of current page indices
    @param pageIndexIterators array of iterators with next page indices
    @return true if all minimum page indices updates, false otherwise
	 */
	private boolean moveForward	(long[] currentPageIndices, Iterator<Long>[] pageIndexIterators) {

		long largest = currentPageIndices[0];
		List<Integer> list = new LinkedList<Integer>();

		for(int i = 0; i < currentPageIndices.length; i++)
			if(currentPageIndices[i] > largest)
				largest = currentPageIndices[i];
		if(allEqual(currentPageIndices))
			largest++;
		for(int i = 0; i<currentPageIndices.length; i++)
			if(currentPageIndices[i] != largest){

				if(!pageIndexIterators[i].hasNext()){
					return false;
				}
				else{
					currentPageIndices[i] = pageIndexIterators[i].next();
				}
				if(currentPageIndices[i] < largest)
					list.add(i);

			}
		for(Integer iterator: list)
			currentPageIndices[iterator] = pageIndexIterators[iterator].next();


		return false;
	}
	/** Check if all elements in an array are equal.
    @param array an array of numbers
    @return true if all are equal, false otherwise
	 */
	private boolean allEqual (long[] array) {

		for(int i  = 1; i<= array.length-1; i++)
			if(array[0] == array[i])
				return true;

		return false;
	}
	Long indexPage(String page){
		Long indexPage = pageDisk.newFile();
		PageFile pageFile = new PageFile(indexPage, page);
		System.out.println("indexing page "+ pageFile.toString());
		pageDisk.put(indexPage, (PageFile) pageFile);
		page2index.put(page, indexPage);
		return indexPage;

	}
	Long indexWord(String word){
		Long index = wordDisk.newFile();
		List<Long> wordFile = new LinkedList<Long>();
		System.out.println("Indexing word "+index+"("+word+")"+wordFile);
		wordDisk.put(index, wordFile);
		word2index.put(word, index);
		return index;
	}
}
