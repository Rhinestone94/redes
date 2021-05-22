package heap;

import java.util.Arrays;
import java.util.*;
//import java.io.*;


public class heapB {

	public int [] vetor;
	public int heapSize; // Nº elementos

	
	public heapB(int tamanho) {
		vetor = new int[tamanho];
		this.heapSize = 0; // inicialmente tem 0 elementos
	}
	

	// insert e troca (faz swap)
	public void inserir(int elemento) {
		heapSize++; // incrementa ultimo por 1
		vetor[heapSize -1] = elemento;
		siftup(heapSize -1); 
	}
	
	
	public int delete_min() {
		
		//vetor[0] = vetor[heapSize];
		
		int x ;
		x = vetor[0];
		vetor[0] = vetor[heapSize -1] ;
		vetor[heapSize -1] = 0;
		heapSize--; // decrementa a heap
		siftdown(0);
		
		return x;
	}

	
	public void siftup(int k) {
		if (k > 0) { 
			
			int parent = (k - 1) / 2;
			
			while (vetor[k] < vetor[parent]) {
				int tmp = vetor[parent];
				vetor[parent] = vetor[k];
				vetor[k] = tmp;
				k = parent;
				parent = (k - 1) / 2;
			}
		}
	}

	
	public void siftdown(int k) {
			int leftChild = 2 * k + 1; // esquerda +1
			int rightChild = 2 * k + 2; // direita +2
			
			int minIndex = rightChild;
			
			if(rightChild >= heapSize) {
				minIndex = leftChild;
			} else if (vetor[leftChild] < vetor[rightChild]) {
				minIndex = leftChild;
			} 
			
			
			while(minIndex < heapSize) { // faz troca
				
			
			if (vetor[k] > vetor[minIndex]) { // faz a troca
				int tmp = vetor[k];
				vetor[k] = vetor[minIndex];
				vetor[minIndex] = tmp;
				//siftdown(minIndex);
			} 
			
			k = minIndex;
			leftChild = 2 * k + 1; // esquerda +1
			rightChild = 2 * k + 2; // direita +2
			
			minIndex = rightChild;
			
			
			if(rightChild >= heapSize) {
				minIndex = leftChild;
			} else if (vetor[leftChild] < vetor[rightChild]) {
				minIndex = leftChild;
			} 
		}
	} 
	

	
	public String toString() {
		return Arrays.toString(vetor);
	}
	

	// metodo print
	public void print() {
			
	}
	
	
	
	//===================================== Classe No_Nopred ==============================

		public class No_Nopred {
			int no;
			int noPred; // nó antecessor - predecessor
			int valor; //  value
		}
		
		
		public class MinHeap{
			ArrayList <No_Nopred> heap;
			
			// construtor
			public MinHeap() {
				heap = new ArrayList<>();
				
			}
			
			
			// insert(i,H). We increment last by one and store the new node i at the last 
			//position of the array DHEAP. Then we execute the procedure siftup(i)
			
			public void insere(No_Nopred elemento) { 
				
				heap.add(elemento); // incrementa e insere na ultima posicao da heap 
				siftup(heap.indexOf(elemento)); // faz siftup
				
			}
			
			// find-min(i,H). The root node of the heap is the node with the minimum key 
			//and it is located at the first position of the array DREAP
			
			public No_Nopred find_min() {
				
	            No_Nopred min = heap.get(0);
	            return min;
			}
			
			
			//Clearly, node i is the root node of the heap. Let node j be 
			//the node stored at the last position of the array DHEAP. We first perform 
			//swap(i,j) and then decrease last by 1
			
			// delete-min(i,H)
			public void del_min() {
	            No_Nopred ultimo = heap.get(heap.size() - 1); //obtem ultimo nó
	            
	            heap.set(0, ultimo); // atualiza elemento com indice 0
	            heap.remove(heap.size()-1); 
	            siftdown(0);
	        }
			
			
			
			// decrease-key. We decrease the key of node i and execute the 
			//procedure siftup(i) 
			public void decrementa_chave(int value, No_Nopred no){
		            int indice = heap.indexOf(no);
		            
		            //Obtem o no e atualiza o value
		            No_Nopred no_Origem = heap.get(indice);
		            
		            no_Origem.valor = value;
		            siftup(indice); 
		        }
			
				
		}
		
		// ======================================= Dijkstra ============================= 

		public int Dijkstra(int No_fonte, ArrayList arr){
			 
	            int INF = Integer.MAX_VALUE;
	            int NumNos_dijkstra; // nº de nós do Dijkstra
	            
	            // BEGIN
	            
	            //Cria Heap
	            MinHeap minHeap = new MinHeap(); //create_heap(H)
	            
	            
	            //d(j) = INF 
	            //No_Nopred [] No = new No_Nopred[NumNos_dijkstra ]; // + 1
	            
	            
	          /*  for (int i = 0; i <NumNos_dijkstra ; i++){
	                No[i] = new No_Nopred();
	                No[i].no = i;
	                No[i].valor = INF;
	            }
	            
	            //Muda o valor da chave do no fonte para zero 
	            No[No_fonte].valor = 0;
	            
	            //o primeiro elemento a ser inserido na minheap tem pos_pred = 0
	            minHeap.insere(No[No_fonte]); //insert(s,H)*/
	            
	            
	            
	            // while H !=0 , ou seja, enquanto a heap não está vazia
	            while(!minHeap.heap.isEmpty()){
	            	
	                //encontra o minimo
	                No_Nopred No_min_valor = minHeap.find_min(); //find_min(i,H)
	                
	                //remove o nó com a chave minima
	                minHeap.del_min(); //delete_min(i,H)
	                           
	                int no_retirado = No_min_valor.no;
	                         
	                //LinkedList <Arco> lista = adjList_dijkstra[no_retirado]; //for each (i,j) 
	                
	                
	              
	            }
	                       
	        }

	       
	    // ================================ FIM Dijkstra ============================== 

	
	
	public static void main(String[] args) {
		heapA heap = new heapA(7);
		
		heap.inserir(35);		
		heap.inserir(27);		
		heap.inserir(44);		
		heap.inserir(9);
		heap.inserir(2);
		heap.inserir(3);
		heap.inserir(2);
		
		System.out.println("heap: " + heap);
		
		System.out.println("");
		
		
		int zr;
		
		while(heap.heapSize > 0) {
			//System.out.println("passou ..");
			zr = heap.delete_min();
			
			
			System.out.println(zr);
			System.out.println("Heap: " + heap);
			
		}
		
		  
		
	}
  
}