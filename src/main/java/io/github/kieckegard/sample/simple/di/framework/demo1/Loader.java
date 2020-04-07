/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.demo1;

import io.github.kieckegard.sample.simple.di.framework.impl.DefaultDIContext;
import io.github.kieckegard.sample.simple.di.framework.impl.factory.BeanFactoryScanner;
import io.github.kieckegard.sample.simple.di.framework.impl.scopes.ScopeScanner;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class Loader {
    
    public static void main(String[] args) {
        
        DefaultDIContext context = new DefaultDIContext(
                new BeanFactoryScanner(),
                new ScopeScanner());
        
        ExternalService externalService = context.getBean(ExternalService.class, "");
        System.out.println(externalService.hello());
        
        System.out.println("\n======\n");
        
        new Thread(() -> {
            BoundToThreadImpl dependency = context.getBean(BoundToThreadImpl.class, "");
            System.out.println(Thread.currentThread().getName() + ": " + dependency.getId());
            System.out.println(Thread.currentThread().getName() + ": " + context.getBean(ExternalService.class, "").hello());
        }).start();
        
        new Thread(() -> {
            BoundToThreadImpl dependency = context.getBean(BoundToThreadImpl.class, "");
            System.out.println(Thread.currentThread().getName() + ": " + dependency.getId());
            System.out.println(Thread.currentThread().getName() + ": " + context.getBean(ExternalService.class, "").hello());
        }).start();
        
        new Thread(() -> {
            BoundToThreadImpl dependency = context.getBean(BoundToThreadImpl.class, "");
            System.out.println(Thread.currentThread().getName() + ": " + dependency.getId());
            System.out.println(Thread.currentThread().getName() + ": " + context.getBean(ExternalService.class, "").hello());
        }).start();
        
        new Thread(() -> {
            BoundToThreadImpl dependency = context.getBean(BoundToThreadImpl.class, "");
            System.out.println(Thread.currentThread().getName() + ": " + dependency.getId());
            System.out.println(Thread.currentThread().getName() + ": " + context.getBean(ExternalService.class, "").hello());
        }).start();
        
        new Thread(() -> {
            BoundToThreadImpl dependency = context.getBean(BoundToThreadImpl.class, "");
            System.out.println(Thread.currentThread().getName() + ": " + dependency.getId());
            System.out.println(Thread.currentThread().getName() + ": " + context.getBean(ExternalService.class, "").hello());
        }).start();
        
        new Thread(() -> {
            BoundToThreadImpl dependency = context.getBean(BoundToThreadImpl.class, "");
            System.out.println(Thread.currentThread().getName() + ": " + dependency.getId());
            System.out.println(Thread.currentThread().getName() + ": " + context.getBean(ExternalService.class, "").hello());
        }).start();
        
        
        
    }
}
