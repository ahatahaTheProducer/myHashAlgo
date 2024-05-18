public class maPerson {
    public int promotionPoint;
    public int monthlyScore;
    public int monthBonus;
    public int totalBonus;
    public int negRemainder;
    public int posRemainder;
    public int totalRemainder;
    public String name;
    public String job;
    maPerson(){

    }
    maPerson(String name, String job) {
        this.name = name;
        this.job = job;
        promotionPoint = 0;
        monthlyScore = 0;
        monthBonus = 0;
        totalBonus = 0;
        negRemainder = 0;
        posRemainder = 0;
        totalRemainder = 0;
    }

    
}
