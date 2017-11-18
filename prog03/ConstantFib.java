package prog03;

public class ConstantFib extends PowerFib{
	public double o (int n) {
		return 1;
	}

	protected double pow (double x, int n) {
		
			return Math.pow(x, n);
	}
}
