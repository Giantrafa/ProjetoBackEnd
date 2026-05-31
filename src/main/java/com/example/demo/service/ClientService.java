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
    ClientRepository clientRepository;

    @Transactional
    public ClientResponseDTO criar(ClientRequestDTO request) {
        validarCpfCnpjNovo(request.getCpfCnpj());

        ClientModel novoCliente = new ClientModel();
        novoClient.setNomeCompleto(request.getNomeCompleto());
        novoClient.setCpfCnpj(request.getCpfCnpj());
        novoClient.setTelefone(request.getTelefone());
        novoClient.setEmail(request.getEmail());
        novoClient.setEndereco(request.getEndereco());

        ClientModel salvo = clientRepository.save(novoClient);
        return toResponseDTO(salvo);
    }

    @Transactional(readOnly = true)
    public Page<ClientResponseDTO> listar(String busca, Pageable pageable) {
        Page<ClientModel> pagina;

        if (busca == null || busca.isBlank()) {
            pagina = clientRepository.findAll(pageable);
        } else {
            pagina = clientRepository.buscarPorNomeOuCpfCnpj(busca.trim(), pageable);
        }

        return pagina.map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public ClientResponseDTO buscarPorId(Long id) {
        ClientModel cliente = buscarOuLancarExcecao(id);
        return toResponseDTO(cliente);
    }

    @Transactional
    public ClientResponseDTO atualizar(Long id, ClientRequestDTO request) {
        ClientModel cliente = buscarOuLancarExcecao(id);

        if (!request.getCpfCnpj().equals(cliente.getCpfCnpj())) {
            validarCpfCnpjEdicao(request.getCpfCnpj(), id);
        }

        cliente.setNomeCompleto(request.getNomeCompleto());
        cliente.setCpfCnpj(request.getCpfCnpj());
        cliente.setTelefone(request.getTelefone());
        cliente.setEmail(request.getEmail());
        cliente.setEndereco(request.getEndereco());

        ClientModel atualizado = clientRepository.save(cliente);
        return toResponseDTO(atualizado);
    }

    public boolean deletarPorId(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }


    //private

    private ClientModel buscarOuLancarExcecao(Long id) {
        return clientRepository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Cliente não encontrado com id: " + id)
            );
    }

    private void validarCpfCnpjNovo(String cpfCnpj) {
        String apenasDigitos = cpfCnpj.replaceAll("[^0-9]", "");
        if (apenasDigitos.length() != 11 && apenasDigitos.length() != 14) {
            throw new RegraDeNegocioException("CPF deve ter 11 dígitos ou CNPJ deve ter 14 dígitos");
        }
        if (clientRepository.existsByCpfCnpj(cpfCnpj)) {
            throw new RegraDeNegocioException("Já existe um cliente cadastrado com o CPF/CNPJ: " + cpfCnpj);
        }
    }

    private void validarCpfCnpjEdicao(String cpfCnpj, Long idAtual) {
        String apenasDigitos = cpfCnpj.replaceAll("[^0-9]", "");
        if (apenasDigitos.length() != 11 && apenasDigitos.length() != 14) {
            throw new RegraDeNegocioException("CPF deve ter 11 dígitos ou CNPJ deve ter 14 dígitos");
        }
        if (clientRepository.existsCpfCnpjEmOutroCliente(cpfCnpj, idAtual)) {
            throw new RegraDeNegocioException("Já existe outro cliente cadastrado com o CPF/CNPJ: " + cpfCnpj);
        }
    }

    private ClientResponseDTO toResponseDTO(ClientModel model) {
        return new ClientResponseDTO(
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
