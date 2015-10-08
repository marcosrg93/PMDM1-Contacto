package com.rubino.pmdmcontacto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by marco on 07/10/2015.
 */
public class Gestor {

    private AdaptadorContacto cl;
    private List<Contacto> lista;

    public  void dialogo(View v, Context ctx){
        AlertDialog.Builder alert= new AlertDialog.Builder(ctx);
        alert.setTitle(R.string.dial_Titulo);
        LayoutInflater inflater= LayoutInflater.from(ctx);
        final View vista = inflater.inflate(R.layout.dialogo_insert, null);
        alert.setView(vista);
        alert.setPositiveButton(R.string.dial_insert,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        long id = lista.size() - 1;
                        TextView et1, et2;
                        et1 = (TextView) vista.findViewById(R.id.etInsertN);
                        et2 = (TextView) vista.findViewById(R.id.etInsertT);

                        Contacto c = new Contacto(id, et1.getText().toString(), et2.getText().toString());
                        lista.add(c);
                        cl.notifyDataSetChanged();
                        Log.v("Inserto datos", "" + c.toString());
                    }
                });
        alert.setNegativeButton(R.string.dial_cancel, null);
        alert.show();
    }

    public  void dialogoEditar( int posicion,  Context ctx){
        AlertDialog.Builder alert= new AlertDialog.Builder(ctx);
        alert.setTitle(R.string.dial_Titulo);

        //cargamos contacto
        Contacto c = new Contacto();

        //cargamos vista
        LayoutInflater inflater= LayoutInflater.from(ctx);
        final View vista = inflater.inflate(R.layout.dialogo_edit, null);
        final EditText et,et1;
        String nom,tel;

        nom=lista.get(posicion).getNombre();
        tel=lista.get(posicion).getTelf();
        c.setNombre(lista.get(posicion).getNombre());
        c.setTelf(lista.get(posicion).getTelf());

        et = (EditText) vista.findViewById(R.id.editN);
        et1 = (EditText) vista.findViewById(R.id.editTelf);
        Log.v(" Obtengo datos",""+c.toString());
        et.setText(nom);
        et1.setText(tel);
        alert.setView(vista);
        alert.setPositiveButton(R.string.dial_insert,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        long id = lista.size()-1;
                        TextView et1, et2;
                        et1 = (TextView) vista.findViewById(R.id.editN);
                        et2 = (TextView) vista.findViewById(R.id.editTelf);

                        Contacto c = new Contacto(id,et1.getText().toString(), et2.getText().toString());
                        lista.add(c);
                        cl.notifyDataSetChanged();
                        Log.v(" EDITO datos",""+c.toString());
                    }
                });
        alert.setNegativeButton(R.string.dial_cancel, null);
        alert.show();
    }


    public void ordenaNombresAsc(List<Contacto> lista){
        Collections.sort(lista, new OrdenaNombresAsc());
    }

    public void ordenaNombresDes(List<Contacto> lista){
        Collections.sort(lista, new OrdenaNombresDes());
    }

    public Toast tostada(String t , Context ctx) {
        Toast toast =
                Toast.makeText(ctx,
                        t + "", Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }
}
