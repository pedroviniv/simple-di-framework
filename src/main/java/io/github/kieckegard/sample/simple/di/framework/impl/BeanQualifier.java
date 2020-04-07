/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.impl;

import io.github.kieckegard.sample.simple.di.framework.Qualifier;
import java.util.Objects;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class BeanQualifier {
    
    private Class<?> type;
    private String qualifier;

    public BeanQualifier(Class<?> type, String qualifier) {
        this.type = type;
        this.qualifier = qualifier;
    }

    public Class<?> getType() {
        return type;
    }

    public String getQualifier() {
        return qualifier;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.type);
        hash = 89 * hash + Objects.hashCode(this.qualifier);
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
        final BeanQualifier other = (BeanQualifier) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        
        if (!Objects.equals(this.qualifier, other.qualifier)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BeanQualifier{" + "type=" + type + ", qualifier=" + qualifier + '}';
    }
}
