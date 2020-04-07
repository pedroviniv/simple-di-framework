/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.sample.simple.di.framework.impl.factory;

import io.github.kieckegard.sample.simple.di.framework.Implementation;
import io.github.kieckegard.sample.simple.di.framework.Inject;
import io.github.kieckegard.sample.simple.di.framework.Provide;
import io.github.kieckegard.sample.simple.di.framework.Provider;
import io.github.kieckegard.sample.simple.di.framework.Qualifier;
import io.github.kieckegard.sample.simple.di.framework.impl.BeanQualifier;
import io.github.kieckegard.sample.simple.di.framework.impl.factory.constructor.ConstructorBeanFactory;
import io.github.kieckegard.sample.simple.di.framework.impl.factory.constructor.InjectableConstructor;
import io.github.kieckegard.sample.simple.di.framework.impl.factory.method.MethodBeanFactory;
import io.github.kieckegard.sample.simple.di.framework.impl.factory.method.OwnerBeanDefinition;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import io.github.kieckegard.sample.simple.di.framework.WithScope;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class BeanFactoryScanner {

    public Reflections reflections = new Reflections(
            "io.github.kieckegard.sample.simple.di.framework", 
            new MethodAnnotationsScanner(), new TypeAnnotationsScanner(), new SubTypesScanner());

    private Boolean isQualifier(Annotation annotation) {
        return annotation.annotationType().equals(Qualifier.class);
    }

    private Set<Dependency> getConstructorDependencies(Class<?> type, Constructor injectableConstructor) {

        Annotation[][] annotationsMatrix = injectableConstructor
                .getParameterAnnotations();

        Parameter[] parameters = injectableConstructor.getParameters();

        return this.getDependencies(parameters, annotationsMatrix);
    }

    private Set<Dependency> getDependencies(Parameter[] parameters, Annotation[][] annotationsMatrix) {

        int parameterCount = parameters.length;

        Set<Dependency> dependencies = IntStream
                .range(0, parameterCount)
                .mapToObj(index -> {

                    Parameter parameter = parameters[index];
                    Annotation[] parameterAnnotations = annotationsMatrix[index];
                    if (parameterAnnotations.length == 0) {
                        return new Dependency(parameter.getType());
                    }

                    List<Annotation> qualifierAnnotations = Stream.of(parameterAnnotations)
                            .filter(this::isQualifier)
                            .collect(toList());

                    if (qualifierAnnotations.isEmpty()) {
                        return new Dependency(parameter.getType());
                    }

                    if (qualifierAnnotations.size() > 1) {
                        throw new IllegalArgumentException("You can not repeat"
                                + "the Qualifier annotation to the same parameter.");
                    }

                    Qualifier qualifier = (Qualifier) qualifierAnnotations.get(0);

                    return new Dependency(parameter.getType(), qualifier);
                })
                .collect(toSet());

        return dependencies;
    }

    private Set<ConstructorBeanFactory> scanProviderConstructorBeanFactories() {
        return this.scanConstructorFactoriesAnnotatedWith(Provider.class, Provider::value);
    }
    
    private Set<ConstructorBeanFactory> scanImplementationConstructorBeanFactories() {
        return this.scanConstructorFactoriesAnnotatedWith(Implementation.class, Implementation::value);
    }
    
    private Set<MethodBeanFactory> scanMethodBeanFactories() {
        
        Set<Method> methods = this.reflections.getMethodsAnnotatedWith(Provide.class);
        
        Set<MethodBeanFactory> factories = methods.stream()
                .map(method -> {
                    
                    final Set<Dependency> dependencies = this.getDependencies(
                            method.getParameters(),
                            method.getParameterAnnotations()
                    );
                    
                    final Class<?> ownerClass = method.getDeclaringClass();
                    final Provider provider = ownerClass.getAnnotation(Provider.class);
                    
                    if (provider == null) {
                        throw new RuntimeException("Only classes annotated with @Provider can declare @Provide methods");
                    }
                    
                    final String providerQualifier = provider.value();
                    
                    final OwnerBeanDefinition ownerBeanDefinition = new OwnerBeanDefinition(ownerClass, providerQualifier);
                    
                    final Qualifier qualifier = method.getAnnotation(Qualifier.class);
                    final String qualifierId = qualifier == null ? "" : qualifier.value();
                    
                    final WithScope scope = method.getAnnotation(WithScope.class);
                    final String scopeId = scope == null ? WithScope.APPLICATION_SCOPE : scope.value();
                    
                    return new MethodBeanFactory(ownerBeanDefinition, method, qualifierId, dependencies, scopeId);
                })
                .collect(toSet());
        
        return factories;
    }
    
    
    private Map<BeanQualifier, BeanFactory> toConstructorFactoryMap(Set<ConstructorBeanFactory> set) {
        return set.stream()
                .map(bf -> {
                    
                    if (bf.getInherits().isEmpty()) {
                        Map<BeanQualifier, ConstructorBeanFactory> qualifiers = new HashMap<>();
                        qualifiers.put(new BeanQualifier(bf.getReturnType(), bf.getQualifier()), bf);
                        return qualifiers.entrySet();
                    }
                    
                    Map<BeanQualifier, ConstructorBeanFactory> qualifiers = bf.getInherits()
                            .stream()
                            .map(inherit -> {
                                return new BeanQualifier(inherit, bf.getQualifier());
                            })
                            .collect(toMap(Function.identity(), e -> bf));
                    
                    return qualifiers.entrySet();
                })
                .flatMap(Set::stream)
                .collect(toMap(Map.Entry::getKey, entry -> (BeanFactory) entry.getValue()));
    }
    
    private Map<BeanQualifier, BeanFactory> toMethodFactoryMap(Set<MethodBeanFactory> set) {
        return set.stream()
                .collect(toMap(entry -> new BeanQualifier(entry.getReturnType(), entry.getQualifier()), Function.identity()));
    }
    
    public Map<BeanQualifier, BeanFactory> scan() {
        
        Set<ConstructorBeanFactory> impl = this.scanImplementationConstructorBeanFactories();
        Set<ConstructorBeanFactory> providers = this.scanProviderConstructorBeanFactories();
        Set<MethodBeanFactory> methods = this.scanMethodBeanFactories();
        
        Map<BeanQualifier, BeanFactory> implFactories = this.toConstructorFactoryMap(impl);
        Map<BeanQualifier, BeanFactory> providerFactories = this.toConstructorFactoryMap(providers);
        Map<BeanQualifier, BeanFactory> methodsFactories = this.toMethodFactoryMap(methods);
        
        Map<BeanQualifier, BeanFactory> all = new HashMap<>();
        all.putAll(implFactories);
        all.putAll(providerFactories);
        all.putAll(methodsFactories);
        
        return all;
    }

    private <T extends Annotation> Set<ConstructorBeanFactory> scanConstructorFactoriesAnnotatedWith(
            Class<T> annotation,
            Function<T, String> qualifierRetriever) {

        Set<ConstructorBeanFactory> implementations = this.reflections
                .getTypesAnnotatedWith(annotation)
                .stream()
                .map((Class<?> type) -> {

                    Set<Class<?>> inherits = new HashSet<>();
                    inherits.addAll(Stream.of(type.getInterfaces()).collect(toSet()));

                    final Class<?> superClass = type.getSuperclass();
                    if (!superClass.equals(Object.class)) {
                        inherits.add(superClass);
                    }

                    Annotation implementation = type.getDeclaredAnnotation(annotation);
                    String qualifier = qualifierRetriever.apply((T) implementation);

                    List<InjectableConstructor> injectableConstructors = Stream.of(type.getConstructors())
                            .map(constructor -> {
                                Inject inject = constructor.getAnnotation(Inject.class);
                                if (inject == null) {
                                    return null;
                                }
                                return new InjectableConstructor(inject, constructor);
                            })
                            .filter(Objects::nonNull)
                            .collect(toList());

                    if (injectableConstructors.size() > 1) {
                        throw new IllegalArgumentException(
                                "You can't have two constructors annotated with"
                                        + "@Inject in the same class");
                    }
                    
                    final WithScope scope = type.getAnnotation(WithScope.class);
                    String scopeId = scope == null ? WithScope.APPLICATION_SCOPE : scope.value();

                    if (injectableConstructors.isEmpty()) {
                        return new ConstructorBeanFactory(inherits, type, qualifier, scopeId);
                    }
                    
                    
                    final InjectableConstructor constructor = injectableConstructors.get(0);
                    
                    final Set<Dependency> dependencies = this.getDependencies(
                            constructor.getConstructor().getParameters(),
                            constructor.getConstructor().getParameterAnnotations()
                    );
                    
                    return new ConstructorBeanFactory(constructor, inherits, type, dependencies, qualifier, scopeId);
                })
                .collect(toSet());

        return implementations;
    }
    
    public static void main(String[] args) {
        
        BeanFactoryScanner scanner = new BeanFactoryScanner();
        Set<MethodBeanFactory> methodBeanFactories = scanner.scanMethodBeanFactories();
        
        System.out.println("\n\n\nMETHOD_BEAN_FACTORIES===");
        methodBeanFactories.forEach(System.out::println);
        
        System.out.println("\n\n\nPROVIDER_CONSTRUCTOR_BEAN_FACTORIES===");
        Set<ConstructorBeanFactory> constructorBeanFactories = scanner.scanProviderConstructorBeanFactories();
        constructorBeanFactories.forEach(System.out::println);
        System.out.println("\n\n\nIMPLEMENTATION_CONSTRUCTOR_BEAN_FACTORIES===");
        Set<ConstructorBeanFactory> implementationsBeanFactories = scanner.scanImplementationConstructorBeanFactories();
        implementationsBeanFactories.forEach(System.out::println);
    }
}
