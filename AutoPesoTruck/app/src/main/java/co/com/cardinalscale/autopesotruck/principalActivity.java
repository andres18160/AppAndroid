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


public class principalActivity extends AppCompatActivity {

    //se declara el tool_bar
    private Toolbar toolbar;
    GridLayout mainGrid;

    TextView textViewUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        //se registra el toolbar
        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        Bundle datos=getIntent().getExtras();
        String userName=datos.getString("UserName");



        mainGrid=(GridLayout)findViewById(R.id.mainGrid);
        EnventoClickCardView(mainGrid);


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
            MensajeToast("Se preciono cerrar sesión!");
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
