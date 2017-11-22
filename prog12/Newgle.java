package prog12;

import java.io.File;
import java.util.*;

public class Newgle implements SearchEngine {

		HardDisk<PageFile> pageDisk = new HardDisk<PageFile>();
		PageTrie page2index= new PageTrie();
		
		HardDisk<List<Long>> wordDisk = new HardDisk<List<Long>>();
		WordTable word2index= new WordTable();

	public Long indexPage(String url){
		//Get the index of a new file from pageDisk, create a new
		Long index = pageDisk.newFile();
		PageFile page = new PageFile( index, url);
		pageDisk.put(index, page);
		page2index.put(url, index);
		
		   //PageFile, and store the PageFile on the pageDisk.  Then it tells
		  // the trie page2index to map url to that index and returns the index.
		
		//returns the index of its newly created information
		   //file
		System.out.println("indexing page " + page.toString());
		
		return index;

	}
	public Long indexWord(String word){
		Long i = wordDisk.newFile();
		LinkedList<Long> pages = new LinkedList<Long>();
		wordDisk.put(i, pages);
		word2index.put(word, i);
		System.out.println("indexing word " + i+"("+word+")"+ pages.toString());

		
		return i;
	}

	public void gather (Browser browser, List<String> startingURLs){
		HashSet<Long> set;
		Long pageIndex;
		
		Queue<Long> pageQueue = new ArrayDeque<Long>();
		
		for(String url: startingURLs){
			if(!page2index.containsKey(url)){
				System.out.println("gather ["+url+"]");

				pageQueue.offer(indexPage(url));

			}
		}

		while(!pageQueue.isEmpty()){
			Long index2 = pageQueue.peek();
			System.out.println("queue " + pageQueue.toString());

			pageIndex = pageQueue.poll();
			set = new HashSet<Long>();
			List<String> URLs = null, words = null;

			System.out.println("dequeued "  + pageDisk.get(index2).toString());

			
		    if(browser.loadPage(pageDisk.get(pageIndex).url)){
		    	URLs = browser.getURLs();
		    	words = browser.getWords();

    			System.out.println("urls " + URLs.toString());


		    	for( String urlLoad : URLs){
		    		Long index = page2index.get(urlLoad);
		    		if(!page2index.containsKey(urlLoad)){
		    			pageQueue.offer(index = indexPage(urlLoad));
		    			

		    		}
		    		//if index isn't in the set
		    		if(!set.contains(index)){
		    			set.add(index);

		    		}
		    			//add it to the set
//		    		pageDisk.get(index).incRefCount();
//		    		System.out.println("inc ref "+pageDisk.get(index).toString());
		    	}
		    	//if the set isn't empty
		    	if(!set.isEmpty()){
		    		for(Long i : set){
			    		pageDisk.get(i).incRefCount();
			    		System.out.println("inc ref "+pageDisk.get(i).toString());

		    		}
		    		System.out.println("words " + words.toString());

		    	}
		    		//for all the indexes in the set
		    			//increment the reference counts
		    			//print out the statement
		    }
		    set = new HashSet<Long>();
			if (words != null) {
				//for all the word in words (words is from the browser, we are indexing all its words)
				for(String word: words){
					//get the i of the word in word2index
					Long i = word2index.get(word);
					//if it wasn't there before
					if(i == null)
						//index it with indexWord
						i = indexWord(word);
					//if it wasn't in the set before
					if(!set.contains(i)){
						
						//add it to the set
						set.add(i);
						//get the list of index (call this one i) from wordDisk
						List<Long> list = wordDisk.get(i);
						//add index (the index of the web site) to to the list
						list.add(pageIndex);
						//print out info
						System.out.println("add page " + i+"("+word+")"+ list.toString());

					}
				}
			}


			
		}
		System.out.println("pageDisk"+ "\n" +  pageDisk.toString());
		page2index.write(pageDisk);
		System.out.println("page2index"+ "\n" +  page2index.toString());
		System.out.println("wordDisk"+ "\n" +  wordDisk.toString());
		word2index.write(wordDisk);
		System.out.println("word2index"+ "\n" +  word2index.toString());

			

	}
	public String[] search(List<String> keyWords, int numResults) {


		return new String[0];
	}


}


