/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.impl;

import io.github.kieckegard.sample.simple.di.framework.DIContext;
import io.github.kieckegard.sample.simple.di.framework.demo.PhraseEndingService;
import io.github.kieckegard.sample.simple.di.framework.demo.Service;
import io.github.kieckegard.sample.simple.di.framework.impl.factory.BeanFactory;
import io.github.kieckegard.sample.simple.di.framework.impl.factory.BeanFactoryScanner;
import io.github.kieckegard.sample.simple.di.framework.impl.scopes.Scope;
import io.github.kieckegard.sample.simple.di.framework.impl.scopes.ScopeScanner;
import java.util.Map;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class DefaultDIContext implements DIContext {
    
    private final Map<BeanQualifier, BeanFactory> beanFactories;
    private final Map<String, Scope> scopes;

    public DefaultDIContext(
            BeanFactoryScanner beanFactoryScanner,
            ScopeScanner scopeScanner) {
        
        this.beanFactories = beanFactoryScanner.scan();
        this.scopes = scopeScanner.scan();
    }
    
    private String generateBeanKey(BeanFactory beanFactory) {
        return String.format("%s_%s", beanFactory.getReturnType().getName(),
                beanFactory.getQualifier());
    }

    @Override
    public <T> T getBean(Class<T> beanType, String qualifier) {
        
        final BeanQualifier beanQualifier = new BeanQualifier(beanType, qualifier);
        final BeanFactory beanFactory = this.beanFactories.get(beanQualifier);
        
        final String beanScope = beanFactory.getScope();
        
        final Scope scope = this.scopes.get(beanScope);
        final String beanKey = this.generateBeanKey(beanFactory);
        
        final Object instance = scope.getBean(beanKey, () -> beanFactory.create(this));
        
        return (T) instance;
    }
    
    public static void main(String[] args) {
        
        DefaultDIContext ctx = new DefaultDIContext(
                new BeanFactoryScanner(),
                new ScopeScanner());
        
        Service service = ctx.getBean(Service.class, "");
        System.out.println(service.helloWorld());
        
        PhraseEndingService service2 = ctx.getBean(PhraseEndingService.class, "");
        System.out.println(service2.getEnding());
    }

    
}
