/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.demo1;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class ExternalService {
    
    private BoundToThreadImpl dependency;
    
    public ExternalService(BoundToThreadImpl dependency) {
        this.dependency = dependency;
    }
    
    public String hello() {
        return String.format("hello, %s", this.dependency.getId());
    }
}
