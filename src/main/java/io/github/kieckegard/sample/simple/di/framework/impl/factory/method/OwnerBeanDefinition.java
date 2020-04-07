/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.impl.factory.method;

import io.github.kieckegard.sample.simple.di.framework.Qualifier;
import java.util.Optional;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class OwnerBeanDefinition {
    
    private final Class<?> type;
    private final String qualifier;

    public OwnerBeanDefinition(Class<?> type, String qualifier) {
        this.type = type;
        this.qualifier = qualifier;
    }

    public OwnerBeanDefinition(Class<?> type) {
        this.type = type;
        this.qualifier = "";
    }

    public Class<?> getType() {
        return type;
    }

    public String getQualifier() {
        return this.qualifier;
    }

    @Override
    public String toString() {
        return "OwnerBeanDefinition{" + "type=" + type + ", qualifier=" + qualifier + '}';
    }
}
