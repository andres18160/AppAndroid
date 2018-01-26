package co.com.cardinalscale.autopesotruck.Datos;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import co.com.cardinalscale.autopesotruck.Entidades.EnUsuario;

public final class TablaUsuarios {

    private db_Helper helper;

    public TablaUsuarios(Context context) {
        helper = new db_Helper(context);
    }


    public static final String TABLE_NAME="usuarios";
    public static final String _ID ="Id";
    public static final String USUARIO ="Usuario";
    public static final String NOMBRE ="Nombre";
    public static final String APELLIDO ="Apellido";
    public static final String CLAVE ="Clave";
    public static final String ESTADO ="Estado";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_USUARIOS =
            "CREATE TABLE " + TablaUsuarios.TABLE_NAME + " (" +
                    TablaUsuarios._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TablaUsuarios.USUARIO + TEXT_TYPE+" UNIQUE NOT NULL " + COMMA_SEP +
                    TablaUsuarios.NOMBRE + TEXT_TYPE +COMMA_SEP +
                    TablaUsuarios.APELLIDO + TEXT_TYPE +COMMA_SEP +
                    TablaUsuarios.CLAVE + TEXT_TYPE +COMMA_SEP +
                    TablaUsuarios.ESTADO + TEXT_TYPE +" )";

    public static final String SQL_DELETE_USUARIOS ="DROP TABLE IF EXISTS " + TablaUsuarios.TABLE_NAME;



    public boolean GuardarUsuario(EnUsuario usuario){

        try{
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(USUARIO, usuario.getNombreDeUsuario());
            values.put(APELLIDO, usuario.getApellidos());
            values.put(NOMBRE, usuario.getNombres());
            values.put(CLAVE, usuario.getClave());
            //inserta los datos y devuelve la clave primaria del registro insertado
            long newRowId = db.insert(TABLE_NAME, null, values);
            db.close();
            if(newRowId==-1){
                return false;
            }
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public boolean ActualizarUsuario(EnUsuario usuario){
            try{
                SQLiteDatabase db = helper.getReadableDatabase();

                // New value for one column
                ContentValues values = new ContentValues();
                values.put(USUARIO, usuario.getNombreDeUsuario());
                values.put(APELLIDO,usuario.getApellidos());
                values.put(NOMBRE, usuario.getNombres());
                values.put(CLAVE, usuario.getClave());

                // Which row to update, based on the title
                String selection = _ID + " LIKE ?";
                String[] selectionArgs = { String.valueOf(usuario.get_id()) };

                int count = db.update(
                        TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                db.close();
                return true;
            }catch (Exception e){
                return false;
            }
    }

    public boolean ActualizarEstadoUsuario(EnUsuario usuario){
        try{
            SQLiteDatabase db = helper.getReadableDatabase();

            // New value for one column
            ContentValues values = new ContentValues();
            values.put(ESTADO, usuario.getEstado());

            // Which row to update, based on the title
            String selection = USUARIO + " LIKE ?";
            String[] selectionArgs = { String.valueOf(usuario.getNombreDeUsuario()) };

            int count = db.update(
                    TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
            db.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public boolean EliminarUsuario(String Id){
        try{
            SQLiteDatabase db = helper.getWritableDatabase();
            // Define 'where' part of query.
            String selection = _ID + " LIKE ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = { Id };
            // Issue SQL statement.
            db.delete(TABLE_NAME, selection,selectionArgs);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public EnUsuario ValidarUsuario(String Username,String Clave){
    EnUsuario usuario=new EnUsuario();
    SQLiteDatabase db = helper.getReadableDatabase();
    // Definir una proyección que especifique qué columnas de la base de datos
// lo usarás después de esta consulta.
    String[] projection = {
            _ID,
            APELLIDO,
            NOMBRE,
            USUARIO,
            CLAVE,
            ESTADO

    };
    // Filtrar resultados DONDE "título" = "Mi título"
    String selection = TablaUsuarios.USUARIO + " = ?";
    String[] selectionArgs = { Username };

// Cómo desea que se clasifiquen los resultados en el Cursor resultante
    // String sortOrder =TablaUsuarios._ID + " DESC";
    try {
        Cursor c = db.query(
                TABLE_NAME,
                projection,                               // Las columnas para regresar
                selection,                                // Las columnas para la cláusula WHERE
                selectionArgs,                            // Los valores para la cláusula WHERE
                null,                                     // no agrupe las filas
                null,                                     // no filtrar por grupos de filas
                null                                 // El orden de clasificación (sortOrder)
        );
        db.close();
        c.moveToFirst();//mueve el cursor al primer registro del resultSet
        usuario.set_id(Integer.parseInt(c.getString(0)));
        usuario.setApellidos(c.getString(1));
        usuario.setNombres(c.getString(2));
        usuario.setNombreDeUsuario(c.getString(3));
        usuario.setClave(c.getString(4));
        usuario.setEstado(c.getString(5));


        if(usuario.getClave().equals(Clave)){
            return  usuario;
        }else{
            return null;
        }


    }catch(Exception e){
        return null;
    }
}
    public EnUsuario BuscarUsuarioId(String Id){
        EnUsuario usuario=new EnUsuario();
        SQLiteDatabase db = helper.getReadableDatabase();

// Definir una proyección que especifique qué columnas de la base de datos
// lo usarás después de esta consulta.
        String[] projection = {
                _ID,
                APELLIDO,
                NOMBRE,
                USUARIO,
                CLAVE,
                ESTADO

        };

// Filtrar resultados DONDE "título" = "Mi título"
        String selection = TablaUsuarios._ID + " = ?";
        String[] selectionArgs = { Id };

// Cómo desea que se clasifiquen los resultados en el Cursor resultante
        // String sortOrder =TablaUsuarios._ID + " DESC";
        try {
            Cursor c = db.query(
                    TABLE_NAME,
                    projection,                               // Las columnas para regresar
                    selection,                                // Las columnas para la cláusula WHERE
                    selectionArgs,                            // Los valores para la cláusula WHERE
                    null,                                     // no agrupe las filas
                    null,                                     // no filtrar por grupos de filas
                    null                                 // El orden de clasificación (sortOrder)
            );

            c.moveToFirst();//mueve el cursor al primer registro del resultSet
            usuario.set_id(Integer.parseInt(c.getString(0)));
            usuario.setApellidos(c.getString(1));
            usuario.setNombres(c.getString(2));
            usuario.setNombreDeUsuario(c.getString(3));
            usuario.setClave(c.getString(4));
            usuario.setEstado(c.getString(5));

            return usuario;


        }catch(Exception e){
            return null;
        }
    }

    public EnUsuario BuscarUsuario(String userName){
        EnUsuario usuario=new EnUsuario();
        SQLiteDatabase db = helper.getReadableDatabase();

// Definir una proyección que especifique qué columnas de la base de datos
// lo usarás después de esta consulta.
        String[] projection = {
                _ID,
                APELLIDO,
                NOMBRE,
                USUARIO,
                CLAVE,
                ESTADO

        };

// Filtrar resultados DONDE "título" = "Mi título"
        String selection = USUARIO + " = ?";
        String[] selectionArgs = { userName };

// Cómo desea que se clasifiquen los resultados en el Cursor resultante
        // String sortOrder =TablaUsuarios._ID + " DESC";
        try {
            Cursor c = db.query(
                    TABLE_NAME,
                    projection,                               // Las columnas para regresar
                    selection,                                // Las columnas para la cláusula WHERE
                    selectionArgs,                            // Los valores para la cláusula WHERE
                    null,                                     // no agrupe las filas
                    null,                                     // no filtrar por grupos de filas
                    null                                 // El orden de clasificación (sortOrder)
            );

            c.moveToFirst();//mueve el cursor al primer registro del resultSet
            usuario.set_id(Integer.parseInt(c.getString(0)));
            usuario.setApellidos(c.getString(1));
            usuario.setNombres(c.getString(2));
            usuario.setNombreDeUsuario(c.getString(3));
            usuario.setClave(c.getString(4));
            usuario.setEstado(c.getString(5));

            return usuario;


        }catch(Exception e){
            return null;
        }
    }

    public EnUsuario BuscarUsuarioActivo(){
        EnUsuario usuario=new EnUsuario();
        SQLiteDatabase db = helper.getReadableDatabase();
        // Definir una proyección que especifique qué columnas de la base de datos
// lo usarás después de esta consulta.
        String[] projection = {
                _ID,
                APELLIDO,
                NOMBRE,
                USUARIO,
                CLAVE,
                ESTADO
        };
// Filtrar resultados DONDE "título" = "Mi título"
        String selection = TablaUsuarios.ESTADO + " = ?";
        String[] selectionArgs = { "Activo" };
// Cómo desea que se clasifiquen los resultados en el Cursor resultante
        // String sortOrder =TablaUsuarios._ID + " DESC";
        try {
            Cursor c = db.query(
                    TABLE_NAME,
                    projection,                               // Las columnas para regresar
                    selection,                                // Las columnas para la cláusula WHERE
                    selectionArgs,                            // Los valores para la cláusula WHERE
                    null,                                     // no agrupe las filas
                    null,                                     // no filtrar por grupos de filas
                    null                                 // El orden de clasificación (sortOrder)
            );

            c.moveToFirst();//mueve el cursor al primer registro del resultSet
            usuario.set_id(Integer.parseInt(c.getString(0)));
            usuario.setApellidos(c.getString(1));
            usuario.setNombres(c.getString(2));
            usuario.setNombreDeUsuario(c.getString(3));
            usuario.setClave(c.getString(4));
            usuario.setEstado(c.getString(5));

            return usuario;


        }catch(Exception e){
            return null;
        }
    }


}

