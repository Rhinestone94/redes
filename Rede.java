package Proj;

import java.util.LinkedList;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Proj.HeapB.No_pred;
import Proj.Arco;


public class Rede {

	int numNos;  // nº de nós na rede
	LinkedList<Arco>[] adjList;  //Lista de adjacências
	
	// solução do dijkstra
	ArrayList<No_pred> arvore = new ArrayList<>(numNos); // guarda na arraylist

	
	//para guardar o caminho de menor custo desde o nó fonte até ao nó destino
	ArrayList<Arco> caminho; // variavel, estrutura, onde vai guardar o caminho desde um certo nó ao outro
	
	
	// para o Prim
	ArrayList <No_pred> Tree;
	
	
	// variáveis auxiliares
	ArrayList<Integer> folhas; // método EFolha
	ArrayList<No_pred> arcoAdicionado;
	
	// para o 2Edge
	ArrayList <No_pred> F; // Tree + links que vão sendo adicionados
	ArrayList <No_pred> M; // links cujas capacidades de backup estão definidas
	ArrayList <No_pred> Ciclo; // Ciclo
	

	
	private int INF = Integer.MAX_VALUE;
		
	
	public void setCaminho(ArrayList<Arco> caminho) {
		this.caminho = caminho;
	}
	
	
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
	// devolve o conjunto dos Predecessores de cada nó à origem - que permite a construção da arvore dos caminhos de menor custo
	public void Dijkstra(int no_fonte) {
		
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

			// 6 - guarda na variável no_min o nó de min chave ;   
			No_pred no_min_chave = h.find_min();  // guarda nó de minima chave
			h.delete_min();
			
			arvore.add(no_min_chave); 
			

			// 7 - percorrer lista de adjacencias do nó i, A(i) arcos incidentes no nó i       	  

			for(int i = 0; i < adjList[no_min_chave.no].size(); i++) {
				Arco a = adjList[no_min_chave.no].get(i);
				int j = a.destino; 

				int valor = d[no_min_chave.no].chave + a.custo;

				if(d[j].chave > valor) {
					if(d[j].chave == INF) {
						d[j].chave = valor;
						d[j].pred = no_min_chave.no;
						h.insert(d[j]);
					}
					else {

						d[j].chave = valor;
						d[j].pred = no_min_chave.no;
						h.decrementa_chave(valor, d[j]);
					}
				}
			}	
		} 	
		
		
		System.out.println("");
		// devolve o caminho de cada nó À origem e o custo total do caminho de cada nó à origem	
	}

	//================================================= FIM Dijkstra ==============

	
	
	// ================================ Método caminho de menor custo ==========
	public void caminhoMenorCusto(int origem, int destino) {
		
		caminho = new ArrayList<Arco>(); // inicializa caminho
		
		for(int i = 0; destino != origem && destino > 0; i++) { 
			
			int aux = destino; //variavel aux para poder ser utilizada no filtro
			No_pred arcoFinal = this.arvore.stream().filter(a -> a.no == aux).findFirst().get();
			
			
			Arco arco = new Arco(arcoFinal.pred, arcoFinal.no, arcoFinal.chave);
			destino = arcoFinal.pred;
			caminho.add(0, arco); // coloca arco no caminho
		
		}
		
		//Collections.reverse(caminho); //inverte a ordem dos elementos (caso meta add(1,arco))
		this.setCaminho(caminho); // atualiza e mete na variável caminho da Rede
		
	}
	
	
	// ================================== FIM do Método caminho menor custo =================
	

	// ====================================== Algoritmo de Prim =============================
	
	
	
	public void Prim(int no_fonte) {

		HeapB h = new HeapB(); // create_heap(H)
		
		No_pred no [] = new No_pred[numNos];

		for (int j = 1; j < numNos; j++) {  
			no[j] = new No_pred();
			no[j].no = j;
			no[j].chave = INF; // d[j] = C + 1 ou seja,  INF
					
			h.insert(no[j]); // for each j E N  -> insere na heap

		}
			
		// d(1) = 0 ; pred(1) = 0 , ou seja, o nó fonte
		
		no[no_fonte].chave = 0;

		h.insert(no[no_fonte]); // insere na heap o nó de minima chave

		Tree = new ArrayList<>(); // inicializa T
		F = new ArrayList<>();

		//Collections.reverse(h.heap);
		
		
		while(Tree.size() < numNos) { // enquanto T é inferior ao nº de nós
			No_pred no_min_chave = h.find_min();
			h.delete_min();

			Tree.add(no_min_chave); 
			
			for(int i = 0; i < adjList[no_min_chave.no].size(); i++) {
				Arco arco = adjList[no_min_chave.no].get(i);
				int j = arco.destino; 

				if(h.heap.contains(no[j])) { // j E H
					
					if(no[j].chave > arco.custo) { 
						no[j].chave = arco.custo; // d(j) = cij
						no[j].pred = no_min_chave.no; // pred(j) = i
						h.decrementa_chave(arco.custo, no[j]);
					}
				}

			}

		}
		F = Tree; // arrayList F para ser usado no método 2-EDGE , ie, para adicionar o arco i e formar ciclo
	}
	// ====================================== FIM Prim =============================
	
	
	// ==================================== 2-EDGE =============================

	
	// metodo auxiliar - arcos não comuns
		public ArrayList<No_pred> Diff(LinkedList<Arco>[] arcosRede, ArrayList<No_pred> arcosTree){
			ArrayList<No_pred> resultado = new ArrayList<>();

			for (int i = 1; i < arcosRede.length; i++) {
				for (Arco arco : arcosRede[i]) {
					final No_pred aux = converteArcoParaNoPred(arco);
					int cont = 0;
					for (No_pred no_pred : arcosTree) {
						if (noPredIguais(aux, no_pred)) {
							cont++;
						}
					}

					if (cont == 0) {
						resultado.add(aux); // adiciona arco 
					}

				}
			}

			resultado = removeDuplicados(resultado); // removeDuplicados

			return resultado; // retorna um arraylist com os arcos que  n pertecem a arvore T
	   }

	   private ArrayList<No_pred> removeDuplicados(ArrayList<No_pred> no_preds) {
			ArrayList<No_pred> SemDuplicados = new ArrayList<>();

			for (No_pred no_pred1 : no_preds) {
				if (SemDuplicados.contains(no_pred1)) {
					continue;
				}
				for (No_pred no_pred2 : no_preds) {
					if (noPredSimetricos(no_pred1, no_pred2)) {
						SemDuplicados.add(no_pred2);
					}

				}
			}

			return SemDuplicados;
	   }

	   private boolean noPredIguais(No_pred a, No_pred b) {
			return ((a.pred == b.pred) && (a.no == b.no) && (a.chave == b.chave));
	   }

		private boolean noPredSimetricos(No_pred a, No_pred b) {
			return ((a.pred == b.no) && (a.no == b.pred) && (a.chave == b.chave));
		}


	   private No_pred converteArcoParaNoPred(Arco arco) {
		   No_pred aux = new No_pred();
		   aux.pred = arco.origem;
		   aux.no = arco.destino;
		   aux.chave = arco.custo;

		   return aux;
	   }
	   
	   
	   public void EFolha(ArrayList<No_pred> Tree) {

		   folhas = new ArrayList<>();
		   //int contador;

		   for(int i = 0 ; i < Tree.size(); i++) {
			   int contador = 0;
			   int no = Tree.get(i).no;
			   for (int j = 0 ; j < Tree.size(); j++) {

				   if(no == Tree.get(j).pred) {
					   contador = 1;
				   }	   
			   }
			   if (contador == 0) {

				   folhas.add(no);
			   }
		   }
	   }   
		   
	   
	   // método 2EDGE
	   public void Edge() {

		   ArrayList<No_pred> arcosNpertencentes = Diff(this.adjList, this.Tree); // guarda os arcos n pert

		   for (No_pred np : arcosNpertencentes) { // arcos i Ñ pertencentes a T
			   int v1 = np.no; // e = (v1,v2)
			   int v2 = np.pred;

			   int cont = 0;
			   for (int j = 0; j < folhas.size(); j++) {
				   int aux1 = folhas.get(j);
				   int aux2 = folhas.get(j);
				   if(v1 == aux1 || v2 == aux2)
				   {
					   cont++;
					   if(cont == 2)
					   {
						   F.add(np);
						   // ..... for()
						   
						   
						   // .workingC = (np.chave) / 2;
						   // .protectionC = .chave / .workingC;
						   //M.add(x);
					   }
				   }
			   }
			   if(cont != 2)
			   {
				   np.workingC = np.chave;
				   np.protectionC = 0;
			   }	
		   }
	   }
			  

	/*public void prim2edge(Rede graph) { //necessário passar como parâmetro a rede para supostamente comparar

		M = new ArrayList<>(); // M inicialmente vazia


		for (int i = 1; i < graph.numNos ; i++) {
			for (int j = 1; i < Tree.size(); j++) {

				if ((graph.adjList[i].get(i).origem == graph.Tree.get(j).no && graph.adjList[i].get(i) .get(j).pred == Tree.get(j).pred ) || (rede .get(j).no == Tree.get(j).pred && rede .get(j).pred == Tree.get(j).no)) {

					
					for (Arco a : graph.adjList[i]) {
						if(a.origem ==  && a.destino == ) {
							
							
						}
					}
					if(se v1 e v2 são nós raiz) {
						Tree.add(arco); // F = F + {ei}

						// é formado um ciclo C , que inclui os arcos da árvore + o arco i

						//for(para cada arco que pertence ao ciclo C  MENOS os que estão em M, inicialmente a 0) {
						for(Arco e : ) {
							.workingC = .chave / 2; // w = u/2
							ao. = .chave - .workingC; // p = u - w
							M_Tree.add(arco); // M = M + e

						}
					}
					else
					{
						.workingC = .chave;
						.protectionC = 0;
					}
				}
			}
		}

	}*/
	
	// ===================================== FIM 2-Edge ================================
	
	
	
	// adiciona arco
	public void adicionaArco(int n1, int n2, int c) {
		Arco arco = new Arco(n1, n2, c);
		adjList[n1].add(arco);

		arco = new Arco(n2, n1, c); // adiciona arco - grafo não dirijido
		adjList[n2].add(arco);
	}


	// método que imprime a Lista de Adjacencias do Grafo
	public static void imprimeListaAdj(Rede graph) {
		for (int i = 1; i < graph.numNos ; i++) {
			System.out.println("");
			System.out.println(i + ":");


			for (Arco e : graph.adjList[i]) {
				System.out.print("(" + e.origem + "," + e.destino + "," + e.custo + ")" );

			} System.out.println("");
		} 
	}

	
	// Método para saber se um dado arco no grafo existe
	public static void Existe_arco(Rede graph, int noOrigem, int noDestino){

		int contador = 0;
		for(int v = 1; v < graph.numNos; v++){

			for (Arco e : graph.adjList[v]){ 
				if(e.origem == noOrigem & e.destino == noDestino)
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


} // fim da classe Rede
	    
	        