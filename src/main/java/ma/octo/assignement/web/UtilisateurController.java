package ma.octo.assignement.web;

import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.exceptions.NotFoundException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.UtilisateurRepository;
import ma.octo.assignement.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/addUtilisateur")
    public Utilisateur addUtilisateur(@RequestBody Utilisateur utilisateur)
    {
        return utilisateurService.addUtilisateur(utilisateur);
    }

    @GetMapping("lister_utilisateurs")
    List<Utilisateur> loadAllUtilisateur() {

        return utilisateurService.loadAllUtilisateur();
    }

    @DeleteMapping("deleteUtilisateur/{username}")
    public void deleteUtilisateur(@PathVariable String username) throws NotFoundException {
        utilisateurService.deleteUtilisateur(username);
    }
}
