/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.impl.factory.constructor;

import io.github.kieckegard.sample.simple.di.framework.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class InjectableConstructor {
    
    private Inject inject;
    private Constructor<?> constructor;

    public InjectableConstructor(Inject inject, Constructor<?> constructor) {
        this.inject = inject;
        this.constructor = constructor;
    }
    
    public Object newInstance(Object[] params) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return this.constructor.newInstance(params);
    }

    public Inject getInject() {
        return inject;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }

    @Override
    public String toString() {
        return "InjectableConstructor{" + "inject=" + inject + ", constructor=" + constructor + '}';
    }

}
