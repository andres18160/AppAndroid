package co.com.cardinalscale.autopesotruck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

import co.com.cardinalscale.autopesotruck.Adaptadores.UsuariosAdapter;
import co.com.cardinalscale.autopesotruck.Datos.TablaUsuarios;
import co.com.cardinalscale.autopesotruck.Entidades.EnUsuario;

public class UsuarioListActivity extends AppCompatActivity {
    ListView listViewUsers;
    ArrayList<EnUsuario> listaUsuarios;
    private EnUsuario usuario;
    private UsuariosAdapter miadaptador;
    private TablaUsuarios cdUsuario=new TablaUsuarios(this);
    private EditText txtBuscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_list);
        txtBuscar=(EditText)findViewById(R.id.txtBuscar);

        listViewUsers=(ListView)findViewById(R.id.listViewUsuarios);
        CargarListaUsuarios();


        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usuario=(EnUsuario) parent.getItemAtPosition(position);
                Intent i=new Intent(getApplicationContext(),UsuariosActivity.class);
                i.putExtra("usuario",(Serializable)usuario);
                startActivity(i);
            }
        });
        txtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                miadaptador.getFilter(miadaptador).filter(txtBuscar.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void AddUser(View view){
        Intent i=new Intent(getApplicationContext(),UsuariosActivity.class);
        startActivity(i);

    }

    @Override
    protected void onResume() {
        super.onResume();
        CargarListaUsuarios();

    }
    private void CargarListaUsuarios(){
        listaUsuarios=cdUsuario.GetListaUsuarios();
         miadaptador=new UsuariosAdapter(getApplicationContext(),listaUsuarios);
        listViewUsers.setAdapter(miadaptador);
    }
}
