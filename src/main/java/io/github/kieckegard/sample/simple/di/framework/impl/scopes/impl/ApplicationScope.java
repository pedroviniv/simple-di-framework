/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.impl.scopes.impl;

import io.github.kieckegard.sample.simple.di.framework.WithScope;
import io.github.kieckegard.sample.simple.di.framework.impl.scopes.BeanProvider;
import io.github.kieckegard.sample.simple.di.framework.impl.scopes.Scope;
import io.github.kieckegard.sample.simple.di.framework.impl.scopes.ScopeDefinition;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
@ScopeDefinition(WithScope.APPLICATION_SCOPE)
public class ApplicationScope implements Scope {

    private final Map<String, Object> holder = Collections.synchronizedMap(new HashMap<>());
    private final Lock lock = new ReentrantLock();

    @Override
    public Object getBean(String key, BeanProvider beanProvider) {

        final Object existent = this.holder.get(key);
        
        if (existent != null) {
            return existent;
        }

        /**
         * guarded block of code. Here just one thread per time
         * are allowed to access.
         */
        this.lock.lock(); 
        
        try {
            if (existent == null) {
                Object provided = beanProvider.provide();
                this.holder.put(key, provided);
                return provided;
            }

            return existent;
        } finally {
            this.lock.unlock();
        }
    }

}
