package ma.octo.assignement.service;

import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.exceptions.NotFoundException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository ;


    public Utilisateur addUtilisateur(Utilisateur utilisateur)
    {
        return utilisateurRepository.save(utilisateur);
    }

    public void deleteUtilisateur(String username) throws NotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username);
        if(utilisateurRepository.findByUsername(username) == null)
        {
            throw new NotFoundException("utilisateur n'existe pas");
        }

        utilisateurRepository.delete(utilisateur);
    }


    public List<Utilisateur> loadAllUtilisateur() {
        List<Utilisateur> all = utilisateurRepository.findAll();

        if (CollectionUtils.isEmpty(all)) {
            return null;
        } else {
            return all;
        }
    }
}
