/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.demo;

import io.github.kieckegard.sample.simple.di.framework.Implementation;
import io.github.kieckegard.sample.simple.di.framework.Inject;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
@Implementation
public class DefaultService implements Service {
    
    private NestedService nestedService;
    private PhraseEndingService phraseEndingService;

    @Inject
    public DefaultService(NestedService nestedService, PhraseEndingService phraseEndingService) {
        this.nestedService = nestedService;
        this.phraseEndingService = phraseEndingService;
    }
    
    @Override
    public String helloWorld() {
        return String.format("Hello, %s%s",
                this.nestedService.getName(),
                this.phraseEndingService.getEnding()
        );
    }
    
}
