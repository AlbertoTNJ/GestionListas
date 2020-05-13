package es.futurasp.gestionlistas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GestionUsuariosInsertar extends AppCompatActivity {
    EditText txtInsUsuario, txtInsEmpresa, txtInsPassword, txtInsCif;
    CheckBox checkListaApertura, checkListaPorterillo;
    String marcadoApertura = "no";
    String marcadoPorterillo = "no";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_usuarios_insertar);

        Button btnVolver = (Button) findViewById(R.id.btnVolver);
        Button btnGuardar = (Button) findViewById(R.id.btnInsGuardar);

        txtInsUsuario = (EditText) findViewById(R.id.txtInsUsuario);
        txtInsEmpresa = (EditText) findViewById(R.id.txtInsEmpresa);
        txtInsPassword = (EditText) findViewById(R.id.txtInsPassword);
        txtInsCif = (EditText) findViewById(R.id.txtInsCif);
        checkListaApertura = (CheckBox) findViewById(R.id.checkApertura);
        checkListaPorterillo = (CheckBox) findViewById(R.id.checkPorterillo);

        //ACCION BOTON VOLVER
        btnVolver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GestionUsuariosInsertar.super.onBackPressed();
            }

        });

        //ACCION BOTON GUARDAR
        btnGuardar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                String verificaUser = txtInsUsuario.getText().toString();
                String verificaPassword = txtInsPassword.getText().toString();
                Integer tamPass = verificaPassword.length();
                String verificaEmpresa = txtInsEmpresa.getText().toString();
                String verificaCif = txtInsCif.getText().toString();


                if (verificaUser.isEmpty()) {
                    txtInsUsuario.setError("No se introdujo ninguna usuario");
                } else if (verificaPassword.isEmpty()) {
                    txtInsPassword.setError("No se introdujo ninguna contraseña");
                    tamPass = txtInsPassword.length();
                } else if (tamPass < 4) {
                    txtInsPassword.setError("La contraseña no puede ser menor a 4 dígitos");
                } else if (verificaCif.isEmpty()) {
                    txtInsCif.setError("No se introdujo ningún CIF");
                } else if (checkListaApertura.isChecked() == false && checkListaPorterillo.isChecked() == false) {
                    Toast.makeText(getApplicationContext(),
                            "Debe seleccionar al menos una de las listas!", Toast.LENGTH_LONG).show();
                } else {
                    if (checkListaApertura.isChecked() == true) {
                        marcadoApertura = "si";
                    }
                    if (checkListaPorterillo.isChecked() == true) {
                            marcadoPorterillo = "si";
                    }
                    new GestionUsuariosInsertar.insertarUsuarios().execute();

                }


            }

        });
    }

    class insertarUsuarios extends AsyncTask<Void, Void, Void> {
        String error = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                //Configuracion de la conexión
                Connection connection = DriverManager.getConnection("jdbc:mysql://185.155.63.198/db_android-cm", "CmAndrUser", "v5hfDugUpiWu");

                Statement statement = connection.createStatement();
                //Inserto usuario
                int resultSet = statement.executeUpdate("insert into usuarios (usuario, pass, empresa, cif, listaApertura, listaPorterillo) values ('" + txtInsUsuario.getText().toString() + "', '" + txtInsPassword.getText().toString() + "', '" + txtInsPassword.getText().toString() + "','" + txtInsCif.getText().toString() + "', '" + marcadoApertura + "', '" + marcadoPorterillo + "');");

            } catch (Exception e) {
                //Guardo el error
                error = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            System.out.println(txtInsUsuario);
            System.out.println(txtInsPassword);
            System.out.println(txtInsEmpresa);
            System.out.println(txtInsCif);
            System.out.println(marcadoApertura);
            System.out.println(marcadoPorterillo);
            if (error == "") {
                Toast.makeText(getApplicationContext(),
                        "Usuario insertado correctamente", Toast.LENGTH_LONG).show();
            } else {
                System.out.println(error);
                Toast.makeText(getApplicationContext(),
                        "Ups!, hubo un problema al insertar el usuario", Toast.LENGTH_LONG).show();

            }
        }
    }
}
