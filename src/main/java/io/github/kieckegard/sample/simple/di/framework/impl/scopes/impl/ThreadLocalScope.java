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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
@ScopeDefinition(WithScope.THREAD_LOCAL_SCOPE)
public class ThreadLocalScope implements Scope {

    private final ThreadLocal<Map<String, Object>> holder = new ThreadLocal<>();
    
    @Override
    public Object getBean(String key, BeanProvider beanProvider) {
        
        Map<String, Object> data = this.holder.get();
        if (data == null) {
            data = new HashMap<>();
            this.holder.set(data);
        }
        
        Object instance = data.get(key);
        if (instance == null) {
            Object provided = beanProvider.provide();
            data.put(key, provided);
            
            return provided;
        }
        
        return instance;
    }
}
