package com.algaworks.algafoodapi;

import com.algaworks.algafoodapi.di.service.AtivacaoClienteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public AtivacaoClienteService ativacaoClienteService(){
        return new AtivacaoClienteService();
    }
}
