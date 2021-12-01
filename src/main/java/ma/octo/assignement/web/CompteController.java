package ma.octo.assignement.web;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.dto.CompteDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.NotFoundException;
import ma.octo.assignement.mapper.CompteMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comptes")
public class CompteController {

    @Autowired
    private CompteService compteService;

    @GetMapping("lister_comptes")
    List<CompteDto> loadAllCompte() {

        List<Compte> comptes = compteService.loadAllCompte();
        return comptes.stream()
                      .map(CompteMapper :: map)
                      .collect(Collectors.toList());
    }

    @PostMapping("/createCompte")
    public CompteDto createCompte(@RequestBody CompteDto compteDto) throws NotFoundException {
        return compteService.createCompte(compteDto);
    }

    @DeleteMapping("deleteCompte/{nrCompte}")
    public void deleteCompte(@PathVariable String nrCompte) throws CompteNonExistantException {
        compteService.deleteCompte(nrCompte);
    }
}
