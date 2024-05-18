import java.util.LinkedList;
import java.util.Queue;
public class maBranch {
    
    
    //branch people info
    public String manager;
    public maHash<String, maPerson> personel = new maHash<String, maPerson>(1000);
    
    public maPerson branchManager = new maPerson();
    String branchName;
    
    
    //dismissal queues
    public Queue<maPerson> fireCook = new LinkedList<>();
    public Queue<maPerson> fireCashier= new LinkedList<>();
    public Queue<maPerson> fireCourier= new LinkedList<>();
    public Queue<maPerson> firePeople= new LinkedList<>();
    public Queue<maPerson> promCook= new LinkedList<>();
    public Queue<maPerson> promCashier= new LinkedList<>();
    public boolean isManagerToFire;
    
    //general knowledge about the branch
    public int cookCount;
    public int cashierCount;
    public int courierCount;
    public int branchMonthlyBonus;
    public int branchFullBonus;
    public static int howManyBranches = 0;
    
    maBranch() {}
    maBranch(String firstGuy, String job, String branchName){
        this.cookCount = 0;
        this.cashierCount = 0;
        this.courierCount = 0;
        this.branchMonthlyBonus = 0;
        this.branchFullBonus = 0;
        this.branchName = branchName;
        howManyBranches++;


        if(job.equals("MANAGER")){
            manager = firstGuy;
            maPerson tempPerson = new maPerson(firstGuy, job);
            personel.put(firstGuy, tempPerson);
            branchManager = tempPerson;
        }
        else if(job.equals("COOK")){
            maPerson tempPerson = new maPerson(firstGuy, job);
            personel.put(firstGuy, tempPerson);
            cookCount++;
        }
        else if(job.equals("CASHIER")){
            maPerson tempPerson = new maPerson(firstGuy, job);
            personel.put(firstGuy, tempPerson);
            cashierCount++;
        }
        else if(job.equals("COURIER")){
            maPerson tempPerson = new maPerson(firstGuy, job);
            personel.put(firstGuy, tempPerson);
            courierCount++;
        }
    }
    

    public void newGuy(String theGuy, String job){
        if(job.equals("MANAGER")){
            manager = theGuy;
            maPerson tempPerson = new maPerson(theGuy, job);
            personel.put(theGuy, tempPerson);
            branchManager = tempPerson;
        }
        else if(job.equals("COOK")){
            maPerson tempPerson = new maPerson(theGuy, job);
            personel.put(theGuy, tempPerson);
            cookCount++;
        }
        else if(job.equals("CASHIER")){
            maPerson tempPerson = new maPerson(theGuy, job);
            personel.put(theGuy, tempPerson);
            cashierCount++;
        }
        else if(job.equals("COURIER")){
            maPerson tempPerson = new maPerson(theGuy, job);
            personel.put(theGuy, tempPerson);
            courierCount++;
        }

    }
    
}
