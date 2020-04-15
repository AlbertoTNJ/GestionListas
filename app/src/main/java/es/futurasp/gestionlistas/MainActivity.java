package es.futurasp.gestionlistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    TextView WelcomeUser;
    Button btnLogin;
    EditText textUsuario, textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Texto para imprimir los resultados
        WelcomeUser = (TextView) findViewById(R.id.WelcomeUser);
        //Texto para imprimir los errores
        //errores = (TextView) findViewById(R.id.textView2);

        //Almaceno usuario
        textUsuario = (EditText) findViewById(R.id.txtUser);
        //Almaceno password
        textPassword = (EditText) findViewById(R.id.txtPassword);

        btnLogin = (Button) findViewById(R.id.btnSesion);

        //Creo el evento del boton
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Las consultas a la BD son tareas asincronas
                Intent intent = new Intent (textUsuario.getContext(), BienvenidoActivity.class);
                //startActivityForResult(intent, 0);
                new Async().execute();
            }
        });
    }
    //Clase Tarea asíncrona
    class Async extends AsyncTask<Void, Void, Void> {
        String resultados = "", error = "";
        String resUsuario = textUsuario.getText().toString();
        String resPassword = textPassword.getText().toString();

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                //Configuracion de la conexión
                Connection connection = DriverManager.getConnection("jdbc:mysql://185.155.63.198/db_android-cm", "CmAndrUser", "v5hfDugUpiWu");

                Statement statement = connection.createStatement();
                //Guardo en resultSet el resultado de la consulta
                ResultSet resultSet = statement.executeQuery("select * from usuarios where usuario = '"+resUsuario+"' and pass = '"+resPassword+"'");

                while (resultSet.next()) {
                    //Formateo la consulta
                    resultados += resultSet.getString(1) + " " + resultSet.getString(2) + resultSet.getString(3) + " " + resultSet.getString(4) +
                            " " + resultSet.getString(5) + " " + resultSet.getString(6) + " " + resultSet.getString(7) + " " +
                            resultSet.getString(8) + "\n";

                }

            } catch (Exception e) {
                //Guardo el error
                error = e.toString();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            //Guardo en la variable del texto el resultado de la consulta
            /*if (resultados=="" || resultados=="      "){
                String userNotValid ="Usuario o contraseñas incorrecto";
                WelcomeUser.setText(userNotValid.toString());

            }*/
            WelcomeUser.setText(resultados);

            if (error != "")
                //Guardo en la variable del texto el error
                WelcomeUser.setText(error);

            super.onPostExecute(aVoid);

        }
    }
}
