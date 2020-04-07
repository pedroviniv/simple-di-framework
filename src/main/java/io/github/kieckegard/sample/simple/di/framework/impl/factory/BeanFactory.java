/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.impl.factory;

import io.github.kieckegard.sample.simple.di.framework.DIContext;
import io.github.kieckegard.sample.simple.di.framework.Qualifier;
import io.github.kieckegard.sample.simple.di.framework.impl.BeanQualifier;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public abstract class BeanFactory {
    
    protected final Class<?> returnType;
    protected final Set<Dependency> dependencies;
    protected final String scope;

    public BeanFactory(Class<?> returnType, Set<Dependency> dependencies, String scope) {
        this.returnType = returnType;
        this.dependencies = dependencies;
        this.scope = scope;
    }

    public BeanFactory(Class<?> returnType, String scope) {
        this.returnType = returnType;
        this.scope = scope;
        this.dependencies = new HashSet<>();
    }
    
    public abstract Object create(DIContext ctx);

    public Class<?> getReturnType() {
        return returnType;
    }

    public Set<Dependency> getDependencies() {
        return dependencies;
    }

    public String getScope() {
        return scope;
    }
    
    public abstract String getQualifier();

    @Override
    public String toString() {
        return "BeanFactory{" + "returnType=" + returnType + ", dependencies=" + dependencies + ", scope=" + scope + '}';
    }
}
