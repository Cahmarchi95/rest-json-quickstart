package org.acme.rest.json;

import jakarta.ws.rs.*;
import org.acme.rest.json.GlobalExceptionHandler.ContaInvalidaException;
import org.acme.rest.json.GlobalExceptionHandler.SaldoInsuficienteException;
import org.acme.rest.json.models.ContaCorrente;


import org.acme.rest.json.services.ContaCorrenteService;

import java.util.List;

@Path("/contas")
public class BancoResource {

    private ContaCorrenteService contaService;

    public BancoResource(ContaCorrenteService contaService) {
        this.contaService = contaService;
    }

    @GET
    public List<ContaCorrente> listarContas() {
        return contaService.listarContas();
    }

    @POST
    public String CriarConta( @FormParam("nome") String nome, @FormParam("cpf") String cpf) {
        try {
            ContaCorrente contaCorrente = contaService.criarConta(nome, cpf);
            return "Conta criada com sucesso!\n" + contaCorrente.toString();
        } catch (ContaInvalidaException e) {
            throw new WebApplicationException("Conta inválida. Detalhes: " + e.getMessage(), 400);
        }
    }

    @GET
    @Path("/{numeroConta}")
    public ContaCorrente verSaldo(@PathParam("numeroConta") String numeroConta) {
        return contaService.getContaPorNumero(numeroConta);
    }

    @POST
    @Path("/depositar")
    public String depositar( @FormParam("numeroConta") String numeroConta,
                             @FormParam("valor") double valor) {
        try {
             contaService.depositar(numeroConta, valor);
            return "Depósito realizado com sucesso para a conta " + numeroConta;
        } catch (ContaInvalidaException e) {
            throw new WebApplicationException("Conta inválida. Detalhes: " + e.getMessage(), 400);
        }
    }

    @POST
    @Path("/sacar")
    public String sacar(@FormParam("numeroConta") String numeroConta,
                        @FormParam("valor") double valor) {
        try {
             contaService.sacar(numeroConta,valor);
            return "Saque de " + valor + " realizado com sucesso da conta " + numeroConta;
        } catch (ContaInvalidaException | SaldoInsuficienteException e) {
            throw new WebApplicationException("Operação inválida. Detalhes: " + e.getMessage(), 400);
        }
    }

    @POST
    @Path("/transferir")
    public String transferir(@FormParam("contaOrigem") String contaOrigem,@FormParam("contaDestino") String contaDestino,@FormParam("valor") double valor) {
        try {
            contaService.transferir(contaOrigem, contaDestino, valor);
           return "Transferência realizada com sucesso";

        } catch (ContaInvalidaException | SaldoInsuficienteException e) {
            throw new WebApplicationException("Operação inválida. Detalhes: " + e.getMessage(), 400);
        }
    }
}
