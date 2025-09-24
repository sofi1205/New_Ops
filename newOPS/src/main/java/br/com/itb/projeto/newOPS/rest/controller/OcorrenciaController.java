package br.com.itb.projeto.newOPS.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itb.projeto.newOPS.model.entity.Localidade;
import br.com.itb.projeto.newOPS.model.entity.Ocorrencia;
import br.com.itb.projeto.newOPS.rest.exception.ResourceNotFoundException;
import br.com.itb.projeto.newOPS.service.OcorrenciaService;

@RestController
@RequestMapping("/ocorrencia")
public class OcorrenciaController {

    private OcorrenciaService ocorrenciaService;

    public OcorrenciaController(OcorrenciaService ocorrenciaService) {
        super();
        this.ocorrenciaService = ocorrenciaService;
    }

    @GetMapping("/test")
    public String getTest() {
        return "Olá, Ocorrencia!";
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Ocorrencia> findById(@PathVariable long id){

        Ocorrencia ocorrencia = ocorrenciaService.findById(id);

        if (ocorrencia != null) {
            return new ResponseEntity<Ocorrencia>(ocorrencia, HttpStatus.OK);
        }

        throw new ResourceNotFoundException("Ocorrencia não encontrada!");
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Ocorrencia>> findAll(){

        List<Ocorrencia> ocorrencias = ocorrenciaService.findAll();

        return new ResponseEntity<List<Ocorrencia>>(ocorrencias, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Ocorrencia ocorrencia) {

        Ocorrencia _ocorrencia = ocorrenciaService.save(ocorrencia);

        return ResponseEntity.ok()
                .body("Ocorrencia cadastrada com sucesso!");


    }



    @PutMapping("/update/{id}")
    public ResponseEntity<Ocorrencia> update(@PathVariable long id, @RequestBody Ocorrencia ocorrenciaDetails) {
        Ocorrencia updatedOcorrencia = ocorrenciaService.update(id, ocorrenciaDetails);
        return ResponseEntity.ok(updatedOcorrencia);
    }

    @PutMapping("/inativar/{id}")
    public ResponseEntity<String> inativar(@PathVariable long id) {
        try {
            Ocorrencia ocorrencia = ocorrenciaService.inativar(id);
            return ResponseEntity.ok("Ocorrência inativada com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Erro: " + e.getMessage());
        }
    }

    @PutMapping("/reativar/{id}")
    public ResponseEntity<String> reativar(@PathVariable long id) {
        try {
            Ocorrencia ocorrencia = ocorrenciaService.reativar(id);
            return ResponseEntity.ok("Ocorrência reativada com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Erro: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOcorrencia(@PathVariable long id) {
        try {
            ocorrenciaService.deleteById(id);
            return ResponseEntity.ok("Ocorrência deletada com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Erro: " + e.getMessage());
        }
    }

}
