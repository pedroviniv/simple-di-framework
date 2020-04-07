/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.impl.factory.constructor;

import io.github.kieckegard.sample.simple.di.framework.DIContext;
import io.github.kieckegard.sample.simple.di.framework.Implementation;
import io.github.kieckegard.sample.simple.di.framework.Inject;
import io.github.kieckegard.sample.simple.di.framework.Qualifier;
import io.github.kieckegard.sample.simple.di.framework.impl.factory.BeanFactory;
import io.github.kieckegard.sample.simple.di.framework.impl.factory.Dependency;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class ConstructorBeanFactory extends BeanFactory {

    private final Optional<InjectableConstructor> constructor;
    private final Set<Class<?>> inherits;
    private final String qualifier;

    public ConstructorBeanFactory(InjectableConstructor constructor, Set<Class<?>> inherits,
            Class<?> returnType,
            Set<Dependency> dependencies,
            String qualifier, String scope) {
        
        super(returnType, dependencies, scope);
        this.constructor = Optional.of(constructor);
        this.inherits = inherits;
        this.qualifier = qualifier;
    }

    public ConstructorBeanFactory(Set<Class<?>> inherits, Class<?> returnType, String qualifier, String scope) {
        super(returnType, scope);
        this.inherits = inherits;
        this.qualifier = qualifier;
        this.constructor = Optional.empty();
    }

    @Override
    public Object create(DIContext ctx) {

        final Object[] dependenciesInstances = getDependencies().stream()
                .map(dep -> {
                    return ctx.getBean(dep.getType(), dep.getQualifier());
                })
                .toArray();

        try {

            if (!this.constructor.isPresent()) {
                return super.getReturnType().newInstance();
            }

            InjectableConstructor presentConstructor = this.constructor.get();

            return presentConstructor.newInstance(dependenciesInstances);
        } catch (InstantiationException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.returnType);
        hash = 13 * hash + Objects.hashCode(this.qualifier);
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
        final ConstructorBeanFactory other = (ConstructorBeanFactory) obj;
        if (!Objects.equals(this.returnType, other.returnType)) {
            return false;
        }
        if (!Objects.equals(this.qualifier, other.qualifier)) {
            return false;
        }
        return true;
    }

    public Set<Class<?>> getInherits() {
        return inherits;
    }

    @Override
    public String getQualifier() {
        return this.qualifier;
    }

}
