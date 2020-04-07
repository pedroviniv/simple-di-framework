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
import java.util.Map;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class DefaultDIContext implements DIContext {
    
    private Map<BeanQualifier, BeanFactory> factories;
    
    private BeanFactoryScanner scanner;

    public DefaultDIContext(BeanFactoryScanner scanner) {
        this.factories = scanner.scan();
    }

    @Override
    public <T> T getBean(Class<T> beanType, String qualifier) {
        
        BeanQualifier beanQualifier = new BeanQualifier(beanType, qualifier);
        BeanFactory factory = this.factories.get(beanQualifier);
        
        Object instance = factory.create(this);
        
        return (T) instance;
    }
    
    public static void main(String[] args) {
        
        DefaultDIContext ctx = new DefaultDIContext(new BeanFactoryScanner());
        
        Service service = ctx.getBean(Service.class, "");
        System.out.println(service.helloWorld());
        
        PhraseEndingService service2 = ctx.getBean(PhraseEndingService.class, "");
        System.out.println(service2.getEnding());
    }

    
}
