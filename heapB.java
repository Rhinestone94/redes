package heap;

import java.util.*;
import heap.Rede.Grafo;
import heap.Rede.Arco;

public class heapB {

		static class No_Nopred{
	        int  no;  
	        int pred;
	        int chave;
	        
	        // construtor 
	        /*public No_Nopred(int no, int pred, int chave) {
	        	this.no = no;
	        	this.pred = pred;
	        	this.chave = chave;
	        }*/

			
	       
	    }
		
	    static class MinHeap{
	        ArrayList<No_Nopred> heap;
	        int cont = 0; 
	        
	        
	        //construtor da MinHeap
	        public MinHeap() {
	            heap = new ArrayList<>();
	            
	        }
	        
	        // insere
	        public void insert(No_Nopred x) {
	        	  
	            heap.add(x); // insere no ultimo 
	            siftup(heap.indexOf(x));
	            cont++;
	        }
	        
	        
	        // find_min
	        public No_Nopred find_min() {
	            No_Nopred min = heap.get(0); // vai a posicao 0 e faz get 
	            return min; // retorna 
	        }
	        
	        
	        
	        // delete_min
	        public int delete_min() {
	        	
	        	int x ;
	        	x = heap.get(0).chave; // primeira posicao da heap, à conta da chave
	        	
	            No_Nopred ultimo = heap.get(heap.size()-1);
	            heap.set(0, ultimo);
	            heap.remove(heap.size()-1); // decrementa a heap
	            siftdown(0);
	            
	            return x;  
	        }
	        
  
	        
	        // siftup
	        public void siftup(int k) { 
	            
	            int pos_no = k;
	            int pos_pred = (k-1) / 2; // minimo da heap esta em 1
	            
	            while(pos_no > 0 &&  heap.get(pos_no).chave < heap.get(pos_pred).chave){
	                swap(pos_no, pos_pred);
	                
	                //atualiza 
	                pos_no = pos_pred;
	                pos_pred = (pos_pred - 1) / 2;
	            }
	        }
  
	        public void siftdown(int k) {
	            int minchild = k;
	            
	            int leftChild = 2 * k +1; // esquerda +1
	            int rightChild = 2 * k +2; // direita +2
	            
	            if(leftChild<heap.size() && heap.get(minchild).chave>heap.get(leftChild).chave){    
	                minchild = leftChild;
	            }
	            if (rightChild < heap.size() && heap.get(minchild).chave > heap.get(rightChild).chave){
	                minchild = rightChild;
	            }
	            
	            if (minchild != k){ 
	                swap(k, minchild);
	                siftdown(minchild); 
	            }
	        }
	        
	        
	        // Troca
	        public void swap(int a, int b) {
	           No_Nopred temp = heap.get(a);
	           heap.set(a, heap.get(b));
	           heap.set(b, temp);
	        }
	        
	        
	        // decrease-key
	        public void decrementa_chave(int value, No_Nopred x){ 
	        	int indice1 = heap.indexOf(x); 
	        	
	        	// quer ir à chave mudar o valor e depois meter no sitio
	        	No_Nopred no = heap.get(indice1); // Obtem o nó e atualiza o valor da chave
	        	no.chave = value;
	        	
	        	siftup(indice1); 
	        }
	        
	            
	     // método PRINT
	    	public void print()
	        {
	    		System.out.print("Heap ordenada: ");
	            for (int i = 0; i < heap.size(); i++) {
	                
	                //System.out.print(heap.get(i).chave); // vai buscar o objeto com indice i 
	                System.out.print("(" + heap.get(i).no + ", " + heap.get(i).pred + ", chave: " + heap.get(i).chave + ")");
	                System.out.print(" ");
	            } 
	            
	        }
	    	
	    }
	 	       
    
	public static void main(String[] args) {
		
		heapB.MinHeap heapp = new heapB.MinHeap(); // objecto heap 
		
		/*No_Nopred np1 = new No_Nopred(5, -1, 35);
		No_Nopred np2 = new No_Nopred(1, -1, 1);
		No_Nopred np3 = new No_Nopred(-15, -1, 14);
		No_Nopred np4 = new No_Nopred(-31, -1, 9);
		No_Nopred np5 = new No_Nopred(-19, -1, 2);
		No_Nopred np6 = new No_Nopred(-14, -1, 3);
		No_Nopred np7 = new No_Nopred(-10, -1, 5);
		
		heapp.insert(np1); // insere na heap
		heapp.insert(np2);
		heapp.insert(np3);
		heapp.insert(np4);
		heapp.insert(np5);
		heapp.insert(np6);
		heapp.insert(np7);*/
		
		heapp.print();
		
		
		System.out.println();
		System.out.println();
		
		int zr;
		//No_Nopred zt;
		
		
		
		for(int i = 0; i < heapp.cont ; i++) // conta ate tamanho da heap
		{
			//zt = heapp.del_min();
			
			zr = heapp.delete_min();
			
			System.out.println(zr);
			//System.out.println(zt);
			heapp.print();
			System.out.println();
		}
	}
}
		