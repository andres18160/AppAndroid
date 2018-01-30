package co.com.cardinalscale.autopesotruck;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.LinearGradient;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PersistableBundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import co.com.cardinalscale.autopesotruck.Adaptadores.UsuariosAdapter;
import co.com.cardinalscale.autopesotruck.Datos.TablaUsuarios;
import co.com.cardinalscale.autopesotruck.Datos.db_Helper;
import co.com.cardinalscale.autopesotruck.Entidades.EnUsuario;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static java.security.AccessController.getContext;

public class UsuariosActivity extends AppCompatActivity {

    private static String APP_DIRECTORY="MyPictureApp/";
    private static String MEDIA_DIRECTORY=APP_DIRECTORY+"PictureApp";
    private final int MY_PERMISSIONS=100;
    private final int PHOTO_CODE=200;
    private final int SELECT_PICTURE=300;
    private String mPath;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        if(mayRequestStoragePermission()){
            // mOptionButton.set
        }else{

        }


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
                    ByteArrayInputStream bais = new ByteArrayInputStream(usuario.getFoto());
                    Bitmap bitmap = BitmapFactory.decodeStream(bais);
                    foto.setImageBitmap(bitmap);
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
                showOptions();
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

    private void showOptions() {
        final CharSequence[] option={"Tomar foto","Elegrin de galeria","Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(UsuariosActivity.this);
        builder.setTitle("Elige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which]=="Tomar foto"){
                    openCamera();
                }else if(option[which]=="Elegir de galeria"){
                    Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("images/*");
                    startActivityForResult(intent.createChooser(intent,"Selecciona app de imagen"),SELECT_PICTURE);
                }else{
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void openCamera() {
        File file=new File(Environment.getExternalStorageDirectory(),MEDIA_DIRECTORY);
        boolean isDirectoryCreated=file.exists();
        if(!isDirectoryCreated){
            isDirectoryCreated= file.mkdirs();//crea los directorios de la app para almacenar las imagenes definidas en las variables estaticas
        }
        if(isDirectoryCreated){
            Long timestamp=System.currentTimeMillis()/1000;
            String imageName=timestamp.toString()+".jpg";
            mPath=Environment.getExternalStorageDirectory()+File.separator+MEDIA_DIRECTORY+File.separator+imageName;
            File newFile=new File(mPath);
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent,PHOTO_CODE);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this, new String[]{mPath}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage","Scanned "+path+":");
                            Log.i("ExternalStorage","->Uri="+ uri);
                        }
                    });

                     bitmap=BitmapFactory.decodeFile(mPath);
                     foto.setImageBitmap(bitmap);
                     ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
                     bitmap.compress(Bitmap.CompressFormat.PNG, 0 , baos);
                     byte[] blob = baos.toByteArray();
                     usuario.setFoto(blob);
                     break;

                case SELECT_PICTURE:
                    Uri path=data.getData();
                    foto.setImageURI(path);
                    break;

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==MY_PERMISSIONS){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                MensajeToast("Permisos aceptados");
                btnfoto.setEnabled(true);

            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder=new AlertDialog.Builder(UsuariosActivity.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri=Uri.fromParts("package",getPackageName(),null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }

    private boolean mayRequestStoragePermission() {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M)
            return true;
        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED))
                        return true;
        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) )||(shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(linearLayoutContent,"Los permisos son necesarios para porder usar la aplicación",Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},MY_PERMISSIONS);
                }
            }).show();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},MY_PERMISSIONS);
        }
        return false;
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
    @Override
    public void onBackPressed(){
                finish();
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("file_path",mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPath=savedInstanceState.getString("file_path");
    }
}
