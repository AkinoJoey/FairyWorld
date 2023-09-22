import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

class RandomWrapper{
    public static double getRanDouble(double min, double max){
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }

    public static boolean ranBoolean(){
        return new Random().nextBoolean();
    }
}

class Name{
    private String firstName;
    private String lastName;

    public Name(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String toString(){
        return this.firstName + " " + this.lastName;
    }
}

class BMI{
    private double heightM;
    private double weightKg;

    public BMI(double heightM, double weightKg){
        this.heightM = heightM;
        this.weightKg = weightKg;
    }

    public double getWeightKg(){
        return this.weightKg;
    }

    public double getValue(){
        return this.weightKg/(this.heightM*this.heightM);
    }

    public String toString(){
        return this.heightM + " meters, " + this.weightKg + "kg, BMI:" + this.getValue();
    }
}

class Animal{
    protected String species;
    protected BMI bmi;
    protected double lifeSpanDays;
    protected String biologicalSex;
    protected Date spawnTime;
    protected Date deathTime;
    protected int hungerPercent = 100;
    protected int sleepPercent = 100;

    public Animal(String species, double heightM, double weightKg, double lifeSpanDays, String biologicalSex){
        this.species = species;
        this.bmi = new BMI(heightM, weightKg);
        this.lifeSpanDays = lifeSpanDays;
        this.biologicalSex = biologicalSex;
        this.spawnTime = new java.util.Date();
    }

    public void eat(){
        if(!this.isAlive()) return;
        this.hungerPercent = 0;
    }

    public void setAsHungry(){
        if(!this.isAlive()) return;
        this.hungerPercent = 100;
    }

    public boolean isHungry(){
        return this.hungerPercent >= 70;
    }

    public void sleep(){
        if(!this.isAlive()) return;
        this.sleepPercent = 0;
    }

    public void setAsSleepy(){
        if(!this.isAlive()) return;
        this.sleepPercent = 100;
    }

    public boolean isSleepy(){
        return this.sleepPercent >= 70;
    }

    public void die(){
        this.sleepPercent = 0;
        this.hungerPercent = 0;
        this.deathTime = new java.util.Date();
    }

    public boolean isAlive(){
        return this.deathTime == null;
    }

    public String toString(){
        return this.species + " " + this.bmi + " lives " + this.lifeSpanDays + " days/" + "gender:" + this.biologicalSex + "." + this.status();
    }

    public String status(){
        return this.species + " status:" + " Hunger - " + this.hungerPercent + "%, " + "sleepiness:"+this.sleepPercent + "%" + ", Alive - " + this.isAlive() + ". First created at " + this.dateCreated();
    }

    public String dateCreated(){
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(this.spawnTime);
    }
}

class Mammal extends Animal{
    private double bodyTemperatureC;
    private double avgBodyTemperatureC;

    public Mammal(String species, double heightM, double weightKg, double lifeSpanDays, String biologicalSex, double avgBodyTemperatureC){
        super(species, heightM, weightKg, lifeSpanDays, biologicalSex);

        this.avgBodyTemperatureC = avgBodyTemperatureC;
        this.bodyTemperatureC = this.avgBodyTemperatureC;
    }

    public void eat(){
        super.eat();
        System.out.println("this " + this.species + " is eating with its single lower jaw");
    }

    public String toString(){
        return super.toString() + " " + this.mammalInformation();
    }

    public void increaseBodyHeat(double celsius){
        this.bodyTemperatureC+=celsius;
    }

    public void decreaseBodyHeat(double celsius){
        this.bodyTemperatureC-=celsius;
    }

    public void adjustBodyHeat(){
        this.bodyTemperatureC = this.avgBodyTemperatureC;
    }

    public String mammalInformation(){
        return "This is a mammal with a temperature of: "+this.bodyTemperatureC;
    }
}

class Person extends Mammal{
    public static final String SPECIES = "Human";
    public static final double LIFE_EXPECTANCY = 30000;
    public static final double BODY_TEMPERATURE = 36;

    private Name name;
    private int age;

    public Person(String firstName, String lastName, int age, double heightM, double weightKg, String biologicalSex){
        super(Person.SPECIES, heightM, weightKg, Person.LIFE_EXPECTANCY, biologicalSex, Person.BODY_TEMPERATURE);
        this.name = new Name(firstName, lastName);
        this.age = age;
    }

    public String getName(){
        return this.name.toString();
    }

    public int getAge(){
        return this.age;
    }

    public String toString(){
        return super.toString() + ". The name of this Person is " + this.getName();
    }
}

interface PlayfulPet{
    abstract public String play();
    abstract public String playWithPerson(Person person);
    abstract public String playNoise();
    abstract public String getPetName();
    abstract public double getRentalCosts();
    abstract public boolean likesActivity(String activity);
    abstract public boolean dislikesActivity(String activity);
    abstract public String doActivity(String activity);
}

class Cat extends Mammal implements PlayfulPet{
    public static final String SPECIES = "Cat";
    public static final double LIFE_EXPECTANCY = 5500;
    public static final double BODY_TEMPERATURE = 37;

    private static final double PLAYFUL_HOURLY_COSTS = 50;
    private static final String[] LIKED_ACTIVITIES = {"eat","nap","groom","drink","crawl","explore","pet"};
    private static final String[] DISLIKED_ACTIVITIES = {"bath"};

    public Cat(double heightM, double weightKg, String biologicalSex){
        super(Cat.SPECIES, heightM, weightKg, Cat.LIFE_EXPECTANCY, biologicalSex, Cat.BODY_TEMPERATURE);
    }

    public String toString(){
        return super.toString() + " this is a cat";
    }

    public void meow(){
        System.out.println("Meooow");
    }

    public String getPetName(){
        return this.species;
    }

    public String play(){
        return "This cat starts rolling on the floor, and pretends to play predator";
    }

    public String playWithPerson(Person person){
        String s = "The cat stares at " + person.getName();
        s+= ". After taking kin to " + person.getName() + ", " + person.getName() + " throws a mouse toy at this cat and the cat starts chasing the mouse toy";
        return s;
    }

    public String playNoise(){
        return "Meow";
    }

    public double getRentalCosts(){
        return Cat.PLAYFUL_HOURLY_COSTS;
    }

    public boolean likesActivity(String activity){
        return Arrays.asList(Cat.LIKED_ACTIVITIES).contains(activity);
    };

    public boolean dislikesActivity(String activity){
        return Arrays.asList(Cat.DISLIKED_ACTIVITIES).contains(activity);
    };

    public String doActivity(String activity){
        if(activity == "eat"){
            this.eat();
            return "The cat enjoyed eating food.";
        }
        else if(activity == "nap"){
            this.sleep();
            return "The cat took a good nap.";
        }
        else if(this.likesActivity(activity)) return "Meow. The cat really enjoyed the " + activity + " activity.";
        else if(this.dislikesActivity(activity)) return "The cat really hated the " + activity + " activity.";
        return "The cat felt indifferent about the " + activity + " activity.";
    };
}

abstract class PlayfulPetAssistant{
    protected static final double DEFAULT_RENT_TIME = 1.0;
    protected static final String DEFAULT_TOUR = "all-rounder pack";

    protected Person currentPerson;
    protected double currentRentTime = PlayfulPetAssistant.DEFAULT_RENT_TIME;
    protected static String[] availableActivities = {"eat","walk","drink","nap","pet","run","explore"};
    protected static String[] availableTours = {"all-rounder pack", "deluxe rounder pack"};

    public String[] getActivities(){
        return PlayfulPetAssistant.availableActivities;
    }

    public String[] getAvailableTours(){
        return PlayfulPetAssistant.availableTours;
    }

    public boolean isValidTour(String tour){
        return Arrays.asList(this.getAvailableTours()).contains(tour);
    }

    protected String getRandomActivity(){
        List<String> activities = Arrays.asList(this.getActivities());
        int ran = new Random().nextInt(activities.size());
        return activities.get(ran);
    }

    public void setPerson(Person person){
        this.currentPerson = person;
    }

    public void setHours(double hours){
        this.currentRentTime = hours;
    }

    public double getCurrentRentTime(){
        return this.currentRentTime;
    }

    public void reset(){
        this.currentPerson = null;
        this.currentRentTime = PlayfulPetAssistant.DEFAULT_RENT_TIME;
    }

    public double runAssistanceTour(Person person){
        return this.runAssistanceTour(person, PlayfulPetAssistant.DEFAULT_TOUR);
    }

    public double runAssistanceTour(Person person, String tour){
        if(!this.isValidTour(tour)) System.out.println("The tour guide does not accept the " + tour + " tour.");
        
        PlayfulPet playfulPet = this.createPlayfulPet();

        System.out.println("");
        System.out.println("Booting up... Playful Pet Assistance robot at your service.");
        System.out.println("Printing information about the Person to service..." + person);
        System.out.println("");
        System.out.println("Printing information about the Playful Pet - " + playfulPet.getPetName() + " to service..." + playfulPet);

        if(tour == "all-rounder pack" || tour == "deluxe rounder pack"){
            int count = tour == "all-rounder pack" ? 1 : 3;
            this.genericRounderTour(count, person, playfulPet);
        }
        else{
            System.out.println("The tour assistant robot for " + playfulPet.getPetName() + " and " + person.getName() + " did nothing.");
        }

        double rentalCosts = playfulPet.getRentalCosts() * this.getCurrentRentTime();

        this.reset();

        return rentalCosts;
    }

    public double runAssistanceTour(Person person, String tour, int amount){
        if(!this.isValidTour(tour)) System.out.println("The tour guide does not accept the " + tour + " tour.");
        
        ArrayList<PlayfulPet> playfulPets = this.createPlayfulPets(amount);

        System.out.println("");
        System.out.println("Booting up... Playful Pet Assistance robot at your service.");
        System.out.println("Printing information about the Person to service..." + person);
        System.out.println("");

        double rentalCosts = 0;

        for(int i = 0; i < playfulPets.size(); i++){
            PlayfulPet playfulPet = playfulPets.get(i);

            System.out.println("Printing information about the Playful Pet - " + playfulPet.getPetName() + " to service..." + playfulPet);

            if(tour == "all-rounder pack" || tour == "deluxe rounder pack"){
                int count = tour == "all-rounder pack" ? 1 : 3;
                this.genericRounderTour(count, person, playfulPet);
            }
            else{
                System.out.println("The tour assistant robot for " + playfulPet.getPetName() + " and " + person.getName() + " did nothing.");
            }

            rentalCosts += playfulPet.getRentalCosts() * this.getCurrentRentTime();
        }

        this.reset();

        return rentalCosts;
    }

    private void genericRounderTour(int activityCount, Person person, PlayfulPet pet){
        String newLine = System.lineSeparator();
        System.out.println(newLine + "Now starting the generic rounder tour with " + activityCount + " activity(s)");
        System.out.println(person.getName() + " greets " + pet.getPetName() + newLine);
        System.out.println(pet.play() + newLine);
        System.out.println(pet.playNoise() + newLine);
        System.out.println(pet.playWithPerson(person) + newLine);
        for(int i = 0; i < activityCount; i++){
            String activity = this.getRandomActivity();
            System.out.println(pet.getPetName() + " will now " + activity);
            System.out.println("--------");
            System.out.println(pet.doActivity(activity));
            System.out.println("--------" + newLine);
        }
    }

    public abstract PlayfulPet createPlayfulPet();

    public abstract ArrayList<PlayfulPet> createPlayfulPets(int amount);
}

class PlayfulCatAssistant extends PlayfulPetAssistant{
    public PlayfulPet createPlayfulPet(){
        return new Cat(RandomWrapper.getRanDouble(0.15,0.3), RandomWrapper.getRanDouble(2.0,4.9), RandomWrapper.ranBoolean() ? "male" : "female");
    }

    public ArrayList<PlayfulPet> createPlayfulPets(int amount){
        ArrayList<PlayfulPet> petList = new ArrayList<>();
        
        for(int i = 0; i < amount; i++){
            petList.add(new Cat(RandomWrapper.getRanDouble(0.15,0.3), RandomWrapper.getRanDouble(2.0,4.9), RandomWrapper.ranBoolean() ? "male" : "female"));
        }

        return petList;
    }
}

class Invoice{
    private String title;
    private String description;
    private double total;

    public Invoice(String title, String description, double total){
        this.title = title;
        this.description = description;
        this.total = total;
    }

    public void printInvoice(){
        System.out.println("---------------");
        System.out.println("Title: " + this.getTitle() + "\n");
        System.out.println("Description: " + this.getDescription() + "\n");
        System.out.println("Total Cost: " + String.valueOf(this.getTotal()) + "\n");
        System.out.println("---------------");
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public double getTotal(){
        return this.total;
    }
}

class FairyWorld{
    private static final int MAX_PET = 5;
    private HashMap<String, PlayfulPetAssistant> assistantMap = new HashMap<>();
    private ArrayList<Invoice> invoiceList = new ArrayList<>();

    public FairyWorld(){
        this.addPlayfulPetAssistant("cat", new PlayfulCatAssistant());
        this.addPlayfulPetAssistant("rabbit", new PlayfulRabbitAssistant());
        this.addPlayfulPetAssistant("dog", new PlayfulDogAssistant());
        this.addPlayfulPetAssistant("pony", new PlayfulPonyAssistant());
        this.addPlayfulPetAssistant("hamster", new PlayfulHamsterAssistant());
        this.addPlayfulPetAssistant("chicken", new PlayfulChickenAssistant());
        this.addPlayfulPetAssistant("goat", new PlayfulGoatAssistant());
    }

    public void rentPet(String petKey, Person person, int amount, String tour){
        if(amount > 0 &&  amount < FairyWorld.MAX_PET){
            System.out.println("ペットをレンタルしてくれてありがとう！");
            PlayfulPetAssistant assistant = this.assistantMap.get(petKey);

            double costs = assistant.runAssistanceTour(person,tour, amount);
            Invoice invoice = this.generateInvoice(petKey, person, amount, tour, costs);
            invoiceList.add(invoice);

            System.out.println(costs + " dollars were charged to " + person.getName() + "'s credit card.");
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxx" + System.lineSeparator());

        }if (amount <= 0) {
            System.out.println("レンタルしたいペット数は1以上を入力してください。");
        }if( amount > FairyWorld.MAX_PET){
            System.out.println("借りられるペット数の上限を超えています。");
        }
        
    }

    public void addPlayfulPetAssistant(String petKey, PlayfulPetAssistant playfulPetAssistant){
            assistantMap.put(petKey, playfulPetAssistant);
    }

    public Invoice generateInvoice(String petKey, Person person, int amount, String tour, double costs){
        String date = LocalDateTime.now().toString();
        String title = date + " " + person.getName();
        String description = person.getName() + " が" + petKey  + "を" + amount + "匹レンタル";
        
        Invoice invoice = new Invoice(title, description, costs);

        return invoice;
    }

    public ArrayList<Invoice> getRentedPetsInvoice(){
        return this.invoiceList;
    }

}

class Dog extends Mammal implements PlayfulPet{
    public static final String SPECIES = "Dog";
    public static final double LIFE_EXPECTANCY = 4800;
    public static final double Body_TEMPERATURE = 39;
    
    private static final double PLAYFUL_HOURLY_COSTS = 35;
    private static final String[] LIKED_ACTIVITIES = {"eat","nap","chase","swim","drink","run","explore","pet"};
    private static final String[] DISLIKED_ACTIVITIES = {"hug","dressup"};

    public Dog(double heightM, double weightKg, String biologicalSex){
        super(Dog.SPECIES, heightM, weightKg, Dog.LIFE_EXPECTANCY, biologicalSex, Dog.LIFE_EXPECTANCY);
    }

    public String toString(){
        return super.toString() + " this is a dog";
    }

    public void woof(){
        System.out.println("Woof Woof");
    }

    public String getPetName(){
        return this.species;
    }

    public String play(){
        return "This dog starts running on the park and chases a ball.";
    }

    public String playWithPerson(Person person){
        String s = "The dog runs towards " + person.getName();
        s+= ". After the dog taking kin to " + person.getName() + ", " + person.getName() + " throws a frisbee disk and the dog chases it.";
        return s;
    }

    public String playNoise(){
        return "Wooooof Woooof";
    }

    public double getRentalCosts(){
        return Dog.PLAYFUL_HOURLY_COSTS;
    }

    public boolean likesActivity(String activity){
        return Arrays.asList(Dog.LIKED_ACTIVITIES).contains(activity);
    };

    public boolean dislikesActivity(String activity){
        return Arrays.asList(Dog.DISLIKED_ACTIVITIES).contains(activity);
    };

    public String doActivity(String activity){
        if(activity == "eat"){
            this.eat();
            return "The dog ate the entire food in a few bites.";
        }
        else if(activity == "nap"){
            this.sleep();
            return "The dog took a quick nap.";
        }
        else if(this.likesActivity(activity)) return "Woof Woof. The dog really enjoyed the " + activity + " activity.";
        else if(this.dislikesActivity(activity)) return "The dog did not like " + activity + " activity. The dog walked away";
        return "The dog felt indifferent about the " + activity + " activity.";
    };
}

class Rabbit extends Mammal implements PlayfulPet{
    public static final String SPECIES = "Rabbit";
    public static final double LIFE_EXPECTANCY = 3000;
    public static final double Body_TEMPERATURE = 40;
    
    private static final double PLAYFUL_HOURLY_COSTS = 30;
    private static final String[] LIKED_ACTIVITIES = {"eat","nap","chase","drink","jump","dig"};
    private static final String[] DISLIKED_ACTIVITIES = {"bath","dressup"};

    public Rabbit(double heightM, double weightKg, String biologicalSex){
        super(Rabbit.SPECIES, heightM, weightKg, Rabbit.LIFE_EXPECTANCY, biologicalSex, Rabbit.LIFE_EXPECTANCY);
    }

    public String toString(){
        return super.toString() + " this is a rabbit";
    }

    public void woof(){
        System.out.println("Squeak Squeak");
    }

    public String getPetName(){
        return this.species;
    }

    public String play(){
        return "This rabbit starts jumping around and chases an insect on the grass.";
    }

    public String playWithPerson(Person person){
        String s = "The bunny hops towards " + person.getName();
        s+= ". After the rabbit stares at " + person.getName() + ", " + person.getName() + " makes the rabbit chase a small carrot. The rabbit hops towards it.";
        return s;
    }

    public String playNoise(){
        return "Squeak";
    }

    public double getRentalCosts(){
        return Rabbit.PLAYFUL_HOURLY_COSTS;
    }

    public boolean likesActivity(String activity){
        return Arrays.asList(Rabbit.LIKED_ACTIVITIES).contains(activity);
    };

    public boolean dislikesActivity(String activity){
        return Arrays.asList(Rabbit.DISLIKED_ACTIVITIES).contains(activity);
    };

    public String doActivity(String activity){
        if(activity == "eat"){
            this.eat();
            return "The rabbit chew on the food bit by bit taking tiny bites.";
        }
        else if(activity == "nap"){
            this.sleep();
            return "The rabbit took a long nap.";
        }
        else if(this.likesActivity(activity)) return ".... The Rabbit really enjoyed the " + activity + " activity.";
        else if(this.dislikesActivity(activity)) return "Squeeeak. The Rabbit did not like " + activity + " activity. The rabbit quickly hopped away";
        return "The rabbit felt indifferent about the " + activity + " activity.";
    };
}

class Pony extends Mammal implements PlayfulPet{
    public static final String SPECIES = "Pony";
    public static final double LIFE_EXPECTANCY = 10950;
    public static final double Body_TEMPERATURE = 37;
    
    private static final double PLAYFUL_HOURLY_COSTS = 50;
    private static final String[] LIKED_ACTIVITIES = {"eat","nap","chase","drink","jump"};
    private static final String[] DISLIKED_ACTIVITIES = {"bath","dig"};

    public Pony(double heightM, double weightKg, String biologicalSex){
        super(Pony.SPECIES, heightM, weightKg, Pony.LIFE_EXPECTANCY, biologicalSex, Pony.LIFE_EXPECTANCY);
    }

    public String toString(){
        return super.toString() + " this is a pony";
    }

    public String getPetName(){
        return this.species;
    }

    public String play(){
        return "This pony is enjoying some playtime, trotting and galloping around with enthusiasm in the field.";
    }

    public String playWithPerson(Person person){
        String s = "The pony runs towards " + person.getName();
        s+= ". After the pony stares at " + person.getName() + ", " + person.getName() + " ride a pony and leisurely walks around the arena.";
        return s;
    }

    public String playNoise(){
        return "Neigh";
    }

    public double getRentalCosts(){
        return Pony.PLAYFUL_HOURLY_COSTS;
    }

    public boolean likesActivity(String activity){
        return Arrays.asList(Pony.LIKED_ACTIVITIES).contains(activity);
    };

    public boolean dislikesActivity(String activity){
        return Arrays.asList(Pony.DISLIKED_ACTIVITIES).contains(activity);
    };

    public String doActivity(String activity){
        if(activity == "eat"){
            this.eat();
            return "The pony chew on the food bit by bit taking tiny bites.";
        }
        else if(activity == "nap"){
            this.sleep();
            return "The pony took a quick nap.";
        }
        else if(this.likesActivity(activity)) return "The pony really enjoyed the " + activity + " activity.";
        else if(this.dislikesActivity(activity)) return "The pony did not like " + activity + " activity. The pony quickly walked away";
        return "The pony felt indifferent about the " + activity + " activity.";
    };
}

class Hamster extends Mammal implements PlayfulPet{
    public static final String SPECIES = "Hamster";
    public static final double LIFE_EXPECTANCY = 1000;
    public static final double Body_TEMPERATURE = 37;
    
    private static final double PLAYFUL_HOURLY_COSTS = 25;
    private static final String[] LIKED_ACTIVITIES = {"eat","gnaw","nest","groom","dig"};
    private static final String[] DISLIKED_ACTIVITIES = {"chase","handle"};

    public Hamster(double heightM, double weightKg, String biologicalSex){
        super(Hamster.SPECIES, heightM, weightKg, Hamster.LIFE_EXPECTANCY, biologicalSex, Hamster.LIFE_EXPECTANCY);
    }

    public String toString(){
        return super.toString() + " this is a hamster";
    }

    public String getPetName(){
        return this.species;
    }

    public String play(){
        return "This hamster is running through tunnels and spinning on her exercise wheel with delight.";
    }

    public String playWithPerson(Person person){
        String s = "The hamster runs towards " + person.getName();
        s+= ". After the hamster stares at " + person.getName() + ", " + person.getName() + " is playing with the hamster, gently handling it, offering treats, and watching it explore a playpen.";
        return s;
    }

    public String playNoise(){
        return "Squeak";
    }

    public double getRentalCosts(){
        return Hamster.PLAYFUL_HOURLY_COSTS;
    }

    public boolean likesActivity(String activity){
        return Arrays.asList(Hamster.LIKED_ACTIVITIES).contains(activity);
    };

    public boolean dislikesActivity(String activity){
        return Arrays.asList(Hamster.DISLIKED_ACTIVITIES).contains(activity);
    };

    public String doActivity(String activity){
        if(activity == "eat"){
            this.eat();
            return "The hamster chew on the food bit by bit taking tiny bites.";
        }
        else if(activity == "nap"){
            this.sleep();
            return "The hamster took a quick nap.";
        }
        else if(this.likesActivity(activity)) return "The hamster really enjoyed the " + activity + " activity.";
        else if(this.dislikesActivity(activity)) return "The hamster did not like " + activity + " activity. The hamster quickly walked away";
        return "The hamster felt indifferent about the " + activity + " activity.";
    };
}

class Chicken extends Mammal implements PlayfulPet {
    public static final String SPECIES = "Chicken";
    public static final double LIFE_EXPECTANCY = 1825; 
    public static final double BODY_TEMPERATURE = 41; 

    private static final double PLAYFUL_HOURLY_COSTS = 30;
    private static final String[] LIKED_ACTIVITIES = {"peck", "scratch", "dust-bathe", "forage", "flap"};
    private static final String[] DISLIKED_ACTIVITIES = {"chase"};

    public Chicken(double heightM, double weightKg, String biologicalSex) {
        super(SPECIES, heightM, weightKg, LIFE_EXPECTANCY, biologicalSex, BODY_TEMPERATURE);
    }

    public String toString() {
        return super.toString() + " This is a chicken.";
    }

    public String getPetName() {
        return this.species;
    }

    public String play() {
        return "This chicken is pecking at the ground and scratching for food.";
    }

    public String playWithPerson(Person person) {
        String s = "The chicken pecks around near " + person.getName();
        s += ". " + person.getName() + " throws some grains, and the chicken happily forages for them.";
        return s;
    }

    public String playNoise() {
        return "Cluck";
    }

    public double getRentalCosts() {
        return PLAYFUL_HOURLY_COSTS;
    }

    public boolean likesActivity(String activity) {
        return Arrays.asList(LIKED_ACTIVITIES).contains(activity);
    }

    public boolean dislikesActivity(String activity) {
        return Arrays.asList(DISLIKED_ACTIVITIES).contains(activity);
    }

    public String doActivity(String activity) {
        if (activity == "eat") {
            this.eat();
            return "The chicken enjoys pecking at its food.";
        }
        else if (activity == "nap") {
            this.sleep();
            return "The chicken takes a brief nap in the sun.";
        }
        else if (likesActivity(activity)) {
            return "Cluck. The chicken really enjoys the " + activity + " activity.";
        }
        else if (dislikesActivity(activity)) {
            return "The chicken does not like the " + activity + " activity.";
        }
        return "The chicken feels indifferent about the " + activity + " activity.";
    }
}

class Goat extends Mammal implements PlayfulPet{
    public static final String SPECIES = "Goat";
    public static final double LIFE_EXPECTANCY = 2920;
    public static final double Body_TEMPERATURE = 38.5;
    
    private static final double PLAYFUL_HOURLY_COSTS = 25;
    private static final String[] LIKED_ACTIVITIES = {"eat","clime","roam","chew"};
    private static final String[] DISLIKED_ACTIVITIES = {"chase","isolate"};

    public Goat(double heightM, double weightKg, String biologicalSex){
        super(Goat.SPECIES, heightM, weightKg, Goat.LIFE_EXPECTANCY, biologicalSex, Goat.LIFE_EXPECTANCY);
    }

    public String toString(){
        return super.toString() + " this is a goat";
    }

    public String getPetName(){
        return this.species;
    }

    public String play(){
        return "This goat is frolicking and playing around, leaping and climbing on rocks and logs in the pasture";
    }

    public String playWithPerson(Person person){
        String s = "The goat runs towards " + person.getName();
        s+= ". After the goat stares at " + person.getName() + ", " + person.getName() + " is offering it treats and gently petting its back as they both enjoy their time in the pasture.";
        return s;
    }

    public String playNoise(){
        return "Baa";
    }

    public double getRentalCosts(){
        return Goat.PLAYFUL_HOURLY_COSTS;
    }

    public boolean likesActivity(String activity){
        return Arrays.asList(Goat.LIKED_ACTIVITIES).contains(activity);
    };

    public boolean dislikesActivity(String activity){
        return Arrays.asList(Goat.DISLIKED_ACTIVITIES).contains(activity);
    };

    public String doActivity(String activity){
        if(activity == "eat"){
            this.eat();
            return "The goat enjoyed eating food.";
        }
        else if(activity == "nap"){
            this.sleep();
            return "The goat took a good nap.";
        }
        else if(this.likesActivity(activity)) return "The goat really enjoyed the " + activity + " activity.";
        else if(this.dislikesActivity(activity)) return "The goat did not like " + activity + " activity.";
        return "The goat felt indifferent about the " + activity + " activity.";
    };
}


class PlayfulDogAssistant extends PlayfulPetAssistant{
    public PlayfulPet createPlayfulPet(){
        return new Dog(RandomWrapper.getRanDouble(0.15,1.3), RandomWrapper.getRanDouble(9.5,25.8), RandomWrapper.ranBoolean() ? "male" : "female");
    }

    public ArrayList<PlayfulPet> createPlayfulPets(int amount){
        ArrayList<PlayfulPet> petList = new ArrayList<>();
        
        for(int i = 0; i < amount; i++){
            petList.add(new Dog(RandomWrapper.getRanDouble(0.15,0.3), RandomWrapper.getRanDouble(2.0,4.9), RandomWrapper.ranBoolean() ? "male" : "female"));
        }

        return petList;
    }
}

class PlayfulRabbitAssistant extends PlayfulPetAssistant{
    public PlayfulPet createPlayfulPet(){
        return new Rabbit(RandomWrapper.getRanDouble(0.15,0.4), RandomWrapper.getRanDouble(2.2,10.2), RandomWrapper.ranBoolean() ? "male" : "female");
    }

    public ArrayList<PlayfulPet> createPlayfulPets(int amount){
        ArrayList<PlayfulPet> petList = new ArrayList<>();
        
        for(int i = 0; i < amount; i++){
            petList.add(new Rabbit(RandomWrapper.getRanDouble(0.15,0.3), RandomWrapper.getRanDouble(2.0,4.9), RandomWrapper.ranBoolean() ? "male" : "female"));
        }

        return petList;
    }
}

class PlayfulPonyAssistant extends PlayfulPetAssistant{
    public PlayfulPet createPlayfulPet(){
        return new Pony(RandomWrapper.getRanDouble(0.15,0.4), RandomWrapper.getRanDouble(2.2,10.2), RandomWrapper.ranBoolean() ? "male" : "female");
    }

    public ArrayList<PlayfulPet> createPlayfulPets(int amount){
        ArrayList<PlayfulPet> petList = new ArrayList<>();
        
        for(int i = 0; i < amount; i++){
            petList.add(new Pony(RandomWrapper.getRanDouble(0.15,0.3), RandomWrapper.getRanDouble(2.0,4.9), RandomWrapper.ranBoolean() ? "male" : "female"));
        }

        return petList;
    }
}

class PlayfulHamsterAssistant extends PlayfulPetAssistant{
    public PlayfulPet createPlayfulPet(){
        return new Hamster(RandomWrapper.getRanDouble(0.15,0.4), RandomWrapper.getRanDouble(2.2,10.2), RandomWrapper.ranBoolean() ? "male" : "female");
    }

    public ArrayList<PlayfulPet> createPlayfulPets(int amount){
        ArrayList<PlayfulPet> petList = new ArrayList<>();
        
        for(int i = 0; i < amount; i++){
            petList.add(new Hamster(RandomWrapper.getRanDouble(0.15,0.3), RandomWrapper.getRanDouble(2.0,4.9), RandomWrapper.ranBoolean() ? "male" : "female"));
        }

        return petList;
    }
}

class PlayfulChickenAssistant extends PlayfulPetAssistant{
    public PlayfulPet createPlayfulPet(){
        return new Chicken(RandomWrapper.getRanDouble(0.15,0.4), RandomWrapper.getRanDouble(2.2,10.2), RandomWrapper.ranBoolean() ? "male" : "female");
    }

    public ArrayList<PlayfulPet> createPlayfulPets(int amount){
        ArrayList<PlayfulPet> petList = new ArrayList<>();
        
        for(int i = 0; i < amount; i++){
            petList.add(new Chicken(RandomWrapper.getRanDouble(0.15,0.3), RandomWrapper.getRanDouble(2.0,4.9), RandomWrapper.ranBoolean() ? "male" : "female"));
        }

        return petList;
    }
}

class PlayfulGoatAssistant extends PlayfulPetAssistant{
    public PlayfulPet createPlayfulPet(){
        return new Goat(RandomWrapper.getRanDouble(0.15,0.4), RandomWrapper.getRanDouble(2.2,10.2), RandomWrapper.ranBoolean() ? "male" : "female");
    }

    public ArrayList<PlayfulPet> createPlayfulPets(int amount){
        ArrayList<PlayfulPet> petList = new ArrayList<>();
        
        for(int i = 0; i < amount; i++){
            petList.add(new Goat(RandomWrapper.getRanDouble(0.15,0.3), RandomWrapper.getRanDouble(2.0,4.9), RandomWrapper.ranBoolean() ? "male" : "female"));
        }

        return petList;
    }
}

interface Rides{
    abstract public double kidFriendliness();// アトラクションの評価を0から100までのdouble値のパーセントで返すメソッド。
    abstract public double teenFriendliness() ; // 10代（ティーンエイジャー）にとってのアトラクションの満足度。
    abstract public double adultFriendliness() ; // 大人にとってのアトラクションの満足度。
    abstract public double scariness() ; // アトラクションの恐怖度。
    abstract public double thrill() ; // アトラクションのスリル度。
    abstract public double laughter() ; // アトラクションの面白度。
    abstract public double sightseeing() ; // アトラクションの観賞度。
    abstract public double comfortableness() ; // アトラクションの快適度。

    // アトラクションに関する情報を提供するメソッド。
    abstract public double averageSpeedM() ; // アトラクションの平均速度(m/s)。
    abstract public double maximumSpeed() ; // アトラクションの最大速度。
    abstract public double maximumWeight() ; // アトラクションがサポートする最大の体重。
    abstract public double minimumHeight() ; // アトラクションがサポートする最小の身長。
    abstract public double maximumHeight() ; // アトラクションがサポートする最大の身長。
    abstract public String warnings() ; // アトラクションが文字列として出力する警告。健康状態、発作、閉所恐怖症、妊娠、首のトラブルのような警告が含まれます。

    // アトラクションの説明を行うメソッド。
    abstract public String description() ; // アトラクションに関する説明。
    abstract public String title() ; // アトラクションの名前。
    abstract public String rideNarration(StateOfAffairs parkState);
}

class StateOfAffairs{
    private LocalDate date;
    private LocalTime time;
    private int numOfPerson;
    private double temperature;
    private double levelOfSound;
    private String mood;
    
}


class Main{
    public static void main(String[] args){
        FairyWorld fairyWorld = new FairyWorld();
        Person jessica = new Person("Jessica", "Roller", 30, 1.65, 95, "female");

        // fairyWorld.rentPet(new PlayfulCatAssistant(), jessica);
        fairyWorld.rentPet("pony", jessica, 3, null);
        fairyWorld.getRentedPetsInvoice().get(0).printInvoice();
    }
}