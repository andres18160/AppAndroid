package co.com.cardinalscale.autopesotruck;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.frosquivel.magicalcamera.Utilities.ConvertSimpleImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import co.com.cardinalscale.autopesotruck.Datos.TablaUsuarios;
import co.com.cardinalscale.autopesotruck.Entidades.EnUsuario;


public class UsuariosActivity extends AppCompatActivity {



        Button btnGuardar,btnActualizar,btnEliminar,btnfoto;
        EditText txtUsuario,txtNombre,txtApellido,txtCalve;
        private EnUsuario usuario;
        private TablaUsuarios cdUsuario=new TablaUsuarios(this);
        private Vibrator vib;
        Animation animShake;
        private TextInputLayout input_username,input_nombre,input_apellido,input_contraseña;
        ImageView foto;
        private Bitmap bitmap;
        LinearLayout linearLayoutContent;
        private  MagicalCamera magicalCamera;
        private int RESIZE_PHOTO_PIXELS_PERCENTAGE = 80 ;
        private MagicalPermissions magicalPermissions;
        private byte[] bytesArrayFoto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        String[] permissions = new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        magicalPermissions = new MagicalPermissions(this, permissions);

        magicalCamera = new MagicalCamera(this,RESIZE_PHOTO_PIXELS_PERCENTAGE, magicalPermissions);
        usuario=new EnUsuario();
        btnGuardar=(Button)findViewById(R.id.btnGuardar);
        btnActualizar=(Button)findViewById(R.id.btnActualizar);
        btnEliminar=(Button)findViewById(R.id.btnEliminar);
        btnfoto=(Button)findViewById(R.id.btnFoto); 
        foto=(ImageView)findViewById(R.id.imgFoto);
        linearLayoutContent=(LinearLayout) findViewById(R.id.linearLayoutContent);


        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);

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

        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        if(extras!=null){
            if(extras.containsKey("usuario")){
                usuario=(EnUsuario)getIntent().getExtras().getSerializable("usuario");

                if(usuario!=null){
                    txtUsuario.setKeyListener(null);
                    txtUsuario.setText(usuario.getNombreDeUsuario());
                    txtApellido.setText(usuario.getApellidos());
                    txtNombre.setText(usuario.getNombres());
                    txtCalve.setText(usuario.getClave());
                    try{
                        ByteArrayInputStream bais = new ByteArrayInputStream(usuario.getFoto());
                        Bitmap bitmap = BitmapFactory.decodeStream(bais);
                        foto.setImageBitmap(bitmap);
                    }catch (Exception e){

                    }

                    btnActualizar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                    btnGuardar.setEnabled(false);
                    btnGuardar.setVisibility(View.GONE);//Apara ocultar el boton
                }
            }
        }else{
            btnGuardar.setVisibility(View.VISIBLE);//Apara MOSTRAR el boton
        }
        btnfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    magicalCamera.takePhoto();//para tomar foto
                    //magicalCamera.selectedPicture("Selecciona una foto");//para seleccionar una foto
                }catch (Exception e){

                }

            }
        });



        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(ValidarControles()){
                        if(cdUsuario.GuardarUsuario(usuario)){
                            MensajeToast("Usuario Registrado");
                            LimpiarControles();
                            onBackPressed();
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
                        onBackPressed();
                    }else{
                        MensajeToast("Ocurrio un error actualizando el registro");
                    }
                }
        }
        });



        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(cdUsuario.EliminarUsuario(usuario.getNombreDeUsuario())){
                        MensajeToast("Usuario Eliminado");
                        LimpiarControles();
                        onBackPressed();
                    }else{
                        MensajeToast("Ocurrio un error eliminando el registro");
                    }

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Map<String, Boolean> map = magicalPermissions.permissionResult(requestCode, permissions, grantResults);
        for (String permission : map.keySet()) {
            Log.d("PERMISSIONS", permission + " was: " + map.get(permission));
        }
        //Following the example you could also
        //locationPermissions(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //CALL THIS METHOD EVER
        magicalCamera.resultPhoto(requestCode, resultCode, data);

        //this is for rotate picture in this method
        //magicalCamera.resultPhoto(requestCode, resultCode, data, MagicalCamera.ORIENTATION_ROTATE_180);

        //with this form you obtain the bitmap (in this example set this bitmap in image view)
        foto.setImageBitmap(magicalCamera.getPhoto());
        bytesArrayFoto =  ConvertSimpleImage.bitmapToBytes(magicalCamera.getPhoto(), MagicalCamera.PNG);
        usuario.setFoto(bytesArrayFoto);

        //if you need save your bitmap in device use this method and return the path if you need this
        //You need to send, the bitmap picture, the photo name, the directory name, the picture type, and autoincrement photo name if           //you need this send true, else you have the posibility or realize your standard name for your pictures.
        String path = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(),"myPhotoName","myDirectoryName", MagicalCamera.JPEG, true);

        if(path != null){
            Toast.makeText(UsuariosActivity.this, "The photo is save in device, please check this path: " + path, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(UsuariosActivity.this, "Sorry your photo dont write in devide, please contact with fabian7593@gmail and say this error", Toast.LENGTH_SHORT).show();
        }
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
          usuario.setFoto(bytesArrayFoto);

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
    @Override
    public void onBackPressed(){
                finish();
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
       // outState.putString("file_path",mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
       // mPath=savedInstanceState.getString("file_path");
    }
}
