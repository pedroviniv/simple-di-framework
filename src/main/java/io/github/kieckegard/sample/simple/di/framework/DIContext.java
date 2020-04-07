/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public interface DIContext {
    
    /**
     * gets a implementation of the
     * beanType provided.
     * 
     * @param <T>
     * @param beanType
     * @param qualifier
     * @return 
     */
    <T> T getBean(Class<T> beanType, String qualifier);
}
