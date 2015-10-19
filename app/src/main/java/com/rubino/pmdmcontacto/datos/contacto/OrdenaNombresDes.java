package com.rubino.pmdmcontacto.datos.contacto;

import com.rubino.pmdmcontacto.datos.contacto.Contacto;

import java.util.Comparator;

/**
 * Created by marco on 07/10/2015.
 */
public class OrdenaNombresDes implements Comparator<Contacto> {

    @Override
    public int compare(Contacto c1, Contacto c2) {
        if(c1.getNombre().compareTo(c2.getNombre())<0)
            return 1;
        if(c1.getNombre().compareTo(c2.getNombre())>0)
            return -1;
        return 0;
    }
}
