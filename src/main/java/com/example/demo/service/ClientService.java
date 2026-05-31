package com.example.demo.service;

import com.example.demo.dto.ClienteRequestDTO;
import com.example.demo.dto.ClienteResponseDTO;
import com.example.demo.exception.RecursoNaoEncontradoException;
import com.example.demo.exception.RegraDeNegocioException;
import com.example.demo.model.ClienteModel;
import com.example.demo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    ClienteRepository clienteRepository;

    @Transactional
    public ClienteResponseDTO criar(ClienteRequestDTO request) {
        validarCpfCnpjNovo(request.getCpfCnpj());

        ClienteModel novoCliente = new ClienteModel();
        novoCliente.setNomeCompleto(request.getNomeCompleto());
        novoCliente.setCpfCnpj(request.getCpfCnpj());
        novoCliente.setTelefone(request.getTelefone());
        novoCliente.setEmail(request.getEmail());
        novoCliente.setEndereco(request.getEndereco());

        ClienteModel salvo = clienteRepository.save(novoCliente);
        return toResponseDTO(salvo);
    }

    @Transactional(readOnly = true)
    public Page<ClienteResponseDTO> listar(String busca, Pageable pageable) {
        Page<ClienteModel> pagina;

        if (busca == null || busca.isBlank()) {
            pagina = clienteRepository.findAll(pageable);
        } else {
            pagina = clienteRepository.buscarPorNomeOuCpfCnpj(busca.trim(), pageable);
        }

        return pagina.map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        ClienteModel cliente = buscarOuLancarExcecao(id);
        return toResponseDTO(cliente);
    }

    @Transactional
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO request) {
        ClienteModel cliente = buscarOuLancarExcecao(id);

        if (!request.getCpfCnpj().equals(cliente.getCpfCnpj())) {
            validarCpfCnpjEdicao(request.getCpfCnpj(), id);
        }

        cliente.setNomeCompleto(request.getNomeCompleto());
        cliente.setCpfCnpj(request.getCpfCnpj());
        cliente.setTelefone(request.getTelefone());
        cliente.setEmail(request.getEmail());
        cliente.setEndereco(request.getEndereco());

        ClienteModel atualizado = clienteRepository.save(cliente);
        return toResponseDTO(atualizado);
    }

    public boolean deletarPorId(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }


    //private

    private ClienteModel buscarOuLancarExcecao(Long id) {
        return clienteRepository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Cliente não encontrado com id: " + id)
            );
    }

    private void validarCpfCnpjNovo(String cpfCnpj) {
        String apenasDigitos = cpfCnpj.replaceAll("[^0-9]", "");
        if (apenasDigitos.length() != 11 && apenasDigitos.length() != 14) {
            throw new RegraDeNegocioException("CPF deve ter 11 dígitos ou CNPJ deve ter 14 dígitos");
        }
        if (clienteRepository.existsByCpfCnpj(cpfCnpj)) {
            throw new RegraDeNegocioException("Já existe um cliente cadastrado com o CPF/CNPJ: " + cpfCnpj);
        }
    }

    private void validarCpfCnpjEdicao(String cpfCnpj, Long idAtual) {
        String apenasDigitos = cpfCnpj.replaceAll("[^0-9]", "");
        if (apenasDigitos.length() != 11 && apenasDigitos.length() != 14) {
            throw new RegraDeNegocioException("CPF deve ter 11 dígitos ou CNPJ deve ter 14 dígitos");
        }
        if (clienteRepository.existsCpfCnpjEmOutroCliente(cpfCnpj, idAtual)) {
            throw new RegraDeNegocioException("Já existe outro cliente cadastrado com o CPF/CNPJ: " + cpfCnpj);
        }
    }

    private ClienteResponseDTO toResponseDTO(ClienteModel model) {
        return new ClienteResponseDTO(
            model.getId(),
            model.getNomeCompleto(),
            model.getCpfCnpj(),
            model.getTelefone(),
            model.getEmail(),
            model.getEndereco(),
            model.getCriadoEm(),
            model.getAtualizadoEm(),
            model.getVeiculos()
        );
    }
}
