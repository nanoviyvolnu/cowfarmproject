package comixobit.SRL.FERMA.DE.VACI.Specification;

import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CowSpecification {

    public static Specification<VacaModel> hasRasa(String rasa){

        return (root, query, cb) -> rasa == null ? null :  cb.equal(root.get("rasa"), rasa);
    }
    public static Specification<VacaModel> hasGenul(String genul){

        return (root, query, cb) -> genul == null ? null :  cb.equal(root.get("genul"), genul);
    }

    public static Specification<VacaModel> hasStatutul(String statutul){

        return (root, query, cb) -> statutul == null ? null :  cb.equal(root.get("statutul"), statutul);
    }
    public static Specification<VacaModel> hasFormaDeAchizitie(String formaAchizitie){

        return (root, query, cb) ->  formaAchizitie == null ? null :  cb.equal(root.get("formaAchizitie"), formaAchizitie);
    }
    public static Specification<VacaModel> hasCategorie(String categorie){
        return (root, query, cb) ->  categorie == null ? null :  cb.equal(root.get("categorie"), categorie);
    }

    public static Specification<VacaModel> hasInitialWeight(String operator, Integer initialWeight) {
        if (initialWeight == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        if (operator.equals("lt")) {
            return (root, query, cb) -> cb.lt(root.get("masaKgInitiala"), initialWeight);
        } else if (operator.equals("lte")) {
            return (root, query, cb) -> cb.le(root.get("masaKgInitiala"), initialWeight);
        } else if (operator.equals("eq")) {
            return (root, query, cb) -> cb.equal(root.get("masaKgInitiala"), initialWeight);
        } else if (operator.equals("gte")) {
            return (root, query, cb) -> cb.ge(root.get("masaKgInitiala"), initialWeight);
        } else if (operator.equals("gt")) {
            return (root, query, cb) -> cb.gt(root.get("masaKgInitiala"), initialWeight);
        } else {
            return null;
        }
    }

    public static Specification<VacaModel> hasMomentWeight(String operator1, Integer momentWeight) {
        if (momentWeight == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        if (operator1.equals("lt")) {
            return (root, query, cb) -> cb.lt(root.get("masaKgMoment"), momentWeight);
        } else if (operator1.equals("lte")) {
            return (root, query, cb) -> cb.le(root.get("masaKgMoment"), momentWeight);
        } else if (operator1.equals("eq")) {
            return (root, query, cb) -> cb.equal(root.get("masaKgMoment"), momentWeight);
        } else if (operator1.equals("gte")) {
            return (root, query, cb) -> cb.ge(root.get("masaKgMoment"), momentWeight);
        } else if (operator1.equals("gt")) {
            return (root, query, cb) -> cb.gt(root.get("masaKgMoment"), momentWeight);
        } else {
            return null;
        }
    }
}
