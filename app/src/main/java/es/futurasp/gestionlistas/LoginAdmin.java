package es.futurasp.gestionlistas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LoginAdmin extends AppCompatActivity {
    ArrayList<String> listaUsuarios = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        final Spinner spinnerUsuarios = (Spinner) findViewById(R.id.spinner);
        Button btnSeleccionar = (Button) findViewById(R.id.btnSeleccionarUsuario);
        Button btnVolver = (Button) findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }

        });
        btnSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BienvenidoActivity.class);
                startActivity(intent);
                intent.putExtra("idUsuario", idUsuario);
            }

        });

        new ListUsuarios().execute();

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(LoginAdmin.this, android.R.layout.simple_spinner_item, listaUsuarios);
        spinnerUsuarios.setAdapter(adaptador);


    }
    class ListUsuarios extends AsyncTask<Void, Void, Void> {
        String error = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                //Configuracion de la conexión
                Connection connection = DriverManager.getConnection("jdbc:mysql://185.155.63.198/db_android-cm", "CmAndrUser", "v5hfDugUpiWu");

                Statement statement = connection.createStatement();
                //Guardo en resultSet el resultado de la consulta
                ResultSet resultSet = statement.executeQuery("select usuario from usuarios order by usuario");

                while (resultSet.next()) {
                    listaUsuarios.add(resultSet.getString(1));
                }

            } catch (Exception e) {
                //Guardo el error
                error = e.toString();
            }
            return null;
        }
    }
}
