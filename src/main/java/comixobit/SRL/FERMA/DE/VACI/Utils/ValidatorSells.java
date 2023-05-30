package comixobit.SRL.FERMA.DE.VACI.Utils;

import comixobit.SRL.FERMA.DE.VACI.Models.ClientiModel;
import comixobit.SRL.FERMA.DE.VACI.Models.ProduseZootehniceModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VanzariModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.ClientsRepository;
import comixobit.SRL.FERMA.DE.VACI.Repository.LivestockProduceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Date;
import java.util.Optional;

@Component
public class ValidatorSells implements Validator {

    @Autowired
    private LivestockProduceRepository livestockProduceRepository;
    @Autowired
    private ClientsRepository clientsRepository;


    @Override
    public boolean supports(Class<?> clazz) {
        return VanzariModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        int cantitatea = 0;
        VanzariModel vanzariModel = (VanzariModel) target;
        ProduseZootehniceModel produseZootehniceModel = vanzariModel.getProduseZootehniceModel();


        if (produseZootehniceModel == null) {
            errors.rejectValue("produseZootehnice", "", "Tipul produsului sau data expirarii nu sunt pentru un anumit produs!");
        }

        if(cantitatea > produseZootehniceModel.getCantitate()){
            errors.rejectValue("cantiatea", "", "Nu este destula cantitate!");
        }
    }
}