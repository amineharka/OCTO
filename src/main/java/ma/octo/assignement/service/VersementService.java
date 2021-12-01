package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Versement;
import ma.octo.assignement.dto.VersementDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.VersementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class VersementService {

    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private VersementRepository versementRepository;

    public VersementDto createVersement(VersementDto versementDto) throws CompteNonExistantException, TransactionException {
        Compte compteBeneficiaire = compteRepository
                .findByNrCompte(versementDto.getNrCompteBeneficiaire());

        if(compteBeneficiaire == null)
        {
            throw new CompteNonExistantException("ce compte n'existe pas");
        }

        if (versementDto.getMontantVirement()==null) {
            System.out.println("Montant vide");
            throw new TransactionException("Montant vide");
        } else if (versementDto.getMontantVirement().intValue() == 0) {
            System.out.println("Montant vide");
            throw new TransactionException("Montant vide");
        }

        if (versementDto.getMotifVersement()==null ||versementDto.getMotifVersement().trim().length() == 0) {
            System.out.println("Motif vide");
            throw new TransactionException("Motif vide");
        }

        compteBeneficiaire
                .setSolde(new BigDecimal(compteBeneficiaire.getSolde().intValue() + versementDto.getMontantVirement().intValue()));
        compteRepository.save(compteBeneficiaire);

        Versement versement = new Versement();
        versement.setMotifVersement(versementDto.getMotifVersement());
        versement.setCompteBeneficiaire(compteBeneficiaire);
        versement.setDateExecution(new Date());
        versement.setNom_prenom_emetteur(versementDto.getNom_prenom_emetteur());
        versement.setMontantVirement(versementDto.getMontantVirement());

        versementRepository.save(versement);
        return versementDto;
    }
}
