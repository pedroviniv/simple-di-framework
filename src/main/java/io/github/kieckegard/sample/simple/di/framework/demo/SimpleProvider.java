/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.demo;

import io.github.kieckegard.sample.simple.di.framework.Inject;
import io.github.kieckegard.sample.simple.di.framework.Provide;
import io.github.kieckegard.sample.simple.di.framework.Provider;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
@Provider
public class SimpleProvider {
    
    private ProviderDependency providerDependency;

    @Inject
    public SimpleProvider(ProviderDependency providerDependency) {
        this.providerDependency = providerDependency;
    }
    
    @Provide
    public PhraseEndingService provide() {
        System.out.println("providerDependency: " + providerDependency.text());
        return new ExclamationMarkPhraseEndingService();
    }
}
