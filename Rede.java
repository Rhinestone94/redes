package heap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import heap.HeapB.No_pred;
import heap.HeapB;


public class Rede {
		 
	        int numNos;  // nº de nós na rede
	        LinkedList<Arco>[] adjList;  //Lista de adjacências
	                
	        //No_pred arvore [] = new No_pred[numNos]; // solução para o dijkstra       
	        int INF = Integer.MAX_VALUE;
	        	        
	        
	        //construtor da classe Rede
	        public Rede(int v) {
	            numNos = v +1; 
	            adjList = new LinkedList[numNos];
	            
	            
	            //Inicializaçao da lista de adjacencias para todos os nos
	            for (int i = 0; i < numNos ; i++) {
	                adjList[i] = new LinkedList<>();
	            }
	                         
	        }
	                
//========================================== DIJKSTRA ====================================
	        
	        public void Dijkstra(int no_fonte) { // nó fonte - source		    	  		    
	        	  
		    	  // 1 - cria heap H
		    	  HeapB h = new HeapB(); // create_heap(H)
		    	  
		    	  No_pred arvore [] = new No_pred[numNos];		    	  
		    	  // 2 - muda o valor da chave dos j's E N    		
		    	  for (int j = 0; j < numNos ; j++) {
		    		  arvore[j] = new No_pred();
		    		  arvore[j].no = j;
		    		  arvore[j].chave = INF;
		    	  }
		    	  
		    	  
		    	  //3 - Muda o valor da chave do nó fonte | d(s) = 0 ; pred(s) = 0
		    	  arvore[no_fonte].chave = 0;
		    	  arvore[no_fonte].pred = 0;
		          	    	  
		          // 4 - insere primeiro elemento na heap ; chave =0 e pred = 0
		          h.insert(arvore[no_fonte]); // insert(s,H)
		            
  
		          // 5 - Enquanto a heap nao está vazia
		          while(!h.heap.isEmpty()) {
		        	  
		        	  // 6 - find-delete-min ;  procurar o nó pelo .no  - nó de minima chave  
		        	  No_pred no_min_chave = h.delete_min(); 
		        	  
		        	  int ii = no_min_chave.no; // procura nó i pelo nó 
		        	  

			          // 7 - percorrer lista de adjacencias do nó i, A(i) arcos incidentes no nó i
		        	  
		        	  LinkedList<Arco> lista = adjList[no_min_chave.no];        	  
		        	  
		        	  for(int i = 0; i < lista.size(); i++) {
		        		  Arco a = lista.get(i);
		        		  int jj = a.no2; // jj = j
		        		  
		        		  int valor = arvore[ii].chave + a.custo;
		        		  
		        		  if(arvore[jj].chave > valor) {
		        			  if(arvore[jj].chave == INF) {
		        				  arvore[jj].chave = valor;
		        				  arvore[jj].pred = ii;
		        				  h.insert(arvore[jj]);
		        			  }
		        				  else {
		        					  
		        					  arvore[jj].chave = valor;
		        					  arvore[jj].pred = ii;
		        					  h.decrementa_chave(valor, arvore[jj]);
	  	  
		        			  }
		        		  }
		        		 	        		  		  
		        	  }
    	      
		          } 
		          
		          
		          printDijkstra(arvore, no_fonte);
		          
		    }
		        	  
		        	 
//========================================== DIJKSTRA ====================================
	        
	        public int Dijkstraa(int noOrigem, LinkedList<Arco>[] arr) { // 
		    	  int INF = Integer.MAX_VALUE; // para d(j)
		    	  int soma = 0 ;
		    	  
		    	  
		    	  // cria heap
		    	  HeapB h = new HeapB(); // create_heap(H)
		    	  
		    	  
		    	  // d(j) = oo
		    	  No_pred arvore [] = new No_pred[numNos ]; // No_Nopred arvore
		    	  
		    	  for (int i = 0; i < numNos ; i++){  
		    		  	
		    		  	arvore[i] = new No_pred();  
		    		  	arvore[i].no = i;
		    		  	arvore[i].chave = INF; // d(j) =oo para 
		          }
		    	  

		    	  //Muda o valor da chave do nó | d(s) : = 0
		    	  arvore[noOrigem].chave = 0;
		    	  arvore[noOrigem].pred = 0;
		          
		          // o primeiro elemento a ser inserido na heap tem pred = 0
		          h.insert(arvore[noOrigem]); // insert(s,H)
		            
  
		          //Enquanto a heap nao esta vazia
		          while(!h.heap.isEmpty()) {
		        	  
		        	  // nó com chave minima
		        	  No_pred min_chave = h.delete_min(); 	        	  
		        	  //h.del_min(); //delete_min(i,H)
		        	  
		        	  
		        	  int no_removido = min_chave.no;
		                   
		              
		              // for each (i,j) E A(i) -> Lista de adjacencias
		              
		              for(int i = 0; i < arr[noOrigem].size(); i++) {
		            	  
		            	  Arco e = arr[noOrigem].get(i);
		            	  int destino = e.no2;

		            	  int value = arvore[no_removido].chave + e.custo; // value = d(i) + custo(ij)
  
		            	  
		            	  // if d(j) > value
		            	  
		            	  if(arvore[destino].chave > value) {
		            		  
		            		  if(arvore[destino].chave == INF) { // d(j) = oo
		            			  
		            			  arvore[destino].chave = value; // d(j) = value
		            			  arvore[destino].pred = no_removido; 
		            			  h.insert(arvore[destino]); // insert(j,H)
		            		  }
		            		  
		            		  else {
		            			  
		            			  arvore[destino].chave = value; //d(j)=value
		            			  arvore[destino].pred = no_removido; //pred(j)=i
	                              h.decrementa_chave(value, arvore[destino]); //decrease_key(value, j,H) 
		            		  }
		            	  }
		            	 		            	  	            	  
		              }

		          
		          } // fim ciclo while  
		          
		          for (int i = 0 ; i < numNos ; i++){
		                if (arvore[i].chave != INF){
		                soma += arvore[i].chave;
		                }
		            }
		            

		            
		         printDijkstra(arvore, noOrigem);

		         return soma;
		         
		      } //=========================================== FIM Dijkstra
	        

	        
	        
	        // adiciona o Arco
	        public void adicionaArco(int n1, int n2, int c) {
	        	Arco arco = new Arco(n1, n2, c);
	        	adjList[n1].add(arco);
	        	
	        	arco = new Arco(n2, n1, c); // adiciona arco - grafo não dirijido
	        	adjList[n2].add(arco);
	        	
	        	
	        }
	         

	       
	        
	        
	        
	        public static void imprimeListaAdj(Rede graph) {
	        	for (int i = 1; i < graph.numNos ; i++) {
	        		System.out.println("");
	        		System.out.println(i + ":");
	        		
	        		
	        		for (Arco e : graph.adjList[i]) {
	        			System.out.print("(" + e.no1 + "," + e.no2 + "," + e.custo + ")" );
	        			
	        		} System.out.println("");
	        	} 
	        }
	        
	        
	        
 
	        
	        public static void Existe_arco(Rede graph, int noOrigem, int noDestino){
	        	
	        	int contador = 0;
	            for(int v = 1; v < graph.numNos; v++){

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
	              
	        
	        public void printDijkstra(No_pred[] cust, int s){
	            System.out.println("");
	            System.out.println("");
	            System.out.println("Dijkstra");
	            for (int i = 1; i < numNos; i++) {
	                System.out.println("Do nó " + s + " até ao nó " + i + " o caminho mais curto tem custo:  " + cust[i].chave);
	            }
	        }
	        
	        
	        
	        public class Arco { 
		        int no1; //nó origem
		        int no2; // nó destino
		        int custo; // custo
		        
		        //construtor da classe
		       public Arco(int no1, int no2, int custo) {
		            this.no1 = no1;
		            this.no2 = no2;
		            this.custo = custo;
		            
		            }
		    }
     	
	        
} // fim da classe Grafo
	   
	      
	    
	    
	         
