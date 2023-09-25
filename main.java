import java.util.Arrays;
import java.util.Random;

class RandomWrapper{
    public static int getRan(int min, int max){
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public static boolean ranBoolean(){
        return new Random().nextBoolean();
    }
}

class Person{
    private String firstName;
    private String lastName;
    private int age;
    private double heightM;
    private double weightKg;
    private String biologicalSex;

    public Person(String firstName, String lastName, int age, double heightM, double weightKg, String biologicalSex){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.heightM = heightM;
        this.weightKg = weightKg;
        this.biologicalSex = biologicalSex;
    }

    public String getName(){
        return this.firstName + " " + this.lastName;
    }

    public String toString(){
        return this.getName();
    }

    public double getAge(){
        return this.age;
    }

    public double getHeightM(){
        return this.heightM;
    }

    public double getWeightKg(){
        return this.weightKg;
    }

    public String getBiologicalSex(){
        return this.biologicalSex;
    }
    
}

interface LaserTagMonsterFactory{
    abstract public LowTierMonster createLowTierMonster();
    abstract public MidTierMonster createMidTierMonster();
    abstract public HighTierMonster createHighTierMonster();
    abstract public FlyingMonster createFlyingMonster();
    abstract public HybridMonster createHybridMonster();
    abstract public FinalBossMonster createFinalBossMonster();
}

class Skill{
    protected int damage;
    protected String description;

    public Skill(int damage, String description){
        this.damage = damage;
        this.description = description;
    }

    public int getDamage(){
        return this.damage;
    }

    public String getDescription(){
        return this.description;
    }
}

interface Monster{
    abstract public int getHp();
    abstract public int getAttack();
    abstract public int getDefense();
    abstract public int getLevel();
    abstract public int getPoints();
    abstract public int getMovementSpeed();
    abstract public String getMonsterName();
    abstract public String getAppearance();
    abstract public String kill(); //kill will return a dying animation narration.
    abstract public Skill[] getSpecialSkill();
}

interface LowTierMonster extends Monster{
    abstract public Skill mainSkill();
}

interface MidTierMonster extends LowTierMonster{
    abstract public Skill sideSkill();
};

interface HighTierMonster extends MidTierMonster{
    abstract public Skill[] sideSkills();
};

interface FlyingMonster extends HighTierMonster{
    abstract public Skill mainFlySkill();
    abstract public Skill[] flySideSkills();
    abstract public Skill[] getFlyingSkills();
    abstract public int getFlySpeed();
    abstract public String flyAnimation();
};

interface HybridMonster extends FlyingMonster{
    abstract public int getAscendSpeed();
    abstract public int getDescendSpeed();
}

interface Fly{
    abstract public String fly();
}

interface FinalBossMonster extends HighTierMonster{
    abstract public String stageIncrement();
    abstract public String stageDecrement();
}

class LaserMonster implements Monster{
    protected static int[] MULTIPLIERS = {100,20,30,5,1};

    protected String monsterName;
    protected int hp;
    protected int attack;
    protected int defense;
    protected int level;
    protected int points;
    protected int movementSpeed;
    protected Skill[] specialSkills;

    public LaserMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.level = level;
        this.points = points;
        this.movementSpeed = movementSpeed;

        this.setInitialSkills();
    }

    public LaserMonster(int level){
        this.hp = level*LaserMonster.MULTIPLIERS[0];
        this.attack = level*LaserMonster.MULTIPLIERS[1];
        this.defense = level*LaserMonster.MULTIPLIERS[2];
        this.level = level;
        this.points = level* level*LaserMonster.MULTIPLIERS[3];
        this.movementSpeed = level*LaserMonster.MULTIPLIERS[4];

        this.setInitialSkills();
    }

    protected void setInitialSkills(){
        Skill[] skills = {new Skill(this.attack,"Generic attack")};
        this.setSpecialSkills(skills);
    }

    protected void setSpecialSkills(Skill[] skills){
        this.specialSkills = skills;
    };

    public int getHp(){
        return this.hp;
    }

    public int getAttack(){
        return this.attack;
    };

    public int getDefense(){
        return this.defense;
    };

    public int getPoints(){
        return this.points;
    };

    public int getLevel(){
        return this.level;
    }

    public int getMovementSpeed(){
        return this.movementSpeed;
    };

    public String kill(){
        this.hp = 0;
        return this.toString() + " died. The body drops and disappears from the screen in 2 seconds.";
    };

    public Skill[] getSpecialSkill(){
        return this.specialSkills;
    };

    public String getMonsterName(){
        return "Generic Monster";
    }

    public String getAppearance(){
        return "Grey block monster with a ? as its texture.";
    }

    public String toString(){
        return this.getMonsterName() 
        + " hp-" + this.hp
        + "/atk-" + this.attack
        + "/def-" + this.defense
        + "/lvl-" + this.level;
    }
}

class LaserLowTierMonster extends LaserMonster implements LowTierMonster{
    public LaserLowTierMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public LaserLowTierMonster(int level){
        super(level);
    }

    public Skill mainSkill(){
        return this.specialSkills[0];
    };

    public String getMonsterName(){
        return "Generic Low Tier Monster";
    }
}

class LaserMidTierMonster extends LaserLowTierMonster implements MidTierMonster{

    public LaserMidTierMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public LaserMidTierMonster(int level){
        super(level);
    }

    public Skill sideSkill(){
        return this.specialSkills[0];
    };

    protected void setInitialSkills(){
        Skill[] s = {new Skill(this.attack,"Generic attack"), new Skill(this.attack*2,"Generic side attack")};
        this.setSpecialSkills(s);
    }

    public String getMonsterName(){
        return "Generic Mid Tier Monster";
    }
};

class LaserHighTierMonster extends LaserMidTierMonster implements HighTierMonster{
    public LaserHighTierMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public LaserHighTierMonster(int level){
        super(level);
    }

    public Skill[] sideSkills(){
        return Arrays.copyOfRange(this.specialSkills, 1, this.specialSkills.length);
    };

    protected void setInitialSkills(){
        Skill[] s = {new Skill(this.attack,"Generic attack"), new Skill(this.attack*2,"Generic side attack"), new Skill(this.attack*3,"Generic side attack 2")};
        this.setSpecialSkills(s);
    }

    public String getMonsterName(){
        return "Generic High Tier Monster";
    }
};

class LaserFlyingMonster extends LaserHighTierMonster implements Fly, FlyingMonster{
    protected int flyingSpeed;
    protected Skill[] flyingSkills;

    public LaserFlyingMonster(int hp, int attack, int defense, int level, int points, int movementSpeed, int flyingSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
        this.flyingSpeed = flyingSpeed;
    }

    public LaserFlyingMonster(int level){
        super(level);
        this.flyingSpeed = level*LaserFlyingMonster.MULTIPLIERS[4];
    }

    public int getFlySpeed(){
        return this.flyingSpeed;
    };

    public String flyAnimation(){
        return "The monster flaps its wings to fly";
    };

    public String fly(){
        return "This object flies. " + this.flyAnimation();
    };

    protected void setInitialSkills(){
        Skill[] s = {new Skill(this.attack,"Generic attack"), new Skill(this.attack*2,"Generic side attack"), new Skill(this.attack*3,"Generic side attack 2")};
        this.setSpecialSkills(s);

        Skill[] fs = {new Skill(this.attack,"Generic fly attack"), new Skill(this.attack*2,"Generic side fly attack"), new Skill(this.attack*3,"Generic side fly attack 2")};
        this.setFlyingSkills(fs);
    }

    protected void setFlyingSkills(Skill[] skills){
        this.flyingSkills = skills;
    }

    public Skill[] getFlyingSkills(){
        return this.flyingSkills;
    };

    public Skill mainFlySkill(){
        return this.flyingSkills[0];
    };

    public Skill[] flySideSkills(){
        return Arrays.copyOfRange(this.flyingSkills, 1, this.flyingSkills.length);
    };

    public String getMonsterName(){
        return "Generic Flying Monster";
    }
};

class LaserHybridMonster extends LaserFlyingMonster implements HybridMonster{
    protected int ascendSpeed;
    protected int descendSpeed;

    public LaserHybridMonster(int hp, int attack, int defense, int level, int points, int movementSpeed, int flyingSpeed, int ascendSpeed, int descendSpeed){
        super(hp, attack, defense, level, points, movementSpeed, flyingSpeed);
        this.ascendSpeed = ascendSpeed;
        this.descendSpeed = descendSpeed;
    }

    public LaserHybridMonster(int level){
        super(level);
        this.ascendSpeed = level*LaserHybridMonster.MULTIPLIERS[4];
    }

    public int getAscendSpeed(){
        return this.ascendSpeed;
    };

    public int getDescendSpeed(){
        return this.descendSpeed;
    };

    public String getMonsterName(){
        return "Generic Hybrid Monster";
    }
}

class LaserFinalBossMonster extends LaserHighTierMonster implements FinalBossMonster{
    protected static final int TOTAL_STAGES = 3;
    protected int currentStage = 0;

    public LaserFinalBossMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public LaserFinalBossMonster(int level){
        super(level);
    }

    public String stageIncrement(){
        if(this.currentStage >= LaserFinalBossMonster.TOTAL_STAGES) return "NO MORE STAGES";
        this.currentStage++;
        this.attack*=this.currentStage;
        this.defense*=this.currentStage;
        return "BOSS MONSTER CHANGE! STAGE - " + this.currentStage;
    };

    public String stageDecrement(){
        if(this.currentStage <= 0) return "NO MORE STAGES";
        this.currentStage--;
        this.attack/=this.currentStage;
        this.defense/=this.currentStage;
        return "BOSS MONSTER REVERT! STAGE - " + this.currentStage;
    };

    public String getMonsterName(){
        return "Generic Final Boss Monster";
    }
}

class GenericLaserTagMonsterFactory implements LaserTagMonsterFactory{
    public LowTierMonster createLowTierMonster(){
        return new LaserLowTierMonster(RandomWrapper.getRan(1,20));
    };

    public MidTierMonster createMidTierMonster(){
        return new LaserMidTierMonster(RandomWrapper.getRan(5,30));
    };

    public HighTierMonster createHighTierMonster(){
        return new LaserHighTierMonster(RandomWrapper.getRan(15,50));
    };

    public FlyingMonster createFlyingMonster(){
        return new LaserFlyingMonster(RandomWrapper.getRan(15,50));
    };

    public HybridMonster createHybridMonster(){
        return new LaserHybridMonster(RandomWrapper.getRan(15,50));
    };

    public FinalBossMonster createFinalBossMonster(){
        return new LaserFinalBossMonster(RandomWrapper.getRan(40,100));
    };
}

//--------------------------------------
// Antarcticaモンスターを定義し、LaserTagMonsterFactory Abstract Factoryを実装したAntarctica Factoryを作成してください。
// また、メソッドを上書きしてください。（例. 各Antarcticaモンスターには異なる初期スキルがあります。）
class AntarcticaLowTierMonster extends LaserLowTierMonster{
    public AntarcticaLowTierMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public AntarcticaLowTierMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Antarctica Ice Low Tier Monster";
    }
}

class AntarcticaMidTierMonster extends LaserMidTierMonster{

    public AntarcticaMidTierMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public AntarcticaMidTierMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Antarctica Ice Mid Tier Monster";
    }
};

class AntarcticaHighTierMonster extends LaserHighTierMonster{
    public AntarcticaHighTierMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public AntarcticaHighTierMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Antarctica Ice High Tier Monster";
    }
};

class AntarcticaFlyingMonster extends LaserFlyingMonster{
    public AntarcticaFlyingMonster(int hp, int attack, int defense, int level, int points, int movementSpeed, int flyingSpeed){
        super(hp, attack, defense, level, points, movementSpeed, flyingSpeed);
    }

    public AntarcticaFlyingMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Antarctica Ice Flying Monster";
    }
};

class AntarcticaHybridMonster extends LaserHybridMonster{
    public AntarcticaHybridMonster(int hp, int attack, int defense, int level, int points, int movementSpeed, int flyingSpeed, int ascendSpeed, int descendSpeed){
        super(hp, attack, defense, level, points, movementSpeed, flyingSpeed, ascendSpeed, descendSpeed);
    }

    public AntarcticaHybridMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Antarctica Ice Hybrid Monster";
    }
}

class AntarcticaFinalBossMonster extends LaserFinalBossMonster{
    public AntarcticaFinalBossMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public AntarcticaFinalBossMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Antarctica Ice Final Boss Monster";
    }
}

// Antarctica abstract factoryの実装
class AntarcticaLaserTagMonsterFactory implements LaserTagMonsterFactory {
    public LowTierMonster createLowTierMonster(){
        return new AntarcticaLowTierMonster(RandomWrapper.getRan(1,20));
    };

    public MidTierMonster createMidTierMonster(){
        return new AntarcticaMidTierMonster(RandomWrapper.getRan(5,30));
    };

    public HighTierMonster createHighTierMonster(){
        return new AntarcticaHighTierMonster(RandomWrapper.getRan(15,50));
    };

    public FlyingMonster createFlyingMonster(){
        return new AntarcticaFlyingMonster(RandomWrapper.getRan(15,50));
    };

    public HybridMonster createHybridMonster(){
        return new AntarcticaHybridMonster(RandomWrapper.getRan(15,50));
    };

    public FinalBossMonster createFinalBossMonster(){
        return new AntarcticaFinalBossMonster(RandomWrapper.getRan(40,100));
    };
}
//--------------------------------------

class MechaLowTierMonster extends LaserLowTierMonster{
    public MechaLowTierMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public MechaLowTierMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Mecha Low Tier Monster";
    }
}

class MechaMidTierMonster extends LaserMidTierMonster{

    public MechaMidTierMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public MechaMidTierMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Mecha Mid Tier Monster";
    }
};

class MechaHighTierMonster extends LaserHighTierMonster{
    public MechaHighTierMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public MechaHighTierMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Mecha High Tier Monster";
    }
};

class MechaFlyingMonster extends LaserFlyingMonster{
    public MechaFlyingMonster(int hp, int attack, int defense, int level, int points, int movementSpeed, int flyingSpeed){
        super(hp, attack, defense, level, points, movementSpeed, flyingSpeed);
    }

    public MechaFlyingMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Mecha Flying Monster";
    }
};

class MechaHybridMonster extends LaserHybridMonster{
    public MechaHybridMonster(int hp, int attack, int defense, int level, int points, int movementSpeed, int flyingSpeed, int ascendSpeed, int descendSpeed){
        super(hp, attack, defense, level, points, movementSpeed, flyingSpeed, ascendSpeed, descendSpeed);
    }

    public MechaHybridMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Mecha Hybrid Monster";
    }
}

class MechaFinalBossMonster extends LaserFinalBossMonster{
    public MechaFinalBossMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public MechaFinalBossMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Mecha Final Boss Monster";
    }
}

// Mecha abstract factoryの実装
class MechaLaserTagMonsterFactory implements LaserTagMonsterFactory {
    public LowTierMonster createLowTierMonster(){
        return new MechaLowTierMonster(RandomWrapper.getRan(1,20));
    };

    public MidTierMonster createMidTierMonster(){
        return new MechaMidTierMonster(RandomWrapper.getRan(5,30));
    };

    public HighTierMonster createHighTierMonster(){
        return new MechaHighTierMonster(RandomWrapper.getRan(15,50));
    };

    public FlyingMonster createFlyingMonster(){
        return new MechaFlyingMonster(RandomWrapper.getRan(15,50));
    };

    public HybridMonster createHybridMonster(){
        return new MechaHybridMonster(RandomWrapper.getRan(15,50));
    };

    public FinalBossMonster createFinalBossMonster(){
        return new MechaFinalBossMonster(RandomWrapper.getRan(40,100));
    };
}

class MadagascarLowTierMonster extends LaserLowTierMonster{
    public MadagascarLowTierMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public MadagascarLowTierMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Madagascar Low Tier Monster";
    }
}

class MadagascarMidTierMonster extends LaserMidTierMonster{

    public MadagascarMidTierMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public MadagascarMidTierMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Madagascar Mid Tier Monster";
    }
};

class MadagascarHighTierMonster extends LaserHighTierMonster{
    public MadagascarHighTierMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public MadagascarHighTierMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Madagascar High Tier Monster";
    }
};

class MadagascarFlyingMonster extends LaserFlyingMonster{
    public MadagascarFlyingMonster(int hp, int attack, int defense, int level, int points, int movementSpeed, int flyingSpeed){
        super(hp, attack, defense, level, points, movementSpeed, flyingSpeed);
    }

    public MadagascarFlyingMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Madagascar Flying Monster";
    }
};

class MadagascarHybridMonster extends LaserHybridMonster{
    public MadagascarHybridMonster(int hp, int attack, int defense, int level, int points, int movementSpeed, int flyingSpeed, int ascendSpeed, int descendSpeed){
        super(hp, attack, defense, level, points, movementSpeed, flyingSpeed, ascendSpeed, descendSpeed);
    }

    public MadagascarHybridMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Madagascar Hybrid Monster";
    }
}

class MadagascarFinalBossMonster extends LaserFinalBossMonster{
    public MadagascarFinalBossMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public MadagascarFinalBossMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Madagascar Final Boss Monster";
    }
}

// Madagascar abstract factoryの実装
class MadagascarLaserTagMonsterFactory implements LaserTagMonsterFactory {
    public LowTierMonster createLowTierMonster(){
        return new MadagascarLowTierMonster(RandomWrapper.getRan(1,20));
    };

    public MidTierMonster createMidTierMonster(){
        return new MadagascarMidTierMonster(RandomWrapper.getRan(5,30));
    };

    public HighTierMonster createHighTierMonster(){
        return new MadagascarHighTierMonster(RandomWrapper.getRan(15,50));
    };

    public FlyingMonster createFlyingMonster(){
        return new MadagascarFlyingMonster(RandomWrapper.getRan(15,50));
    };

    public HybridMonster createHybridMonster(){
        return new MadagascarHybridMonster(RandomWrapper.getRan(15,50));
    };

    public FinalBossMonster createFinalBossMonster(){
        return new MadagascarFinalBossMonster(RandomWrapper.getRan(40,100));
    };
}

class UnderworldLowTierMonster extends LaserLowTierMonster{
    public UnderworldLowTierMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public UnderworldLowTierMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Underworld Low Tier Monster";
    }
}

class UnderworldMidTierMonster extends LaserMidTierMonster{

    public UnderworldMidTierMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public UnderworldMidTierMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Underworld Mid Tier Monster";
    }
};

class UnderworldHighTierMonster extends LaserHighTierMonster{
    public UnderworldHighTierMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public UnderworldHighTierMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Underworld High Tier Monster";
    }
};

class UnderworldFlyingMonster extends LaserFlyingMonster{
    public UnderworldFlyingMonster(int hp, int attack, int defense, int level, int points, int movementSpeed, int flyingSpeed){
        super(hp, attack, defense, level, points, movementSpeed, flyingSpeed);
    }

    public UnderworldFlyingMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Underworld Flying Monster";
    }
};

class UnderworldHybridMonster extends LaserHybridMonster{
    public UnderworldHybridMonster(int hp, int attack, int defense, int level, int points, int movementSpeed, int flyingSpeed, int ascendSpeed, int descendSpeed){
        super(hp, attack, defense, level, points, movementSpeed, flyingSpeed, ascendSpeed, descendSpeed);
    }

    public UnderworldHybridMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Underworld Hybrid Monster";
    }
}

class UnderworldFinalBossMonster extends LaserFinalBossMonster{
    public UnderworldFinalBossMonster(int hp, int attack, int defense, int level, int points, int movementSpeed){
        super(hp, attack, defense, level, points, movementSpeed);
    }

    public UnderworldFinalBossMonster(int level){
        super(level);
    }

    public String getMonsterName(){
        return "Underworld Final Boss Monster";
    }
}

// Underworld abstract factoryの実装
class UnderworldLaserTagMonsterFactory implements LaserTagMonsterFactory {
    public LowTierMonster createLowTierMonster(){
        return new UnderworldLowTierMonster(RandomWrapper.getRan(1,20));
    };

    public MidTierMonster createMidTierMonster(){
        return new UnderworldMidTierMonster(RandomWrapper.getRan(5,30));
    };

    public HighTierMonster createHighTierMonster(){
        return new UnderworldHighTierMonster(RandomWrapper.getRan(15,50));
    };

    public FlyingMonster createFlyingMonster(){
        return new UnderworldFlyingMonster(RandomWrapper.getRan(15,50));
    };

    public HybridMonster createHybridMonster(){
        return new UnderworldHybridMonster(RandomWrapper.getRan(15,50));
    };

    public FinalBossMonster createFinalBossMonster(){
        return new UnderworldFinalBossMonster(RandomWrapper.getRan(40,100));
    };
}

class FairyWorld{
    public void playLaserTag(Person person, LaserTagMonsterFactory factory){
        String endl = System.lineSeparator();
        System.out.println(person + " will now play laser tag!" + endl);

        LowTierMonster lowMon = factory.createLowTierMonster();
        System.out.println("Fighting " + lowMon + "....Defeated." + endl);
        MidTierMonster midMon  = factory.createMidTierMonster();
        System.out.println("Fighting " + midMon + "....Defeated." + endl);
        HighTierMonster highMon = factory.createHighTierMonster();
        System.out.println("Fighting " + highMon + "....Defeated." + endl);
        FlyingMonster flyMon = factory.createFlyingMonster();
        System.out.println("Fighting " + flyMon + "....Defeated." + endl);
        HybridMonster hybridMon = factory.createHybridMonster();
        System.out.println("Fighting " + hybridMon + "....Defeated." + endl);
        FinalBossMonster finalMon = factory.createFinalBossMonster();
        System.out.println("Fighting " + finalMon + "....Defeated." + endl);


        System.out.println("Congratulations! All monsters were defeated!" + endl);
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxx" + endl);
    }
}

class Main{
    public static void main(String[] args){
        FairyWorld fairyWorld = new FairyWorld();
        Person jessica = new Person("Jessica", "Roller", 30, 1.65, 95, "female");

        fairyWorld.playLaserTag(jessica, new GenericLaserTagMonsterFactory());
        fairyWorld.playLaserTag(jessica, new AntarcticaLaserTagMonsterFactory());
        fairyWorld.playLaserTag(jessica, new MechaLaserTagMonsterFactory());
        fairyWorld.playLaserTag(jessica, new MadagascarLaserTagMonsterFactory());
        fairyWorld.playLaserTag(jessica, new UnderworldLaserTagMonsterFactory());
    }
}