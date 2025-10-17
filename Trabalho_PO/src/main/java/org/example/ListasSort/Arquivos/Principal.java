package org.example.ListasSort.Arquivos;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Principal {
    Arquivo arqOrd, arqRev, arqRand, auxRev, auxRand;
    int compO, movO;
    long tini, tfim, ttotalO;
    RandomAccessFile arq;

    private void gravaNoArq(String linha) {
        try{
            arq.writeBytes(linha);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cabecalho() {
        String colunas = """
                |--------------------|--------------------------------------------|--------------------------------------------|--------------------------------------------|\n
                |    Metodos Ord     |              Arquivo ordenado              |          Arquivo em ordem reversa          |              Arquivo randomico             |\n
                |--------------------|--------------------------------------------|--------------------------------------------|--------------------------------------------|\n
                |                    |  Comp. |  Comp. |  mov.  |  mov.  | Tempo  |  Comp. |  Comp. |  mov.  |  mov.  | Tempo  |  Comp. |  Comp. |  mov.  |  mov.  | Tempo  |\n
                |                    |  Prog* |  equa# |  Prog* |  equa# | (Seg)  |  Prog* |  equa# |  Prog* |  equa# | (Seg)  |  Prog* |  equa# |  Prog* |  equa# | (Seg)  |\n
                |--------------------|--------------------------------------------|--------------------------------------------|--------------------------------------------|
                """;
        gravaNoArq(colunas);
    }
    private String centraliza(int num) {
        String registro="",valor=""+num;
        int qtd=(8-valor.length())/2;
        for (int i = 0; i < qtd; i++)
            registro+=" ";
        registro+=valor;
        while (registro.length()<8)
            registro+=" ";
        return registro;
    }

    private void gravaLinhaCompleta() {
        String linha="|--------------------|--------------------------------------------|--------------------------------------------|--------------------------------------------|\n";
        gravaNoArq(linha);
    }

    public void gravaLinhaTabela(int comp,int compCalculado, int mov, int movCalculado, int tempo, boolean finalLinha){
        String reg="",linha="";
        reg=centraliza(comp);
        reg+="|";
        linha+=reg;
        reg=centraliza(compCalculado);
        reg+="|";
        linha+=reg;
        reg=centraliza(mov);
        reg+="|";
        linha+=reg;
        reg=centraliza(movCalculado);
        reg+="|";
        linha+=reg;
        reg=centraliza(tempo);
        reg+="|";
        linha+=reg;
        if (finalLinha){
            linha+="\n";
            gravaNoArq(linha);
            gravaLinhaCompleta();
        }
        else
            gravaNoArq(linha);
    }
    public void gravaNomeMetodo(String nome){
        int tamanho= nome.length();
        String linha="|";
        for (int i = 0; i < (20-tamanho)/2; i++) {
            linha+=" ";
        }
        linha+=nome;
        while (linha.length()<21) {
            linha+=" ";
        }
        linha+="|";
        gravaNoArq(linha);
    }
    public void GerarTabela()
    {
        try{
            arq = new RandomAccessFile("Tabela.txt", "rw");
            arq.setLength(0);
        }catch (IOException e){

        }
        cabecalho();
        arqOrd = new Arquivo("Ordenado.txt");
        arqRev = new Arquivo("Reverso.txt");
        arqRand = new Arquivo("Randomico.txt");
        auxRev = new Arquivo("AuxRev.txt");
        auxRand = new Arquivo("AuxRand.txt");

        arqOrd.geraArquivoOrdenado(1024);
        arqRev.geraArquivoReverso(1024);
        arqRand.geraArquivoRandomico(1024);
        int TL = arqOrd.filesize();
        //Arquivo Ordenado
        gravaNomeMetodo("Insercao Direta");
        arqOrd.initComp();
        arqOrd.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.InsercaoDireta();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=arqOrd.getComp();
        movO=arqOrd.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,TL - 1,movO,3*(TL - 1),(int)(ttotalO/1000),false);

        //Arquivo Reverso;
        auxRev.copiaArquivo(arqRev.getFile());
        auxRev.initComp();
        auxRev.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRev.InsercaoDireta();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRev.getComp();
        movO=auxRev.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,(TL * TL + TL - 4) / 4,movO,(TL * TL + 3 * TL - 4) / 2,(int)(ttotalO/1000),false);

        //Arquivo Randomico;
        auxRand.copiaArquivo(arqRand.getFile());
        auxRand.initComp();
        auxRand.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRand.InsercaoDireta();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRand.getComp();
        movO=auxRand.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,(TL * TL + TL - 2) / 4,movO,(TL * TL  + 9 * TL - 10) / 4,(int)(ttotalO/1000),true);

        //Arquivo Ordenado;
        gravaNomeMetodo("Insercao Binaria");
        arqOrd.initComp();
        arqOrd.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.InsercaoBinaria();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=arqOrd.getComp();
        movO=arqOrd.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,TL*((int)(Math.log(TL)-Math.log(Math.E)+0.5)),movO,3*(TL - 1),(int)(ttotalO/1000),false);

        //Arquivo Reverso;
        auxRev.copiaArquivo(arqRev.getFile());
        auxRev.initComp();
        auxRev.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRev.InsercaoBinaria();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRev.getComp();
        movO=auxRev.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,TL*((int)(Math.log(TL)-Math.log(Math.E)+0.5)),movO,(TL*TL + 3*TL - 4)/2,(int)(ttotalO/1000),false);

        //Arquivo Randomico;
        auxRand.copiaArquivo(arqRand.getFile());
        auxRand.initComp();
        auxRand.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRand.InsercaoBinaria();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRand.getComp();
        movO=auxRand.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,TL*((int)(Math.log(TL)-Math.log(Math.E)+0.5)),movO, (TL * TL + 9*TL - 10)/4,(int)(ttotalO/1000),true);

        //Arquivo Ordenado;
        gravaNomeMetodo("Selecao Direta");
        arqOrd.initComp();
        arqOrd.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.SelecaoDireta();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=arqOrd.getComp();
        movO=arqOrd.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,(TL*TL - TL)/2,movO,3*(TL - 1),(int)(ttotalO/1000),false);

        //Arquivo Reverso;
        auxRev.copiaArquivo(arqRev.getFile());
        auxRev.initComp();
        auxRev.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRev.SelecaoDireta();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRev.getComp();
        movO=auxRev.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,(TL*TL - TL)/2,movO,(TL * TL)/4 + 3 * (TL - 1),(int)(ttotalO/1000),false);

        //Arquivo Randomico;
        auxRand.copiaArquivo(arqRand.getFile());
        auxRand.initComp();
        auxRand.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRand.SelecaoDireta();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRand.getComp();
        movO=auxRand.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,(TL*TL - TL)/2,movO,TL * ((int)(Math.log(TL) + 0.577216)),(int)(ttotalO/1000), true);

        //Arquivo Ordenado
        gravaNomeMetodo("BubbleSort");
        arqOrd.initComp();
        arqOrd.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.Bubblesort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=arqOrd.getComp();
        movO=arqOrd.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,(TL * TL - TL) / 2,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Reverso;
        auxRev.copiaArquivo(arqRev.getFile());
        auxRev.initComp();
        auxRev.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRev.Bubblesort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRev.getComp();
        movO=auxRev.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,(TL * TL - TL) / 2,movO,3*(TL * TL - TL)/4,(int)(ttotalO/1000),false);

        //Arquivo Randomico;
        auxRand.copiaArquivo(arqRand.getFile());
        auxRand.initComp();
        auxRand.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRand.Bubblesort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRand.getComp();
        movO=auxRand.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,(TL * TL - TL) / 2,movO,3*(TL * TL - TL)/2,(int)(ttotalO/1000),true);

        //Arquivo Ordenado
        gravaNomeMetodo("ShakeSort");
        arqOrd.initComp();
        arqOrd.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.ShakeSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=arqOrd.getComp();
        movO=arqOrd.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,(TL * TL - TL) / 2,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Reverso;
        auxRev.copiaArquivo(arqRev.getFile());
        auxRev.initComp();
        auxRev.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRev.ShakeSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRev.getComp();
        movO=auxRev.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,(TL * TL - TL) / 2,movO,3*(TL * TL - TL)/4,(int)(ttotalO/1000),false);

        //Arquivo Randomico;
        auxRand.copiaArquivo(arqRand.getFile());
        auxRand.initComp();
        auxRand.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRand.ShakeSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRand.getComp();
        movO=auxRand.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,(TL * TL - TL) / 2,movO,3*(TL * TL - TL)/2,(int)(ttotalO/1000),true);

        //Arquivo Ordenado
        gravaNomeMetodo("ShellSort");
        arqOrd.initComp();
        arqOrd.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.ShellSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=arqOrd.getComp();
        movO=arqOrd.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Reverso;
        auxRev.copiaArquivo(arqRev.getFile());
        auxRev.initComp();
        auxRev.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRev.ShellSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRev.getComp();
        movO=auxRev.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Randomico;
        auxRand.copiaArquivo(arqRand.getFile());
        auxRand.initComp();
        auxRand.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRand.ShellSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRand.getComp();
        movO=auxRand.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),true);

        //Arquivo Ordenado
        gravaNomeMetodo("HeapSort");
        arqOrd.initComp();
        arqOrd.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.HeapSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=arqOrd.getComp();
        movO=arqOrd.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Reverso;
        auxRev.copiaArquivo(arqRev.getFile());
        auxRev.initComp();
        auxRev.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRev.HeapSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRev.getComp();
        movO=auxRev.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Randomico;
        auxRand.copiaArquivo(arqRand.getFile());
        auxRand.initComp();
        auxRand.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRand.HeapSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRand.getComp();
        movO=auxRand.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),true);

        //Arquivo Ordenado
        gravaNomeMetodo("Quick s/Pivo");
        arqOrd.initComp();
        arqOrd.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.QuickSemPivo();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=arqOrd.getComp();
        movO=arqOrd.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Reverso;
        auxRev.copiaArquivo(arqRev.getFile());
        auxRev.initComp();
        auxRev.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRev.QuickSemPivo();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRev.getComp();
        movO=auxRev.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Randomico;
        auxRand.copiaArquivo(arqRand.getFile());
        auxRand.initComp();
        auxRand.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRand.QuickSemPivo();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRand.getComp();
        movO=auxRand.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),true);

        //Arquivo Ordenado
        gravaNomeMetodo("Quick c/Pivo");
        arqOrd.initComp();
        arqOrd.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.QuickComPivo();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=arqOrd.getComp();
        movO=arqOrd.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Reverso;
        auxRev.copiaArquivo(arqRev.getFile());
        auxRev.initComp();
        auxRev.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRev.QuickComPivo();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRev.getComp();
        movO=auxRev.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Randomico;
        auxRand.copiaArquivo(arqRand.getFile());
        auxRand.initComp();
        auxRand.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRand.QuickComPivo();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRand.getComp();
        movO=auxRand.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),true);

        //Arquivo Ordenado
        gravaNomeMetodo("Merge 1");
        arqOrd.initComp();
        arqOrd.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.Merge1();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=arqOrd.getComp();
        movO=arqOrd.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Reverso;
        auxRev.copiaArquivo(arqRev.getFile());
        auxRev.initComp();
        auxRev.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRev.Merge1();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRev.getComp();
        movO=auxRev.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Randomico;
        auxRand.copiaArquivo(arqRand.getFile());
        auxRand.initComp();
        auxRand.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRand.Merge1();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRand.getComp();
        movO=auxRand.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),true);

        //Arquivo Ordenado
        gravaNomeMetodo("Merge 2");
        arqOrd.initComp();
        arqOrd.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.Merge2();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=arqOrd.getComp();
        movO=arqOrd.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Reverso;
        auxRev.copiaArquivo(arqRev.getFile());
        auxRev.initComp();
        auxRev.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRev.Merge2();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRev.getComp();
        movO=auxRev.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Randomico;
        auxRand.copiaArquivo(arqRand.getFile());
        auxRand.initComp();
        auxRand.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRand.Merge2();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRand.getComp();
        movO=auxRand.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),true);

        //Arquivo Ordenado
        gravaNomeMetodo("Counting");
        arqOrd.initComp();
        arqOrd.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.CountingSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=arqOrd.getComp();
        movO=arqOrd.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Reverso;
        auxRev.copiaArquivo(arqRev.getFile());
        auxRev.initComp();
        auxRev.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRev.CountingSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRev.getComp();
        movO=auxRev.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Randomico;
        auxRand.copiaArquivo(arqRand.getFile());
        auxRand.initComp();
        auxRand.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRand.CountingSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRand.getComp();
        movO=auxRand.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),true);

        //Arquivo Ordenado
        gravaNomeMetodo("Bucket");
        arqOrd.initComp();
        arqOrd.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.BucketSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=arqOrd.getComp();
        movO=arqOrd.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Reverso;
        auxRev.copiaArquivo(arqRev.getFile());
        auxRev.initComp();
        auxRev.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRev.BucketSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRev.getComp();
        movO=auxRev.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Randomico;
        auxRand.copiaArquivo(arqRand.getFile());
        auxRand.initComp();
        auxRand.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRand.BucketSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRand.getComp();
        movO=auxRand.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),true);

        //Arquivo Ordenado
        gravaNomeMetodo("Radix");
        arqOrd.initComp();
        arqOrd.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.RadixSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=arqOrd.getComp();
        movO=arqOrd.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Reverso;
        auxRev.copiaArquivo(arqRev.getFile());
        auxRev.initComp();
        auxRev.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRev.RadixSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRev.getComp();
        movO=auxRev.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Randomico;
        auxRand.copiaArquivo(arqRand.getFile());
        auxRand.initComp();
        auxRand.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRand.RadixSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRand.getComp();
        movO=auxRand.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),true);

        //Arquivo Ordenado
        gravaNomeMetodo("Comb");
        arqOrd.initComp();
        arqOrd.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.CombSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=arqOrd.getComp();
        movO=arqOrd.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Reverso;
        auxRev.copiaArquivo(arqRev.getFile());
        auxRev.initComp();
        auxRev.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRev.CombSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRev.getComp();
        movO=auxRev.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Randomico;
        auxRand.copiaArquivo(arqRand.getFile());
        auxRand.initComp();
        auxRand.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRand.CombSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRand.getComp();
        movO=auxRand.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),true);

        //Arquivo Ordenado
        gravaNomeMetodo("Gnome");
        arqOrd.initComp();
        arqOrd.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.GnomeSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=arqOrd.getComp();
        movO=arqOrd.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Reverso;
        auxRev.copiaArquivo(arqRev.getFile());
        auxRev.initComp();
        auxRev.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRev.GnomeSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRev.getComp();
        movO=auxRev.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Randomico;
        auxRand.copiaArquivo(arqRand.getFile());
        auxRand.initComp();
        auxRand.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRand.GnomeSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRand.getComp();
        movO=auxRand.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),true);

        //Arquivo Ordenado
        gravaNomeMetodo("Tim");
        arqOrd.initComp();
        arqOrd.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.TimSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=arqOrd.getComp();
        movO=arqOrd.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Reverso;
        auxRev.copiaArquivo(arqRev.getFile());
        auxRev.initComp();
        auxRev.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRev.TimSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRev.getComp();
        movO=auxRev.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),false);

        //Arquivo Randomico;
        auxRand.copiaArquivo(arqRand.getFile());
        auxRand.initComp();
        auxRand.initMov();
        tini= System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        auxRand.TimSort();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=auxRand.getComp();
        movO=auxRand.getMov();
        ttotalO= tfim-tini;
        gravaLinhaTabela(compO,0,movO,0,(int)(ttotalO/1000),true);
    }
    public static void main(String args[])
    {
        Principal p = new Principal();
        p.GerarTabela();
    }
}
