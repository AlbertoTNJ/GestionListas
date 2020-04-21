package es.futurasp.gestionlistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BienvenidoActivity extends AppCompatActivity {
    private TextView bienvenido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);

        Button btnListaApertura = (Button) findViewById(R.id.btnApertura);
        Button btnListaPorterillo = (Button) findViewById(R.id.btnPorterillo);
        TextView textoSinLista = (TextView) findViewById(R.id.textView_SinListas);
        Button btnVolver = (Button) findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }

        });
        Integer idUsuario = getIntent().getIntExtra("idUsuario", 0);
        String usuario = getIntent().getStringExtra("usuario");
        String listaApertura = getIntent().getStringExtra("listaApertura");
        String listaPorterillo = getIntent().getStringExtra("listaPorterillo");


        /*System.out.println("El id de user es: "+idUsuario);
        System.out.println("El usuario es: "+usuario);
        System.out.println("listaApertura: "+listaApertura);
        System.out.println("listaPorterillo: "+listaPorterillo);*/

        if (listaApertura.equals("si")){
            btnListaApertura.setVisibility(View.VISIBLE);

        }
        if (listaPorterillo.equals("si")){
            btnListaPorterillo.setVisibility(View.VISIBLE);
        }
        if (listaApertura.equals("no") && listaPorterillo.equals("no")) {
            textoSinLista.setVisibility(View.VISIBLE);

        }
    }
}