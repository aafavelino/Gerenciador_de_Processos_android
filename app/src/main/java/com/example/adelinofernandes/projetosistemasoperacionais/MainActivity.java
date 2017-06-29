package com.example.adelinofernandes.projetosistemasoperacionais;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.graphics.Paint;

import android.view.View;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private boolean update;
    private Button botao_mem;
    private EditText txtTeste;
    private Button botao_cpu;
    private DonutProgress progressomem;
    private DonutProgress progressocpu;
    protected static final int TIMER = 10000;
    protected boolean mbActive;
    protected ProgressBar nProgressBar;







    public Paint mPaintProgress = new Paint();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.update = true;


        progressomem = (DonutProgress) this.findViewById(R.id.donut_progress);
        progressocpu = (DonutProgress) this.findViewById(R.id.donut_progress2);
        botao_mem = (Button) this.findViewById(R.id.consulta_mem);
        botao_cpu = (Button) findViewById(R.id.consulta_cpu);
        txtTeste = (EditText) findViewById(R.id.editText);





        botao_mem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent it;
                it = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(it);


            }

        });

        botao_cpu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent it;
                it = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(it);
            }

        });



        progressocpu.setProgress((int) Arquivos.cpuTotal());
        progressomem.setProgress((int) Arquivos.dadosTotais());
        txtTeste.setText(Arquivos.processoMaior());



    }

    @Override
    protected void onResume() {
        super.onResume();
        System.gc();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                imprimirFloat();
                onResume();
            }
        }, 1000);
    }

    private void init() {
        this.update = true;

    }
/*
 * Função para pegar os dados e printar nas barras de progresso
 */
    private void imprimirFloat(){
        progressomem.setProgress((int) Arquivos.dadosTotais());
        txtTeste.setText(Arquivos.processoMaior());
        //progressocpu.setProgress((int) Arquivos.cpuTotal());

    }


}
