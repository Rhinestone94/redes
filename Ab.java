package heap;

import java.util.Arrays;


public class Ab {

	public int [] vetor;
	public int hSize;

	
	public Ab(int tamanho) {
		vetor = new int[tamanho];
		this.hSize = 0;
	}
	

	// insert e troca (faz swap)
	public void inserir(int elemento) {
		hSize++; // incrementa ultimo por 1
		vetor[hSize -1] = elemento;
		siftup(hSize -1); 
	}
	
	
	public int delete_min() {
		hSize--;
		vetor[0] = vetor[hSize];
		
		vetor[hSize] = 0;
		siftdown(0);
		int x = vetor[0];
		return x;
	}

	// swap value upwards
	public void siftup(int idx) {
		if (idx > 0) {
			
			int parent = (idx - 1) / 2;
			while (vetor[idx] < vetor[parent]) {
				int tmp = vetor[parent];
				vetor[parent] = vetor[idx];
				vetor[idx] = tmp;
				idx = parent;
				parent = (idx - 1) / 2;
			}
		}
	}

	// swap value downwards
	public void siftdown(int idx) {
			int leftChild = 2 * idx + 1;
			int rightChild = 2 * idx + 2;
			
			int minIndex = rightChild;
			if(rightChild >= hSize && leftChild >= hSize) {
				return;
			} else if(rightChild >= hSize) {
				minIndex = leftChild;
			} else if (vetor[leftChild] < vetor[rightChild]) {
				minIndex = leftChild;
			} 
			if (vetor[idx] > vetor[minIndex]) { // troca
				int tmp = vetor[idx];
				vetor[idx] = vetor[minIndex];
				vetor[minIndex] = tmp;
				siftdown(minIndex);
			} 
	}
	
	private void troca(int idx, int minIdx)
	{
		int tmp;
		tmp = vetor[idx];
		vetor[idx] = vetor[minIdx];
		vetor[minIdx] = tmp;
	} 

	@Override
	public String toString() {
		return Arrays.toString(vetor);
	}
	
	public static void main(String[] args) {
		Ab heap = new Ab(7);
		
		heap.inserir(35);		
		heap.inserir(1);		
		heap.inserir(14);		
		heap.inserir(9);
		heap.inserir(2);
		heap.inserir(0);
		heap.inserir(5);
		
		System.out.println(heap);
		
		while (heap.hSize > 0)  {
			System.out.println("valor min: " + heap.delete_min());
		}

	}
}