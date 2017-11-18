package prog03;
import prog02.GUI;
import prog02.UserInterface;

/**
 *
 * @author vjm
 */
public class Main {
	/** Use this variable to store the result of each call to fib. */
	public static double fibN;

	/** Determine the time in microseconds it takes to calculate the
      n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time for the call to fib(n) in microseconds
	 */
	public static double time (Fib fib, int n) {
		// Get the starting time in nanoseconds.
		long start = System.nanoTime();

		// Calculate the n'th Fibonacci number.

		// Store the result in fibN.

		fibN = fib.fib(n);

		// Get the ending time in nanoseconds.  Use long, as for start.

		long end = System.nanoTime();

		// Uncomment the following for a quick test.
		//System.out.println("start " + start + " end " + end);

		// Return the difference between the end time and the
		// start time divided by 1000.0 to convert to microseconds.

		return (end - start) / 1000.0; 
	}

	/** Determine the average time in microseconds it takes to calculate
      the n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @param ncalls the number of calls to average over
      @return the average time per call
	 */
	public static double averageTime (Fib fib, int n, long ncalls) {
		double totalTime = 0;

		// Add up the total call time for calling time (above) ncalls times.
		// Use long instead int in your declaration of the counter variable.

		for(long counter = 0; counter < ncalls; counter++) {
			totalTime += time(fib, n);
		}


		return totalTime/ncalls;

		// Return the average time.

	}

	/** Determine the time in microseconds it takes to to calculate
      the n'th Fibonacci number accurate to three significant figures.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time it it takes to compute the n'th Fibonacci number
	 */
	public static double accurateTime (Fib fib, int n) {
		// Get the time using the time method above.
		int ncalls = 1;
		double t = time(fib, n);



		// Since it is not very accurate, it might be zero.  If so set it to 0.1



		ncalls = (int) ((int) 1000000/(t*t));
		// Calculate the number of calls to average over that will give
		// three figures of accuracy.  You will need to "cast" it to long
		// by putting   (long)   in front of the formula.


		// If ncalls is 0, increase it to 1.

		if( ncalls ==0) {
			ncalls =1;
		}


		// Get the accurate time using averageTime.
		return averageTime(fib, n, ncalls);
	}

	static void labExperiments () {
		// Create (Exponential time) Fib object and test it.
		Fib efib = new LinearFib();
		System.out.println(efib);
		for (int i = 0; i < 11; i++)
			System.out.println(i + " " + efib.fib(i));

		// Determine running time for n1 = 20 and print it out.
		int n1 = 20;
		double time1 = averageTime(efib, n1, 1000);
		System.out.println("n1 " + n1 + " time1 " + time1);
		//    System.out.println("n1" + n1 + "actual time" + accurateTime(n1, n));
		int ncalls = (int) (1000000/(time1*time1));
		time1 = averageTime(efib, n1, ncalls);


		// Calculate constant:  time = constant times O(n).
		double c = time1 / efib.o(n1);
		System.out.println("c " + c);

		// Estimate running time for n2=30.
		int n2 = 30;
		double time2est = c * efib.o(n2);
		System.out.println("n2 " + n2 + " estimated time " + time2est);

		// Calculate actual running time for n2=30.
		double time2 = averageTime(efib, n2, 100);
		System.out.println("n2 " + n2 + " actual time " + time2);

		// Estimate running time for n3=100.
		int n3 = 100;
		double time3est = c * efib.o(n3);
		System.out.println("n3 " + n3 + " estimated time " + time3est);

		// Calculate actual running time for n3=100.
		double time3years = (time3est/(3.15*Math.pow(10,13) ));
		System.out.println("which is "  + time3years+ " years ");


	}

	private static UserInterface ui = new GUI();

	static void hwExperiments (Fib fib) {
		double c = -1;  // -1 indicates that no experiments have been run yet.

		while (true) {
			int n;
			do{
				n = 0;
			
			    String s = ui.getInfo("Enter n");
			    if(s==null) {
				  return;
			    }

		     	try{
			    	n = Integer.parseInt(s);
			    	if( n<= 0)
			    		ui.sendMessage("Enter a positive integer");
			    }
			    catch(Exception e){
					ui.sendMessage(e + " try again ");
				}
			}while( n <= 0 );

			// accurateTime(fib, n);

			// Ask the user for an integer n.
			// Return if the user cancels.
			// Deal with bad inputs:  not positive, not an integer.

			// Estimated running time. -1 indicates no estimate
			double tEst = -1;

			// If this not the first experiment, estimate the running time for
			// that n using the value of the constant from the last time.

			if( c != -1){
				tEst = c * fib.o(n);
				// Display the estimate.

				ui.sendMessage("Estimated time is " + tEst + " ms.");
				
				// ADD LATER: If it is greater than an hour, ask the user for
				// confirmation before running the experiment.
				// If not, the "continue;" statement will skip the experiment.
				if(tEst > 3600e6){
					ui.sendMessage("Estimated to be longer than an hour, do you want to do that?");
					String[] commands = {"yes", "no"};
					int answer = ui.getCommand(commands);
					if(answer == 1)
						continue;
				}
				
				
			}

			

			// Now, calculate the (accurate) running time for that n.
			double tAcc = accurateTime(fib, n);
			// Calculate the constant c.
			c = tAcc / fib.o(n);
			// Display fib(n) and the actual running time.
			ui.sendMessage("fib(" + n + ") = " + fib.o(n) + " done in " + tAcc + " ms");

			if (tEst != -1)
				ui.sendMessage("Estimate had " + (int) (100 * (tEst - tAcc) / tAcc) + "% error.");
		}
	}

	static void hwExperiments () {
		// In a loop, ask the user which implementation of Fib or exit,
		// and call hwExperiments (above) with a new Fib of that type.

		String[] commands = {
				"ConstantFib",
				"LogFib",
				"LinearFib",
				"ExponentialFib",
		"EXIT"};
		while (true) {
			int c = ui.getCommand(commands);
			switch (c) {
			case 0: hwExperiments(new ConstantFib());
			break;
			case 1: hwExperiments(new LogFib());
			break;
			case 2: hwExperiments(new LinearFib());
			break;
			case 3: hwExperiments(new ExponentialFib());
			break;
			case 4:
				return;
			}
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main (String[] args) {
		labExperiments();
		hwExperiments();
	}
}
