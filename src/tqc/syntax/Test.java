package tqc.syntax;

import java.util.Vector;

public class Test {

	public static void main(String[] args) {
		
		Vector<Integer> vector1 = new Vector<>();
		vector1.add(1);
		vector1.add(2);
		vector1.add(2);
		vector1.add(2);
		vector1.add(2);
		
		Vector<Integer> vector2 = new Vector<>();
		vector2.add(0);
		vector2.add(0);
		vector2.add(0);
		
		System.out.printf("%-30s|\n", vector1);
		System.out.printf("%-30s|\n", vector2);
		
		
	}
}
