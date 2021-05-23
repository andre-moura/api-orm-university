package br.com.bandtec.apifaculdade.classes;

public class FilaObj<T> {

    private T[] fila;
    private int tamanho;

    private int inicio;
    private int fim;

    public FilaObj(int capacidade) {
        this.fila = (T[]) new Object[capacidade];
        this.tamanho = 0;
        this.inicio = 0;
        this.fim = 0;
    }

    public int size(){
        return tamanho;
    }

    public boolean isEmpty(){
        return tamanho == 0;
    }

    public boolean isFull(){
        return tamanho == fila.length;
    }

    public void insert(T info){
        if (!isFull()) {
            this.fila[fim] = info;
            fim = fim+1 % fila.length;
            tamanho++;
        } else {
            System.out.println("Fila cheia!");
        }
    }

    public T peek() {
        return  fila[inicio];
    }

    public T poll(){
        T primeiro = peek();

        if (!isEmpty()){
            inicio = (inicio+1) % fila.length;
            fila[--tamanho] = null;
        }
        return primeiro;
    }

    public void exibe(){
        if (isEmpty()){
            System.out.println("Fila vazia!");
        } else {
            for (int i = 0, count = 0; count < tamanho; i=(i+1) % fila.length, count++) {
                System.out.print(fila[i] + "\t");
            }
        }
    }
}
