package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by raullima on 29/12/15.
 */
public class CargaDao extends SQLiteOpenHelper{

    public static final String DATABASE = "BD_GEOROTAS";

    public static final int VERSAO = 1;
    public static final String TABELA_CARGA = "carga";

    private static final String TAG_I_C = "INSERIR_CARGA";
    private static final String TAG_L_C = "LISTAR_CARGA";
    private static final String TAG_R_C = "REMOVER_CARGA";

    public CargaDao(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String carga = "CREATE TABLE " + TABELA_CARGA
                + " ('id' INTEGER PRIMARY KEY NOT NULL , "
                + " 'operacao' TEXT NOT NULL"
                + ", 'retorno' TEXT NOT NULL"
                + ", 'descricao' TEXT NOT NULL ";

    db.execSQL(carga);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
