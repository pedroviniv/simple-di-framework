/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.demo1;

import io.github.kieckegard.sample.simple.di.framework.Provide;
import io.github.kieckegard.sample.simple.di.framework.Provider;
import io.github.kieckegard.sample.simple.di.framework.WithScope;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
@Provider
public class ExternalServiceProvider {
    
    @Provide
    @WithScope(WithScope.APPLICATION_SCOPE)
    public ExternalService provide(BoundToThreadImpl dependency) {
        return new ExternalService(dependency);
    }
}
