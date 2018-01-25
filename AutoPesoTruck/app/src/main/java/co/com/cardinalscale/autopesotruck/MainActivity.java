package co.com.cardinalscale.autopesotruck;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import co.com.cardinalscale.autopesotruck.Datos.TablaUsuarios;
import co.com.cardinalscale.autopesotruck.Entidades.EnUsuario;
import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class MainActivity extends AppCompatActivity {
    private EnUsuario enUser=new EnUsuario();
private TablaUsuarios cdUsuario=new TablaUsuarios(this);
    TextView txtUserName,txtPassword;
    RelativeLayout RelativeLayoutSuperior,RelativeLayoutInferior;
    Animation uptodown,downtoup;
String username,clave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayoutSuperior=(RelativeLayout)findViewById(R.id.relativeLayoutSuperior);
        RelativeLayoutInferior=(RelativeLayout)findViewById(R.id.relativeLayoutInferior);
        uptodown= AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup=AnimationUtils.loadAnimation(this,R.anim.downtoup);
        RelativeLayoutSuperior.setAnimation(uptodown);
        RelativeLayoutInferior.setAnimation(downtoup);
        txtUserName=(TextView)findViewById(R.id.txtUserNmae);
        txtPassword=(TextView)findViewById(R.id.txtPassword);



    }


    public void Login(View view){


        String ms="";
        if(txtUserName.getText().toString().trim().length()==0){
            ms= (String) getResources().getText(R.string.msUsernameRequerido);
            MensajeToast(ms);
            return;
        }
        if(txtPassword.getText().toString().trim().length()==0){
            ms= (String) getResources().getText(R.string.msPasswordRequerido);
            MensajeToast(ms);
            return;
        }

        if(!ValidarUsuario()){
            ms= (String) getResources().getText(R.string.msUsarioInvalido);
            MensajeToast(ms);
            return;
        }

        Intent i = new Intent(this,principalActivity.class);
        i.putExtra("UserName",enUser.getNombreDeUsuario());
        startActivity(i);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);



    }

    private boolean ValidarUsuario(){
        username=txtUserName.getText().toString();
        clave=txtPassword.getText().toString();
        enUser=cdUsuario.ValidarUsuario(username,clave);
        if(enUser==null){
            if(username.equals("admin")&& clave.equals("1987")){
                enUser=new EnUsuario();
                enUser.setNombreDeUsuario(username);
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }

    }

    public void MensajeToast(String mensaje){
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }


    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.msCerrarApp);
        builder.setCancelable(true);
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

}
