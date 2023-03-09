package com.example.bancosqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BancoDeDados extends SQLiteOpenHelper {

    private String criarTabelaProduto = "CREATE TABLE produtos (" +
            "_id INTERGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VANCHAR(40) NOT NULL, " +"preco REAL NOT NULL);";

    public BancoDeDados(@Nullable Context context /*context devemos informar qual processo iremos utilizar*/ /*, @Nullable SQLiteDatabase.CursorFactory factory select, semelhante ao datariver*/, int version) {
        super(context, "BancoLocal", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    //Criação das tabelas. Uma por vez.
        sqLiteDatabase.execSQL(criarTabelaProduto);
    }

    //Método para executar um comando de INSERT
    public boolean cadastrar(int id, String nome, double preco) {
        SQLiteDatabase conexao = getWritableDatabase(); //abre a conexão
        //Para passar os valores que serão inseridos usamos
        //a classe ContetValues
        ContentValues valores = new ContentValues();
        //Indicar o nome da coluna e o valor que será inserido nela
        valores.put("_id", id);
        valores.put("nome", nome);
        valores.put("preco", preco);

        //O método insert() retorna um número associado a linha que foi
        //cadastrado, ou -1 se caso ocorreu um erro
        if (conexao.insert(
                "produtos", //Nome da tabela que receberá o INSERT
                null, //Indica que todas as colunas receberão valor
                valores//Valor que serão inseridos
                ) != -1) {
            conexao.close();
            return true;
        }else{conexao.close();
            return false;
        }
    }
    //Método para atualizar um produto (UPDATE)
    public boolean atualizar(int id, String nome, double preco){
        SQLiteDatabase conexao = getWritableDatabase(); // Abre a conexão
        //a classe ContetValues
        ContentValues valores = new ContentValues();
        //Colunas e valores que serão atualizados
        valores.put("nome", nome);
        valores.put("preco", preco);
        //O método update() retorna a quantidade de linhas atualizadas
        if (conexao.update("produtos", valores, "_id = ?",//Condição WHERE do UPDATE
                            new String[]{id + ""} //Indicando o valo que no ? (concatenando para virar string)
                           ) != 0) {
            conexao.close();
            return true;
        }else{
            conexao.close();
            return false;
        }
    }
    public boolean remover(int id){
        SQLiteDatabase conexao = getWritableDatabase();
        //O método delete também retorna a quantidade de Linhas removidas
        if (conexao.delete("produtos", "_id = ?", new String[]{id + ""}) != 0){
            conexao.close();
            return true;
        }else {
            conexao.close();
            return  false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
