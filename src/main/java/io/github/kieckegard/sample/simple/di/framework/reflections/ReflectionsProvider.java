/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.reflections;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class ReflectionsProvider {
    
    public static Reflections get() {
        return Holder.INSTANCE;
    }
    
    public static class Holder {
        
         private static final Reflections INSTANCE = new Reflections(
            "io.github.kieckegard.sample.simple.di.framework", 
            new MethodAnnotationsScanner(),
            new TypeAnnotationsScanner(),
            new SubTypesScanner()
         );
    }
}
