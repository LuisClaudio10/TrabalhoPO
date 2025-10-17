# Trabalho do 1º Bimestre - Métodos de Ordenação

## Descrição

Este projeto tem como objetivo a implementação de diversos algoritmos de ordenação aplicados a **Listas Encadeadas** e **Arquivos Binários**, com a geração de métricas sobre as operações realizadas (comparações e movimentações). Através da implementação desses algoritmos, é possível analisar o desempenho de cada um em diferentes tipos de dados, como ordenados, em ordem reversa e randômicos.

Os algoritmos implementados seguem os tópicos estudados na disciplina de **Pesquisa e Ordenação**, incluindo métodos clássicos e outros menos tradicionais. O projeto foi desenvolvido em **Java**, com foco na manipulação de arquivos binários e listas encadeadas.

## Algoritmos Implementados

### Algoritmos de Ordenação em **Lista Encadeada**:
- **Inserção Direta**
- **Inserção Binária**
- **Seleção Direta**
- **Bolha**
- **Shake**
- **Shell**
- **Heap**
- **Quick Sort (com e sem pivô)**
- **Fusão Direta (Merge) - duas implementações**

### Algoritmos a serem pesquisados e implementados:
- **Counting Sort**
- **Bucket Sort**
- **Radix Sort**
- **Comb Sort**
- **Gnome Sort**
- **Tim Sort**

### Algoritmos de Ordenação em **Arquivos Binários**:
A ordenação será aplicada em três tipos de arquivos:
1. **Ordenado**
2. **Ordem Reversa**
3. **Randômico**

## Funcionalidades

- **Medição de Desempenho**: Cada algoritmo possui contadores de **comparações** e **movimentações**, sendo estes valores armazenados em uma tabela que será gerada e gravada em um arquivo de texto.
  
- **Estruturas de Dados**: Utilização de uma **Lista Encadeada** para os métodos de ordenação e **Arquivo Binário** para armazenar e processar os dados.

- **Classes Principais**:
    - `Registro`: Representa um item que será ordenado, com um campo de número inteiro e um campo de lixo de 2044 bytes.
    - `Arquivo`: Gerencia a leitura e escrita dos arquivos binários, além de implementar os métodos de ordenação.
    - `Principal`: Orquestra a execução do programa, gerenciando a geração de arquivos, medições e exibição dos resultados.

## Implementação

### Estruturas Principais

1. **Classe Registro**
    - Contém o número (4 bytes) e um campo de lixo (2044 bytes).
    - Métodos para gravar e ler no arquivo binário.

2. **Classe Arquivo**
    - Gerencia a criação e manipulação dos arquivos binários.
    - Implementa os métodos de ordenação e coleta de métricas.

3. **Classe Principal**
    - Gera a tabela de comparações, movimentações e tempos de execução para cada algoritmo de ordenação.

### Exemplo de Uso

```java
public class Principal {
    public static void main(String[] args) {
        Principal p = new Principal();
        p.geraTabela();
    }
}
