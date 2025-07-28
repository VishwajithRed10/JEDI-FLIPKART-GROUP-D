package com.flipkart.rest;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class FlipFitApplication extends Application<FlipFitConfiguration> {

    public static void main(String[] args) throws Exception {
        new FlipFitApplication().run(args);
    }

    @Override
    public String getName() {
        return "FlipFit";
    }

    @Override
    public void initialize(Bootstrap<FlipFitConfiguration> bootstrap) {
        // ...
    }

    @Override
    public void run(FlipFitConfiguration configuration, Environment environment) {
        System.out.println("Registering RESTful resources");

        // Create and register all the API resources
        final GymUserResource userResource = new GymUserResource();
        final GymAdminResource adminResource = new GymAdminResource();
        final GymCustomerResource customerResource = new GymCustomerResource();
        final GymOwnerResource ownerResource = new GymOwnerResource();

        environment.jersey().register(userResource);
        environment.jersey().register(adminResource);
        environment.jersey().register(customerResource);
        environment.jersey().register(ownerResource);
    }
}