package Proj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Proj.HeapB.No_pred;

public class Main {

	public static void main(String[] args) throws IOException {

		File ficheiro = new File("node6.txt"); 

		if(!ficheiro.exists()){ 
			System.out.print("Ficheiro não encontrado FAIL...");
			System.out.printf("\n");     
		}

		BufferedReader buffer = new BufferedReader (new FileReader(ficheiro)); 
		BufferedReader buffer_2 = new BufferedReader(new FileReader(ficheiro)); 

		BufferedReader buffer_3 = new BufferedReader(new FileReader(ficheiro));
		
		String[] elemento;
		String linha;

		ArrayList tabela_total = new ArrayList<>();  //vai adicionando ; a tabela vai sendo preenchida

		while ((linha = buffer.readLine()) != null) { // para saber o tamanho / nº de nós a utilizar no grafo / para construir o grafo
			elemento = linha.split(" ");  
			if (!tabela_total.contains(Integer.parseInt(elemento[0]))) // se a tabela total nao contem um certo numero da coluna dos nos origem, entao adiciona-o, se nao o tiver adiciona-o
				tabela_total.add(Integer.parseInt(elemento[0]));  // 0
			if(!tabela_total.contains(Integer.parseInt(elemento[1]))) 
				tabela_total.add(Integer.parseInt(elemento[1])); 
		}

		// Cria o grafo

		//Rede grafo = new Rede(7); //EXEMPLO para grafo Desconexo
		Rede grafo = new Rede(tabela_total.size());

		Rede grafo_custosNegativos = new Rede(tabela_total.size());


		//lê linha a linha - elementos separados por um espaço
		while( (linha = buffer_2.readLine()) != null ){
			elemento = linha.split(" ");

			int no1 = Integer.parseInt(elemento[0]);
			int no2 = Integer.parseInt(elemento[1]);
			int custo = Integer.parseInt(elemento[2]);

			grafo.adicionaArco(no1, no2, custo);

		}

		int max = 0;
		while( (linha = buffer_3.readLine()) != null ){
			elemento = linha.split(" ");
			
			int no1 = Integer.parseInt(elemento[0]);
			int no2 = Integer.parseInt(elemento[1]);			
			int custo = Integer.parseInt(elemento[2]);
			
			// calcula o maximo
			if (custo > max) {
				max = custo;
			}	
			//custo *= (-1);
			
			grafo_custosNegativos.adicionaArco(no1, no2, custo);	
		}
		
		for (int i = 1; i < grafo_custosNegativos.numNos ; i++) {
			
			for (Arco a : grafo_custosNegativos.adjList[i]) {
				a.custo *= (-1);
				a.custo += (max +1);
		
			}
			
		}

		buffer.close();
		buffer_2.close();
		buffer_3.close();

		System.out.println("Lista de Adjacencias: ");
		Rede.imprimeListaAdj(grafo);


		// ================= DEBUG Grafo com custos negativos
		System.out.println();
		//System.out.print("Lista de adjacencias - Grafo");

		//Rede.imprimeListaAdj(grafo_custosNegativos);

		// ==================================

		System.out.println("");
		System.out.println("=========================================================");
		
		
		// ========================== Árvore e caminho de menor custo ==========

		int noFonte = 1;
		//int noDestino = 3;


		grafo.Dijkstra(noFonte);



		//debug Dijkstra
		System.out.println("");
		System.out.println("Dijkstra - Árvore dos caminhos mais curtos desde o nó fonte: " + noFonte);
		System.out.println("Nó   Pred   CustoTotal" );
		for(int i = 0; i < grafo.arvore.size() ; i++) {
			//if (grafo.arvore.get(i).pred != 0) {

			System.out.println(grafo.arvore.get(i).no + "  -  " + grafo.arvore.get(i).pred + "  -  " + grafo.arvore.get(i).chave);		

		}	
		
		System.out.println();

		//grafo.caminhoMenorCusto(noFonte, noDestino); 

		// debug caminho de menor custo
		/*System.out.println("\nCaminho de menor custo desde o nó fonte " + grafo.caminho.get(0).origem);
		System.out.print(grafo.caminho.get(0).origem);
		for(int i = 0; i < grafo.caminho.size(); i++)
		{
			System.out.print(" - " + grafo.caminho.get(i).destino);
		}
		System.out.println("\nCusto total do caminho: " + grafo.caminho.get(grafo.caminho.size() - 1).custo);

		System.out.println("");
		System.out.println("Custo arco a arco: ");

		int cont = 0;

		for(int i = 0 ; i < grafo.caminho.size(); i++)
		{

			Arco aux = grafo.caminho.get(i);

			System.out.println(aux.origem + " - " + aux.destino + "  custo " + (aux.custo - cont));
			cont = aux.custo;
		}
		System.out.println();*/
		
		
		grafo.Prim(noFonte);
		grafo.ColocaWC();

		System.out.println("Prim - Árvore mínima abrangente desde o nó " + noFonte);
		System.out.println("Nó    Pred    Custo do arco    Capacidade de trabalho");
		for(int i = 0; i < grafo.Tree.size() ; i++) {
			
			System.out.println(grafo.Tree.get(i).no + "   -  " + grafo.Tree.get(i).pred + "   -  " + grafo.Tree.get(i).chave + "            -   " + grafo.Tree.get(i).workingC);		

		}	


		// =========================== debug Prim com custos negativos ==========================
		int noOrigemPrim = 1;	
		grafo_custosNegativos.Prim(noOrigemPrim); // arvore maxima 
		

		

		for (int i = 1; i < grafo_custosNegativos.numNos ; i++) {

			for (Arco e : grafo_custosNegativos.adjList[i]) {

				e.custo -= max +1; // -max - 1 
				e.custo *= (-1) ;
			}
		}

		
		for(int j = 1; j < grafo_custosNegativos.Tree.size(); j++) {
			grafo_custosNegativos.Tree.get(j).chave -= max +1; // -max - 1
			grafo_custosNegativos.Tree.get(j).chave *= (-1) ;
		}
		
		grafo_custosNegativos.ColocaWC();
		
		System.out.println();
		System.out.println("Prim - Árvore abrangente máxima desde o nó " + noOrigemPrim);
		System.out.println("Nó     Pred     Custo       Capacidade de trabalho");

		for(int i = 0; i < grafo_custosNegativos.Tree.size() ; i++) {	
			if(grafo_custosNegativos.Tree.get(i).pred == 0) { 
				System.out.println(grafo_custosNegativos.Tree.get(i).no + "   -  " + grafo_custosNegativos.Tree.get(i).pred + "  -  " + grafo_custosNegativos.Tree.get(i).chave);
				continue;
			}
			for (int j = 1; j < grafo_custosNegativos.numNos; j++) {
				for (Arco e : grafo_custosNegativos.adjList[j]) {
					if(e.origem == grafo_custosNegativos.Tree.get(i).pred && e.destino == grafo_custosNegativos.Tree.get(i).no) {
						System.out.println(grafo_custosNegativos.Tree.get(i).no + "   -  " + grafo_custosNegativos.Tree.get(i).pred + "  -  " + e.custo + "        -        " + grafo_custosNegativos.Tree.get(i).workingC);
						break;
					}
				}
			}
		}
		
		System.out.println();
		
		
		
		grafo_custosNegativos.EFolha(grafo_custosNegativos.Tree);
		//grafo_custosNegativos.arcosEdge();
		
		grafo_custosNegativos.Diff(grafo_custosNegativos.adjList, grafo_custosNegativos.Tree);
		//grafo_custosNegativos.CalculaCiclo();
		grafo_custosNegativos.Edge();
		
		//System.out.println(" ");
		
		for(int i = 0; i < grafo_custosNegativos.TotalCiclos.size(); i++) {
			System.out.println("Os arcos do ciclo #" + i + " são: ");
			for(int j = 0; j < grafo_custosNegativos.TotalCiclos.get(i).size(); j++)
			{
				System.out.println("(" + grafo_custosNegativos.TotalCiclos.get(i).get(j).no + "," + grafo_custosNegativos.TotalCiclos.get(i).get(j).pred + ")" +"  com custo: " + grafo_custosNegativos.TotalCiclos.get(i).get(j).chave);
				
			}	
			System.out.println();
		}
		
		System.out.println(" ");
		
		
		// ======================== Criação e escrita do ficheiro ============================ 
		

		try {
			File myObj = new File("grafo.gv");
			if (myObj.createNewFile()) {
				System.out.println("Ficheiro criado: " + myObj.getName());
			} else {
				System.out.println("O ficheiro já existe.");
			}
		} catch (IOException e) {
			System.out.println("Erro ao criar ...");
			e.printStackTrace();
		}
		try {
			FileWriter myWriter = new FileWriter("grafo.gv");
			myWriter.write("graph Grafo {\n\t");

			for(int i = 2; i < grafo.Tree.size(); i++) {

				myWriter.write("\"" + grafo.Tree.get(i).pred + "\"" + "  --  " + "\"" + grafo.Tree.get(i).no + "\"" + " [label=" + "\"" + grafo.Tree.get(i).chave + "\"" + "]" + " [color=blue]" + ";\n\t");	

			}	

			myWriter.write("}");
			myWriter.close();

		} catch (IOException e) {
			System.out.println("Erro na escrita ...");
			e.printStackTrace();
		}

		// =================================== FIM da escrita do ficheiro =================

	}
} 
//  =========================  FIM Main =====================