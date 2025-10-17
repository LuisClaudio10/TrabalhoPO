package org.example.ListasSort.Arquivos;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class Arquivo {
    private String nomearquivo;
    private RandomAccessFile arquivo;
    private int comp, mov;
    public Arquivo(String nomearquivo) {
        try{
            this.nomearquivo = nomearquivo;
            arquivo = new RandomAccessFile(nomearquivo, "rw");
        }catch(IOException e){

        }
    }
    public void copiaArquivo(RandomAccessFile arquivoOrigem) {
        Registro reg = new Registro();
        try {
            arquivoOrigem.seek(0);
            seekArq(0);
            truncate(0);
            while(arquivoOrigem.getFilePointer() < arquivoOrigem.length())
            {
                reg.leDoArq(arquivoOrigem);
                reg.gravaNoArq(arquivo);
            }
        }catch (IOException e){
        }
    }
    public RandomAccessFile getFile() {
        return arquivo;
    }
    public void truncate(long pos) {
        try{
            arquivo.setLength(pos * Registro.length());
        }catch (IOException e){

        }
    }
    public boolean eof()
    {
        try {
            if(arquivo.getFilePointer() == arquivo.length())
                return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void seekArq(int pos) {
        try {
            arquivo.seek(pos * Registro.length());
        }catch (IOException e) {
        }
    }
    public int filesize() {
        try{
            return (int) arquivo.length() / Registro.length();
        } catch (Exception e) {

        }
        return -1;
    }
    public void initComp() {
        this.comp = 0;
    }
    public void initMov() {
        this.mov = 0;
    }
    public int getComp() {
        return comp;
    }
    public int getMov() {
        return mov;
    }
    // demais métodos de ordenação
    public void geraArquivoOrdenado(int t) {
        Registro reg;
        for(int i = 0; i < t; i++)
        {
            reg = new Registro(i);
            reg.gravaNoArq(arquivo);
        }
    }
    public void geraArquivoReverso(int t) {
        Registro reg;
        for(int i = t; i > 0; i--)
        {
            reg = new Registro(i);
            reg.gravaNoArq(arquivo);
        }
    }

    public void geraArquivoRandomico(int t) {
        Registro reg;
        Random random = new Random();
        truncate(0);
        for(int i = 0; i < t; i++)
        {
            reg = new Registro(random.nextInt(1024) + 1);
            reg.gravaNoArq(arquivo);
        }
    }
    public void exibe() {
        Registro reg = new Registro();
        seekArq(0);
        while (!eof()) {
            reg.leDoArq(arquivo);
            System.out.println(reg.getNumero());
        }
    }
    public void InsercaoDireta()
    {
        Registro reg = new Registro(), regpos = new Registro();
        int pos, aux;
        for(int i = 1; i < filesize(); i++)
        {
            seekArq(i);
            reg.leDoArq(arquivo);
            pos = i;
            seekArq(pos - 1);
            regpos.leDoArq(arquivo);
            comp++;
            while(pos > 0 && reg.getNumero() < regpos.getNumero())
            {
                seekArq(pos);
                mov++;
                regpos.gravaNoArq(arquivo);
                pos--;
                if(pos > 0)
                {
                    seekArq(pos - 1);
                    regpos.leDoArq(arquivo);
                }
                comp++;
            }
            seekArq(pos);
            mov++;
            reg.gravaNoArq(arquivo);
        }
    }
    public int BuscaBinaria(int chave, int TL)
    {
        int i = 0, f = TL, meio = f / 2;
        Registro reg = new Registro();
        seekArq(meio);
        reg.leDoArq(arquivo);
        while(i < f && reg.getNumero() != chave)
        {
            comp += 2;
            if(chave > reg.getNumero())
                i = meio + 1;
            else
                f = meio - 1;
            meio = (i + f) / 2;
            seekArq(meio);
            reg.leDoArq(arquivo);
        }
        comp++;
        if(chave > reg.getNumero())
            return meio + 1;
        else
            return meio;
    }
    public void InsercaoBinaria()
    {
        Registro reg = new Registro(), reg2 = new Registro();
        int pos;
        for(int i = 1; i < filesize(); i++)
        {
            seekArq(i);
            reg.leDoArq(arquivo);
            pos = BuscaBinaria(reg.getNumero(), i);
            for(int j = i; j > pos; j--)
            {
                mov++;
                seekArq(j - 1);
                reg2.leDoArq(arquivo);
                reg2.gravaNoArq(arquivo);
            }
            mov++;
            seekArq(pos);
            reg.gravaNoArq(arquivo);
        }
    }
    public void SelecaoDireta()
    {
        Registro reg = new Registro(), reg1 = new Registro();
        int posmenor = 0, menor;
        for(int i = 0; i < filesize() - 1; i++)
        {
            seekArq(i);
            reg.leDoArq(arquivo);
            menor = reg.getNumero();
            posmenor = i;
            for(int j = i + 1; j < filesize(); j++)
            {
                reg1.leDoArq(arquivo);
                comp++;
                if(reg1.getNumero() < menor) {
                    menor = reg1.getNumero();
                    posmenor = j;
                }
            }
            if(posmenor != i)
            {
                mov += 2;
                seekArq(posmenor);
                reg1.leDoArq(arquivo);
                seekArq(i);
                reg1.gravaNoArq(arquivo);
                seekArq(posmenor);
                reg.gravaNoArq(arquivo);
            }
        }
    }
    public void Bubblesort()
    {
        Registro reg1 = new Registro(), reg2 = new Registro();
        int TL = filesize();
        boolean flag = true;
        while(TL > 1 && flag){
            flag = false;
            for(int i = 0; i < TL - 1; i++)
            {
                seekArq(i);
                reg1.leDoArq(arquivo);
                reg2.leDoArq(arquivo);
                comp++;
                if(reg1.getNumero() > reg2.getNumero())
                {
                    mov+=2;
                    seekArq(i);
                    reg2.gravaNoArq(arquivo);
                    reg1.gravaNoArq(arquivo);
                    flag = true;
                }
            }
            TL--;
        }
    }
    public void ShakeSort()
    {
        int inicio = 0, fim = filesize(), i, j;
        Registro reg1 = new Registro(), reg2 = new Registro();
        boolean flag = true;
        while(inicio < fim && flag)
        {
            flag = false;
            for(i = 0; i < fim - 1; i++)
            {
                seekArq(i);
                reg1.leDoArq(arquivo);
                reg2.leDoArq(arquivo);
                comp++;
                if(reg1.getNumero() > reg2.getNumero())
                {
                    mov += 2;
                    seekArq(i);
                    reg2.gravaNoArq(arquivo);
                    reg1.gravaNoArq(arquivo);
                    flag = true;
                }
            }
            fim--;
            for(j = fim; j > inicio; j--)
            {
                seekArq(j);
                reg1.leDoArq(arquivo);
                seekArq(j - 1);
                reg2.leDoArq(arquivo);
                comp++;
                if(reg1.getNumero() < reg2.getNumero())
                {
                    reg2.gravaNoArq(arquivo);
                    seekArq(j - 1);
                    reg1.gravaNoArq(arquivo);
                    flag = true;
                }
            }
            inicio++;
        }
    }
    public void ShellSort()
    {
        Registro reg1 = new Registro(), reg2 = new Registro();
        int dist = 1, pos, aux;
        while(dist < filesize())
            dist = dist * 2 + 1;

        dist = dist / 2;
        while(dist > 0)
        {
            for(int i = dist; i < filesize(); i++)
            {
                seekArq(i);
                reg1.leDoArq(arquivo);
                pos = i;
                seekArq(pos - dist);
                reg2.leDoArq(arquivo);
                comp++;
                while(pos >= dist && reg1.getNumero() < reg2.getNumero())
                {
                    seekArq(pos);
                    reg2.gravaNoArq(arquivo);
                    mov++;
                    pos = pos - dist;
                    if(pos >= dist) {
                        seekArq(pos - dist);
                        reg2.leDoArq(arquivo);
                    }
                    comp++;
                }
                seekArq(pos);
                reg1.gravaNoArq(arquivo);
            }
            dist = dist / 2;
        }
    }
    public void HeapSort()
    {
        int TL = filesize(), fe, fd, pai, m;
        Registro R1 = new Registro(), R2 = new Registro(), maior = new Registro(), Pai = new Registro();
        while(TL > 1)
        {
            pai = TL / 2 - 1;
            while(pai >= 0)
            {
                fe = pai * 2 + 1;
                seekArq(fe);
                R1.leDoArq(arquivo);
                fd = fe + 1;
                maior = R1;
                m = fe;
                comp++;
                if(fd < TL ) {
                    R2.leDoArq(arquivo);
                    if(R2.getNumero() > R1.getNumero()) {
                        maior = R2;
                        m = fd;
                    }
                }
                seekArq(pai);
                Pai.leDoArq(arquivo);
                comp++;
                if(maior.getNumero() > Pai.getNumero())
                {
                    mov += 2;
                    seekArq(m);
                    Pai.gravaNoArq(arquivo);
                    seekArq(pai);
                    maior.gravaNoArq(arquivo);
                }
                pai--;
            }
            mov += 2;
            seekArq(0);
            R1.leDoArq(arquivo);
            seekArq(TL - 1);
            R2.leDoArq(arquivo);
            seekArq(0);
            R2.gravaNoArq(arquivo);
            seekArq(TL - 1);
            R1.gravaNoArq(arquivo);
            TL--;
        }
    }
    public void QuickSem(int inicio, int fim){
        Registro pi = new Registro(), pj = new Registro();
        boolean flag = true;
        int i = inicio, j = fim;
        while(i < j)
        {

            seekArq(i);
            pi.leDoArq(arquivo);
            seekArq(j);
            pj.leDoArq(arquivo);
            if(flag) {
                comp++;
                while(i < j && pi.getNumero() <= pj.getNumero()) {
                    i++;
                    seekArq(i);
                    pi.leDoArq(arquivo);
                    comp++;
                }
            }
            else
            {
                comp++;
                while(j > i && pj.getNumero() >= pi.getNumero())
                {
                    j--;
                    seekArq(j);
                    pj.leDoArq(arquivo);
                    comp++;
                }
            }
            if(i < j) {
                mov += 2;
                seekArq(i);
                pj.gravaNoArq(arquivo);
                seekArq(j);
                pi.gravaNoArq(arquivo);
                flag = !flag;
            }
        }
        if(inicio < i - 1)
            QuickSem(inicio, i - 1);
        if(i + 1 < fim)
            QuickSem(i + 1, fim);
    }
    public void QuickSemPivo()
    {
        QuickSem(0, filesize() - 1);
    }
    public void QuickCom(int inicio, int fim){
        Registro pi = new Registro(), pj = new Registro(), pivo = new Registro();
        seekArq((inicio + fim) / 2);
        pivo.leDoArq(arquivo);
        int i = inicio, j = fim;
        while(i < j)
        {

            seekArq(i);
            pi.leDoArq(arquivo);
            comp++;
            while(pi.getNumero() < pivo.getNumero()) {
                i++;
                seekArq(i);
                pi.leDoArq(arquivo);
                comp++;
            }
            seekArq(j);
            pj.leDoArq(arquivo);
            comp++;
            while(pj.getNumero() > pivo.getNumero())
            {
                j--;
                seekArq(j);
                pj.leDoArq(arquivo);
                comp++;
            }
            if(i <= j) {
                mov += 2;
                if(i != j) {
                    seekArq(i);
                    pj.gravaNoArq(arquivo);
                    seekArq(j);
                    pi.gravaNoArq(arquivo);
                }
                i++;
                j--;
            }
        }
        if(inicio < j)
            QuickCom(inicio, j);
        if(i < fim)
            QuickCom(i, fim);
    }
    public void QuickComPivo()
    {
        QuickCom(0, filesize() - 1);
    }
    public void delete(Arquivo arq, String nomearq)
    {
        try {
            arq.getFile().close();
        }
        catch(Exception e) {

        }
        Path path = Paths.get(nomearq);
        path.toFile().delete();
    }
    public void Particao(Arquivo a1, Arquivo a2)
    {
        Registro r1 = new Registro(), r2 = new Registro();
        a1.truncate(0);
        a2.truncate(0);
        for(int i = 0; i < filesize() / 2; i++)
        {
            seekArq(i);
            r1.leDoArq(arquivo);
            r1.gravaNoArq(a1.getFile());
            seekArq(filesize() / 2 + i);
            r2.leDoArq(arquivo);
            r2.gravaNoArq(a2.getFile());
        }
    }
    public void fusao(Arquivo a1, Arquivo a2, int seq)
    {
        int i = 0, j = 0, s = seq, k = 0;
        Registro pi = new Registro(), pj = new Registro();
        seekArq(0);
        a1.seekArq(0);
        a2.seekArq(0);
        pi.leDoArq(a1.arquivo);
        pj.leDoArq(a2.arquivo);
        while(k < filesize())
        {
            while(i < seq && j < seq)
            {
                comp++;
                if(pi.getNumero() < pj.getNumero())
                {
                    mov++;
                    seekArq(k);
                    pi.gravaNoArq(arquivo);
                    k++;
                    i++;
                    pi.leDoArq(a1.arquivo);
                }
                else
                {
                    mov++;
                    seekArq(k);
                    pj.gravaNoArq(arquivo);
                    k++;
                    j++;
                    pj.leDoArq(a2.arquivo);
                }
            }
            while(i < seq)
            {
                i++;
                k++;
                pi.gravaNoArq(arquivo);
                pi.leDoArq(a1.arquivo);
                mov++;
            }
            while(j < seq)
            {
                j++;
                k++;
                pj.gravaNoArq(arquivo);
                pj.leDoArq(a2.arquivo);
                mov++;
            }
            seq = seq + s;
        }
    }
    public void Merge1()
    {
        Arquivo a1 = new Arquivo("a1.txt"), a2 = new Arquivo("a2.txt");
        int seq = 1;
        while(seq < filesize())
        {
            Particao(a1, a2);
            fusao(a1, a2, seq);
            seq *= 2;
        }
        delete(a1, "a1.txt");
        delete(a2, "a2.txt");
    }
    public void Fusao(int ini1, int fim1, int ini2, int fim2, Arquivo arq)
    {
        int i = ini1, j = ini2, k = 0;
        arq.truncate(0);
        arq.seekArq(0);
        Registro regi = new Registro(), regj = new Registro();
        while(i <= fim1 && j <= fim2)
        {
            seekArq(i);
            regi.leDoArq(arquivo);
            seekArq(j);
            regj.leDoArq(arquivo);
            comp++;
            if(regi.getNumero() < regj.getNumero())
            {
                regi.gravaNoArq(arq.arquivo);
                k++;
                i++;
            }
            else
            {
                regj.gravaNoArq(arq.arquivo);
                k++;
                j++;
            }
            mov++;
        }
        seekArq(i);
        while(i <= fim1)
        {
            regi.leDoArq(arquivo);
            regi.gravaNoArq(arq.arquivo);
            k++;
            i++;
        }
        seekArq(j);
        while(j <= fim2)
        {
            regj.leDoArq(arquivo);
            regj.gravaNoArq(arq.arquivo);
            k++;
            j++;
        }
        seekArq(ini1);
        for(i = 0; i < k; i++)
        {
            arq.seekArq(i);
            regi.leDoArq(arq.arquivo);
            regi.gravaNoArq(arquivo);
        }
    }
    public void Merge(int esq, int dir, Arquivo arq)
    {
        int meio;
        if(esq < dir)
        {
            meio = (esq + dir) / 2;
            Merge(esq, meio, arq);
            Merge(meio + 1, dir, arq);
            Fusao(esq, meio, meio + 1, dir, arq);
        }
    }
    public void Merge2()
    {
        Arquivo temp = new Arquivo("temp.txt");
        Merge(0, filesize() - 1, temp);
        delete(temp, "temp.txt");
    }
    public void CountingSort()
    {
        int maior = 0, j;
        Arquivo arq = new Arquivo("temp.txt");
        Registro reg = new Registro(), reg2 = new Registro();
        seekArq(0);
        while(!eof())
        {
            reg.leDoArq(arquivo);
            comp++;
            if(reg.getNumero() > maior)
                maior = reg.getNumero();
        }
        int vet[] = new int[maior + 1];
        for(int i = 0; i < vet.length; i++)
            vet[i] = 0;
        seekArq(0);
        for(int i = 0; i < filesize(); i++)
        {
            reg.leDoArq(arquivo);
            j = 0;
            while(j < reg.getNumero())
                j++;
            vet[j] = vet[j] + 1;
        }
        for(int i = 1; i < vet.length; i++)
            vet[i] = vet[i] + vet[i-1];

        seekArq(0);
        for(int i = 0; i < filesize(); i++)
        {
            reg.leDoArq(arquivo);
            j = 0;
            while(j < reg.getNumero())
                j++;
            arq.seekArq(vet[j] - 1);
            mov++;
            reg.gravaNoArq(arq.arquivo);
            vet[j] = vet[j] - 1;
        }
        copiaArquivo(arq.arquivo);
        delete(arq, "temp.txt");
    }
    public void BucketSort()
    {
        Registro reg = new Registro();
        int maior = 0;
        seekArq(0);
        while(!eof()) {
            reg.leDoArq(arquivo);
            if(reg.getNumero() > maior)
                maior = reg.getNumero();
        }
        Arquivo vet[] = new Arquivo[10];
        for(int i = 0; i < vet.length; i++)
            vet[i] = new Arquivo("temp" + i + ".txt");

        seekArq(0);
        for(int i = 0; i < filesize(); i++)
        {
            reg.leDoArq(arquivo);
            int pos = (int)((reg.getNumero() / (double)(maior + 1)) * 10);
            reg.gravaNoArq(vet[pos].arquivo);
        }
        seekArq(0);
        for(int i = 0; i < vet.length; i++)
            vet[i].InsercaoDireta();
        seekArq(0);
        for(int i = 0; i < vet.length; i++)
        {
            vet[i].seekArq(0);
            while(!vet[i].eof())
            {
                reg.leDoArq(vet[i].arquivo);
                reg.gravaNoArq(arquivo);
            }
        }
        for(int i = 0; i < vet.length; i++)
            delete(vet[i], "temp" + i + ".txt");
    }
    public void CountingRadix(int vet[], Arquivo arq)
    {
        Registro reg = new Registro();
        int maior = 0, i, j, pos;
        for(i = 0; i < vet.length; i++)
        {
            if(vet[i] > maior)
                maior = vet[i];
        }
        int vetC[] = new int[maior + 1];
        for(int c = 0; c < vetC.length; c++)
            vetC[c] = 0;
        for(i = 0; i < vet.length; i++)
            vetC[vet[i]] = vetC[vet[i]] + 1;
        for(i = 1; i < vetC.length; i++)
            vetC[i] = vetC[i] + vetC[i - 1];
        Arquivo novo = new Arquivo("novo.txt");
        novo.copiaArquivo(arq.arquivo);
        for(i = vet.length - 1; i >= 0; i--)
        {
            seekArq(i);
            reg.leDoArq(arquivo);
            pos = vetC[vet[i]] - 1;
            mov++;
            novo.seekArq(pos);
            reg.gravaNoArq(novo.arquivo);
            vetC[vet[i]] = vetC[vet[i]] - 1;
        }
        arq.copiaArquivo(novo.arquivo);
        delete(novo, "novo.txt");
    }
    public void RadixSort()
    {
        Registro reg = new Registro();
        int vet[] = new int[filesize()], maior = 0, tamanho = 1, j = 0, pos;
        seekArq(0);
        for(int i = 0; i < filesize(); i++)
        {
            reg.leDoArq(arquivo);
            if(reg.getNumero() > maior)
                maior = reg.getNumero();
        }
        String m = "" + maior;
        for(int i = 0; i < m.length(); i++)
        {
            seekArq(0);
            for(j = 0; j < filesize(); j++)
            {
                reg.leDoArq(arquivo);
                String num = String.valueOf(reg.getNumero());
                pos = num.length() - tamanho;
                if(pos >= 0)
                    vet[j] = num.charAt(pos) - '0';
                else
                    vet[j] = 0;
            }
            CountingRadix(vet, this);
            tamanho++;
        }
    }
    public void CombSort()
    {
        Registro reg = new Registro(), reg2 = new Registro();
        int h = (int)(filesize() / 1.3), i, j;
        boolean troca = true;
        if(h < 1)
            h = 1;
        while(h > 1 || troca)
        {
            troca = false;
            i = 0;
            j = h;
            while(j < filesize())
            {
                seekArq(i);
                reg.leDoArq(arquivo);
                seekArq(j);
                reg2.leDoArq(arquivo);
                comp++;
                if(reg.getNumero() > reg2.getNumero())
                {
                    mov++;
                    seekArq(i);
                    reg2.gravaNoArq(arquivo);
                    seekArq(j);
                    reg.gravaNoArq(arquivo);
                    troca = true;
                }
                i++;
                j++;
            }
            h = (int)(h / 1.3);
            if(h == 9 || h == 10)
                h = 11;
            if(h < 1)
                h = 1;
        }
    }
    public void GnomeSort()
    {
        Registro reg1 = new Registro(), reg2 = new Registro();
        int i;
        for(i = 0; i < filesize() - 1; i++)
        {
            seekArq(i);
            reg1.leDoArq(arquivo);
            reg2.leDoArq(arquivo);
            comp++;
            if(reg1.getNumero() >  reg2.getNumero())
            {
                mov += 2;
                seekArq(i);
                reg2.gravaNoArq(arquivo);
                reg1.gravaNoArq(arquivo);
                for(int j = i; j > 0; j--)
                {
                    seekArq(j);
                    reg1.leDoArq(arquivo);
                    seekArq(j - 1);
                    reg2.leDoArq(arquivo);
                    comp++;
                    if(reg1.getNumero() < reg2.getNumero())
                    {
                        mov += 2;
                        reg2.gravaNoArq(arquivo);
                        seekArq(j - 1);
                        reg1.gravaNoArq(arquivo);
                    }
                }
            }
        }
    }
    public void InsercaoDiretaTim(int inicio, int fim)
    {
        int pos;
        Registro reg = new Registro(), regpos = new Registro();
        seekArq(inicio + 1);
        for(int i = inicio + 1; i < fim; i++)
        {
            seekArq(i);
            reg.leDoArq(arquivo);
            pos = i;
            seekArq(pos - 1);
            regpos.leDoArq(arquivo);
            comp++;
            while(pos > inicio && reg.getNumero() < regpos.getNumero())
            {
                mov++;
                seekArq(pos);
                regpos.gravaNoArq(arquivo);
                pos--;
                if(pos > inicio)
                {
                    seekArq(pos - 1);
                    regpos.leDoArq(arquivo);
                }
                comp++;
            }
            seekArq(pos);
            reg.gravaNoArq(arquivo);
        }
    }
    public void TimSort()
    {
        Arquivo arq = new Arquivo("temp.txt");
        int inicio = 0, fim = 32, ini1, f1, ini2, f2, run = 32;
        if(filesize() <= 32)
            InsercaoDiretaTim(0, filesize());
        else
        {
            while(inicio < filesize())
            {
                if(fim <= filesize())
                    InsercaoDiretaTim(inicio, fim);
                else
                {
                    fim = filesize();
                    InsercaoDiretaTim(inicio, fim);
                }
                inicio += 32;
                fim += 32;
            }
        }
        while(run < filesize())
        {
            ini1 = 0;
            f1 = ini1 + run;
            if(f1 > filesize())
                f1 = filesize();
            while(ini1 < filesize())
            {
                ini2 = f1;
                f2 = ini2 + run;
                if(ini2 < filesize())
                {
                    if(f2 <= filesize())
                        Fusao(ini1, f1 - 1, ini2, f2 - 1, arq);
                    else
                        Fusao(ini1, f1 - 1, ini2, filesize()-1, arq);
                }
                ini1 = f2;
                f1 = ini1 + run;
                if(f1 > filesize())
                    f1 = filesize();
            }
            run = run * 2;
        }
        delete(arq, "temp.txt");
    }
}
