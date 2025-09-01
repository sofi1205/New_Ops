package br.com.itb.projeto.newOPS.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.itb.projeto.newOPS.model.entity.Localidade;
import br.com.itb.projeto.newOPS.model.entity.Ocorrencia;
import br.com.itb.projeto.newOPS.model.entity.Usuario;

import br.com.itb.projeto.newOPS.model.repository.OcorrenciaRepository;
import br.com.itb.projeto.newOPS.model.repository.UsuarioRepository;
import br.com.itb.projeto.newOPS.model.repository.LocalidadeRepository;
import jakarta.transaction.Transactional;

@Service
public class OcorrenciaService {

    private OcorrenciaRepository ocorrenciaRepository;
    private UsuarioRepository usuarioRepository;
    private LocalidadeRepository localidadeRepository;
    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository, UsuarioRepository usuarioRepository,
                             LocalidadeRepository localidadeRepository) {
        super();
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.usuarioRepository = usuarioRepository;
        this.localidadeRepository = localidadeRepository;
    }

    public Ocorrencia findById(long id) {
        Optional<Ocorrencia> ocorrencia = ocorrenciaRepository.findById(id);

        if (ocorrencia.isPresent()) {
            return ocorrencia.get();
        }

        return null;
    }

    public List<Ocorrencia> findAll(){
        List<Ocorrencia> ocorrencia = ocorrenciaRepository.findAll();
        return ocorrencia;
    }

    @Transactional
    public Ocorrencia save(Ocorrencia ocorrencia) {

        ocorrencia.setDataOcorrencia(LocalDateTime.now());
        ocorrencia.setStatusOcorrencia("ABERTA");

        Ocorrencia _ocorrencia =  ocorrenciaRepository.save(ocorrencia);
        return _ocorrencia;
    }

    @Transactional
    public Ocorrencia update(long id, Ocorrencia ocorrenciaDetails) {
        Ocorrencia ocorrencia = findById(id);
        if (ocorrencia == null) {
            throw new RuntimeException("Ocorrencia não encontrada com o ID: " + id);
        }

        ocorrencia.setDescricao(ocorrenciaDetails.getDescricao());
        ocorrencia.setStatusOcorrencia(ocorrenciaDetails.getStatusOcorrencia());
        ocorrencia.setDataOcorrencia(ocorrenciaDetails.getDataOcorrencia());
        ocorrencia.setLocalidade(ocorrenciaDetails.getLocalidade());
        ocorrencia.setUsuario(ocorrenciaDetails.getUsuario());

        return ocorrenciaRepository.save(ocorrencia);
    }


    public void deleteById(long id) {
        if (ocorrenciaRepository.existsById(id)) {
            ocorrenciaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Ocorrencia não encontrada com o ID: " + id);
        }
    }

    @Transactional
    public Ocorrencia inativar(long id) {
        Ocorrencia ocorrencia = findById(id);
        if (ocorrencia == null) {
            throw new RuntimeException("Ocorrencia não encontrada com o ID: " + id);
        }


        ocorrencia.setStatusOcorrencia("INATIVA");

        return ocorrenciaRepository.save(ocorrencia);
    }
    @Transactional
    public Ocorrencia reativar(long id) {
        Ocorrencia ocorrencia = findById(id);
        if (ocorrencia == null) {
            throw new RuntimeException("Ocorrencia não encontrada com o ID: " + id);
        }

        // Alterando o status para "Ativa"
        ocorrencia.setStatusOcorrencia("ATIVA");

        return ocorrenciaRepository.save(ocorrencia);  // Salva a ocorrência com o status atualizado
    }
}
