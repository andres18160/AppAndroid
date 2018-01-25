package co.com.cardinalscale.autopesotruck;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.com.cardinalscale.autopesotruck.Datos.TablaUsuarios;
import co.com.cardinalscale.autopesotruck.Datos.db_Helper;
import co.com.cardinalscale.autopesotruck.Entidades.EnUsuario;

import static java.security.AccessController.getContext;

public class UsuariosActivity extends AppCompatActivity {

   final db_Helper helper = new db_Helper(this);

        Button btnGuardar,btnActualizar,btnEliminar,btnBuscar;
        EditText txtId,txtNombre,txtApellido,txtUsuario,txtCalve;
        private EnUsuario usuario=new EnUsuario();
        private TablaUsuarios cdUsuario=new TablaUsuarios(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        btnGuardar=(Button)findViewById(R.id.btnGuardar);
        btnActualizar=(Button)findViewById(R.id.btnActualizar);
        btnEliminar=(Button)findViewById(R.id.btnEliminar);
        btnBuscar=(Button)findViewById(R.id.btnBuscar);

        txtId=(EditText)findViewById(R.id.txtId);
        txtNombre=(EditText)findViewById(R.id.txtNombre);
        txtApellido=(EditText)findViewById(R.id.txtApellido);
        txtUsuario=(EditText)findViewById(R.id.txtUsuario);
        txtCalve=(EditText)findViewById(R.id.txtClave);


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(ValidarControles()){
                        if(cdUsuario.GuardarUsuario(usuario)){
                            MensajeToast("Usuario Registrado");
                            LimpiarControles();
                        }else{
                            MensajeToast("Ocurrio un error insertando el registro");
                        }
                    }
            }
        });



        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidarControles()){
                    if(cdUsuario.ActualizarUsuario(usuario)){
                        MensajeToast("Usuario Actualizado");
                        LimpiarControles();
                    }else{
                        MensajeToast("Ocurrio un error actualizando el registro");
                    }
                }
        }
        });



        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtId.getText().toString().equalsIgnoreCase("")){
                    if(cdUsuario.EliminarUsuario(txtId.getText().toString())){
                        MensajeToast("Usuario Eliminado");
                        LimpiarControles();
                    }else{
                        MensajeToast("Ocurrio un error eliminando el registro");
                    }
                }else{
                    MensajeToast("Debes ingresar un Id para realizar la consulta!");
                }
            }
        });


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario=cdUsuario.BuscarUsuario((txtId.getText().toString()));
                if(usuario !=null){
                    txtApellido.setText(usuario.getApellidos());
                    txtNombre.setText(usuario.getNombres());
                    txtUsuario.setText(usuario.getNombreDeUsuario());
                    txtCalve.setText(usuario.getClave());
                }else{
                    MensajeToast("No se encontro el usuario");
                    LimpiarControles();
                }


            }
        });
    }

    private boolean ValidarControles(){

        if(txtId.getText().toString().equalsIgnoreCase("")){
            MensajeToast("El campo Id es obligatorio!");
            return false;
        }
        if(txtApellido.getText().toString().equalsIgnoreCase("")){
            MensajeToast("El campo Apellido es obligatorio!");
            return false;
        }
        if(txtCalve.getText().toString().equalsIgnoreCase("")){
            MensajeToast("El campo Clave es obligatorio!");
            return false;
        }
        if(txtNombre.getText().toString().equalsIgnoreCase("")){
            MensajeToast("El campo Nombre es obligatorio!");
            return false;
        }
        if(txtUsuario.getText().toString().equalsIgnoreCase("")){
            MensajeToast("El campo Usuario es obligatorio!");
            return false;
        }

        usuario.set_id(Integer.parseInt(txtId.getText().toString()));
        usuario.setApellidos(txtApellido.getText().toString());
        usuario.setClave(txtCalve.getText().toString());
        usuario.setNombreDeUsuario(txtUsuario.getText().toString());
        usuario.setNombres(txtNombre.getText().toString());

        return true;

    }

    private void LimpiarControles(){
        txtApellido.setText("");
        txtNombre.setText("");
        txtUsuario.setText("");
        txtCalve.setText("");
        txtId.setText("");
    }

    private void MensajeToast(String mensaje){
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
}
