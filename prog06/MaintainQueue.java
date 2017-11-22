package prog06;

import prog02.UserInterface;
import prog02.ConsoleUI;
import prog02.GUI;
import javax.swing.JOptionPane;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;

/**
 * Class to maintain a queue of customers.
 * @author vjm
 **/
public class MaintainQueue {

  // Data Field
  private Queue<String> customers;

  // Constructor
  /** Create an empty queue. */
  public MaintainQueue() {
    customers = new ArrayDeque<String>();
    // customers = new ArrayQueue<String>();
    // customers = new LinkedQueue<String>();
  }
    
  public static void main (String[] args) {
    MaintainQueue mq = new MaintainQueue();
    mq.processCustomers();
  }

  /**
   * Performs the operations selected on queue customers.
   * @pre  customers has been created.
   * @post customers is modified based on user selections.
   */
  public void processCustomers() {
    UserInterface ui = new GUI();
    String[] choices = {
      "add", "peek", "remove", "size", "position", "quit"};
    int choiceNum = 0;
    String name;
    int countAhead;
    int foundAhead;

    // Perform all operations selected by user.
    while (true) {
      ui.sendMessage("queue is " + this);
      // Select the next operation.
      choiceNum = ui.getCommand(choices);
      try {
        switch (choiceNum) {
        case 0:
          name = ui.getInfo("Enter new customer name");
          if (name != null && name.length() > 0) {
            customers.offer(name);
            ui.sendMessage("Customer " + name +
                           " added to the queue");
          }
          break;
        case 1:
          ui.sendMessage("Customer " + customers.element() +
                         " is first in the queue");
          break;
        case 2:
          ui.sendMessage("Customer " + customers.remove() +
                         " removed from the queue");
          break;
        case 3:
          ui.sendMessage("Size of queue is " + customers.size());
          break;
        case 4:
          name = ui.getInfo("Enter customer name");
          if (name == null || name.length() == 0)
            break;
          countAhead = 0;
          foundAhead = -1;
          for (String nextName : customers) {
            if (nextName.equals(name))
              foundAhead = countAhead;
            countAhead++;
          }
          if (foundAhead == -1)
            ui.sendMessage(name + " is not in queue");
          else
            ui.sendMessage("The number of customers ahead of " +
                           name + " is " + foundAhead);
          break;
        case 5:
          ui.sendMessage("Leaving customer queue. " +
                         "\nNumber of customers in queue is " +
                         customers.size());
          return;
        default:
          return;
        }
      } catch (NoSuchElementException e) {
        ui.sendMessage("ERROR:  The Queue is empty");
      }
    }
  }

  /**
   * Create a string representation of the queue contents with each
   * entry on its own line
   * @return a String representation of the queue contents
   */
  @Override
  public String toString() {
    String s = "";
    // EXERCISE 2
//    void print(Queue<String>queue){
    	for(String string:customers){
    		s+=string + "\n";
    		return s;
    	}
    

    
    return s;
  }
}
