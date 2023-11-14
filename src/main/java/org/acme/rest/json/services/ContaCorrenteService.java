package org.acme.rest.json.services;
import org.acme.rest.json.GlobalExceptionHandler.ContaInvalidaException;
import org.acme.rest.json.GlobalExceptionHandler.SaldoInsuficienteException;
import org.acme.rest.json.models.ContaCorrente;

import java.util.List;

public interface ContaCorrenteService {
        ContaCorrente getContaPorNumero(String numeroConta);
        void depositar(String numeroConta, double valor) throws ContaInvalidaException;
        void sacar(String numeroConta, double valor) throws ContaInvalidaException, SaldoInsuficienteException;
        void transferir(String contaOrigem, String contaDestino, double valor) throws ContaInvalidaException, SaldoInsuficienteException;
        ContaCorrente criarConta(String nome, String cpf) throws ContaInvalidaException;


        List<ContaCorrente> listarContas();
}

