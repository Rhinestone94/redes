package heap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import heap.heapB.No_Nopred;
import heap.Rede.Arco;
import heap.heapB.MinHeap;


public class Rede {
	
	    static class Arco { 
	        int no1; //nó origem
	        int no2; // nó destino
	        int custo; // custo
	        
	        //construtor da classe
	        public Arco(int origem, int destino, int custo) {
	            this.no1 = origem;
	            this.no2 = destino;
	            this.custo = custo;
	            }
	    }
	   
	    static class Grafo{
	        int NumNos;  // nº de nós no grafo
	        LinkedList<Arco>[] adjList;  //Lista de Adjacencias
	        
	       
	        
	        //construtor da classe Grafo
	        public Grafo(int v) {
	            this.NumNos = v+1; // mais 1 por causa dos indices baterem certo com o numero dos nos
	            adjList = new LinkedList[NumNos];
	            
	            
	            //Inicializaçao da lista de adjacencias para todos nos
	            for (int i = 0; i < NumNos  ; i++) {
	                adjList[i] = new LinkedList<>();
	            }
	        }

	        
//========================================== DIJKSTRA ====================================
	        
	        public int Dijkstra(int noOrigem, LinkedList<Arco>[] list) { // 
		    	  int INF = Integer.MAX_VALUE; // para d(j)
		    	  int soma = 0 ;
		    	  
		    	  
		    	  // cria heap
		    	  MinHeap h = new MinHeap(); // create_heap(H)
		    	  
		    	  
		    	  // d(j) = oo
		    	  No_Nopred No [] = new No_Nopred[NumNos +1];
		    	  
		    	  
		    	  for(int v = 1; v < list.length ; v++){
			            System.out.println("");
			            System.out.println("Ligações do nó "+ v);
			            
			            for (Arco e : list[v]){ // for each - para cada posicao da lista de adjacencias
			                System.out.println("Para o nó "+ e.no2 +" com custo: " + e.custo);
			            }
			        }
		    	  
		    	  
		    	  
		    	  for (int i = 0; i < NumNos +1; i++){  
		    		  	
		    		  	No[i] = new No_Nopred(); // não inicializar?
		    		    No[i].no = i;
		                No[i].chave = INF; // d(j) =oo para 
		          }
		    	  

		    	  //Muda o valor da chave do nó | d(s) : = 0
		          No[noOrigem].chave = 0;
		          
		          // o primeiro elemento a ser inserido na heap tem pred = 0
		          h.insert(No[noOrigem]); // insert(s,H)
		            
  
		          //Enquanto a heap nao esta vazia
		          while(!h.heap.isEmpty()) {
		        	  
		        	  // nó com chave minima
		        	  No_Nopred min_chave = h.find_min(); //find_min(i,H)
		        	  
		        	  System.out.println("");
		        	  System.out.println("Min chave: " + min_chave.no);
		                
		        	  
		        	  h.delete_min(); //delete_min(i,H)
		        	  
		        	  
		        	  int no_removido = min_chave.no;
		              
		              //LinkedList<Arco> lista = adjList[no_removido];  // criar sempre a lista de adj?
		              
		              // for each (i,j) E A(i) -> Lista de adjacencias
		              
		              for(int i = 0; i < list[noOrigem].size(); i++) {
		            	  
		            	  Arco e = list[noOrigem].get(i);
		            	  int destino = e.no2;

		            	  int value; // value = d(i) + custo(ij)
		            	  value = No[no_removido].chave + e.custo;
		            	  
		            	  
		            	  // if d(j) > value
		            	  
		            	  if(No[destino].chave > value) {
		            		  
		            		  if(No[destino].chave == INF) { // d(j) = oo
		            			  
		            			  No[destino].chave = value; // d(j) = value
		            			  No[destino].pred = no_removido; 
		            			  h.insert(No[destino]); // insert(j,H)
		            		  }
		            		  
		            		  else {
		            			  
		            			  No[destino].chave = value; //d(j)=value
	                              No[destino].pred = no_removido; //pred(j)=i
	                              h.decrementa_chave(value, No[destino]); //decrease_key(value,i,H) 
		            		  }
		            	  }
		            	 		            	  	            	  
		              }

		          
		          } // fim ciclo while  
		          
		          for (int i = 1 ; i < NumNos +1; i++){
		                if (No[i].chave != INF){
		                soma = soma + No[i].chave;
		                }
		            }
		            

		            
		         printDijkstra(No, noOrigem);

		         return soma;
		         
		      } // FIM Dijkstra
	        
	        
	        
	        //Metodo do grafo para adicionar os arcos
	        public void adiciona_Arco(int src, int dest, int cust){
	        adjList[src].add(new Arco (src, dest, cust));
	        adjList[dest].add(new Arco (dest, src, cust)); //adiciona arco - grafo não dirijido
	    }
	         
	        
	        
	        
	        //metodo para imprimir o grafo
	        public static void Imprime_Grafo(Grafo graph){
	            for(int v = 1; v < graph.NumNos; v++){
	            System.out.println("");
	            System.out.println("Ligações do nó "+ v);
	            
	            for (Arco e : graph.adjList[v]){ // for each - para cada posicao da lista de adjacencias
	                System.out.println("Para o nó "+ e.no2 +" com custo: " + e.custo);
	            }
	        }
	        System.out.println("\n"); 
	    }
	        
	        
	        
 
	        
	        public static void Existe_arco(Grafo graph, int noOrigem, int noDestino){
	        	
	        	int contador = 0;
	            for(int v = 1; v < graph.NumNos; v++){

	            for (Arco e : graph.adjList[v]){ 
	                if(e.no1 == noOrigem & e.no2 == noDestino)
	                {
	                	contador = 1;                	
	                	break;
	                }
	            }
	         }
	            if (contador == 1) {
	            	
	            	System.out.println("O arco (" + noOrigem + "," + noDestino + ") existe ...");	            	
	            }
	            else {       	
	            	System.out.println("O arco (" + noOrigem + "," + noDestino + ") não existe ...");
	            }
	          
	    }
	              
	        
	        public void printDijkstra(No_Nopred[] cost, int s){
	            System.out.println("");
	            System.out.println("");
	            System.out.println("Dijkstra");
	            for (int i = 1; i < NumNos; i++) {
	                System.out.println("Do nó " + s + " até ao nó " + i + " o custo minimo é:  " + cost[i].chave);
	            }
	        }
	        
	        
	        	   
	   } // fim classe Grafo
	
	    
 
	    LinkedList<Arco>[] adjList; //Lista de adjacências do tipo arco
	    
	    
	    // para o dijkstra
	    //LinkedList<Arco>[] adjList_dijkstra;  
	    
}
	

 
    
