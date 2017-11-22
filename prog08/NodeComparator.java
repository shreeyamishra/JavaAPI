package prog08;

import java.util.Comparator;

import javax.xml.soap.Node;

public class NodeComparator implements Comparator<Node>{

	String target;
	NodeComparator(String target){
		this.target = target;
	}
	int value (Node node){

		int count = 0;
		int counter =0;
		while(node.getPreviousSibling() != null){
			node = node.previous;
			count++;
		}
		for(int i =0; i<= target.length(); i++){
			if(target.charAt(i)!= node.word.charAt(i))
				counter++;
		}
		return count+counter;
	}
	public int compare(Node node1, Node node2){
		return value(node1)-value(node2);
	}
	public int distance(WordPath.Node node){
		int counter = 0;
		for(WordPath.Node entry = node; entry != null; entry = entry.previous)
			counter ++;
		return counter;
	}
}
