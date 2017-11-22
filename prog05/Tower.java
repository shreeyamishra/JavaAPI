package prog05;

import java.util.Stack;

import prog02.GUI;
import prog02.UserInterface;

public class Tower {
	static UserInterface ui = new GUI();

	static public void main (String[] args) {
		int n = getInt("How many disks?");
		if (n <= 0)
			return;
		Tower tower = new Tower(n);

		String[] commands = { "Human plays.", "Computer plays." };
		int c = ui.getCommand(commands);
		if (c == 0)
			tower.play();
		else
			tower.solve();
	}

	/** Get an integer from the user using prompt as the request.
	 *  Return 0 if user cancels.  */
	static int getInt (String prompt) {
		while (true) {
			String number = ui.getInfo(prompt);
			if (number == null)
				return 0;
			try {
				return Integer.parseInt(number);
			} catch (Exception e) {
				ui.sendMessage(number + " is not a number.  Try again.");
			}
		}
	}

	int nDisks;
	StackInt<Integer>[] pegs = (StackInt<Integer>[]) new ArrayStack[3];

	Tower (int nDisks) {
		this.nDisks = nDisks;
		for (int i = 0; i < pegs.length; i++)
			pegs[i] = new ArrayStack<Integer>();

		for(int x = nDisks; x>=0; x--) {
			pegs[0].push(x);
		}
		// EXERCISE: Initialize game with pile of nDisks disks on peg 'a' (pegs[0]).

	}

	void play () {
		while (pegs[0] != null || pegs[1] != null){ //* EXERCISE:  player has not won yet. */ true /* Not right. */) {
			displayPegs();
			String move = getMove();
			int from = move.charAt(0) - 'a';
			int to = move.charAt(1) - 'a';
			move(from, to);
		}

		ui.sendMessage("You win!");
	}

	String stackToString (StackInt<Integer> peg) {
		StackInt<Integer> helper = new ArrayStack<Integer>();

		// String to append items to.
		String s = "";

		while(!peg.empty()) 
			helper.push(peg.pop());
		while(!helper.empty()) {
			int x = helper.pop();
			s+= ""+x;
			peg.push(x);

		}
		// EXERCISE:  append the items in peg to s from bottom to top.


		return s;
	}

	void displayPegs () {
		String s = "";
		for (int i = 0; i < pegs.length; i++) {
			char abc = (char) ('a' + i);
			s = s + abc + stackToString(pegs[i]);
			if (i < pegs.length-1)
				s = s + "\n";
		}
		ui.sendMessage(s);
	}

	String getMove () {
		String[] moves = { "ab", "ac", "ba", "bc", "ca", "cb" };
		return moves[ui.getCommand(moves)];
	}

	void move (int from, int to) {
		if(from<to)
			pegs[to] = pegs[from];

		else ui.sendMessage("Cannot place " + from + "on top of " + to);
		// EXERCISE:  move one disk form pegs[from] to pegs[to].
		// Don't allow illegal moves.  Send a warning message instead, like:
		// Cannot place 2 on top of 1.  Use ui.sendMessage().


	}

	static String[] pegNames = { "a", "b", "c" };

	// EXERCISE:  create Goal class.
	class Goal {
		int howMany;
		int fromPeg; //0 is a, 1 is b, 2 is c
		int toPeg;
		// Data.

		int a =0;
		int b =1;
		int c =2;
		// Constructor.

		Goal(int howMany, int fromPeg, int toPeg){
			this.howMany = howMany;
			this.fromPeg = fromPeg;
			this.toPeg = toPeg;
		}
		public String toString () {
			// Convert to String and return it.
			String i = "";
			if(i.length()>0){
				i= i.concat(String.valueOf(howMany) + pegNames[fromPeg] + pegNames[toPeg]);
				return i;
			}

			else return null; // Not right.

		}

	}




	// EXERCISE:  display contents of a stack of goals
	void displayGoals (StackInt<Goal> goals) {

		StackInt<Goal> middle = new ArrayStack<Goal>();
		String s = "";
		while(!goals.empty())
			middle.push(goals.pop());
		while(!middle.empty()){
			goals.push(middle.pop());
			s= s.concat("\n" + goals.peek().toString());
		}
		ui.sendMessage(s);
	}

	void solve () {
		StackInt<Goal> goals = new ArrayStack<Goal>();
		//	StackInt<Goal> middle = new ArrayStack<Goal>();


		Goal newG = new Goal(nDisks, 0, 2);
		goals.push(newG);
		displayPegs();
		while(!pegs[0].empty()|| pegs[1].empty()){
			newG = goals.pop();
			int newPeg = 0;
			if(newG.fromPeg != 0 && newG.toPeg !=0)
				newPeg = 0;

			if(newG.fromPeg != 1 && newG.toPeg!=1)
				newPeg =1;
			if(newG.fromPeg != 2 && newG.toPeg!=2)
				newPeg =2;	
			if(newG.howMany>1){
				Goal g1 = new Goal(newG.howMany-1, newG.fromPeg, newPeg);
				Goal g2 = new Goal(1, newG.fromPeg, newG.toPeg);
				Goal g3 = new Goal(newG.howMany-1, newPeg, newG.toPeg);

				goals.push(g3);
				goals.push(g2);
				goals.push(g1);
				displayGoals(goals);
			}
			if(newG.howMany==1){
				move(newG.fromPeg, newG.toPeg);
				displayPegs();
			}
			// EXERCISE

		}        
	}
}
