package com.algaworks.algafoodapi.di.listener;

import com.algaworks.algafoodapi.di.notificacao.NivelUrgencia;
import com.algaworks.algafoodapi.di.notificacao.Notificador;
import com.algaworks.algafoodapi.di.notificacao.TipoDoNotificador;
import com.algaworks.algafoodapi.di.service.ClienteAtivadoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class EmissaoNotaFiscalService {

    @TipoDoNotificador(NivelUrgencia.URGENTE)
    @Autowired
    Notificador notificador;

    @EventListener
    @Order(2)
    public void emissaoNotaFiscalService(ClienteAtivadoEvent event){
        notificador.notificar(event.getCliente(), "Nota Fiscal emitida para o cliente");
    }
}
