package finalwork;

import java.util.Stack;

public class Test {
	
	static Content[][] action = new Content[100][100];
	
	public static void analyse(StringBuffer str){
		
		System.out.println("steps | op-stack | input | operation | state-stack | ACTION | GOTO");
		
		Stack<Character> op_stack = new Stack<>();
		Stack<Integer> st_stack = new Stack<>(); 
		
		str.append('#');
		
		op_stack.push('#');
		st_stack.push(0);
		
		int steps = 1;
		
		for(int i=0;i<str.length();i++){
			
			char u = str.charAt(i);
			int top = st_stack.peek();
			
			Content act = action[top][u];
			if (act.type == 0) {
				System.out.println(get_steps);
			}
		}
	}
	
	static class Content{
		public int type;
		public int num;
		public String out = "";
		public Content(){
			type = -1;
		}
		public Content(int t, int n){
			this.type = t;
			this.num = n;
		}
	}
	
	public static void main(String[] args) {
		
		
		
		
	}
}
