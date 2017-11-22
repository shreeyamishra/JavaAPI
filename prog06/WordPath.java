package prog06;

import java.util.List;
import java.io.*;
import java.util.*;
import prog02.GUI;
import prog02.ConsoleUI;
import javax.xml.soap.Node;
import prog02.UserInterface;
import java.util.ArrayList;

public class WordPath {

	static GUI ui = new GUI();
	List<Node> newNode = new ArrayList<Node>();

	public static void main ( String args[]){
		WordPath game = new WordPath();
		boolean loaded = false;
		while(!loaded){
			String newFile = ui.getInfo("Enter dictionary file: ");

			if(newFile.equals(null))
				return;

			else {loaded = game.loadDictionary(newFile);
			}
		}
		String start = ui.getInfo("Enter starting word ");
		String target = ui.getInfo("Enter target word ");

		String[] commands = { "Human plays.", "Computer plays." };
		game.play(start, target);


	}



	public void play(String start, String target){

		while(true){
			ui.sendMessage("Current word: " + start + "\n" + "Target word: " + target);
			start = ui.getInfo("What is your next word?");
			if(start.equals(target)){
				ui.sendMessage("You win!") ;
				return;
			}

			else {
				start = ui.getInfo("What is your next word?");

			}
			oneDegree(start,target);
		}
	}

	public static boolean oneDegree(String start, String target){
		if(start.length() == target.length())
			return true;
		int counter = 0;
		for(int i = 0; i<= start.length(); i++){
			if(start.charAt(i) != target.charAt(i))
				counter++;
			if(counter >1)
				return false;
		}
		return true;
	}
	public boolean loadDictionary(String filename){

		try{
			Scanner scanner = new Scanner(new File(filename));
			while(scanner.hasNextLine()){
				String word = scanner.nextLine();
				Node node = new Node(word);
				newNode.add(node);
			}
			return true;
		}
		catch(Exception e){
			ui.sendMessage(e);

			return false;
		}
	}
	private static class Node{
		String word; 
		Node previous;
		Node(String word){
			this.word = word;
		}


	}
	Node find(String word){
		for(int i=0; i< newNode.size(); i++)
			newNode.get(i).previous = null;
	}
	public void solve(String fail, String pass) {
		// TODO Auto-generated method stub
		//EXCERCISE 10
		Queue<Node> queue = new LinkedList<Node>();
		Node first= find(start);
		queue.offer(first);
		
		while(!queue.isEmpty()){
			Node currentNode = queue.poll();
			System.out.println("D" + node.word);
			System.out.print("E: ");
			for(int i = 0; i<= newNode.size()-1; i++){
				Node next = newNode.get(i);
				if(next != first && next.previous == null ){
					next.previous = node;
					queue.offer(next);
					System.out.print(next.word);
					if(next.word.equals(target)){
						ui.sendMessage("Got to" + target + "from" + node.word);
						while(node != first)
							node = node.previous;
						ui.sendMessage(node.word );
						ui.sendMessage(target);
						return;
				}
			}
		}
			System.out.println();
			

	}
}
}