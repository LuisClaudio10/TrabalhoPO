package org.example.ListasSort.Listas;

import org.example.ListasSort.Arquivos.Principal;

public class Aplicacao {
    public static void main(String[] args) {
        Lista lista;

        // Insercao Direta
        lista = new Lista();
        lista.inserirAleatorio();
        System.out.println("\n=== Insercao Direta ===\nANTES:");
        lista.exibir();
        lista.InsercaoDireta();
        System.out.println("DEPOIS:");
        lista.exibir();

        // Insercao Binaria
        lista = new Lista();
        lista.inserirAleatorio();
        System.out.println("\n=== Insercao Binaria ===\nANTES:");
        lista.exibir();
        lista.InsercaoBinaria();
        System.out.println("DEPOIS:");
        lista.exibir();

        // Selecao Direta
        lista = new Lista();
        lista.inserirAleatorio();
        System.out.println("\n=== Selecao Direta ===\nANTES:");
        lista.exibir();
        lista.SelecaoDireta();
        System.out.println("DEPOIS:");
        lista.exibir();

        // Bolha
        lista = new Lista();
        lista.inserirAleatorio();
        System.out.println("\n=== Bolha ===\nANTES:");
        lista.exibir();
        lista.Bolha();
        System.out.println("DEPOIS:");
        lista.exibir();

        // Shake Sort
        lista = new Lista();
        lista.inserirAleatorio();
        System.out.println("\n=== Shake Sort ===\nANTES:");
        lista.exibir();
        lista.ShakeSort();
        System.out.println("DEPOIS:");
        lista.exibir();

        // Heap Sort
        lista = new Lista();
        lista.inserirAleatorio();
        System.out.println("\n=== Heap Sort ===\nANTES:");
        lista.exibir();
        lista.HeapSort();
        System.out.println("DEPOIS:");
        lista.exibir();

        // Shell Sort
        lista = new Lista();
        lista.inserirAleatorio();
        System.out.println("\n=== Shell Sort ===\nANTES:");
        lista.exibir();
        lista.ShellSort();
        System.out.println("DEPOIS:");
        lista.exibir();

        // Counting Sort
        lista = new Lista();
        lista.inserirAleatorio();
        System.out.println("\n=== Counting Sort ===\nANTES:");
        lista.exibir();
        lista.CountingSort();
        System.out.println("DEPOIS:");
        lista.exibir();

        // Bucket Sort
        lista = new Lista();
        lista.inserirAleatorio();
        System.out.println("\n=== Bucket Sort ===\nANTES:");
        lista.exibir();
        lista.BucketSort();
        System.out.println("DEPOIS:");
        lista.exibir();

        // Radix Sort
        lista = new Lista();
        lista.inserirAleatorio();
        System.out.println("\n=== Radix Sort ===\nANTES:");
        lista.exibir();
        lista.RadixSort();
        System.out.println("DEPOIS:");
        lista.exibir();

        // Comb Sort
        lista = new Lista();
        lista.inserirAleatorio();
        System.out.println("\n=== Comb Sort ===\nANTES:");
        lista.exibir();
        lista.CombSort();
        System.out.println("DEPOIS:");
        lista.exibir();

        // Gnome Sort
        lista = new Lista();
        lista.inserirAleatorio();
        System.out.println("\n=== Gnome Sort ===\nANTES:");
        lista.exibir();
        lista.GnomeSort();
        System.out.println("DEPOIS:");
        lista.exibir();

        // Quick Sort (sem pivo)
        lista = new Lista();
        lista.inserirAleatorio();
        System.out.println("\n=== Quick Sort (sem pivo) ===\nANTES:");
        lista.exibir();
        lista.QuickSortSemPivo();
        System.out.println("DEPOIS:");
        lista.exibir();

        // Quick Sort (com pivo)
        lista = new Lista();
        lista.inserirAleatorio();
        System.out.println("\n=== Quick Sort (com pivo) ===\nANTES:");
        lista.exibir();
        lista.QuickSortComPivo();
        System.out.println("DEPOIS:");
        lista.exibir();

        // Merge Sort v1
        lista = new Lista();
        lista.inserirAleatorio();
        System.out.println("\n=== Merge Sort v1 ===\nANTES:");
        lista.exibir();
        lista.MergeSort1();
        System.out.println("DEPOIS:");
        lista.exibir();

        // Merge Sort v2
        lista = new Lista();
        lista.inserirAleatorio();
        System.out.println("\n=== Merge Sort v2 ===\nANTES:");
        lista.exibir();
        lista.MergeSort2();
        System.out.println("DEPOIS:");
        lista.exibir();

        // TimSort
        lista = new Lista();
        lista.inserirAleatorio();
        System.out.println("\n=== TimSort===\nANTES:");
        lista.exibir();
        lista.TimSort();
        System.out.println("DEPOIS:");
        lista.exibir();
    }
}

