import java.util.Random;

public abstract class Character {
    private String id;
    private String name;
    private int hp;
    private boolean isAlive = true;

    protected Character(String name, int hp ) {
        this.id = generateId();
        this.name = name;
        this.hp = hp;
//        this.isAlive = true;
    }
///getters
    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public String getId() {
        return id;
    }

    public boolean isAlive() {
        return isAlive;
    }
///setters
    public void setName(String name) {
        this.name = name;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    protected void setAlive(boolean alive)  {
        isAlive = alive;
    }

///For autogenerated ID also

    public void updateId() {
        this.id = generateId();
    }

    private String generateId(){
        Random random = new Random();
        return Integer.toString(random.nextInt(1000));
   }
   ///To generate a random HP for character type
   public static int randomHp(String characterType){
        Random random = new Random();
        if(characterType.equals("warrior")){
            return random.nextInt(101) + 100;
        } else if (characterType.equals("wizard")) {
            return random.nextInt(51) + 50;
        } else{
            throw new IllegalArgumentException("Invalid Character Type");
        }

    }

    protected void receiveDamage(int damage) {
    }
}
