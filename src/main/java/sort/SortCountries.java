/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sort;

import java.util.Comparator;

/**
 *
 * @author elisc
 */
public class SortCountries implements Comparator<String>{

    @Override
    public int compare(String s1, String s2) {
        if(s1.compareTo(s2) > 0){
            return 1;
        }
        else if(s1.compareTo(s2) < 0){
            return -1;
        }
        return 0;
    }
    
}
