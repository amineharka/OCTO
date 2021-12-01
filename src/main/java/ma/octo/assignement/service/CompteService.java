package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.CompteDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.NotFoundException;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.naming.CommunicationException;
import java.util.List;

@Service
public class CompteService {

    @Autowired
    private CompteRepository compteRepository ;
    @Autowired
    private UtilisateurRepository utilisateurRepository ;


    public List<Compte> loadAllCompte()
    {
        List<Compte> all = compteRepository.findAll();

        if (CollectionUtils.isEmpty(all)) {
            return null;
        } else {
            return all;
        }
    }

    public CompteDto createCompte(CompteDto compteDto) throws NotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(compteDto.getUtilisateur());
        if(utilisateur == null)
        {
            throw new NotFoundException("utilisateur n'existe pas");
        }

        Compte compte = new Compte();
        compte.setNrCompte(compteDto.getNrCompte());
        compte.setRib(compteDto.getRib());
        compte.setSolde(compteDto.getSolde());
        compte.setUtilisateur(utilisateur);

        compteRepository.save(compte);

        return compteDto ;

    }

    public void deleteCompte(String nrCompte) throws CompteNonExistantException {
        Compte compte = compteRepository.findByNrCompte(nrCompte);
        if(compte == null)
        {
            throw new CompteNonExistantException("ce compte n'existe pas");
        }

        compteRepository.delete(compte);
    }
}
