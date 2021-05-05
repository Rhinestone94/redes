package heap;


public class heap1 {
    public int n; // numero de elementos na heap
    public int [] vetor; // vetor para guardar os elementos
    public int currentSize; // dimensao
    
    
    public heap1(int capacity){
        this.n=capacity;
        vetor = new int [capacity+1];
       currentSize = 0;
    }
    

    public void criaHeap(int [] arr){
        if(arr.length>0){
            for(int i=0; i<arr.length; i++){
                insere(arr[i]);
            }
        }
    }
    
    
    public void mostra(){
        for(int i=1; i<vetor.length; i++){
            System.out.print(" " + vetor[i]);
        }
        System.out.println("");
    }
    
    public void insere(int x) {
        if(currentSize==n){
            System.out.println("heap is full");
            return;
        }
        currentSize++;
        int idx = currentSize;
        vetor[idx] = x;
        bubbleUp(idx);
    }

    public void bubbleUp(int pos) {
        int parentIdx = pos/2;
        int currentIdx = pos;
        while (currentIdx > 0 && vetor[parentIdx] > vetor[currentIdx]) {

            troca(currentIdx,parentIdx);
            currentIdx = parentIdx;
            parentIdx = parentIdx/2;
        }
    }

    /*public int extractMin() {
        int min = vetor[1];
        vetor[1] = vetor[currentSize];
        vetor[currentSize] = 0;
        sinkDown(1);
        currentSize--;
        return min;
    }

    public void sinkDown(int k) {
        int smallest = k;
        int leftChildIdx = 2 * k;
        int rightChildIdx = 2 * k+1;
        if (leftChildIdx < heapSize() && vetor[smallest] > vetor[leftChildIdx]) {
            smallest = leftChildIdx;
        }
        if (rightChildIdx < heapSize() && vetor[smallest] > vetor[rightChildIdx]) {
            smallest = rightChildIdx;
        }
        if (smallest != k) {

            troca(k, smallest);
            sinkDown(smallest);
        }
    }*/

    //swap
    public void troca(int a, int b) {
        int temp = vetor[a];
        vetor[a] = vetor[b];
        vetor[b] = temp;
    }
   
    public int heapSize(){
        return currentSize;
    }

    public static void main(String args[]){
        int arr[] = {10,20,3,9,2,6,5};
        System.out.print("Heap:");
        for(int i=0; i<arr.length; i++){
            System.out.print(" " + arr[i]);
        }
        
        heap1 h = new heap1(arr.length); // cria objeto h
        System.out.print("\nOrdenada: ");
        h.criaHeap(arr);
        h.mostra();
        
        //System.out.print("Troca posicao 2 com 4: ");
        //h.troca(2, 4);
        //h.mostra();
    }
}
