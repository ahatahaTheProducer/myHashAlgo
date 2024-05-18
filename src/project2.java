import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;


import java.util.LinkedList;
import java.util.Queue;


// we are goingt to get rid of hashmap after we implement our own hashmap

public class project2 {
    public static void main(String[] args) {
        String initFileName = args[0];
        String inputFileName = args[1];
        String outputFileName = args[2];

        
        // now we know the file names in the current directory

        String[] months = { "January:", "February:", "March:", "April:", "May:", "June:", "July:", "August:", "September:",
                "October:", "November:", "December:" };
        // we have an array of months to understand the month we are in while reading
        // inputFile

        // lets Read the initFile
        File initFile = new File(initFileName);
        maHash<String, maBranch> infoHash = new maHash<String, maBranch>(32203);
        //System.out.println(infoHash.capacity +" "+ infoHash.includes);
        //HashMap<String, maBranch> infoHash = new HashMap<String, maBranch>();
        try {
            Scanner initReader = new Scanner(initFile);
            while (initReader.hasNextLine()) {
                String tempStr = initReader.nextLine();
                String[] tempArr = tempStr.split(", ");
                for (int i = 0; i < tempArr.length; i++) {
                    tempArr[i] = tempArr[i].trim();
                }
                // TRIM ?????????????????????????????????????????????????????????????????????
                String branchName = tempArr[0] + " " + tempArr[1];
                String tempName = tempArr[2];
                String tempJob = tempArr[3];
                //System.out.println("lalala\n");
                if (!infoHash.containsKey(branchName)) {
                    //System.out.println("ananı arıyorum");
                    maBranch tempBranch = new maBranch(tempName, tempJob, branchName);
                    infoHash.put(branchName, tempBranch);
                } else {
                    infoHash.get(branchName).newGuy(tempName, tempJob);
                }
            }
            //ArrayList<String> a = infoHash.keySet();
            //System.out.println(a.size());
            //for(String s : a){
            //    System.out.println(s);
            //}
            initReader.close();
        } catch (Exception e) {
            System.out.println("Error occured while reading the init file" + e.getMessage());
        }

        // we have read the init file and stored it in an arraylist
        // lets read the input file
        try {
            // actually the main functions will mostly be called here
            File inputFile = new File(inputFileName);
            Scanner inputReader = new Scanner(inputFile);
            
            try { 
                File output = new File(outputFileName);
                FileWriter outputFile = new FileWriter(output, true);
                
                boolean firstMonth = true;
                while (inputReader.hasNextLine()) {
                    // System.out.println("\n\n");
                    
                    // maBranch abranch = infoHash.get("Ankara Mamak");
                    // for (String person : abranch.personel.keySet()) {
                    //     maPerson tempPerson = abranch.personel.get(person);
                    //     System.out.println(tempPerson.name + " " + tempPerson.job + " " + tempPerson.promotionPoint + " " + tempPerson.monthBonus + " " + tempPerson.totalRemainder);
                    // }
                    // System.out.println("\n\n\n\n");
                    
                    boolean enteringMonth = false;
                    String tempStr = inputReader.nextLine().trim();
                    //System.out.println(tempStr);
                    // check whether the line is empty or not
                    if (tempStr.equals("") || tempStr.equals("\n")) {
                        continue;
                    }
                    // check wheter the line is a month or not
                    for (String s : months) {
                        if (s.equals(tempStr))
                            enteringMonth = true;
                    }
                    // the line is either a month or sth else. we have an if else statement for each
                    // case
                    

                    if (enteringMonth ) {
                        //////////////////////// NEW MONTH ///////////////////////////
                        if (firstMonth) {
                            firstMonth = false;
                        } else {
                            for (String branchName : infoHash.keySet()) {
                                maBranch tempBranch = infoHash.get(branchName);
                                tempBranch.branchMonthlyBonus = 0;
                                // renewing the monthly counts
                                for (String person : tempBranch.personel.keySet()) {
                                    maPerson tempPerson = tempBranch.personel.get(person);
                                    tempPerson.monthBonus = 0;
                                    tempPerson.monthlyScore = 0;
                                    
                                }
                            }

                        }

                    } else {
                        // if the line is not a month, it is one of the following statements in if else
                        // statements
                        String[] tempArr = tempStr.split(":");
                        tempArr[1] = tempArr[1].trim();
                        
                        if (tempArr[0].equals("PERFORMANCE_UPDATE")) {
                            boolean personExists = true;
                            String[] tempArr2 = tempArr[1].split(", ");
                            String branchName = tempArr2[0] + " " + tempArr2[1];
                            String tempName = tempArr2[2];
                            if (!infoHash.get(branchName).personel.containsKey(tempName)) {
                                // the person is not in the branch

                                personExists = false;
                                
                                mywrite("There is no such employee.", outputFile);
                                continue;
                            }
                            String tempPerfPoint = tempArr2[3];
                            int perfPoint = Integer.parseInt(tempPerfPoint);
                            maPerson tempPerson = infoHash.get(branchName).personel.get(tempName);
                            
                            if (perfPoint > 0) {
                                ///////////////////////////////////////////////
                                
                                int prevPromPoint = tempPerson.promotionPoint;
                                
                                String tempJob = tempPerson.job;
                                int border = 0;
                                if (tempJob.equals("COOK")) {
                                    border = 10;
                                } else if (tempJob.equals("CASHIER")) {
                                    border = 3;
                                }
                                boolean alreadyAdded = true;
                                if (prevPromPoint < border) {
                                    alreadyAdded = false;
                                }
                                // in this part we checked whether the person is already added to the promotion
                                // queue or not
                                //////////////////////////////////////////////////
                                maBranch tempBranch = infoHash.get(branchName);
                                tempBranch.branchMonthlyBonus += perfPoint%200;
                                tempBranch.branchFullBonus += perfPoint%200;


                                //perfPoint += tempPerson.totalRemainder;
                                
                                
                                //tempPerson.totalRemainder = perfPoint%200;
                                tempPerson.promotionPoint += perfPoint/200;
                                int curPromPoint = tempPerson.promotionPoint;
                                if(tempPerson.job.equals("COOK") && curPromPoint > -5 && prevPromPoint <= -5){
                                    Queue<maPerson> tempQueue = new LinkedList<>();
                                    while(tempBranch.fireCook.size() != 0){
                                        maPerson tempPerson2 = tempBranch.fireCook.poll();
                                        if(tempPerson2.name.equals(tempPerson.name)){
                                            continue;
                                        }
                                        tempQueue.add(tempPerson2);
                                    }
                                    tempBranch.fireCook = tempQueue;
                                }
                                else if(tempPerson.job.equals("CASHIER") && curPromPoint > -5 && prevPromPoint <= -5){
                                    Queue<maPerson> tempQueue = new LinkedList<>();
                                    while(tempBranch.fireCashier.size() != 0){
                                        maPerson tempPerson2 = tempBranch.fireCashier.poll();
                                        if(tempPerson2.name.equals(tempPerson.name)){
                                            continue;
                                        }
                                        tempQueue.add(tempPerson2);
                                    }
                                    tempBranch.fireCashier = tempQueue;
                                }
                                else if(tempPerson.job.equals("COURIER") && curPromPoint > -5 && prevPromPoint <= -5){
                                    Queue<maPerson> tempQueue = new LinkedList<>();
                                    while(tempBranch.fireCourier.size() != 0){
                                        maPerson tempPerson2 = tempBranch.fireCourier.poll();
                                        if(tempPerson2.name.equals(tempPerson.name)){
                                            continue;
                                        }
                                        tempQueue.add(tempPerson2);
                                    }
                                    tempBranch.fireCourier = tempQueue;
                                }
                                else if(tempPerson.job.equals("MANAGER") && curPromPoint > -5 && prevPromPoint <= -5){
                                    tempBranch.isManagerToFire = false;
                                }
                                
                                
                                
                                
                                /// now lets check whether we should add the person to the promotion queue or
                                /// not
                                if (!alreadyAdded && tempPerson.promotionPoint >= border) {
                                    if (tempJob.equals("COOK")) {
                                        tempBranch.promCook.add(tempPerson);
                                        
                                        dismissManager(tempBranch, outputFile);
                                        promoteCook(tempBranch, outputFile);
                                    } else if (tempJob.equals("CASHIER")) {
                                        tempBranch.promCashier.add(tempPerson);
                                        dismissCook(tempBranch, outputFile);
                                        promoteCashier(tempBranch, outputFile);
                                    }
                                }
                            } else if (perfPoint < 0) {
                                
                                int prevPromPoint = tempPerson.promotionPoint;
                                
                                String tempJob = tempPerson.job;
                                int border = -5;
                                boolean alreadyAdded = true;
                                /////////////////////////////
                                if (prevPromPoint > border) {
                                    alreadyAdded = false;
                                }

                                //perfPoint += tempPerson.totalRemainder;
                                
    
                                
                                tempPerson.totalRemainder = perfPoint%200;
                                tempPerson.promotionPoint += perfPoint/200;
                                maBranch tempBranch = infoHash.get(branchName);
                                int curPromPoint = tempPerson.promotionPoint;
                                if(tempPerson.job.equals("COOK") && curPromPoint < 10 && prevPromPoint >= 10){
                                    Queue<maPerson> tempQueue = new LinkedList<>();
                                    while(tempBranch.promCook.size() != 0){
                                        maPerson tempPerson2 = tempBranch.promCook.poll();
                                        if(tempPerson2.name.equals(tempPerson.name)){
                                            continue;
                                        }
                                        tempQueue.add(tempPerson2);
                                    }
                                    tempBranch.promCook = tempQueue;
                                }
                                else if(tempPerson.job.equals("CASHIER") && curPromPoint < 3 && prevPromPoint >= 3){
                                    Queue<maPerson> tempQueue = new LinkedList<>();
                                    while(tempBranch.promCashier.size() != 0){
                                        maPerson tempPerson2 = tempBranch.promCashier.poll();
                                        if(tempPerson2.name.equals(tempPerson.name)){
                                            continue;
                                        }
                                        tempQueue.add(tempPerson2);
                                    }
                                    tempBranch.promCashier = tempQueue;
                                }
                                
                                
                                //System.out.println(tempPerson.name);
                                //////////////////////////////
                                if (!alreadyAdded && tempPerson.promotionPoint <= -5) {
                                    //System.out.println("heyy");
                                    if (tempJob.equals("COOK")) {
                                        tempBranch.fireCook.add(tempPerson);
                                        dismissCook(tempBranch, outputFile);
                                    } else if (tempJob.equals("CASHIER")) {
                                        tempBranch.fireCashier.add(tempPerson);
                                        dismissCashier(tempBranch, outputFile);
                                    } else if (tempJob.equals("COURIER")) {
                                        tempBranch.fireCourier.add(tempPerson);
                                        dismissCourier(tempBranch, outputFile);
                                    } else if (tempJob.equals("MANAGER")) {
                                        tempBranch.isManagerToFire = true;
                                        
                                        dismissManager(tempBranch, outputFile);
                                    }
                                }

                            }

                        }
                        else if (tempArr[0].equals("ADD")) {
                            
                            String[] tempArr2 = tempArr[1].split(", ");
                            String branchName = tempArr2[0] + " " + tempArr2[1];
                            String tempName = tempArr2[2];
                            String tempJob = tempArr2[3];
                            boolean exists = infoHash.get(branchName).personel.containsKey(tempName);
                            if(exists){
                                mywrite("Existing employee cannot be added again.", outputFile);
                                continue;
                            }
                            infoHash.get(branchName).newGuy(tempName, tempJob);
                            maBranch tempBranch = infoHash.get(branchName);
                            if (tempJob.equals("COOK")) {
                                dismissManager(tempBranch, outputFile);
                                dismissCook(tempBranch, outputFile);
                                promoteCook(tempBranch, outputFile);
                            }
                            else if (tempJob.equals("CASHIER")) {
                                dismissCook(tempBranch, outputFile);
                                dismissCashier(tempBranch, outputFile);
                                promoteCashier(tempBranch, outputFile);
                            }
                            else if (tempJob.equals("COURIER")) {
                                
                                dismissCourier(tempBranch, outputFile);
                            }

                        }
                        else if (tempArr[0].equals("LEAVE")) {
                            String[] tempArr2 = tempArr[1].split(", ");
                            String branchName = tempArr2[0] + " " + tempArr2[1];
                            String tempName = tempArr2[2];boolean personExists = true;
                            if (!infoHash.get(branchName).personel.containsKey(tempName)) {
                                // the person is not in the branch
                                //System.out.println("do i ever get herejjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
                                mywrite("There is no such employee.", outputFile);
                                personExists = false;
                                continue;
                            }
                            
                            
                            maPerson tempPerson = infoHash.get(branchName).personel.get(tempName);
                            String tempJob = tempPerson.job;
                            
                            //System.out.println("zorliiiiiiiiiiim");
                            
                            
                            if (tempJob.equals("MANAGER")) {
                                leaveManager(infoHash.get(branchName), outputFile);
                            }
                            else if (tempJob.equals("COOK")) {
                                leaveCook(infoHash.get(branchName), tempPerson, outputFile);
                            }
                            else if (tempJob.equals("CASHIER")) {
                                leaveCashier(infoHash.get(branchName), tempPerson, outputFile);
                            }
                            else if (tempJob.equals("COURIER")) {
                                leaveCourier(infoHash.get(branchName), tempPerson, outputFile);
                            }
                        }
                        else if (tempArr[0].equals("PRINT_MONTHLY_BONUSES")) {
                            //System.out.println("aşlskdjfşalksdjfşlaksdjfşslafdkj");
                            String[] tempArr2 = tempArr[1].split(", ");
                            String branchName = tempArr2[0] + " " + tempArr2[1];
                            int tempMBonus = infoHash.get(branchName).branchMonthlyBonus;
                            String tempMBonusStr = Integer.toString(tempMBonus);
                            String[] tempArr3 = branchName.split(" ");
                            mywrite("Total bonuses for the " + tempArr3[1] + " branch this month are: " + tempMBonusStr,
                                    outputFile);

                        }
                        else if (tempArr[0].equals("PRINT_OVERALL_BONUSES")) {
                            String[] tempArr2 = tempArr[1].split(", ");
                            String branchName = tempArr2[0] + " " + tempArr2[1];
                            int tempTotBonus = infoHash.get(branchName).branchFullBonus;
                            String[] tempArr3 = branchName.split(" ");
                            mywrite("Total bonuses for the " + tempArr3[1] + " branch are: " + tempTotBonus, outputFile);

                        }
                        else if (tempArr[0].equals("PRINT_MANAGER")) {
                            String[] tempArr2 = tempArr[1].split(", ");
                            String branchName = tempArr2[0] + " " + tempArr2[1];
                            String tempManager = infoHash.get(branchName).manager;
                            String[] tempArr3 = branchName.split(" ");
                            mywrite("Manager of the " + tempArr3[1] + " branch is " + tempManager + ".", outputFile);
                        }

                    }
                }
                outputFile.close();
            } catch (Exception e) {
                System.out.println("an error ocurred while creating the file" + e.getMessage());
            }

            inputReader.close();
        } catch (Exception e) {
            System.out.println("Error occured while reading the inPUT file" + e.getMessage());
        }

    }
    public static void dismissManager(maBranch tempBranch, FileWriter outputFile) {
        // the begining of any new non-first month means its the end of any other month
        // or it is the end of the file
        // at the end of any month
        // 3. we need to fire the people who are in the fire queues if there are people
        // to replace
        // 2. we need to promote people if suitable
        // we can dismiss the manager if there is a cook to replace manager
        //System.out.println(tempBranch.branchManager.promotionPoint + tempBranch.branchManager.name);
        //System.out.println("heyy");
        //System.out.println(tempBranch.branchName);
        if (tempBranch.isManagerToFire) {
            //System.out.println("heyy");
            maPerson tempPerson = tempBranch.branchManager;
            
            //System.out.println("saay my name");
            //System.out.println(tempPerson.promotionPoint + tempPerson.name);
            if (tempPerson.promotionPoint <= -5) {
                if (tempBranch.cookCount == 1 || tempBranch.promCook.size() == 0) {
                    
                    // we can not dismiss the manager
                } else {
                    
                    // we can dismiss the manager
                    // lets find the new manager
                    maPerson newManager = null;
                    if(tempBranch.promCook == null)System.out.println("null");
                    //System.out.println("saay my name");
                    //System.out.println( tempBranch.promCook.size());
                    while (tempBranch.promCook.size() != 0) {
                        maPerson tempCook = tempBranch.promCook.poll();
                        if(!tempBranch.personel.containsKey(tempCook.name))continue;
                        if (tempCook.promotionPoint >= 10) {
                            newManager = tempCook;
                            
                            break;
                        }
                    }
                    //System.out.println(newManager.name + "aşlksdf");
                    if (newManager != null) {
                        String prevManager = tempBranch.manager;
                        tempBranch.personel.remove(prevManager);
                        tempBranch.branchManager = newManager;
                        tempBranch.manager = newManager.name;
                        tempBranch.cookCount--;
                        tempBranch.branchManager.promotionPoint -= 10;
                        tempBranch.branchManager.job = "MANAGER";
                        tempBranch.isManagerToFire = false;
                        String[] tempArr = tempBranch.branchName.split(" ");
                        String outStr = prevManager + " is dismissed from branch: " + tempArr[1] +"." + "\n" + newManager.name
                                + " is promoted from Cook to Manager.";
                        mywrite(outStr, outputFile);
                    }
                }

            } else {
                tempBranch.isManagerToFire = false;
            }
        }

    }
    public static void dismissCook(maBranch tempBranch, FileWriter outputFile) {
        // the begining of any new non-first month means its the end of any other month
        // or it is the end of the file
        // at the end of any month
        // 3. we need to fire the people who are in the fire queues if there are people
        // to replace
        // 2. we need to promote people if suitable
        // we can dismiss the manager if there is a cook to replace manager
        // Queue<maPerson> tempQueue = tempBranch.fireCook;
        // if (tempBranch.cookCount != 1 && (tempBranch.promCashier != null && tempBranch.promCashier.size() != 0 )) {
        //     while (tempQueue != null && tempQueue.size() != 0) {
        //         maPerson tempPerson = tempQueue.poll();
        //         if (tempPerson.promotionPoint <= -5) {
        //             // we can dismiss the cook if there is a cashier to replace
        //             // lets find the new cook
        //             maPerson newCook = null;
        //             while (tempBranch.promCashier.size() != 0) {
        //                 maPerson tempCook = tempBranch.promCook.poll();
        //                 if (tempCook.promotionPoint >= 10) {
        //                     newCook = tempCook;
        //                     break;
        //                 }
        //             }
        //             if (newCook != null) {
        //                 String prevCook = tempPerson.name;
        //                 tempBranch.personel.remove(prevCook);
        //                 tempBranch.cashierCount--;
        //                 newCook.promotionPoint -= 3;
        //                 newCook.job = "COOK";
        //                 String[] tempArr = tempBranch.branchName.split(" ");
        //                 String outStr = prevCook + "is dismissed from branch: " + tempArr[1] + "\n" + newCook.name
        //                         + " is promoted from Cashier to Cook.";
        //                 mywrite(outStr, outputFile);
        //             }
        //             break;
        //         }

        //     }
        // }
        Queue<maPerson> tempQueue = tempBranch.fireCook;
        if (tempBranch.cookCount != 1) {
            while (tempQueue != null && tempQueue.size() != 0) {
                maPerson tempPerson = tempQueue.poll();
                if (tempPerson.promotionPoint <= -5) {
                    
                
                    
                        
                    tempBranch.personel.remove(tempPerson.name);
                    tempBranch.cookCount--;
                        
                    String[] tempArr = tempBranch.branchName.split(" ");
                    String outStr = tempPerson.name + " is dismissed from branch: " + tempArr[1] + ".";
                    mywrite(outStr, outputFile);
                    
                    break;
                }

            }
        }

    }
    public static void dismissCashier(maBranch tempBranch, FileWriter outputFile) {
        if (tempBranch.cashierCount != 1) {
            Queue<maPerson> tempQueue = tempBranch.fireCashier;
            while (tempQueue != null && tempQueue.size() != 0) {
                maPerson tempPerson = tempQueue.poll();
                if (tempPerson.promotionPoint <= -5) {
                    // we can dismiss the cashier
                    String prevCashier = tempPerson.name;
                    tempBranch.personel.remove(prevCashier);
                    tempBranch.cashierCount--;
                    String[] tempArr = tempBranch.branchName.split(" ");
                    String outStr = prevCashier + " is dismissed from branch: " + tempArr[1] + ".";
                    mywrite(outStr, outputFile);
                    break;
                }
            }
        }
    }
    public static void dismissCourier(maBranch tempBranch, FileWriter outputFile) {
        
        if (tempBranch.courierCount != 1) {
            Queue<maPerson> tempQueue = tempBranch.fireCourier;
            
            while (tempQueue != null && tempQueue.size() != 0) {
                
                maPerson tempPerson = tempQueue.poll();
                if (tempPerson.promotionPoint <= -5) {
                    // we can dismiss the cashier
                    String prevCourier = tempPerson.name;
                    tempBranch.personel.remove(prevCourier);
                    tempBranch.courierCount--;
                    String[] tempArr = tempBranch.branchName.split(" ");
                    String outStr = prevCourier + " is dismissed from branch: " + tempArr[1] + ".";
                    mywrite(outStr, outputFile);
                    break;
                }
            }
        }
    }
    public static void leaveManager(maBranch tempBranch, FileWriter outputFile) {
        
        
        if (tempBranch.cookCount == 1 || tempBranch.promCook.size() == 0) {
            // we can not make the manager leave
            //then we need to give bonus for the manager
            if(tempBranch.branchManager.promotionPoint > -5){
                tempBranch.branchManager.monthBonus += 200;
                tempBranch.branchManager.totalBonus += 200;
                tempBranch.branchFullBonus += 200;
                tempBranch.branchMonthlyBonus += 200;
            }
            

        } else {
            // we can dismiss the manager
            // lets find the new manager
            maPerson newManager = null;
            while (tempBranch.promCook.size() != 0) {
                maPerson tempCook = tempBranch.promCook.poll();
                if(!tempBranch.personel.containsKey(tempCook.name))continue;
                if (tempCook.promotionPoint >= 10) {
                    newManager = tempCook;
                    
                    break;
                }
            }
            if (newManager != null) {
                String prevManager = tempBranch.manager;
                tempBranch.personel.remove(prevManager);
                tempBranch.branchManager = newManager;
                tempBranch.manager = newManager.name;
                tempBranch.cookCount--;
                tempBranch.branchManager.promotionPoint -= 10;
                tempBranch.branchManager.job = "MANAGER";
                String[] tempArr = tempBranch.branchName.split(" ");
                String outStr = prevManager + " is leaving from branch: " + tempArr[1] + "." + "\n" + newManager.name
                        + " is promoted from Cook to Manager.";
                mywrite(outStr, outputFile);
            }
            else{
                // we can not make the manager leave
                //then we need to give bonus for the manager
                if(tempBranch.branchManager.promotionPoint > -5){
                    tempBranch.branchManager.monthBonus += 200;
                    tempBranch.branchManager.totalBonus += 200;
                    tempBranch.branchFullBonus += 200;
                    tempBranch.branchMonthlyBonus += 200;
            }
            }
        }

        

    }
    public static void leaveCook(maBranch tempBranch, maPerson tempPerson, FileWriter outputFile) {
        // the begining of any new non-first month means its the end of any other month
        // or it is the end of the file
        // at the end of any month
        // 3. we need to fire the people who are in the fire queues if there are people
        // to replace
        // 2. we need to promote people if suitable
        // we can dismiss the manager if there is a cook to replace manager
        
        if (tempBranch.cookCount != 1) {
            // we can dismiss the cook 
            // lets find the new cook
            
            
            String fireCook = tempPerson.name;
            tempBranch.personel.remove(fireCook);
            tempBranch.cookCount--;
            
            
            String[] tempArr = tempBranch.branchName.split(" ");
            String outStr = fireCook + " is leaving from branch: " + tempArr[1] + ".";
            mywrite(outStr, outputFile);
            
            
        }
        else{
            //we cannot make the cook leave
            //if deserves, give bonus
            if(tempPerson.promotionPoint > -5){
                tempPerson.monthBonus += 200;
                tempPerson.totalBonus += 200;
                tempBranch.branchMonthlyBonus += 200;
                tempBranch.branchFullBonus += 200;
            }
        }

    }
    public static void leaveCashier(maBranch tempBranch, maPerson tempPerson, FileWriter outputFile) {
        if (tempBranch.cashierCount != 1) {
            // we can dismiss the cashier
            String prevCashier = tempPerson.name;
            tempBranch.personel.remove(prevCashier);
            tempBranch.cashierCount--;
            String[] tempArr = tempBranch.branchName.split(" ");
            String outStr = prevCashier + " is leaving from branch: " + tempArr[1] + ".";
            mywrite(outStr, outputFile);
              
        }
        else{
            //we cannot make the cashier leave
            //if deserves, give bonus
            if(tempPerson.promotionPoint > -5){
                tempPerson.monthBonus += 200;
                tempPerson.totalBonus += 200;
                tempBranch.branchMonthlyBonus += 200;
                tempBranch.branchFullBonus += 200;
            }
        }
    }
    public static void leaveCourier(maBranch tempBranch, maPerson tempPerson, FileWriter outputFile) {

        if (tempBranch.courierCount != 1) {
            // we can dismiss the cashier
            String prevCourier = tempPerson.name;
            tempBranch.personel.remove(prevCourier);
            tempBranch.courierCount--;
            String[] tempArr = tempBranch.branchName.split(" ");
            String outStr = prevCourier + " is leaving from branch: " + tempArr[1] + ".";
            mywrite(outStr, outputFile);
              
        }
        else{
            //we cannot make the cashier leave
            //if deserves, give bonus
            if(tempPerson.promotionPoint > -5){
                tempPerson.monthBonus += 200;
                tempPerson.totalBonus += 200;
                tempBranch.branchMonthlyBonus += 200;
                tempBranch.branchFullBonus += 200;
            }
        }
    }
    public static void promoteCashier(maBranch tempBranch, FileWriter outputFile){
        if(tempBranch.cashierCount != 1){
            //we can promote a cashier
            while(tempBranch.promCashier.size() != 0){
                maPerson tempPerson = tempBranch.promCashier.poll();
                if(tempPerson.promotionPoint >= 3){
                    //we can promote this cashier
                    tempPerson.job = "COOK";
                    tempPerson.promotionPoint -= 3;
                    tempBranch.cookCount++;
                    tempBranch.cashierCount--;
                    String outStr = tempPerson.name + " is promoted from Cashier to Cook.";
                    if(tempPerson.promotionPoint >= 10){
                        tempBranch.promCook.add(tempPerson);
                    }
                    mywrite(outStr, outputFile);
                    break;
                }
            } 
        }
    }
    public static void promoteCook(maBranch tempBranch, FileWriter outputFile){
        if(tempBranch.cashierCount != 1 && tempBranch.branchManager.promotionPoint <= -5){
            //we can promote a cook
            while(tempBranch.promCook.size() != 0){
                maPerson tempPerson = tempBranch.promCook.poll();
                if(tempPerson.promotionPoint >= 10){
                    //we can promote this cook
                    tempPerson.job = "MANAGER";
                    tempPerson.promotionPoint -= 10;
                    tempBranch.cookCount--;
                    String outStr = tempPerson.name + " is promoted from Cook to Manager.";
                    mywrite(outStr, outputFile);
                    break;
                }
            } 
        }
    }
    public static void mywrite(String s, FileWriter fw) {
        try {
            //System.out.println(s);
            fw.write(s+"\n");
        } catch (Exception e) {
            System.out.println("an error ocurred while creating the file\n DO NOT FORGET TO DELETE THIS CODE LINE");
        }
    }

}