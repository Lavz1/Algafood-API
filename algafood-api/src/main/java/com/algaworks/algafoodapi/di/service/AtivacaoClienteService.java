package com.algaworks.algafoodapi.di.service;

import com.algaworks.algafoodapi.di.modelo.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public class AtivacaoClienteService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void ativar(Cliente cliente) {
        cliente.ativar();

        applicationEventPublisher.publishEvent(new ClienteAtivadoEvent(cliente));
    }
}
