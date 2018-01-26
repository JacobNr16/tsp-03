package bruteforce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import base.Population;
import base.Tour;
import base.City;
import main.Configuration;
import random.MersenneTwisterFast;

public class BruteForce {

    private MersenneTwisterFast mtwister = (MersenneTwisterFast) Configuration.instance.random;
    private Population population=new Population();

    /*
    public BruteForce(TSPLIBReader x, InstanceReader y) {
        this.tspReader = x;
        this.instReader = y;
    }
    */

    public Population createPermutations(ArrayList<City> cityList, long permutationsNumber){
        HashSet<Tour> tours=new HashSet<Tour>();
        ArrayList<Tour> PopulationTours=new ArrayList<Tour>();

        for(int i=0;i<permutationsNumber;i++){
            int counter=0;
            Tour newTour = generateTour(cityList);
            if (tours.add(newTour)) {
                counter++;
            }
        }
        for(Tour tour:tours){
            PopulationTours.add(tour);
        }
        this.population.setTours(PopulationTours);

        return this.population;
        }


    public Tour generateTour(ArrayList<City> cityArrayList){

        Tour newTour=new Tour();
        while(!cityArrayList.isEmpty()) {

            int random1 = mtwister.nextInt(0, cityArrayList.size()-2);
            int random2 = mtwister.nextInt(0, cityArrayList.size()-2);

            Collections.swap(cityArrayList,random1,random2);

            newTour.addCity(cityArrayList.get(random1));
            newTour.addCity(cityArrayList.get(random2));

            cityArrayList.remove(cityArrayList.get(random1));
            cityArrayList.remove(cityArrayList.get(random2));
        }
        return newTour;
    }

    public int getPopulationSizeQuarter(){
        ArrayList<Tour> populationTours=population.getTours();

        int index=populationTours.size()/4;

        return index;
    }

    public double getFitnessTop25(){
        double populationFitness=0;


        ArrayList<Tour> populationTours=population.getTours();
        int quarter=getPopulationSizeQuarter();

        for(Tour tour:populationTours){
            int counter=0;
            while(counter<quarter){
                populationFitness=populationFitness+tour.getFitness();
                counter++;
            }
        }

        return populationFitness;
    }

    public double getFitnessLast25(){
        double populationFitness=0;

        ArrayList<Tour> populationTours=population.getTours();
        int quarter=getPopulationSizeQuarter();

        for(Tour tour:populationTours){
            int counter=quarter*3;
            while(counter<populationTours.size()){
                populationFitness=populationFitness+tour.getFitness();
                counter++;
            }
        }

        return populationFitness;
    }

    public double getFitnessMid50(){
        double populationFitness=0;

        ArrayList<Tour> populationTours=population.getTours();
        int quarter=getPopulationSizeQuarter();

        for(Tour tour:populationTours){
            int counter=quarter;
            while(counter<populationTours.size()-quarter){
                populationFitness=populationFitness+tour.getFitness();
                counter++;
            }
        }

        return populationFitness;
    }

}