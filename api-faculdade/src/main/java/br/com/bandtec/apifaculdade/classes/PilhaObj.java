package br.com.bandtec.apifaculdade.classes;

import java.util.Arrays;

public class PilhaObj<T> {


    private int topo;
    private T[] PilhaObj;

    public PilhaObj(int capacidade) {
        this.topo = -1;
        this.PilhaObj = (T[]) new Object[capacidade];
    }

    public boolean isEmpty(){
        return this.topo == -1;
    }

    public boolean isFull(){
        return this.topo >= PilhaObj.length - 1;
    }

    public void push(T info){
        if (!isFull()) {
            this.PilhaObj[++topo] = info;
        }
    }

    public T pop(){
        if (isEmpty()){
            return null;
        }
        return this.PilhaObj[(--topo)+1];
    }

    public T peak(){
        if (topo != -1){
            return this.PilhaObj[topo];
        }
        return null;
    }

    public void exibe(){
        if (isEmpty()){
            System.out.println("exercicio1.PilhaObj está vazia!");
        }
        for (int i = 0; i < topo+1; i++){
            System.out.println(this.PilhaObj[i]);
        }
    }

    public PilhaObj<T> multiPop(int n){
        if (topo > n){
            PilhaObj<T> aux = new PilhaObj<>(n);

            for (int i = 0; i < n; i++) {
                aux.push(pop());
            }
            return aux;
        }
        return null;
    }

    public void multiPush(PilhaObj<T> aux){
        while (!aux.isEmpty()){
            push(aux.pop());
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(PilhaObj);
    }
}
