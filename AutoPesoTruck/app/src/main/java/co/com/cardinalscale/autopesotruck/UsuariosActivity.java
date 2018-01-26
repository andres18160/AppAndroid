package co.com.cardinalscale.autopesotruck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.com.cardinalscale.autopesotruck.Datos.TablaUsuarios;
import co.com.cardinalscale.autopesotruck.Datos.db_Helper;
import co.com.cardinalscale.autopesotruck.Entidades.EnUsuario;

import static java.security.AccessController.getContext;

public class UsuariosActivity extends AppCompatActivity {

        Button btnGuardar,btnActualizar,btnEliminar,btnBuscar;
        EditText txtUsuario,txtNombre,txtApellido,txtCalve;
        private EnUsuario usuario;
        private TablaUsuarios cdUsuario=new TablaUsuarios(this);
        private Vibrator vib;
        Animation animShake;
        private TextInputLayout input_username,input_nombre,input_apellido,input_contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        usuario=new EnUsuario();

        btnGuardar=(Button)findViewById(R.id.btnGuardar);
        btnActualizar=(Button)findViewById(R.id.btnActualizar);
        btnEliminar=(Button)findViewById(R.id.btnEliminar);
        btnBuscar=(Button)findViewById(R.id.btnBuscar);

        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnBuscar.setEnabled(false);

        txtNombre=(EditText)findViewById(R.id.txtNombre);
        txtApellido=(EditText)findViewById(R.id.txtApellido);
        txtUsuario=(EditText)findViewById(R.id.txtUsuario);
        txtCalve=(EditText)findViewById(R.id.txtClave);

        input_username=(TextInputLayout)findViewById(R.id.input_username);
        input_nombre=(TextInputLayout)findViewById(R.id.input_nombre);
        input_apellido=(TextInputLayout)findViewById(R.id.input_apellido);
        input_contraseña=(TextInputLayout)findViewById(R.id.input_contraseña);

        animShake= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        vib=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);


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


        txtUsuario.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                usuario=cdUsuario.BuscarUsuario((txtUsuario.getText().toString()));
                if(usuario !=null){
                    txtApellido.setText(usuario.getApellidos());
                    txtNombre.setText(usuario.getNombres());
                    txtCalve.setText(usuario.getClave());
                    btnActualizar.setEnabled(true);
                    btnEliminar.setEnabled(true);
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
         /*       if(!txtId.getText().toString().equalsIgnoreCase("")){
                    if(cdUsuario.EliminarUsuario(txtId.getText().toString())){
                        MensajeToast("Usuario Eliminado");
                        LimpiarControles();
                    }else{
                        MensajeToast("Ocurrio un error eliminando el registro");
                    }
                }else{
                    MensajeToast("Debes ingresar un Id para realizar la consulta!");
                }*/
            }
        });


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario=cdUsuario.BuscarUsuario((txtUsuario.getText().toString()));
                if(usuario !=null){
                    txtApellido.setText(usuario.getApellidos());
                    txtNombre.setText(usuario.getNombres());
                    txtCalve.setText(usuario.getClave());
                }else{
                    MensajeToast("No se encontro el usuario");
                    LimpiarControles();
                }


            }
        });
    }

    private boolean ValidarControles(){

      try{


          if(txtUsuario.getText().toString().trim().isEmpty()){
              input_username.setErrorEnabled(true);
              input_username.setError(getResources().getText(R.string.err_msg_usuario));
              txtUsuario.setError(getResources().getText(R.string.err_msg_requerido));
              input_username.setAnimation(animShake);
              input_username.startAnimation(animShake);
              vib.vibrate(120);
              requestFocus(txtUsuario);
              return false;
          }
          if(txtNombre.getText().toString().trim().isEmpty()){
              input_nombre.setErrorEnabled(true);
              input_nombre.setError(getResources().getText(R.string.err_msg_nombre));
              txtNombre.setError(getResources().getText(R.string.err_msg_requerido));
              input_nombre.setAnimation(animShake);
              input_nombre.startAnimation(animShake);
              vib.vibrate(120);
              requestFocus(txtNombre);
              return false;
          }
          if(txtApellido.getText().toString().trim().isEmpty()){
              input_apellido.setErrorEnabled(true);
              input_apellido.setError(getResources().getText(R.string.err_msg_apellido));
              txtApellido.setError(getResources().getText(R.string.err_msg_requerido));
              input_apellido.setAnimation(animShake);
              input_apellido.startAnimation(animShake);
              vib.vibrate(120);
              requestFocus(txtApellido);
              return false;
          }
          if(txtCalve.getText().toString().trim().isEmpty()){
              input_contraseña.setErrorEnabled(true);
              input_contraseña.setError(getResources().getText(R.string.err_msg_contrasena));
              txtCalve.setError(getResources().getText(R.string.err_msg_requerido));
              input_contraseña.setAnimation(animShake);
              input_contraseña.startAnimation(animShake);
              vib.vibrate(120);
              requestFocus(txtCalve);
              return false;
          }
          input_apellido.setErrorEnabled(false);
          input_contraseña.setErrorEnabled(false);
          input_nombre.setErrorEnabled(false);
          input_username.setErrorEnabled(false);
          usuario=new EnUsuario();
          usuario.setApellidos(txtApellido.getText().toString());
          usuario.setClave(txtCalve.getText().toString());
          usuario.setNombreDeUsuario(txtUsuario.getText().toString());
          usuario.setNombres(txtNombre.getText().toString());

          return true;
      }catch (Exception e){
          Log.e("Error",e.getMessage().toString());
          return false;
      }

    }

    private void LimpiarControles(){
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        txtApellido.setText("");
        txtNombre.setText("");
        txtUsuario.setText("");
        txtCalve.setText("");
    }

    private void MensajeToast(String mensaje){
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    private void requestFocus(View view){
        if(view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
