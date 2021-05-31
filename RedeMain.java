package heap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import heap.Rede.Grafo;

import heap.Rede.Arco;
import heap.heapB;


public class RedeMain {
	
	public static void main(String[] args) throws IOException {
    	
        File ficheiro = new File("yolo.txt"); 
        
        if(!ficheiro.exists()){ 
            System.out.print("Ficheiro não encontrado FAIL...");
            System.out.printf("\n");     
        }
        
        BufferedReader buffer = new BufferedReader (new FileReader(ficheiro)); // buffer para as linhas 
        BufferedReader buffer_2 = new BufferedReader(new FileReader(ficheiro)); 
        
        String [] elemento;
        String linha;
        
        ArrayList <Integer> tabela_total = new ArrayList<>();  //vai adicionando ; a tabela vai sendo preenchida
        
        while ((linha = buffer.readLine()) != null) { // para saber o tamanho / nº de nós a utilizar no grafo / para construir o grafo
        elemento = linha.split(" "); // separação por espacos 
        if (!tabela_total.contains(Integer.parseInt(elemento[0]))) // se a tabela total contem um certo numero da coluna dos nos origem, se nao o tiver adiciona-o
            tabela_total.add(Integer.parseInt(elemento[0]));  // 0
        if(!tabela_total.contains(Integer.parseInt(elemento[1]))) // se nao conter o 1 
            tabela_total.add(Integer.parseInt(elemento[1])); // 1
        }
        
        
        
        // Cria o GRAFO
        Grafo grafo = new Grafo(tabela_total.size()); // tamanho
        int VerOrigem, VerDestino, custo;
        
        
        
        //LinkedList<Arco>[] adjList;
        //ArrayList arr = new ArrayList();
       
        
        
        buffer.close();
        
               
        //lê linha a linha elementos separados por um espaços
        while( (linha = buffer_2.readLine()) != null ){
            elemento = linha.split(" ");
            VerOrigem = Integer.parseInt(elemento[0]);
            VerDestino = Integer.parseInt(elemento[1]);
            custo = Integer.parseInt(elemento[2]);
            grafo.adiciona_Arco(VerOrigem, VerDestino, custo);
               
        }
        
      
        System.out.println("Grafo: ");
        Grafo.Imprime_Grafo(grafo);
        
        Grafo.Existe_arco(grafo, 1, 4); // alterar consoante o pretendido
      
        
        //int zt = grafo.Dijkstra(1);
        
        //System.out.println(zt);
        
        grafo.Dijkstra(1, grafo.adjList);
        
        
        
        
     
  
    } 
}
    
    //  =========================  FIM Main =====================
    

 

