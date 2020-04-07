/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.impl.scopes;

import io.github.kieckegard.sample.simple.di.framework.reflections.ReflectionsProvider;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import static java.util.stream.Collectors.toMap;
import org.reflections.Reflections;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */

public class ScopeScanner {

    public Map<String, Scope> scan() {
        
        final Reflections reflections = ReflectionsProvider.get();
        final Set<Class<? extends Scope>> scopesClasses = reflections.getSubTypesOf(Scope.class);

        final Map<String, Scope> scanned = scopesClasses.stream()
                .map(scopeClass -> {
                    
                    final ScopeDefinition scopeDefinition = scopeClass
                            .getAnnotation(ScopeDefinition.class);
                    
                    if (scopeDefinition == null) {
                        return null;
                    }
                    
                    try {
                        String scopeId = scopeDefinition.value();
                        
                        Scope scopeInstance = scopeClass.newInstance();
                        return new AbstractMap.SimpleEntry<>(scopeId, scopeInstance);
                    } catch (InstantiationException | IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .filter(Objects::nonNull)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
        
        return scanned;
    }
}
