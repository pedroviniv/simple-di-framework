/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.demo1;

import io.github.kieckegard.sample.simple.di.framework.Implementation;
import io.github.kieckegard.sample.simple.di.framework.WithScope;
import java.util.UUID;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
@Implementation
@WithScope(WithScope.THREAD_LOCAL_SCOPE)
public class BoundToThreadImpl {
    
    private String id = UUID.randomUUID().toString();
    
    public String getId() {
        return this.id;
    }
}
