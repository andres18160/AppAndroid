package co.com.cardinalscale.autopesotruck;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import co.com.cardinalscale.autopesotruck.Datos.TablaUsuarios;
import co.com.cardinalscale.autopesotruck.Entidades.EnUsuario;


public class principalActivity extends AppCompatActivity {

    //se declara el tool_bar
    private Toolbar toolbar;
    private EnUsuario usuario=new EnUsuario();
    private TablaUsuarios cdUsuariuo=new TablaUsuarios(this);
    TextView textViewUser;
    CardView CardUser,CardConfig,CardOperacion,CardReporte,CardSend;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        //se registra el toolbar
        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        Bundle datos=getIntent().getExtras();
        usuario.setNombreDeUsuario(datos.getString("UserName"));

        CardUser=(CardView)findViewById(R.id.CardUser);
        CardConfig=(CardView)findViewById(R.id.CardConfig);
        CardOperacion=(CardView)findViewById(R.id.CardOperacion);
        CardReporte=(CardView)findViewById(R.id.CardReporte);
        CardSend=(CardView)findViewById(R.id.CardSend);


        CardUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),UsuarioListActivity.class);
                startActivity(i);
            }
        });

        CardConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i=new Intent(getApplicationContext(),UsuariosActivity.class);
                startActivity(i);*/
            }
        });
        CardOperacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),OperacionActivity.class);
                startActivity(i);
            }
        });

        CardReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i=new Intent(getApplicationContext(),UsuariosActivity.class);
                startActivity(i);*/
            }
        });
        CardSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i=new Intent(getApplicationContext(),UsuariosActivity.class);
                startActivity(i);*/
            }
        });

    }



    private void EnventoClickCardView(GridLayout mainGrid) {
        for(int i=0;i<mainGrid.getChildCount();i++){
            CardView cardView=(CardView)mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   if(finalI==0){
                       Intent inten=new Intent("android.intent.action.UsuariosActivity");
                       startActivity(inten);
                   }

                   if(finalI==1){
                       Intent inten=new Intent(getApplicationContext(),OperacionActivity.class);
                       startActivity(inten);
                   }


                    Toast.makeText(principalActivity.this,"Click="+ finalI,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_en_principal,menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem opcion_menu){
        int id=opcion_menu.getItemId();
        if(id==R.id.CerrarSession){
            final AlertDialog.Builder builder=new AlertDialog.Builder(principalActivity.this);
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
                    usuario.setEstado("");
                    cdUsuariuo.ActualizarEstadoUsuario(usuario);
                    finish();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
            return true;

        }
     return super.onOptionsItemSelected(opcion_menu);
    }
    public void MensajeToast(String mensaje){
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
    @Override
    public void onBackPressed(){

    }



}
