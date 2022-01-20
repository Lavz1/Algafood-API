package com.algaworks.algafoodapi.di.notificacao;

import com.algaworks.algafoodapi.di.modelo.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@TipoDoNotificador(NivelUrgencia.NORMAL)
@Component
public class NotificadorEmail implements Notificador{

//    @Value("${notificador.email.host-servidor}")
//    private String host;
//
//    @Value("${notifacador.email.porta-servidor}")
//    private Integer port;
    @Autowired
    private NotificadorProperties notificadorProperties;

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        System.out.println(notificadorProperties.getHostServidor());
        System.out.println(notificadorProperties.getPortaServidor());

        System.out.printf("Notificando %s através do e-mail %s : %s\n",
                cliente.getNome(), cliente.getEmail(), mensagem);
    }
}
