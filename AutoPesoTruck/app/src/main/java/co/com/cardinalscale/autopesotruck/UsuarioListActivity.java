package co.com.cardinalscale.autopesotruck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    private TablaUsuarios cdUsuario=new TablaUsuarios(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_list);

        listViewUsers=(ListView)findViewById(R.id.listViewUsuarios);
        CargarListaUsuarios();
        UsuariosAdapter miadaptador=new UsuariosAdapter(getApplicationContext(),listaUsuarios);
        listViewUsers.setAdapter(miadaptador);

        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usuario=(EnUsuario) parent.getItemAtPosition(position);
                Intent i=new Intent(getApplicationContext(),UsuariosActivity.class);
                i.putExtra("usuario",(Serializable)usuario);
                startActivity(i);
            }
        });
    }

    private void CargarListaUsuarios(){
        listaUsuarios=cdUsuario.GetListaUsuarios();
    }
}
