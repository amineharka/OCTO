package ma.octo.assignement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class CompteDto {

    private String nrCompte;
    private String rib;
    private BigDecimal solde;
    private String utilisateur ;
}
