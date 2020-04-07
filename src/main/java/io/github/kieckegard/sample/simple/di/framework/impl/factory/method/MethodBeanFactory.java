/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.impl.factory.method;

import io.github.kieckegard.sample.simple.di.framework.DIContext;
import io.github.kieckegard.sample.simple.di.framework.Qualifier;
import io.github.kieckegard.sample.simple.di.framework.impl.factory.BeanFactory;
import io.github.kieckegard.sample.simple.di.framework.impl.factory.Dependency;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class MethodBeanFactory extends BeanFactory {

    private final OwnerBeanDefinition ownerBeanDefinition;
    private final Method factoryMethod;
    private final String qualifier;

    public MethodBeanFactory(OwnerBeanDefinition ownerBeanDefinition, Method factoryMethod, String qualifier, Set<Dependency> dependencies, String scope) {
        super(factoryMethod.getReturnType(), dependencies, scope);
        this.ownerBeanDefinition = ownerBeanDefinition;
        this.factoryMethod = factoryMethod;
        this.qualifier = qualifier;
    }
    
    @Override
    public Object create(DIContext ctx) {

        Object ownerBeanInstance = ctx.getBean(
                this.ownerBeanDefinition.getType(),
                this.ownerBeanDefinition.getQualifier()
        );

        final Object[] dependenciesInstances = getDependencies().stream()
                .map(dep -> {
                    return ctx.getBean(dep.getType(), dep.getQualifier());
                })
                .toArray();

        try {
            return factoryMethod.invoke(ownerBeanInstance, dependenciesInstances);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.returnType);
        hash = 29 * hash + Objects.hashCode(this.getQualifier());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MethodBeanFactory other = (MethodBeanFactory) obj;
        if (!Objects.equals(this.returnType, other.returnType)) {
            return false;
        }
        
        
        if (!Objects.equals(this.getQualifier(), other.getQualifier())) {
            return false;
        }
        return true;
    }
    
    public String getQualifier() {
        return this.qualifier;
    }

}
