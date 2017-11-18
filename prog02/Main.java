package prog02;

/**
 *
 * @author vjm
 */
public class Main {

	/** Processes user's commands on a phone directory.
      @param fn The file containing the phone directory.
      @param ui The UserInterface object to use
             to talk to the user.
      @param pd The PhoneDirectory object to use
             to process the phone directory.
	 */
	public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
		pd.loadData(fn);

		String[] commands = {
				"Add/Change Entry",
				"Look Up Entry",
				"Remove Entry",
				"Save Directory",
		"Exit"};

		String name, number, oldNumber;

		while (true) {
			int c = ui.getCommand(commands);
			switch (c) {
			case 0:
				name = ui.getInfo("Enter the name ");
				if(name == null || name.length() == 0) 
                	break;
				number = ui.getInfo("Enter the number ");
				if(number == null) 
                	break;
				oldNumber = pd.addOrChangeEntry(name, number);
				if(oldNumber == null) {
				ui.sendMessage(name + " has number " + number);

				}
				else
					ui.sendMessage(name + " has changed his number from" + oldNumber + " to " + number);
				// implement add/change entry
				break;
			case 1:
				name = ui.getInfo("Enter the name ");
				if(name == null || name.length() == 0) 
                	break;
                number = pd.lookupEntry(name);
                
                if(number == null) 
                	ui.sendMessage(name + " is not listed in the directory");
                
                else
                	ui.sendMessage(name + " has number " + number); 
                break;
				// implement
			
			case 2:
				// implement remove entry
				name = ui.getInfo("Enter the name ");
				if(name == null || name.length() == 0) 
                	break;
				number = pd.lookupEntry(name);
                
                if(number == null) 
                	ui.sendMessage(name + " is not listed in the directory");
                
                else
                	ui.sendMessage("Removed entry with name " + name + " and number " + number); 
            	number = pd.removeEntry(name);

				break;
			case 3:
				pd.save();// implement
				break;
			case 4:
				// implement
				pd.save();
				
				return;
			}
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		String fn = "csc220.txt";
		PhoneDirectory pd = new SortedPD();
		UserInterface ui = new GUI();
		processCommands(fn, ui, pd);
	}
}
