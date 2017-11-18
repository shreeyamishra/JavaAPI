package prog03;

public class LinearFib implements Fib{
	public double o (int n) {
		return n;
	}

	public double fib(int n){
		int a =0;
		int b = 1;
		
		if(n<1) {
			return 0;
		}
		for(int i = 0; i<=n; i++) {
			int c=a;
			a = a+b;
			b=c;
		
				
		}
		
		 return b;
	}
}
