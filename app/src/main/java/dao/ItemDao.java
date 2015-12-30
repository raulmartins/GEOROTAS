package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import bean.Carga;
import bean.Item;
import bean.Smbc;
import helper.RegistroHelperCarregarCarga;

/**
 * Created by raullima on 30/12/15.
 */
public class ItemDao extends SQLiteOpenHelper {
    Carga carga = new Carga();
    public static final String DATABASE = "BD_GEOROTAS.1";
    public static final int VERSAO = 1;
    public static final String TABELA_ITEM = "item";


    public ItemDao(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String item = "CREATE TABLE "+TABELA_ITEM
                + " ('codigo' INTEGER PRIMARY KEY NOT NULL "
                + ", 'empCodigo' TEXT NOT NULL"
                + ", 'tipo' TEXT NOT NULL"
                + ", 'nome' TEXT NOT NULL"
                + ", 'endereco' TEXT NOT NULL"
                + ", 'ordem' TEXT NOT NULL"
                + ", 'status' TEXT NOT NULL"
                + ", 'parada' TEXT NOT NULL)";
        db.execSQL(item);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String item = "DROP TABLE IF EXISTS "+TABELA_ITEM;
        db.execSQL(item);
        onCreate(db);

    }
    public void carregarBaseDeDados() {
        RegistroHelperCarregarCarga rhcc = new RegistroHelperCarregarCarga();
        carga = rhcc.smbcRequest(new Smbc("123","123","1"));
        for (Item itens: carga.getListaItem()) {
            ContentValues valores_C = new ContentValues();

            valores_C.put("codigo", Integer.parseInt(itens.getCodigo()));
            valores_C.put("empCodigo", itens.getEmpCodigo().toString());
            valores_C.put("tipo", itens.getTipo().toString());
            valores_C.put("nome", itens.getNome().toString());
            valores_C.put("endereco", itens.getEndereco().toString());
            valores_C.put("ordem", itens.getOrdem().toString());
            valores_C.put("status", itens.getStatus().toString());
            valores_C.put("parada", itens.getParada().toString());

            getWritableDatabase().insert(TABELA_ITEM, null, valores_C);

        }





    }

    public List<Item> recuperarRegistrosCargaItens() {



        List<Item> listaItem = new ArrayList<Item>();

        String sql = "Select * from "+TABELA_ITEM;

        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        try {
            while (cursor.moveToNext()) {

                Item item = new Item();

                item.setCodigo(cursor.getString(0));
                item.setEmpCodigo(cursor.getString(1));
                item.setTipo(cursor.getString(2));
                item.setNome(cursor.getString(3));
                item.setEndereco(cursor.getString(4));
                item.setOrdem(cursor.getString(5));
                item.setStatus(cursor.getString(6));
                item.setParada(cursor.getString(7));


                listaItem.add(item);
            }
        } catch (SQLException sqle) {
            //  Log.e(TAG_L_C, sqle.getMessage());
        } finally {
            cursor.close();
        }


        carga.setListaItem(listaItem);
        return carga.getListaItem();
    }

    public void removerRegistroCargaItem(Item item) {
        String[] args = {item.getCodigo().toString()};

        getWritableDatabase().delete(TABELA_ITEM, "id=?", args);

        //Log.i(TAG_R_P, "cliente removido: "+ cliente.getNome());
    }

}
