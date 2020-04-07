/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.impl.factory;

import io.github.kieckegard.sample.simple.di.framework.Qualifier;
import java.util.Optional;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class Dependency {
    
    private final Class<?> type;
    private final Optional<Qualifier> qualifier;

    public Dependency(Class<?> type, Qualifier qualifier) {
        this.type = type;
        this.qualifier = Optional.of(qualifier);
    }

    public Dependency(Class<?> type) {
        this.type = type;
        this.qualifier = Optional.empty();
    }
    

    public Class<?> getType() {
        return type;
    }

    public String getQualifier() {
        if (this.qualifier.isPresent()) {
            return this.qualifier.get().value();
        }
        return "";
    }

    @Override
    public String toString() {
        return "Dependency{" + "type=" + type + ", qualifier=" + qualifier + '}';
    }
}
