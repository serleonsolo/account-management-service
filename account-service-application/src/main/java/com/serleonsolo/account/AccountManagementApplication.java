package com.serleonsolo.account;

import com.serleonsolo.account.configuration.CommonConfiguration;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.bootstrap.BootstrapApplicationListener;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancerAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@SpringBootApplication(exclude = {
        LoadBalancerAutoConfiguration.class,
        ReactiveLoadBalancerAutoConfiguration.class,
        FlywayAutoConfiguration.class
})
@ComponentScan(basePackages = {"com.serleonsolo.account.*"})
@EnableConfigurationProperties(CommonConfiguration.class)
public class AccountManagementApplication {

    public static void main(String[] args) {
        String developmentProfile = "development";
        String profileProperty = "spring.profiles.active";
        boolean development = developmentProfile.equals(System.getProperty(profileProperty)) || Arrays.stream(args)
                .filter(arg -> arg.contains(profileProperty))
                .anyMatch(arg -> arg.contains(developmentProfile));

        SpringApplication app = new SpringApplication(AccountManagementApplication.class) {
            @Override
            public void setListeners(@NotNull Collection<? extends ApplicationListener<?>> listeners) {
                super.setListeners(listeners.stream()
                        .filter((listener) -> !development || !(listener instanceof BootstrapApplicationListener))
                        .collect(Collectors.toList()));
            }
        };
        app.setWebApplicationType(WebApplicationType.REACTIVE);
        app.run(args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            final String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (final String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }
}
