# GREMLINS 
### GeREnciador de Memoria com LIsta eNcadeada Simples

## Descrição

O GREMLINS é um gerenciador de memória que requisita ao sistema operacional um determinado bloco de memória e a partir do mesmo são realizadas operações de alocação de memória em blocos menores quando solicitado pelo cliente. No gremlins existem duas funções uma Allocate e uma Free nas quais uma realiza alocações e a outra libera memória alocada. Neste trabalho tivemos a colaboração do Professor Selan, na funcão de testes StoragePoolTest com o uso da priority_queue a qual até então não estava sendo utilizada. 


## Compilação e execução
**Avaliando o Desempenho do GM**
```shell
g++ -std=c++11 -pedantic -I include/ src/driver_gremlins.cpp -o bin/exe && ./bin/exe
```

**Teste GM vs SO**
```shell
g++ -std=c++11 -pedantic -I include/ src/drive_comparador.cpp -o bin/exe && ./bin/exe 
```

**Teste de Allocate e Free**
```shell
g++ -std=c++11 -pedantic -I include/ src/driver_generico.cpp -o bin/exe -D TIPO1 && ./bin/exe
```

**Teste de Allocate Best e Free**
```shell
g++ -std=c++11 -pedantic -I include/ src/driver_generico.cpp -o bin/exe -D TIPO2 && ./bin/exe
```

**Teste Geral**
```shell
sh run.sh
```

## Autores:

Adelino Afonso Fernandes Avelino
 - [GitHub](https://github.com/aafavelino)

Irene Ginani Costa Pinheiro 
 - [GitHub](https://github.com/IreneGinani)

##Politica de colaboração

**Colaborador**

Selan R. dos Santos
 - [DIMAp](https://www.dimap.ufrn.br/~selan/index.html)

## Disponível em:

[Projeto Gremlins](https://github.com/aafavelino/GREMLINS)



