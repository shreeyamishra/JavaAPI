package prog11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import prog02.UserInterface;
import prog02.ConsoleUI;
import prog02.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Jumble {
	/**
	 * Sort the letters in a word.
	 * @param word Input word to be sorted, like "computer".
	 * @return Sorted version of word, like "cemoptru".
	 */
	public static String sort (String word) {
		char[] sorted = new char[word.length()];
		for (int n = 0; n < word.length(); n++) {
			char c = word.charAt(n);
			int i = n;

			while (i > 0 && c < sorted[i-1]) {
				sorted[i] = sorted[i-1];
				i--;
			}

			sorted[i] = c;
		}

		return new String(sorted, 0, word.length());
	}

	public static void main (String[] args) throws FileNotFoundException {
		UserInterface ui = new GUI();
		Map<String,List<String>> map = new ChainedHashTable<String,List<String>>();
		List list = new ArrayList();
		Scanner in = null;
		File file = null;
		do {
			try {
				file = new File(ui.getInfo("Enter word file."));
				in = new Scanner(file);
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("Try again.");
			}
		} while (in == null);

		int n = 0;
		while (in.hasNextLine()) {
			String word = in.nextLine();
			if (n++ % 1000 == 0)
				System.out.println(word + " sorted is " + sort(word));
			if (map.get(sort(word)) == null){
				list = new ArrayList();
				list.add(word);

				map.put(sort(word), list);
			}
			else{
				list = map.get(sort(word));
				list.add(word);
			}

		}
		String jumble = ui.getInfo("Enter jumble.");

		while (jumble != null) {
			List<String> wordList = map.get(jumble);

			if (wordList == null)
				ui.sendMessage("No match for " + jumble);
			else
				ui.sendMessage(jumble + " unjumbled is " + list.toString());
			jumble = ui.getInfo("Enter jumble.");
		}
		//step 13
		while(true) {
			String letters = ui.getInfo("What are the letters of the clue?");
			if(letters == null)
				break;
			String sortedLetters = sort(letters);

			int clueOne = -1;
			do{
				clueOne = Integer.parseInt(ui.getInfo("Length of the first word: "));
				try{
					if(clueOne < 0)
						ui.sendMessage("The number is not positive");
				}
				catch(Exception e){
					ui.sendMessage("A number was not entered");
				}



			}
			while(clueOne <= -1);
			load(map, clueOne, sortedLetters);

		}
	}

	public static void load(Map<String,List<String>> map, int length, String sort){
		UserInterface ui = new GUI();

		if(length != -1)
			for(String key1: map.keySet()){
				if(key1.length() == length){
					String key2 = "";
					int j = 0;
					for(int i = 0; i< sort.length(); i++)
						if(j<key1.length() && sort.charAt(i) == key1.charAt(j))
							j++;
						else
							key2 = key2 + sort.charAt(i);
					if(j == key1.length()){
						if(map.containsKey(key2)){
							ui.sendMessage(map.get(key1) + " " + map.get(key2));
						}

					}
				}

			}

	}
}




