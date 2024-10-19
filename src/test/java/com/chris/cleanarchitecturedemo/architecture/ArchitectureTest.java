package com.chris.cleanarchitecturedemo.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "com.chris.cleanarchitecturedemo")
class ArchitectureTest {

    private static final String SOURCE_ROOT = "com.chris.cleanarchitecturedemo";
    private static final String ADAPTER_LAYER = "Adapters";
    private static final String CORE_LAYER = "Core";
    private static final String DOMAIN_LAYER = "Domain";

    @ArchTest
    static final ArchRule cleanArchitecturePrinciplesAreRespected = layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            .layer(DOMAIN_LAYER).definedBy(SOURCE_ROOT + ".core.domain..")
            .layer(CORE_LAYER).definedBy(SOURCE_ROOT + ".core..")
            .layer(ADAPTER_LAYER).definedBy(SOURCE_ROOT + ".adapters..")

            .whereLayer(CORE_LAYER).mayOnlyAccessLayers(DOMAIN_LAYER)
            .whereLayer(CORE_LAYER).mayOnlyBeAccessedByLayers(ADAPTER_LAYER)
            .whereLayer(ADAPTER_LAYER).mayNotBeAccessedByAnyLayer()
            .because("dependencies across layers should only point towards the core");

    @ArchTest
    static final ArchRule coreShouldHaveOnlyAllowedDependencies =
            classes().that().resideInAPackage(SOURCE_ROOT + ".core..")
                    .should().onlyDependOnClassesThat().resideInAnyPackage(
                            // Internal business logic classes
                            SOURCE_ROOT + ".core..",
                            // Foundational Java packages
                            "java..",
                            // Other non-detail packages
                            "lombok..",
                            "org.slf4j.."
                    )
                    .because("the core layer should be independent of any external details");
}
