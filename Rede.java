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
	ArrayList<Integer> folhas = new ArrayList<>(); // método EFolha



	// para o 2Edge
	ArrayList <No_pred> F; // Tree + links que vão sendo adicionados
	//ArrayList<No_pred> links = new ArrayList<>(); // links que ambos os extremos NÃO são 2-edge

	ArrayList <No_pred> M = new ArrayList<>(); // links cujas capacidades de backup estão definidas

	ArrayList <No_pred> ciclo1; // auxiliar para o extremo 1 do arco
	ArrayList <No_pred> ciclo2; // extremo 2 do arco
	ArrayList <No_pred> CicloFinal = new ArrayList<>();
	
	ArrayList<ArrayList<No_pred>> TotalCiclos = new  ArrayList<ArrayList<No_pred>>();


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

		for (int j = 1; j < numNos ; j++) {  
			no[j] = new No_pred();
			no[j].no = j;
			no[j].chave = INF; // d[j] = C + 1 ou seja,  INF

			h.insert(no[j]); // for each j E N  -> insere na heap

		}

		// d(1) = 0 ; pred(1) = 0 , ou seja, o nó fonte

		no[no_fonte].chave = 0;

		h.insert(no[no_fonte]); // insere na heap o nó de minima chave

		h.heap.remove(h.heap.size() -1);
		//h.delete_min();


		Tree = new ArrayList<>(); // inicializa T
		F = new ArrayList<>();


		while(Tree.size()  < numNos-1 ) { // enquanto T é inferior ao nº de nós
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


	// método para colocar a capacidade de trabalho dos arcos da Tree igual ao "custo"

	public void ColocaWC() {
		for(int i = 0; i < Tree.size(); i++)
		{
			Tree.get(i).workingC = Tree.get(i).chave;
		}
	}

	
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


		return resultado; // retorna uma arraylist com os arcos que n pertecem a arvore T
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
		aux.workingC = arco.custo;

		return aux;
	}


	public void EFolha(ArrayList<No_pred> Tree) {

		//folhas = new ArrayList<>();

		// a Raiz também é folha?
		int noRaiz = Tree.get(0).no;
		int contRaiz = 0;

		for(int k = 1; k < Tree.size(); k++) {

			if(Tree.get(k).pred == noRaiz) {
				contRaiz++;
			} 
		}
		if(contRaiz == 1) { // então adiciona a raiz 
			folhas.add(noRaiz);
		}

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

	
	public ArrayList<No_pred> CalculaCiclo(int v1, int v2, int v3) {

		No_pred nopred = new No_pred();
		nopred.no = v1;
		nopred.pred = v2;
		nopred.chave = v3;


		ciclo1 = new ArrayList<>();
		ciclo2 = new ArrayList<>();
		CicloFinal = new ArrayList<>();


		for (int i = F.size() -1; i > 0; i--)
		{

			if(F.get(i).no == v1)
			{
				ciclo1.add(F.get(i));
				v1 = F.get(i).pred;
				i = F.size() -1;
			}
			if(F.get(i).no == v2)
			{
				ciclo2.add(F.get(i));
				v2 = F.get(i).pred;
				i = F.size() -1;
			} 
		}

		for(int i = 0; i < ciclo1.size(); i++) {
			if(!ciclo2.contains(ciclo1.get(i))){
				CicloFinal.add(ciclo1.get(i));     
			}  
		}
		for(int i = 0; i < ciclo2.size(); i++) {
			if (!ciclo1.contains(ciclo2.get(i))) {
				CicloFinal.add(ciclo2.get(i));
			}      
		} 

		CicloFinal.add(nopred);


		for(int k = 0; k < CicloFinal.size() && nopred.chave != 0; k++) {
			CicloFinal.get(k).workingC = nopred.chave / 2;
			CicloFinal.get(k).protectionC = CicloFinal.get(k).chave - CicloFinal.get(k).workingC;
			if(!M.contains(CicloFinal.get(k))) {
				M.add(CicloFinal.get(k));
			}
		}

		return CicloFinal;
	}

	public void Edge() {

		TotalCiclos = new  ArrayList<ArrayList<No_pred>>();

		// guarda os arcos ñ pertencentes na varivavel
		ArrayList<No_pred> arcosNPert = Diff(this.adjList, this.Tree);

		// arcos NÃO 2-EDGE , ou seja, que entram no ciclo
		ArrayList<No_pred> links = new ArrayList<>();


		for(int k = 0; k < folhas.size(); k++) {
			for(int i = 0; i < arcosNPert.size(); i++) {

				if(arcosNPert.get(i).no == folhas.get(k) || arcosNPert.get(i).pred == folhas.get(k)) {

					if(!links.contains(arcosNPert.get(i))) {
						links.add(arcosNPert.get(i));
					}

				} 
			}
		}


		for(int i = 0; i < links.size(); i++) {
			TotalCiclos.add(CalculaCiclo(links.get(i).no, links.get(i).pred, links.get(i).chave));   
		}
	}


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
	    
	        