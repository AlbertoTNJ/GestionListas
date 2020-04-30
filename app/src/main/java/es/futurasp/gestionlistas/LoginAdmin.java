package es.futurasp.gestionlistas;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.camera2.TotalCaptureResult;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LoginAdmin extends AppCompatActivity {
    ArrayList<String> listaUsuarios = new ArrayList<String>();
    public String itemSeleccionado = null;
    Integer idUsuario;
    String usuario, listaApertura, listaPorterillo;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        final Spinner spinnerUsuarios = (Spinner) findViewById(R.id.mSpinner);
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
                /*Intent intent= new Intent(LoginAdmin.this,BienvenidoActivity.class);
                intent.putExtra("data",String.valueOf(spinnerUsuarios.getSelectedItem()));
                startActivity(intent);*/
                new datosUsuarioSeleccionado().execute();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(view.getContext(), BienvenidoActivity.class);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("usuario", usuario);
                intent.putExtra("listaApertura", listaApertura);
                intent.putExtra("listaPorterillo", listaPorterillo);
                startActivity(intent);
            }

        });

        new ListUsuarios().execute();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listaUsuarios);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUsuarios.setAdapter(adaptador);
        spinnerUsuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //itemSeleccionado = (String) spinnerUsuarios.getAdapter().getItem(position);
                itemSeleccionado = adaptador.getItem(position).toString();


                System.out.println("No se ha seleccionado nada");
                if (itemSeleccionado.isEmpty()){
                    System.out.println("No se ha seleccionado nada");
                }
                else{
                    System.out.println("Se ha seleccionado:"+ itemSeleccionado);

                }
                Toast.makeText(getApplicationContext(),
                        "Usuario seleccionado : " + itemSeleccionado, Toast.LENGTH_LONG).show();

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
    class datosUsuarioSeleccionado extends AsyncTask<Void, Void, Void> {
        String error = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                //Configuracion de la conexión
                Connection connection = DriverManager.getConnection("jdbc:mysql://185.155.63.198/db_android-cm", "CmAndrUser", "v5hfDugUpiWu");

                Statement statement = connection.createStatement();
                //Guardo en resultSet el resultado de la consulta
                System.out.println("Se ha seleccionado el item en la consulta:"+ itemSeleccionado);
                ResultSet resultSet = statement.executeQuery("select * from usuarios where usuario='"+itemSeleccionado+"'");

                while (resultSet.next()) {
                    idUsuario=resultSet.getInt(1);
                    usuario=resultSet.getString(2);
                    listaApertura=resultSet.getString(7);
                    listaPorterillo=resultSet.getString(8);
                }


            } catch (Exception e) {
                //Guardo el error
                error = e.toString();
            }
            return null;
        }
    }
}
