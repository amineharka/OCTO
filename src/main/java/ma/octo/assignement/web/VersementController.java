package ma.octo.assignement.web;

import ma.octo.assignement.dto.VersementDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.VersementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/versement")
public class VersementController {

    @Autowired
    private VersementService versementService ;

    @PostMapping("/createVersement")
    public VersementDto createVersement(@RequestBody VersementDto versementDto) throws TransactionException, CompteNonExistantException {
        return versementService.createVersement(versementDto);
    }
}
