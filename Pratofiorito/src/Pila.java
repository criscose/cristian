

public class Pila {
	
	private int[] v;
	private int pos;
	
	public Pila() {
		v = new int[50];
		pos=0;
	}
	
	public int pop() {
		
		pos--;
		return v[pos];
	}
	
	public int peak() {
		return v[pos-1];
	}
	
	public void push(int x) {
		if(pos == v.length) {
			allargaArray();
		}
		v[pos]=x;
		pos++;
	}
	
	private void allargaArray() {
		int[]v1 = new int [v.length*2];
		for(int i=0; i<pos; i++) {
			v1[i]=v[i];
		}
		v=v1;
	}
	
	public boolean empty() {
		return pos==0;
	}
}
