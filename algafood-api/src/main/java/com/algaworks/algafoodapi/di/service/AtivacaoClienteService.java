package com.algaworks.algafoodapi.di.service;

import com.algaworks.algafoodapi.di.modelo.Cliente;
import com.algaworks.algafoodapi.di.notificacao.NivelUrgencia;
import com.algaworks.algafoodapi.di.notificacao.Notificador;
import com.algaworks.algafoodapi.di.notificacao.TipoDoNotificador;
import org.springframework.beans.factory.annotation.Autowired;

public class AtivacaoClienteService {

    @TipoDoNotificador(NivelUrgencia.NORMAL)
    @Autowired
    private Notificador notificadorEmail;

//    @PostConstruct
    public void init(){
        System.out.println("INIT");
    }
//    @PreDestroy
    public void preDestruct(){
        System.out.println("PRE DESTRUCT");
    }

    public void ativar(Cliente cliente) {
        cliente.ativar();
        this.notificadorEmail.notificar(cliente, "Seu cadastro no sistema está ativo!");
    }
}
