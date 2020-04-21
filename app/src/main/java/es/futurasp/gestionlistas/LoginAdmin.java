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

        final Spinner mSpinner = (Spinner) findViewById(R.id.mSpinner);
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

        new ListUsuarios().execute();

        ArrayAdapter<String> adp = new ArrayAdapter<>(LoginAdmin.this, android.R.layout.simple_spinner_dropdown_item, listaUsuarios);

        mSpinner.setAdapter(adp);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String elemento = (String) mSpinner.getAdapter().getItem(position);
                Toast.makeText(LoginAdmin.this,"Seleccionastes: "+elemento, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
