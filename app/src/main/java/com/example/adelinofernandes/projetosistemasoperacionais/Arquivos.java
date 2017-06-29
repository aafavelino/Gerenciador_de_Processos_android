package com.example.adelinofernandes.projetosistemasoperacionais;

        import android.util.Log;

        import java.io.File;
        import java.io.BufferedReader;
        import java.util.ArrayList;
        import java.io.FileReader;
        import java.io.IOException;
        import java.util.HashMap;
        import java.util.Map;
        import java.util.Scanner;
        import java.util.StringTokenizer;


/**
 * Created by adelinofernandes on 19/06/17.
 */

public class Arquivos {

    public static boolean Numero(String s) {
        // cria um array de char
        char[] c = s.toCharArray();
        boolean d = true;
        for ( int i = 0; i < c.length; i++ ){
            // verifica se o char não é um dígito
            if ( !Character.isDigit(c[i])) {
                d = false;
                break;
            }
        }
        return d;
    }

    private String diretorio = "/proc";
    private static ArrayList<String> arqs = new ArrayList<String>();

    public Arquivos() throws IOException {

        File file = new File(diretorio);
        File afile[] = file.listFiles();
        int i = 0;
        for (int j = afile.length; i < j; i++) {
            File arquivos = afile[i];
            if (Numero(arquivos.getName())){
                arqs.add(arquivos.getName());
            }
        }

    }
    public static float dadosTotais(){
        float porcentagem = 0;
        StringBuilder retorno = new StringBuilder();
        try{
            FileReader arq = new FileReader("/proc/meminfo");
            BufferedReader lerArq = new BufferedReader(arq);
            String linha0 = lerArq.readLine();
            String linha1 = lerArq.readLine();
            String[] free = linha1.replaceAll("[ ]+", " ").split(" ");
            String[] total = linha0.replaceAll("[ ]+", " ").split(" ");
            int mem_total = Integer.parseInt(total[1]);
            int mem_livre = Integer.parseInt(free[1]);
            int calma = mem_total - mem_livre;
            porcentagem = 100 - ((mem_livre * 100)/mem_total);
            retorno.append("Memória usada: ");
            retorno.append(porcentagem); // vc ta usando tanto da memória do seu celular, feche algumas aplicações para melhorar o funcionamento.
            return porcentagem;
        }catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
        return porcentagem;
    }


    public static String processoMaior() {
        StringBuilder retorno = new StringBuilder();
        ArrayList<String> ddd = new ArrayList<String>();
        float totalCPUuse = 0;
        File file = new File("/proc");
        File afile[] = file.listFiles();
        int p = 0;
        for (int j = afile.length; p < j; p++) {
            File arquivos = afile[p];
            if (Numero(arquivos.getName())){
                ddd.add(arquivos.getName());
            }
        }
        try{
            String processo = null;
            float maior = 0;
            float porcm = 0;

            for(int i = 0; i < ddd.size(); i++){
                FileReader arq1 = new FileReader("/proc/"+ddd.get(i)+"/stat");
                BufferedReader lerArq1 = new BufferedReader(arq1);
                String linha1 = lerArq1.readLine();

                while (linha1 != null) {
                    String texto_split1 [] = linha1.split(" ");
                    float x = Float.parseFloat(texto_split1[23]);
                    if (maior < x){
                        porcm = x;
                        processo = texto_split1[1];
                    }

                    linha1 = lerArq1.readLine(); //  ps -o etime 1( tempo do processo aberto) lê da segunda até a última linha, talvez 14 + 13, tenho que pegar a memoria total, ver as procentagens, e daí mandar notificações caso esteja usando muito do que o além. exibir tbm os que não são de sistema.
                }
                arq1.close();
            }
            if(porcm > 1.0){
                retorno.append("Para melhorar o funcionamento do dispositivo, feche o programa: ");
                retorno.append(processo);
            }
            return retorno.toString();
        }catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s. Na função Maior\n",
                    e.getMessage());
        }
        return retorno.toString();
    }



    public static float cpuTotal(){
        StringBuilder retorno = new StringBuilder();
        ArrayList<String> ddd = new ArrayList<String>();
        float totalCPUuse = 0;
        File file = new File("/proc");
        File afile[] = file.listFiles();
        int p = 0;
        for (int j = afile.length; p < j; p++) {
            File arquivos = afile[p];
            if (Numero(arquivos.getName())){
                ddd.add(arquivos.getName());
            }
        }
        try {
            int c = 0;
            float jiffies_d = 0, jiffies_o = 0, cpuDelta = 0, totalProcessDelta = 0, processo_d = 0;
            float total = 0, total2 = 0;
            Map<String,Float> processos = new HashMap<String,Float>();
            while(c < 2){
                FileReader arq1 = new FileReader("/proc/stat");
                BufferedReader lerArq1 = new BufferedReader(arq1);
                String linha1 = lerArq1.readLine();
                String texto_split1 [] = linha1.split(" ");
                linha1 = lerArq1.readLine();
                String texto_split2 [] = linha1.split(" ");
                linha1 = lerArq1.readLine();
                String texto_split3 [] = linha1.split(" ");
                float jiffies_c = 0;
                for(int i = 0; i < 3; i++){
                    if (i > 1){
                        int aux = Integer.parseInt(texto_split1[i]);
                        int aux1 = Integer.parseInt(texto_split2[i]);
                        int aux2 = Integer.parseInt(texto_split3[i]);
                        jiffies_c = jiffies_c + aux + aux1 + aux2;
                    }
                }
                cpuDelta = (jiffies_c - jiffies_o);
                jiffies_o = jiffies_c;
                for(int i = 0; i < ddd.size(); i++){
                    Arquivos arquivos = new Arquivos();
                    FileReader arq = new FileReader("/proc/"+ddd.get(i)+"/stat");
                    BufferedReader lerArq = new BufferedReader(arq);
                    String linha = lerArq.readLine();
                    while (linha != null){
                        String texto_split [] = linha.split(" ");
                        int numero = Integer.parseInt(texto_split[14]);
                        int numero1 = Integer.parseInt(texto_split[15]);
                        total =  numero + numero1;
                        if (!(processos.containsKey(ddd.get(i)))){
                            processos.put(ddd.get(i),total);
                        }else{
                            processo_d = total - (processos.get(ddd.get(i)));
                            totalProcessDelta = totalProcessDelta + processo_d;
                            float cpuUsageResult = 100 * (processo_d/cpuDelta);
                            processos.remove(ddd.get(i));
                            processos.put(ddd.get(i), total);
                        }
                        linha = lerArq.readLine();
                    }
                    arq.close();
                }
                totalCPUuse = (float)totalProcessDelta;
                c++;
            }
            return totalCPUuse;
        } catch (IOException e)  {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
        return totalCPUuse;
    }

    public static String ComcpuTotal(){
        StringBuilder retorno = new StringBuilder();
        ArrayList<String> ddd = new ArrayList<String>();
        File file = new File("/proc");
        File afile[] = file.listFiles();
        int p = 0;
        for (int j = afile.length; p < j; p++) {
            File arquivos = afile[p];
            if (Numero(arquivos.getName())){
                ddd.add(arquivos.getName());
            }
        }
        try {
            int c = 0;
            float jiffies_d = 0, jiffies_o = 0, cpuDelta = 0, totalProcessDelta = 0, processo_d = 0;
            float total = 0, total2 = 0;
            float totalCPUuse = 0;
            Map<String,Float> processos = new HashMap<String,Float>();
            Map<String,Float> porcentagens = new HashMap<String,Float>();
          while(c < 10){
                FileReader arq1 = new FileReader("/proc/stat");
                BufferedReader lerArq1 = new BufferedReader(arq1);
                String linha1 = lerArq1.readLine();
                String texto_split1 [] = linha1.split(" ");
                linha1 = lerArq1.readLine();
                String texto_split2 [] = linha1.split(" ");
                linha1 = lerArq1.readLine();
                String texto_split3 [] = linha1.split(" ");
                float jiffies_c = 0;
                for(int i = 0; i < 11; i++){
                    if (i > 1){
                        int aux = Integer.parseInt(texto_split1[i]);
                        int aux1 = Integer.parseInt(texto_split2[i]);
                        int aux2 = Integer.parseInt(texto_split3[i]);
                        jiffies_c = jiffies_c + aux + aux1 + aux2;
                    }
                }
                cpuDelta = (jiffies_c - jiffies_o);
                jiffies_o = jiffies_c;
                totalProcessDelta = 0;
                for(int i = 0; i < ddd.size(); i++){
                    FileReader arq = new FileReader("/proc/"+ddd.get(i)+"/stat");
                    BufferedReader lerArq = new BufferedReader(arq);
                    String linha = lerArq.readLine();
                    while (linha != null){
                        String texto_split [] = linha.split(" ");
                        int numero = Integer.parseInt(texto_split[14]);
                        int numero1 = Integer.parseInt(texto_split[15]);
                        total =  numero + numero1;
                        if (!(processos.containsKey(ddd.get(i)))){
                            processos.put(ddd.get(i),total);
                            porcentagens.put(ddd.get(i),processo_d);
                        }else {
                            processo_d = total - (processos.get(ddd.get(i)));
                            totalProcessDelta = totalProcessDelta + processo_d;
                            float cpuUsageResult = 100 * (processo_d / cpuDelta);
                            processos.remove(ddd.get(i));
                            processos.put(ddd.get(i), total);
                            float a = porcentagens.get(ddd.get(i));
                            porcentagens.remove(ddd.get(i));
                            porcentagens.put(ddd.get(i), processo_d + a);
                        }
                        linha = lerArq.readLine();
                    }
                    arq.close();
                }
                totalCPUuse =  (float)totalProcessDelta;
                Thread.currentThread().sleep(100);
                c++;
            }
            for (int i = 0; i < ddd.size(); i++){
                FileReader arq = new FileReader("/proc/"+ddd.get(i)+"/stat");
                BufferedReader lerArq = new BufferedReader(arq);
                String linha = lerArq.readLine();
                while (linha != null){
                    String texto_split [] = linha.split(" ");
                    retorno.append("PID: "+texto_split[0]).append("\n");
                    retorno.append("Nome: "+texto_split[1]).append("\n");
                    retorno.append("Consumo de cpu: "+((porcentagens.get(ddd.get(i)))/cpuDelta)+"%").append("\n");
                    linha = lerArq.readLine();
                }
            }
            return retorno.toString();
        } catch (IOException e)  {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        } catch (InterruptedException ex){
         	System.err.printf("Thread interrompida: %s.\n",
               ex.getMessage());
        }
        return retorno.toString();
    }






    public static String listarDados(){
        StringBuilder retorno = new StringBuilder();
        try {
            Arquivos arq1 = new Arquivos();
            for(int i = 0; i < arqs.size(); i++){
                FileReader arq = new FileReader("/proc/"+arqs.get(i)+"/stat");
                BufferedReader lerArq = new BufferedReader(arq);
                String linha = lerArq.readLine();
                while (linha != null){
                    String texto_split [] = linha.split(" ");
                    int numero = Integer.parseInt(texto_split[23]);
                                    retorno.append("PID: " + texto_split[0]).append("\n");
                                    retorno.append("Nome: " + texto_split[1]).append("\n");
                                    retorno.append("Tamanho: " + texto_split[23] + "kb"); // listar os processos que estão em execução, consumindo mais que 0.16 de memória.
                    linha = lerArq.readLine(); //  ps -o etime 1( tempo do processo aberto) lê da segunda até a última linha, talvez 14 + 13, tenho que pegar a memoria total, ver as procentagens, e daí mandar notificações caso esteja usando muito do que o além. exibir tbm os que não são de sistema.
                }
                arq.close();
                retorno.append("\n");
            }
            return retorno.toString();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
        return retorno.toString();
    }
}