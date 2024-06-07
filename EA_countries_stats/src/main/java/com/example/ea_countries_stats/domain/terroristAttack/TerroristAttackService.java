package com.example.ea_countries_stats.domain.terroristAttack;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TerroristAttackService {
    private TerroristAttackRepository attackRepository;


    @Autowired
    public TerroristAttackService(TerroristAttackRepository attackRepository){
        this.attackRepository = attackRepository;
    }
    public void deleteTerroristAttack(long id) {
        attackRepository.deleteById(id);
    }

    public List<TerroristAttack> getAllTeroristAttacks() {
        List<TerroristAttack> terroristAttacks = new ArrayList<>();
        attackRepository.findAll().forEach(terroristAttacks::add);
        return terroristAttacks;
    }
    public TerroristAttack createTerroristAttack(TerroristAttack terroristAttack){
        return attackRepository.save(terroristAttack);
    }
    public Optional<TerroristAttack> getTerroristAttack(Long id) {
        return attackRepository.findById(id);
    }
    public TerroristAttack updateTerroristAttack(Long id, TerroristAttack terroristAttack) {
        terroristAttack.setAttackId(id);
        return attackRepository.save(terroristAttack);
    }

    public void saveTerroristAttacks(List<TerroristAttack> terroristAttacks) {
        attackRepository.saveAll(terroristAttacks);
    }

    public double calculateAverageCasualitiesInRange(int min, int max) {
        List<TerroristAttack> attacks = getAllTeroristAttacks();
        int sumCasualties = 0;
        int count = 0;

        for (TerroristAttack attack : attacks) {
            int casualties = attack.getCasualties();
            if (casualties >= min && casualties <= max) {
                sumCasualties += casualties;
                count++;
            }
        }

        if (count == 0) {
            return 0.0;
        } else {
            return (double) sumCasualties / count;
        }

    }


    public List<TerroristAttackResponse> getTerroristAttacksByCasualities(String direction, int higherOrLowerNumber, String sortOrder) {
        List<TerroristAttack> attacks = getAllTeroristAttacks();
        List<TerroristAttack> filteredAttacks = new ArrayList<>();

        // Filtering

        for (TerroristAttack attack : attacks) {
            if (direction.equals("lower") && attack.getCasualties() < higherOrLowerNumber) {
                filteredAttacks.add(attack);
            } else if (direction.equals("higher") && attack.getCasualties() > higherOrLowerNumber) {
                filteredAttacks.add(attack);
            }
        }

        return fromArrayToResponse(sortingByAscOrDesc(filteredAttacks,sortOrder));


    }

    private TerroristAttack[] sortingByAscOrDesc(List<TerroristAttack> filteredAttacks, String sortOrder){
        TerroristAttack[] attackArray = filteredAttacks.toArray(new TerroristAttack[0]);


        for (int i = 0; i < attackArray.length - 1; i++) {
            int minOrMaxIndex = i;
            for (int j = i + 1; j < attackArray.length; j++) {
                if (sortOrder.equals("ASC")) {
                    if (attackArray[j].getCasualties() < attackArray[minOrMaxIndex].getCasualties()) {
                        minOrMaxIndex = j;
                    }
                } else {
                    if (attackArray[j].getCasualties() > attackArray[minOrMaxIndex].getCasualties()) {
                        minOrMaxIndex = j;
                    }
                }
            }

            TerroristAttack temp = attackArray[minOrMaxIndex];
            attackArray[minOrMaxIndex] = attackArray[i];
            attackArray[i] = temp;
        }
        return attackArray;
    }
    private List<TerroristAttackResponse>  fromArrayToResponse(TerroristAttack[] attackArray){
        List<TerroristAttackResponse> responseList = new ArrayList<>();
        for (TerroristAttack attack : attackArray) {
            responseList.add(new TerroristAttackResponse(attack));
        }

        return responseList;
    }


    public double calculateWeightedAverageCasualities(List<Double> weights) {
        List<TerroristAttack> attacks = getAllTeroristAttacks();
        int sizeOfListOfAttacks= attacks.size();

        if (sizeOfListOfAttacks == 0) {return 0.0;}
        if(attacks.size()>weights.size()){//extension of weights
            double sumOfWeights = weights.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            while (weights.size() < sizeOfListOfAttacks) {
                weights.add(sumOfWeights);
            }
            return calculateWeightedAverage(attacks,weights);

        } else if (attacks.size()==weights.size()) {//normal weighted average
            return calculateWeightedAverage(attacks,weights);
        }
        else {//cut of weights
            weights = weights.subList(0,sizeOfListOfAttacks);
            return calculateWeightedAverage(attacks,weights);
        }
    }

    private double calculateWeightedAverage(List<TerroristAttack> attacks, List<Double> weights){
        //3 cycles
        // 1st one- mutiplying weight with number
        // 2nd one - add weighted numbers
        // 3rd one - add weights
        // divide
        double sumOfWeightedNumbers=0.0;
        double sumofWeights=0.0;
        List<Double> multiplyedWeightsWithNumber = new ArrayList<>();

        for (int i = 0; i < attacks.size(); i++) {
            multiplyedWeightsWithNumber.add(attacks.get(i).getCasualties()*weights.get(i));
        }
        for (int i = 0; i < multiplyedWeightsWithNumber.size() ; i++) {
            sumOfWeightedNumbers+=multiplyedWeightsWithNumber.get(i);
        }
        for (int i = 0; i < weights.size(); i++) {
            sumofWeights+=weights.get(i);
        }
        return sumOfWeightedNumbers/sumofWeights;
    }

    public double calculateStandardDeviationFromValue(double value) {
        List<TerroristAttack> attacks = getAllTeroristAttacks();
        List<Double> deviationFromNumberListSquared  = new ArrayList<>();
        double sumOfNumbersFromDeviation=0.0;
        double numberOfNumbersFromDeviation=0.0;

        for(TerroristAttack attack : attacks){
            deviationFromNumberListSquared.add(Math.pow(attack.getCasualties()-value,2));
        }
        for(Double numbersFromDeviation : deviationFromNumberListSquared){
            sumOfNumbersFromDeviation+=numbersFromDeviation;
            numberOfNumbersFromDeviation+=1;
        }
        return Math.sqrt(sumOfNumbersFromDeviation/numberOfNumbersFromDeviation);

    }

}
