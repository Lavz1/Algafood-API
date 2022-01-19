package com.algaworks.algafoodapi.di.notificacao;

import com.algaworks.algafoodapi.di.modelo.Cliente;


public class NotificadorEmail implements Notificador{

    private boolean caixaAlta;
    private String hostServicoSmtp;

    public NotificadorEmail(String hostServicoSmtp){
        this.hostServicoSmtp = hostServicoSmtp;
    }

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        if(this.caixaAlta) {
            mensagem = mensagem.toUpperCase();
        }

        System.out.printf("Notificando %s através do e-mail %s usando SMTP %s: %s\n",
                cliente.getNome(), cliente.getEmail(), this.hostServicoSmtp, mensagem);
    }

    public void setCaixaAlta(boolean caixaAlta) {
        this.caixaAlta = caixaAlta;
    }
}
