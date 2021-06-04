package heap;

import java.util.*;
import heap.Rede;
import heap.Rede.Arco;

public class HeapB {
    
	        ArrayList<No_pred> heap;
	         
	        	        
	        //construtor da Heap
	        public HeapB() {
	            heap = new ArrayList<>();
	            
	        }
	        
	        // insere
	        public void insert(No_pred x) {
	        	  
	            heap.add(x); // insere no ultimo 
	            siftup(heap.indexOf(x));    
	            
	        }
	        
	        
	        // find_min
	        public No_pred find_min() {
	            No_pred min = heap.get(0); // vai a posicao 0 e faz get 
	            return min; // retorna 
	        }
	        
	        
	        
	        // delete_min
	        public No_pred delete_min() {
	        	
	        	No_pred ultimo ;
	        	ultimo = heap.get(0); // primeira posicao da heap, � conta da chave
	        	
	            ultimo = heap.get(heap.size()-1);
	            heap.set(0, ultimo);
	            heap.remove(heap.size()-1); // decrementa a heap
	            siftdown(0);
	            
	            return ultimo;
	            
	        }
	        
	        
	        public void del_min() {
	        	
	        	//int x ;
	        	//x = heap.get(0).chave; // primeira posicao da heap, � conta da chave
	        	
	            No_pred ultimo = heap.get(heap.size()-1);
	            heap.set(0, ultimo);
	            heap.remove(heap.size()-1); // decrementa a heap
	            siftdown(0);
	            
	            //return x;  
	        }
	        
	        
  
	        
	        // siftup
	        public void siftup(int k) {  
	            
	            int pos_no = k;
	            int pos_pred = (k-1) / 2; 
	            
	            while(pos_no > 0 &&  heap.get(pos_no).chave > heap.get(pos_pred).chave){ // mudei o sinal
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
	            
	            if(leftChild < heap.size() && heap.get(minchild).chave > heap.get(leftChild).chave){    
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
	           No_pred temp = heap.get(a);
	           heap.set(a, heap.get(b));
	           heap.set(b, temp);
	        }
	        
	        
	        // decrease-key
	        public void decrementa_chave(int value, No_pred x){ 
	        	int indice1 = heap.indexOf(x); 
	        	
	        	// quer ir � chave mudar o valor e depois meter no sitio
	        	No_pred no = heap.get(indice1); // Obtem o n� e atualiza o valor da chave
	        	no.chave = value;
	        	
	        	siftup(indice1); 
	        }
	        
	            
	     // m�todo PRINT
	    	public void print()
	        {
	    		System.out.print("Heap ordenada: ");
	            for (int i = 0; i < heap.size(); i++) {
	                 
	                System.out.print("(" + heap.get(i).no + ", " + heap.get(i).pred + ", chave: " + heap.get(i).chave + ")");
	                System.out.print(" ");
	            } 
	            
	        }
	    	static class No_pred{
		        int  no;  
		        int pred;
		        int chave;	       
		    }
	    	
	    }
	    
	   




		