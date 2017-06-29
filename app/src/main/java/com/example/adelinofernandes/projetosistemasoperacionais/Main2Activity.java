package com.example.adelinofernandes.projetosistemasoperacionais;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {
    private EditText editTexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aplicacoes);

        editTexto = (EditText) findViewById(R.id.editTextApp);
        printOnText();

    }



    private void printOnText(){
        Log.d("LALAL", Arquivos.listarDados());
        editTexto.setText(Arquivos.listarDados());
    }

}
