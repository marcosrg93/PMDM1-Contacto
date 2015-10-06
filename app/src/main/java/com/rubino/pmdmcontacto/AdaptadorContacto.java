package com.rubino.pmdmcontacto;

/**
 * Created by marco on 06/10/2015.
 */

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdaptadorContacto extends ArrayAdapter<Contacto>{

    private Context ctx;
    private int res;
    private LayoutInflater lInflator;
    private List<Contacto> valores;

    static class ViewHolder {
        public TextView tv1, tv2;
        public ImageView iv;
    }

    public AdaptadorContacto(Context context, int resource, List<Contacto> objects) {
        super(context, resource, objects);
        this.ctx = context;//actividad
        this.res = resource;//layout del item
        this.valores = objects;//lista de valores
        this.lInflator = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }



    public boolean borrar(int position) {
        try {
            valores.remove(position);
            this.notifyDataSetChanged();
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1
        ViewHolder gv = new ViewHolder();
        if(convertView==null){
            convertView = lInflator.inflate(res, null);
            TextView tv = (TextView) convertView.findViewById(R.id.tvNombre);
            gv.tv1 = tv;
            tv = (TextView) convertView.findViewById(R.id.tvTelf);
            gv.tv2 = tv;
            ImageView iv = (ImageView) convertView.findViewById(R.id.ivNum);
            gv.iv = iv;
            convertView.setTag(gv);
        } else {
            gv = (ViewHolder) convertView.getTag();
        }
        gv.iv.setTag(position);
        addListener(gv.iv, position);
        gv.tv1.setText(valores.get(position).getNombre());
        gv.tv2.setText("Tlf: "+valores.get(position).getTelf());
        return convertView;
    }

    private void addListener(ImageView iv, final int position){
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "flor "+position, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //Toast.makeText(ctx, "flor "+position, Toast.LENGTH_LONG).show();
            }
        });
    }
}
