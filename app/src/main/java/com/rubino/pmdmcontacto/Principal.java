package com.rubino.pmdmcontacto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class  Principal extends AppCompatActivity {


    private AdaptadorContacto cl;
    private List<Contacto> lista;
    private FloatingActionButton fab;
    private Contacto c;
    private ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        init();
    }

    //Inicializamos todos los componentes que llamaremos en el onCreate
    private void init() {
        //Floating Action Button
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialogo(view);
            }
        });

        //Comprobamos la posicion para que se muestre o no
        OnScrollUpDownListener.Action scrollAction = new OnScrollUpDownListener.Action() {

            @Override
            public void up() {
                fab.hide();
            }

            @Override
            public void down() {
                fab.show();
            }

        };

        iv = (ImageView)findViewById(R.id.ivNum);


        final ListView lv = (ListView) findViewById(R.id.lvContactos);
        lista = new ArrayList<>();
        lista = cl.getListaContactos(this);

        cl = new AdaptadorContacto(this, R.layout.elementos_lv, lista);

        lv.setAdapter(cl);
        lv.setTag(lista);

        //Comprueba la posición en el listview para ocultarlo o no según la acción realizada
        lv.setOnScrollListener(new OnScrollUpDownListener(lv, 8, scrollAction));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, "Pulsado encima", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        lv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });


        registerForContextMenu(lv);

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.mn_ordenaMayor) {
            ordenaNombresAsc();
            cl.notifyDataSetChanged();
            cl = new AdaptadorContacto(Principal.this, R.layout.elementos_lv, lista);
            ListView lv = (ListView) findViewById(R.id.lvContactos);
            lv.setAdapter(cl);
            return true;
        }
        if (id == R.id.mn_ordenaMenor) {
            ordenaNombresDes();
            cl.notifyDataSetChanged();
            cl = new AdaptadorContacto(Principal.this, R.layout.elementos_lv, lista);
            ListView lv = (ListView) findViewById(R.id.lvContactos);
            lv.setAdapter(cl);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Creamos el menu contextual que nos dará las opciones de editar y borrar de cada Contacto
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual, menu);

    }
    //Damos funcionalidad al los elementos del menu contextual
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        long id = item.getItemId();
        AdapterView.AdapterContextMenuInfo vistaInfo =
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int posicion = vistaInfo.position;

        if(id==R.id.mn_editar){
            dialogoEditar(posicion);
            return true;
        } else if(id==R.id.mn_borrar){
            cl.borrar(posicion);
            return true;
        }
        return super.onContextItemSelected(item);
    }



    public  void dialogo(View v){
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle(R.string.dial_Titulo);
        LayoutInflater inflater= LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_insert, null);
        alert.setView(vista);
        alert.setPositiveButton(R.string.dial_insert,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        long id = lista.size() - 1;
                        EditText et1, et2;
                        et1 = (EditText) vista.findViewById(R.id.etInsertN);
                        et2 = (EditText) vista.findViewById(R.id.etInsertT);

                        Contacto c = new Contacto(id, et1.getText().toString(), et2.getText().toString());
                        lista.add(c);
                        cl.notifyDataSetChanged();
                        cl = new AdaptadorContacto(Principal.this, R.layout.elementos_lv, lista);
                        ListView lv = (ListView) findViewById(R.id.lvContactos);
                        lv.setAdapter(cl);
                        Log.v("Inserto datos", "" + c.toString());
                    }
                });
        alert.setNegativeButton(R.string.dial_cancel, null);
        alert.show();
    }

    public  void dialogoEditar( final int posicion){
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle(R.string.dial_Titulo_ed);

        //cargamos contacto
        Contacto c = new Contacto();
        //cargamos vista
        LayoutInflater inflater= LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_edit, null);
        final EditText et,et1;
        String nom,tel;

        nom=lista.get(posicion).getNombre();
        tel=cl.getListaTelefonos(this, lista.get(posicion).getId()).get(0);
        c.setNombre(lista.get(posicion).getNombre());
        c.setTelf(cl.getListaTelefonos(this, lista.get(posicion).getId()).get(0));

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
                        EditText et1, et2;
                        et1 = (EditText) vista.findViewById(R.id.editN);
                        et2 = (EditText) vista.findViewById(R.id.editTelf);

                        lista.remove(posicion);
                        Contacto c = new Contacto(posicion,et1.getText().toString(), et2.getText().toString());
                        lista.add(c);
                        cl.notifyDataSetChanged();
                        Log.v(" EDITO datos",""+c.toString());
                    }
                });
        alert.setNegativeButton(R.string.dial_cancel, null);
        alert.show();
    }

    public void ordenaNombresAsc(){
        Collections.sort(lista, new OrdenaNombresAsc());
    }

    public void ordenaNombresDes(){
        Collections.sort(lista, new OrdenaNombresDes());
    }

    //Mostramos la imagen comprobando la versión de android que estamos utilizando
    //Separando las versiones Pre-Lollipop de las Lollipop o superior
    public void muestraImagen(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_yes,
                    getApplicationContext().getTheme()));
        }else{
            iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_yes));
        }
    }


}
