/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.impl.scopes;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
@FunctionalInterface
public interface BeanProvider {
    
    Object provide();
}
