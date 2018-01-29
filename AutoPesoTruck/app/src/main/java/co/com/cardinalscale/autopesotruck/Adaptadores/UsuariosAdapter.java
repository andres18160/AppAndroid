package co.com.cardinalscale.autopesotruck.Adaptadores;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.com.cardinalscale.autopesotruck.Entidades.EnUsuario;
import co.com.cardinalscale.autopesotruck.R;

/**
 * Created by ANDRE on 29/01/2018.
 */

public class UsuariosAdapter extends BaseAdapter {

     Context contexto;
     List<EnUsuario> ListaObjetos;

    public UsuariosAdapter(Context contexto,List<EnUsuario> Objetos){
        this.contexto=contexto;
        ListaObjetos=Objetos;
    }

    @Override
    public int getCount() {
        return ListaObjetos.size();//Retorna la cantidad de elementos de la lista.
    }

    @Override
    public Object getItem(int position) {

        return ListaObjetos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ListaObjetos.get(position).get_id();//Retorna el id de la posicion indicada
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista=convertView;
        LayoutInflater inflate=LayoutInflater.from(contexto);
        vista=inflate.inflate(R.layout.itemlistviewusers,null);

        ImageView imagen=(ImageView)vista.findViewById(R.id.foto);
        TextView titulo=(TextView)vista.findViewById(R.id.txtviewTitle);
        TextView descripcion=(TextView)vista.findViewById(R.id.txtviewDecripcion);

        String nombreCompleto=ListaObjetos.get(position).getNombres()+" "+ListaObjetos.get(position).getApellidos();
        titulo.setText(nombreCompleto);
        descripcion.setText(ListaObjetos.get(position).getNombreDeUsuario());
        imagen.setImageResource(ListaObjetos.get(position).getImagen());
        return vista;

    }
}
