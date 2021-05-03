package heap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class DijkstraPQ {
    public static void main(String[] args) throws FileNotFoundException {
    	
        // input parameters
        final Graph graph =
            readFiles("yolo.txt"); // leitura do ficheiro

        final int sourceVertex = 1; // desde o nó x

        graph.dijkstra_GetMinDistances(sourceVertex);
    }

    static class Edge {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }	
    }

    static class Graph {
        int vertices;
        LinkedList<Edge>[] adjacencylist;

        Graph(int vertices) {
            this.vertices = vertices;
            adjacencylist = new LinkedList[vertices];
            //initialize adjacency lists for all the vertices
            for (int i = 0; i < vertices; i++) {
                adjacencylist[i] = new LinkedList<>();
            }
        }

        public void addEdge(int source, int destination, int weight) {
            Edge edge = new Edge(source, destination, weight);
            adjacencylist[source].addFirst(edge);

            edge = new Edge(destination, source, weight);
            adjacencylist[destination].addFirst(edge); 
        }

        public void dijkstra_GetMinDistances(int sourceVertex) {
            // create-heap(H)
            PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(vertices, (p1, p2) -> {
                //sort using distance values
                int key1 = p1.getKey();
                int key2 = p2.getKey();
                return key1 - key2;
            });

            // initialize d
            int[] distance = new int[vertices];

            // d(j) = infinity for all j E N;
            for (int i = 0; i < vertices; i++) {
                distance[i] = Integer.MAX_VALUE;
            }

            // d(s) = 0 and pred(s) = 0
            distance[sourceVertex] = 0;
            Pair<Integer, Integer> p0 = new Pair<>(distance[sourceVertex], sourceVertex);

            // insert(s,H)
            pq.offer(p0);

            // while H != 0 do
            while (!pq.isEmpty()) {
                // find-min(i, H);
                // delete-min(i, H)
                Pair<Integer, Integer> extractedPair = pq.poll();

                // for each (i,j) E A(i) do
                int extractedVertex = extractedPair.getValue();
                LinkedList<Edge> list = adjacencylist[extractedVertex];
                for (int i = 0; i < list.size(); i++) {

                    // value = d(i) + cij
                    Edge edge = list.get(i);
                    int destination = edge.destination;
                    int newKey = distance[extractedVertex] + edge.weight;
                    int currentKey = distance[destination];

                    // if d(j) > value then
                    if (currentKey > newKey) {

                        // if d(j) = infinity then
                        if (currentKey == Integer.MAX_VALUE) {
                            Pair<Integer, Integer> p = new Pair<>(newKey, destination);
                            pq.offer(p);
                            distance[destination] = newKey;
                        } else { // else
                            Pair<Integer, Integer> p = new Pair<>(newKey, destination);
                            pq.remove(p);
                            distance[destination] = newKey;
                        }

                    }

                }
            }

            //print 
            printDijkstra(distance, sourceVertex);
        }

        public void printDijkstra(int[] distance, int sourceVertex) { 
            for (int i = 0; i < vertices; i++) {
                if (distance[i] != Integer.MAX_VALUE) {
                    System.out.println("Desde o nó " + sourceVertex + " até ao nó " + +i +
                                       " , tem custo: " + distance[i]);
                }
            }
        }
	
    }

    public static Graph readFiles(String file) {
        try {
            // leitura
            File f = new File(file);
            Scanner s = new Scanner(f);

            List<Integer> list_noOrigem = new ArrayList<>();
            List<Integer> list_noDestino = new ArrayList<>();
            List<Integer> list_custo = new ArrayList<>();

            while (s.hasNext()) {
                list_noOrigem.add(s.nextInt());
                list_noDestino.add(s.nextInt());    
                list_custo.add(s.nextInt());
            }
            
            int firstNode = Math.min(Collections.min(list_noOrigem), Collections.min(list_noDestino));
            int lastNode = Math.max(Collections.max(list_noOrigem), Collections.max(list_noDestino));
            
            
            
            final Graph graph = new Graph(firstNode + lastNode);
            
            

            for (int i = 0; i < list_noOrigem.size();i++) {
                graph.addEdge(list_noOrigem.get(i),
                              list_noDestino.get(i),
                              list_custo.get(i));

            }
            

       
            return graph;
        } catch (Exception e) {
            return null;
        }
    }

    public static class Pair<A, B> {
        private A key;
        private B value;

        public Pair(A key, B value) {
            super();
            this.key = key;
            this.value = value;
        }

        public int hashCode() {
            int hashFirst = key != null ? key.hashCode() : 0;
            int hashSecond = value != null ? value.hashCode() : 0;

            return (hashFirst + hashSecond) * hashSecond + hashFirst;
        }

        public boolean equals(Object other) {
            if (other instanceof Pair) {
                Pair otherPair = (Pair) other;
                return
                    ((  this.key == otherPair.key ||
                        ( this.key != null && otherPair.key != null &&
                          this.key.equals(otherPair.key))) &&
                     (  this.value == otherPair.value ||
                        ( this.value != null && otherPair.value != null &&
                          this.value.equals(otherPair.value))) );
            }

            return false;
        }

        public String toString()
        {
            return "(" + key + ", " + value + ")";
        }

        public A getKey() {
            return key;
        }

        public void setKey(A key) {
            this.key = key;
        }

        public B getValue() {
            return value;
        }

        public void setValue(B value) {
            this.value = value;
        }
    }
}