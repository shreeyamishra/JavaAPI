package prog09;

public class MainTest {
  public static void main (String args[]) {
    String[] tests = { "20", "1000", "10000", "100000", "1000000", "10000000" };
    String[] test = new String[1];
    for (String s : tests) {
      test[0] = s;
      SortTest.main(test);
    }
  }
}
