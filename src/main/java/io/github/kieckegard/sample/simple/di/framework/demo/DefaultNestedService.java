/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.demo;

import io.github.kieckegard.sample.simple.di.framework.Implementation;


/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
@Implementation
public class DefaultNestedService implements NestedService {

    @Override
    public String getName() {
        return "Fulano";
    }
    
}
