package ma.octo.assignement.dto;

import lombok.Getter;
import lombok.Setter;
import ma.octo.assignement.domain.Compte;

import java.math.BigDecimal;
import java.util.Date;

@Getter @Setter
public class VersementDto {

    private BigDecimal montantVirement;
    private Date dateExecution;
    private String nom_prenom_emetteur;
    private String nrCompteBeneficiaire;
    private String motifVersement;
}
