package com.rubino.pmdmcontacto;

import java.util.Comparator;

/**
 * Created by marco on 07/10/2015.
 */
public class OrdenaTelefonos implements Comparator<Contacto> {

    @Override
    public int compare(Contacto c1, Contacto c2) {

        if(c1.getTelf().compareTo(c2.getTelf())>0)
            return 1;
        if(c1.getTelf().compareTo(c2.getTelf())<0)
            return -1;
        return 0;
    }
}
