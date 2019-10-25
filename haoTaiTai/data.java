public class data{
    double output=4000;//产量；
    double cycleTime[] = {2,3,7,7};//周期时间；
    ArrayList<Double> sigalNeed,currentPackQuantity;//单台用量e，当前包装数量j
    ArrayList<Double> layer,layerAmount;//层数，每层数量；
    public static ArrayList<Double> getUsedAmount(){//R
        //单台使用量 = 产量*单台用量/包装数量
        ArryList<Double> usedAmount;
        usedAmount = getSigalNeed()*getcurrentPackQuantity();
        return usedAmount;
    }
    public static ArrayList<Double> getSigalNeed(){
        ArryList<Double> temp = {3,1,0.002,1,2,1,2,2,2,3,1,1,2};
       return temp;
    }
    public static ArrayList<Double> getcurrentPackQuantity(){
        ArryList<Double> temp = {35000
            ,2000
            ,30
            ,10000
            ,20000
            ,3000
            ,500
            ,24
            ,80
            ,200
            ,5
            ,25
            ,25
            };
       return temp;
    }
    public static void main(String[] args){
        ArrayList<Double> lists = new List<Double>();
        lists = getSigalNeed();
        for (int i = 0; i < lists.size(); i++) {
             System.out.print(lists.get(i));
        }
    }


}