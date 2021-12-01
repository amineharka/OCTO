package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.dto.VirementDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.VirementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class VirementService {

    public static final int MONTANT_MAXIMAL = 10000;

    Logger LOGGER = LoggerFactory.getLogger(VirementService.class);

    @Autowired
    private VirementRepository virementRepository;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private AutiService monservice;


    public List<Virement> loadAll() {
        List<Virement> all = virementRepository.findAll();

        if (CollectionUtils.isEmpty(all)) {
            return null;
        } else {
            return all;
        }
    }

    public Virement createTransaction(VirementDto virementDto)
            throws SoldeDisponibleInsuffisantException, CompteNonExistantException, TransactionException {
        Compte compteEmetteur = compteRepository.findByNrCompte(virementDto.getNrCompteEmetteur());
        Compte compteBeneficiaire = compteRepository
                .findByNrCompte(virementDto.getNrCompteBeneficiaire());

        if (compteEmetteur == null) {
            System.out.println("Compte Non existant");
            throw new CompteNonExistantException("Compte Emetteur Non existant");
        }

        if (compteBeneficiaire == null) {
            System.out.println("Compte Non existant");
            throw new CompteNonExistantException("Compte Beneficiare Non existant");
        }

        if (virementDto.getMontantVirement().equals(null)) {
            System.out.println("Montant vide");
            throw new TransactionException("Montant vide");
        } else if (virementDto.getMontantVirement().intValue() == 0) {
            System.out.println("Montant vide");
            throw new TransactionException("Montant vide");
        } else if (virementDto.getMontantVirement().intValue() < 10) {
            System.out.println("Montant minimal de virement non atteint");
            throw new TransactionException("Montant minimal de virement non atteint");
        } else if (virementDto.getMontantVirement().intValue() > MONTANT_MAXIMAL) {
            System.out.println("Montant maximal de virement dépassé");
            throw new TransactionException("Montant maximal de virement dépassé");
        }

        if (virementDto.getMotif() == null ||virementDto.getMotif().trim().length() == 0) {
            System.out.println("Motif vide");
            throw new TransactionException("Motif vide");
        }

        if (compteEmetteur.getSolde().intValue() - virementDto.getMontantVirement().intValue() < 0) {
            LOGGER.error("Solde insuffisant pour l'utilisateur");
            throw new SoldeDisponibleInsuffisantException("Solde insuffisant pour l'utilisateur");
        }


        compteEmetteur.setSolde(compteEmetteur.getSolde().subtract(virementDto.getMontantVirement()));
        compteRepository.save(compteEmetteur);

        compteBeneficiaire
                .setSolde(new BigDecimal(compteBeneficiaire.getSolde().intValue() + virementDto.getMontantVirement().intValue()));
        compteRepository.save(compteBeneficiaire);

        Virement virement = new Virement();
        virement.setDateExecution(new Date());
        virement.setCompteBeneficiaire(compteBeneficiaire);
        virement.setCompteEmetteur(compteEmetteur);
        virement.setMontantVirement(virementDto.getMontantVirement());
        virement.setMotifVirement(virementDto.getMotif());



        monservice.auditVirement("Virement depuis " + virementDto.getNrCompteEmetteur() + " vers " + virementDto
                .getNrCompteBeneficiaire() + " d'un montant de " + virementDto.getMontantVirement()
                .toString());

        return virementRepository.save(virement);
    }
}
