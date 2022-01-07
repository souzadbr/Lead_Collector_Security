package br.com.zup.LeadCollector.lead;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

@RestController
@RequestMapping("/leads")
public class LeadController {
    @Autowired
    private LeadService leadService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Lead cadastrarLead(@RequestBody Lead lead){
        return leadService.salvarLead(lead);
    }

    @GetMapping
    public Iterable<Lead> exibirTodosLeads(){
        return leadService.exibirLeads();
    }

    @PutMapping
    public Lead atualizarLead(@RequestBody Lead lead){
        return leadService.atualizaLead(lead);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT) //204 - resposta sem corpo no método de resposta
    public void deletarLead(@RequestParam String email){
        leadService.deletarLead(email);
    }
}
