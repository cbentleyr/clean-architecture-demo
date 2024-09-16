package com.chris.cleanarchitecturedemo;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ModularityTests {

    @Test
    void verifyModularityAndWriteDocumentation() {
        var modules = ApplicationModules.of(CleanArchitectureDemoApplication.class).verify();

        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();
    }
}
