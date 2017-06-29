package com.example.adelinofernandes.projetosistemasoperacionais;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;

public class Main3Activity extends AppCompatActivity {
    private EditText editTexto;
    Arquivos arquivos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        try {
            arquivos = new Arquivos();
        } catch (IOException e) {
            e.printStackTrace();
        }
        editTexto = (EditText) findViewById(R.id.editTextApp);
        printOnText();

    }



    private void printOnText(){
        Log.d("LALAL", arquivos.ComcpuTotal());
        editTexto.setText(arquivos.ComcpuTotal());
    }

}
