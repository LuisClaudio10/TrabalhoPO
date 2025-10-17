package org.example.ListasSort.Listas;

public class Lista {
    private No inicio;
    private No fim;

    public Lista() {
        this.inicio = null;
        this.fim = null;
    }
    public void InserirnoFim(int info){
        No caixa = new No(info, null, null);
        if(inicio == null){
            inicio = fim = caixa;
        }
        else
        {
            caixa.setAnt(fim);
            fim.setProx(caixa);
            fim = caixa;
        }
    }
    public boolean buscar(int info){
        No aux = inicio;
        while(aux != null && info != aux.getInfo()){
            aux = aux.getProx();
        }
        if(aux != null)
            return true;

        return false;
    }
    public void inserirAleatorio(){
        int i = 0;
        while(getTl() < 32)
        {
            int numero = (int)(Math.random() * 32) + 1;
            if(!buscar(numero)) {
                InserirnoFim(numero);
            }
        }
    }
    public int getTl(){
        No caixa = inicio;
        int cont = 0;
        while(caixa != null){
            caixa = caixa.getProx();
            cont++;
        }
        return cont;
    }
    public void InsercaoDireta() {
        int aux = 0, cont = 0, TL = getTl();
        No pos = null, auxiliar;
        auxiliar = inicio;
        for (int i = 1; i < TL; i++) {
            while (auxiliar != null && cont < i) {
                auxiliar = auxiliar.getProx();
                cont++;
            }
            aux = auxiliar.getInfo();
            pos = auxiliar;
            while (pos != inicio && aux < pos.getAnt().getInfo()) {
                pos.setInfo(pos.getAnt().getInfo());
                pos = pos.getAnt();
            }
            pos.setInfo(aux);
        }
    }
    public No deslocarmeio(No aux, int i, char L, int m)
    {
        No T = aux;
        if(L == 'D')
            while(i < m)
            {
                T = T.getProx();
                i++;
            }
        else
            while(i > m)
            {
                T = T.getAnt();
                i--;
            }
        return T;
    }
    public No buscaBinaria(int chave, int TL)
    {
        No meio = null, inicio = null, fim = null, aux;
        int cont = 0, i = 0, f = TL-1, m = f / 2;
        aux = this.inicio;
        meio = deslocarmeio(aux, 0, 'D', m);
        fim = deslocarmeio(meio, m, 'D', TL);
        while(i < f && chave != meio.getInfo())
        {
            if(chave > meio.getInfo()) {
                inicio = meio.getProx();
                i = m + 1;
                aux = deslocarmeio(inicio, i, 'D', (i + f)/2);
            }
            else {
                fim = meio.getAnt();
                f = m - 1;
                aux = deslocarmeio(fim, f, 'E', (i + f)/2);
            }
            m = (i + f) / 2;
            if(aux != null)
                meio = aux;
        }
        if(chave > meio.getInfo())
            return meio.getProx();
        return meio;
    }
    public void InsercaoBinaria()
    {
        int aux = 0, TL = getTl();
        No pos = null, auxiliar, a;
        auxiliar = inicio;
        int i;
        for(i = 1; i < TL; i++)
        {
            auxiliar = auxiliar.getProx();
            aux = auxiliar.getInfo();
            a = auxiliar;
            pos = buscaBinaria(aux, i);
            while(a != pos)
            {
                a.setInfo(a.getAnt().getInfo());
                a = a.getAnt();
            }
            pos.setInfo(aux);
        }
    }
    public void SelecaoDireta(){
        No I = inicio, J, pos = null;
        int menor;
        while(I.getProx() != null)
        {
            J = I.getProx();
            menor = I.getInfo();
            pos = I;
            while(J != null)
            {
                if(J.getInfo() < menor)
                {
                    menor = J.getInfo();
                    pos = J;
                }
                J = J.getProx();
            }
            pos.setInfo(I.getInfo());
            I.setInfo(menor);
            I = I.getProx();
        }
    }
    public void Bolha()
    {
        No i;
        boolean flag = true;
        int TL = getTl(), aux;
        while(TL > 1 && flag)
        {
            flag = false;
            i = inicio;
            for(int j = 0; j < TL - 1; j++)
            {
                if(i.getInfo() > i.getProx().getInfo())
                {
                    aux = i.getInfo();
                    i.setInfo(i.getProx().getInfo());
                    i.getProx().setInfo(aux);
                    flag = true;
                }
                i = i.getProx();
            }
            TL--;
        }
    }
    public void ShakeSort()
    {
        No inicio = this.inicio, fim = this.fim, auxiliar;
        int i = 0, f = getTl()-1, aux;
        boolean flag = true;
        while(i < f && flag)
        {
            flag = false;
            auxiliar = inicio;
            for(int j = i; j < f; j++)
            {
                if(auxiliar.getInfo() > auxiliar.getProx().getInfo())
                {
                    aux = auxiliar.getInfo();
                    auxiliar.setInfo(auxiliar.getProx().getInfo());
                    auxiliar.getProx().setInfo(aux);
                    flag = true;
                }
                auxiliar = auxiliar.getProx();
            }
            f--;
            auxiliar = deslocarmeio(inicio, i, 'D', f);
            for(int j = f; j > i; j--)
            {
                if(auxiliar.getInfo() < auxiliar.getAnt().getInfo())
                {
                    aux = auxiliar.getInfo();
                    auxiliar.setInfo(auxiliar.getAnt().getInfo());
                    auxiliar.getAnt().setInfo(aux);
                    flag = true;
                }
                auxiliar = auxiliar.getAnt();
            }
            i++;
            inicio = inicio.getProx();
        }
    }
    public void HeapSort()
    {
        int TL = getTl(), FE, FD, pai, aux;
        No TL2 = fim, p, fe, fd, maior, ini = inicio;
        while(TL > 1)
        {
            pai = TL / 2 - 1;
            p = deslocarmeio(inicio, 0, 'D', pai);
            while(pai >= 0)
            {
                FE = 2 * pai + 1;
                FD = FE + 1;
                fe = deslocarmeio(p, pai, 'D', FE);
                maior = fe;
                fd = fe.getProx();
                if(FD < TL && fd.getInfo() > fe.getInfo())
                        maior = fd;
                if(maior.getInfo() > p.getInfo())
                {
                    aux = p.getInfo();
                    p.setInfo(maior.getInfo());
                    maior.setInfo(aux);
                }
                pai--;
                p = p.getAnt();
            }
            aux = TL2.getInfo();
            TL2.setInfo(ini.getInfo());
            ini.setInfo(aux);
            TL2 = TL2.getAnt();
            TL--;
        }
    }
    public void ShellSort()
    {
        No D, p, pi, auxiliar;
        int dist = 1, pos, i, aux;
        while(dist < getTl())
            dist = dist * 2 + 1;
        dist = dist / 2;
        while(dist > 0)
        {
            for(i = dist; i < getTl(); i++)
            {
                pi = deslocarmeio(inicio, 0, 'D', i);
                aux = pi.getInfo();
                pos = i;
                p = pi;
                auxiliar = deslocarmeio(inicio, 0, 'D', pos - dist);
                while(pos >= dist && aux < auxiliar.getInfo())
                {
                    p.setInfo(auxiliar.getInfo());
                    pos = pos - dist;
                    p = deslocarmeio(inicio, 0, 'D', pos);
                    auxiliar = deslocarmeio(inicio, 0, 'D', pos - dist);
                }
                p.setInfo(aux);
            }
            dist = dist / 2;
        }
    }
    public void CountingSort()
    {
        int maior = inicio.getInfo(), i, pos, vet[];
        No aux = inicio.getProx(), auxiliar;
        while (aux != null)
        {
            if(aux.getInfo() > maior)
                maior = aux.getInfo();
            aux = aux.getProx();
        }
        vet = new int[maior + 1];
        for(i = 0; i < vet.length; i++)
            vet[i] = 0;
        aux =  inicio;
        while(aux != null)
        {
            i = 0;
            while(i < aux.getInfo())
                i++;
            vet[i] = vet[i] + 1;
            aux = aux.getProx();
        }
        for(i = 1; i < vet.length; i++)
            vet[i] = vet[i] + vet[i-1];
        aux = inicio;
        Lista cf = new Lista();
        for(i = 0; i < getTl(); i++)
            cf.InserirnoFim(0);
        while(aux != null)
        {
            i = 0;
            while(i < aux.getInfo())
                i++;
            pos = vet[i] - 1;
            vet[i] = vet[i] - 1;
            auxiliar = deslocarmeio(cf.inicio, 0, 'D', pos);
            auxiliar.setInfo(aux.getInfo());
            if(auxiliar.getProx() == null)
                cf.fim = auxiliar;
            aux = aux.getProx();
        }
        inicio = cf.inicio;
        fim = cf.fim;
    }
    public void InsertionSortBucket(No inicio)
    {
        No aux = inicio.getProx(), pos;
        int auxiliar;
        while(aux != null)
        {
            auxiliar = aux.getInfo();
            pos = aux;
            while(pos != inicio && auxiliar < pos.getAnt().getInfo())
            {
                pos.setInfo(pos.getAnt().getInfo());
                pos = pos.getAnt();
            }
            pos.setInfo(auxiliar);
            aux = aux.getProx();
        }
    }
    public void BucketSort()
    {
        int pos, i, maior;
        Lista vet[] = new Lista[10];
        for(i = 0; i < vet.length; i++)
            vet[i] = new Lista();
        No aux = inicio.getProx(), auxiliar;
        maior = aux.getInfo();
        while(aux != null)
        {
            if(aux.getInfo() > maior)
                maior = aux.getInfo();
            aux = aux.getProx();
        }
        aux = inicio;
        while(aux != null)
        {
            pos = (int)((aux.getInfo() / (double)(maior + 1)) * 10);
            vet[pos].InserirnoFim(aux.getInfo());
            aux = aux.getProx();
        }
        for(i = 0; i < vet.length; i++)
        {
            if(vet[i].inicio != null)
                InsertionSortBucket(vet[i].inicio);
        }
        auxiliar = inicio;
        for(i = 0; i < vet.length; i++)
        {
            aux = vet[i].inicio;
            while(aux != null) {
                auxiliar.setInfo(aux.getInfo());
                aux = aux.getProx();
                auxiliar = auxiliar.getProx();
            }
        }
    }
    public void CountingSortRadix(int vet[])
    {
        int maior = vet[0], pos;

        int vetC[] = new int[10];

        for(int i = 0; i < vetC.length; i++)
            vetC[i] = 0;

        for(int i = 0; i < vet.length; i++)
            vetC[vet[i]] = vetC[vet[i]] + 1;

        for(int i = 1; i < vetC.length; i++)
            vetC[i] = vetC[i] + vetC[i-1];
        Lista c = new Lista();
        No aux, auxiliar = fim;
        for(int i = 0; i < getTl(); i++)
            c.InserirnoFim(0);
        for(int i = vet.length - 1; i >= 0; i--)
        {
            pos = vetC[vet[i]] - 1;
            aux = deslocarmeio(c.inicio, 0, 'D', pos);
            aux.setInfo(auxiliar.getInfo());
            vetC[vet[i]] = vetC[vet[i]] - 1;
            auxiliar = auxiliar.getAnt();
        }
        inicio = c.inicio;
        fim = c.fim;
    }
    public void RadixSort()
    {
        int vet[] = new int[getTl()], maior, tamanho = 1, j = 0;
        No aux = inicio.getProx();
        maior = inicio.getInfo();
        while(aux != null)
        {
            if(aux.getInfo() > maior)
                maior = aux.getInfo();
            aux = aux.getProx();
        }
        int i = 0;
        String m = "" + maior;
        while(i < m.length())
        {
            aux = inicio;
            j = 0;
            while(aux != null)
            {
                String num = String.valueOf(aux.getInfo());
                int pos = num.length() - tamanho;
                if(pos >= 0)
                    vet[j] = num.charAt(pos) - '0';
                else
                    vet[j] = 0;
                j++;
                aux = aux.getProx();
            }
            CountingSortRadix(vet);
            tamanho++;
            i++;
        }
    }
    public void CombSort()
    {
        int Tl = getTl(), h, i, j, aux;
        boolean troca = true;
        No Pi, Pj;
        h = (int)(Tl / 1.3);
        if(h < 1)
            h = 1;
        while(h > 1 || troca)
        {
            troca = false;
            i = 0;
            j = h;
            while(j < Tl)
            {
                Pi = deslocarmeio(inicio, 0, 'D', i);
                Pj = deslocarmeio(inicio, 0, 'D', j);
                if(Pi.getInfo() > Pj.getInfo())
                {
                    aux = Pi.getInfo();
                    Pi.setInfo(Pj.getInfo());
                    Pj.setInfo(aux);
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
        No Pi, Pj, Pa, Pa2;
        int Tl = getTl(), i, aux;
        i = 0;
        while(i < Tl - 1)
        {
            Pi = deslocarmeio(inicio, 0, 'D', i);
            Pj = deslocarmeio(inicio, 0, 'D', i + 1);
            if(Pi.getInfo() > Pj.getInfo())
            {
                aux = Pi.getInfo();
                Pi.setInfo(Pj.getInfo());
                Pj.setInfo(aux);
                Pa = Pj.getAnt();
                while(Pa != inicio)
                {
                    Pa2 = Pa.getAnt();
                    if(Pa.getInfo() < Pa2.getInfo())
                    {
                        aux = Pa.getInfo();
                        Pa.setInfo(Pa2.getInfo());
                        Pa2.setInfo(aux);
                    }
                    Pa = Pa.getAnt();
                }
            }
            i++;
        }
    }
    public void QuickSortSemPivo()
    {
        Quick(0, getTl()-1);
    }
    public void Quick(int inicio, int fim)
    {
        No Pi, Pj;
        int i = inicio, j = fim, aux;
        while(i < j)
        {
            Pi = deslocarmeio(this.inicio, 0, 'D', i);
            Pj = deslocarmeio(this.inicio, 0, 'D', j);
            while(i < j && Pi.getInfo() <= Pj.getInfo())
            {
                i++;
                Pi = Pi.getProx();
            }
            aux = Pi.getInfo();
            Pi.setInfo(Pj.getInfo());
            Pj.setInfo(aux);
            while(i < j && Pj.getInfo() >= Pi.getInfo())
            {
                j--;
                Pj = Pj.getAnt();
            }
            aux = Pj.getInfo();
            Pj.setInfo(Pi.getInfo());
            Pi.setInfo(aux);
        }
        if(inicio < i - 1)
            Quick(inicio, i-1);
        if(j + 1 < fim)
            Quick(j + 1, fim);
    }
    public void QuickPivo(int inicio, int fim)
    {
        No Pi, Pj, Pivo;
        int i = inicio, j = fim, aux, pivo = (inicio + fim) / 2;
        Pivo = deslocarmeio(this.inicio, 0, 'D', pivo);
        pivo = Pivo.getInfo();
        Pi = deslocarmeio(this.inicio, 0, 'D', i);
        Pj = deslocarmeio(this.inicio, 0, 'D', j);
        while(i < j)
        {
            while(Pi.getInfo() < pivo)
            {
                i++;
                Pi = Pi.getProx();
            }
            while(Pj.getInfo() > pivo)
            {
                j--;
                Pj = Pj.getAnt();
            }
            if(i <= j)
            {
                aux = Pi.getInfo();
                Pi.setInfo(Pj.getInfo());
                Pj.setInfo(aux);
                Pi = Pi.getProx();
                i++;
                j--;
                Pj = Pj.getAnt();
            }
        }
        if(inicio < j)
            QuickPivo(inicio, j);
        if(i < fim)
            QuickPivo(i, fim);
    }
    public void QuickSortComPivo()
    {
        QuickPivo(0, getTl()-1);
    }
    public void Particao(Lista l1, Lista l2)
    {
        int i = 0;
        No aux = inicio;
        while(i < getTl() / 2)
        {
            l1.InserirnoFim(aux.getInfo());
            i++;
            aux = aux.getProx();
        }
        while(aux != null)
        {
            l2.InserirnoFim(aux.getInfo());
            aux = aux.getProx();
        }
    }
    public void Fusao(Lista l1, Lista l2, int seq)
    {
        No Pi = l1.inicio, Pj = l2.inicio, aux = inicio;
        int i = 0, j = 0, S = seq, k = 0;
        while(k < getTl())
        {
            while(i < seq && j < seq)
            {
                if(Pi.getInfo() < Pj.getInfo())
                {
                    aux.setInfo(Pi.getInfo());
                    i++;
                    Pi = Pi.getProx();
                }
                else
                {
                    aux.setInfo(Pj.getInfo());
                    j++;
                    Pj = Pj.getProx();
                }
                aux = aux.getProx();
                k++;
            }
            while(i < seq)
            {
                aux.setInfo(Pi.getInfo());
                aux = aux.getProx();
                k++;
                Pi = Pi.getProx();
                i++;
            }
            while(j < seq)
            {
                aux.setInfo(Pj.getInfo());
                aux = aux.getProx();
                k++;
                Pj = Pj.getProx();
                j++;
            }
            seq = seq + S;
        }
    }
    public void MergeSort1()
    {
        int seq = 1;
        Lista v1 = new Lista();
        Lista v2 = new Lista();
        while(seq < getTl())
        {
            Particao(v1, v2);
            Fusao(v1, v2, seq);
            seq = seq * 2;
            v1 = new Lista();
            v2 = new Lista();
        }
    }
    public void fusao(Lista L, int ini1, int fim1, int ini2, int fim2)
    {
        int i = ini1, j = ini2, k = 0;
        No Pi, Pj, aux, aux1;
        L.inicio = L.fim = null;
        Pi = deslocarmeio(inicio, 0, 'D', i);
        Pj = deslocarmeio(inicio, 0, 'D', j);
        while(i <= fim1 && j <= fim2)
        {
            if(Pi.getInfo() < Pj.getInfo())
            {
                L.InserirnoFim(Pi.getInfo());
                Pi = Pi.getProx();
                i++;
            }
            else
            {
                L.InserirnoFim(Pj.getInfo());
                Pj = Pj.getProx();
                j++;
            }
            k++;
        }
        while(i <= fim1)
        {
            L.InserirnoFim(Pi.getInfo());
            k++;
            Pi = Pi.getProx();
            i++;
        }
        while(j <= fim2)
        {
            L.InserirnoFim(Pj.getInfo());
            k++;
            Pj = Pj.getProx();
            j++;
        }
        aux1 = L.inicio;
        aux = deslocarmeio(inicio, 0, 'D', 0 + ini1);
        for(i = 0; i < k; i++)
        {
            aux.setInfo(aux1.getInfo());
            aux1 = aux1.getProx();
            aux = aux.getProx();
        }
    }
    public void Merge(Lista L, int esq, int dir)
    {
        int meio;
        if(esq < dir)
        {
            meio = (esq + dir) / 2;
            Merge(L, esq, meio);
            Merge(L, meio + 1, dir);
            fusao(L, esq, meio, meio + 1, dir);
        }
    }
    public void MergeSort2()
    {
        Lista L = new Lista();
        Merge(L, 0, getTl()-1);
    }
    public void InsercaoDiretaTim(int inicio, int fim)
    {
        int aux, qtde = fim - inicio;
        No pos, auxiliar, ini = deslocarmeio(this.inicio, 0, 'D', inicio);
        auxiliar = ini.getProx();
        for(int i = 1; i < qtde && auxiliar != null; i++)
        {
            aux = auxiliar.getInfo();
            pos = auxiliar;
            while(pos != ini && aux < pos.getAnt().getInfo())
            {
                pos.setInfo(pos.getAnt().getInfo());
                pos = pos.getAnt();
            }
            pos.setInfo(aux);
            auxiliar = auxiliar.getProx();
        }
    }
    public void TimSort()
    {
        int inicio = 0, fim = 32, ini1, f1, ini2, f2, run;
        if(getTl() <= 32)
            InsercaoDiretaTim(0, getTl());
        else
        {
            while(inicio < getTl())
            {
                if(fim <= getTl())
                    InsercaoDiretaTim(inicio, fim);
                else
                {
                    fim = getTl();
                    InsercaoDiretaTim(inicio, fim);
                }
                inicio += 32;
                fim += 32;
            }
            Lista l = new Lista();
            run = 32;
            while(run < getTl())
            {
                ini1 = 0;
                f1 = ini1 + run;
                if(f1 > getTl())
                    f1 = getTl();
                while(ini1 < getTl())
                {
                    ini2 = f1;
                    f2 = ini2 + run;
                    if(ini2 < getTl())
                    {
                        if(f2 <= getTl())
                            fusao(l, ini1, f1 - 1, ini2, f2 - 1);
                        else
                            fusao(l, ini1, f1 - 1, ini2, getTl() - 1);
                    }
                    ini1 = f2;
                    f1 = ini1 + run;
                    if(f1 > getTl())
                        f1 = getTl();
                }
                run = run * 2;
            }
        }
    }
    public void exibir(){
        No aux = inicio;
        while(aux != null){
            System.out.println(aux.getInfo());
            aux = aux.getProx();
        }
    }
}
