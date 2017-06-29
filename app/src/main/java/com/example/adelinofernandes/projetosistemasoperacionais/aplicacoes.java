package com.example.adelinofernandes.projetosistemasoperacionais;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class aplicacoes extends AppCompatActivity {
    TextView textView = (TextView) findViewById(R.id.editText);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aplicacoes);

    }
    /*
     * Função para printar dados no Textview
     */
    private void printOnText(){
        Log.d("Teste", Arquivos.listarDados());
        textView.setText(Arquivos.listarDados());
    }


}
