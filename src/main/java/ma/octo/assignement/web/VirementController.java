package ma.octo.assignement.web;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.dto.VirementDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.mapper.VirementMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.UtilisateurRepository;
import ma.octo.assignement.repository.VirementRepository;
import ma.octo.assignement.service.AutiService;
import ma.octo.assignement.service.VirementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController(value = "/virements")
@RequestMapping("/virements")
class VirementController {

    public static final int MONTANT_MAXIMAL = 10000;

    Logger LOGGER = LoggerFactory.getLogger(VirementController.class);


    @Autowired
    private AutiService monservice;

    @Autowired
    private VirementService virementService ;



    @GetMapping("lister_virements")
    List<VirementDto> loadAll() {

        List<Virement> virements = virementService.loadAll();
        return virements.stream()
                        .map(VirementMapper::map)
                        .collect(Collectors.toList());
    }




    @PostMapping("/executerVirements")
    @ResponseStatus(HttpStatus.CREATED)
    public Virement createTransaction(@RequestBody VirementDto virementDto)
            throws SoldeDisponibleInsuffisantException, CompteNonExistantException, TransactionException {

        return virementService.createTransaction(virementDto);
    }

    //private void save(Virement Virement) {re2.save(Virement);}
}
