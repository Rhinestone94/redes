package Proj;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Proj.HeapB.No_pred;
import Proj.HeapB;


public class Rede {

	int numNos;  // nº de nós na rede
	LinkedList<Arco>[] adjList;  //Lista de adjacências



	No_pred d [] = new No_pred[numNos]; // arvore dos caminhos mais curtos
	No_pred no_min_ch = new No_pred();
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
	        
	/*public void Dijkstra(int no_fonte) { // nó fonte - source		    	  		    

		// cria heap H
		HeapB h = new HeapB(); 


		No_pred arvore [] = new No_pred[numNos];		

		// Muda o valor da chave do nó fonte | d(s) = 0 ; pred(s) = 0
		no_min_ch.no = no_fonte;
		no_min_ch.pred = 0;
		no_min_ch.chave = 0;

		// insere primeiro elemento na heap ; com chave = 0 e pred = 0
		h.insert(no_min_ch); // insert(s,H)

		int d [] = new int [numNos]; //  "d's do algoritmo" 
		
		
		//Initialize all distance values as INFINITE
		for (int j = 1; j < numNos; j++ ) {

			//d[j] = j;
			d[j] = INF;
		}
		// Assign distance value as 0 for the source vertex	           	          
		d[no_fonte] = 0;   
		
		int cont = 0;
		
		
		
		//  Enquanto a heap nao está vazia
		while(!h.heap.isEmpty()) { // 

			// delete-min ;  procurar o nó pelo .no  - nó de minima chave  
			no_min_ch = h.find_min();
			h.delete_min();
	
			
			// colocar na arvore		        	  
			//arvore[cont] = no_min_ch; 
			//cont++;


			

			// percorrer lista de adjacencias do nó i, A(i) arcos incidentes no nó i

			LinkedList<Arco> lista = adjList[no_min_ch.no];        	  

			for(int i = 0; i < lista.size(); i++) {
				Arco a = lista.get(i);
				int j = a.no2; 

				
				int ii = no_min_ch.no; // procura nó i pelo nó 
				int valor = arvore[ii].chave + a.custo ; 

				if(d[j] > valor) {
					if(d[j] == INF) {
						d[j] = valor;
						no_min_ch.no = j;

						no_min_ch.pred = no_min_ch.no;
						no_min_ch.chave = valor;

						h.insert(no_min_ch);
					}
					else {
						no_min_ch.no = j;
						no_min_ch.pred = no_min_ch.no;
						no_min_ch.chave=valor;

						h.decrementa_chave(valor, no_min_ch);

					}
				}

			}

		} 


		printDijkstra(arvore, no_fonte);

	}*/
		        	  	        	 
//========================================== DIJKSTRA ====================================
	        
	public void Dijkstraa(int no_fonte) {  
		// 1 - cria heap H
		HeapB h = new HeapB(); // create_heap(H)

		No_pred d [] = new No_pred[numNos];	

		// 2 - muda o valor da chave dos j's E N    		
		for (int j = 1; j < numNos ; j++) {
			d[j] = new No_pred();
			d[j].no = j;
			d[j].chave = INF;
		}


		//3 - Muda o valor da chave do nó fonte | d(s) = 0 ; pred(s) = 0
		d[no_fonte].chave = 0;
		d[no_fonte].pred = 0;



		// 4 - insere primeiro elemento na heap ; chave =0 e pred = 0
		h.insert(d[no_fonte]); // insert(s,H)


		// 5 - Enquanto a heap nao está vazia
		while(!h.heap.isEmpty()) {

			// 6 - find-delete-min ;  procurar o nó pelo .no  - nó de minima chave  
			No_pred no_min_chave = h.find_min();  
			h.delete_min();
			int ii = no_min_chave.no; // procura nó i pelo nó 


			// 7 - percorrer lista de adjacencias do nó i, A(i) arcos incidentes no nó i

			LinkedList<Arco> lista = adjList[no_min_chave.no];        	  

			for(int i = 0; i < lista.size(); i++) {
				Arco a = lista.get(i);
				int jj = a.no2; // jj = j

				int valor = d[ii].chave + a.custo;

				if(d[jj].chave > valor) {
					if(d[jj].chave == INF) {
						d[jj].chave = valor;
						d[jj].pred = ii;
						h.insert(d[jj]);
					}
					else {

						d[jj].chave = valor;
						d[jj].pred = ii;
						h.decrementa_chave(valor, d[jj]);

					}
				}

			}

		} 


		printDijkstra(d, no_fonte);
		caminhos_e_custo(d, no_fonte);  	

	}

	//================================================= FIM Dijkstra ==============0



	// adiciona arco
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

			System.out.print("O arco (" + noOrigem + "," + noDestino + ") existe ...");	            	
		}
		else {       	
			System.out.print("O arco (" + noOrigem + "," + noDestino + ") não existe ...");
		}

	}


	public void printDijkstra(No_pred[] cust, int s){
		System.out.println("");
		System.out.println("=======================================================================");
		System.out.println("");
		System.out.println("Dijkstra");
		for (int i = 1; i < numNos; i++) {
			System.out.println("Do nó " + s + " até ao nó " + i + " o caminho mais curto tem custo:  " + cust[i].chave);
		}
	}

	public void caminhos_e_custo(No_pred [] c, int origem) {
		System.out.println("=======================================================================");
		System.out.println("");
		System.out.println("Árvore dos caminhos mais curtos desde o nó " + origem + ":");
		System.out.println("");
		for (int i = 1; i < numNos; i++) {
			System.out.println("O caminho mais curto desde o nó " + origem + " até ao nó " + i  + " é: " );
			if(origem == i) { 
				// para o nó fonte / origem
				System.out.println("(" + origem + ")" + " e tem custo " + c[i].chave);
				System.out.println("");
				// para o resto dos nós
			} else {
				System.out.println(origem + "->" + i + " e tem custo " + c[i].chave);

				System.out.println("");
			}	
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
	    
	    
	        