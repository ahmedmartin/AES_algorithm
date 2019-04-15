/*
first we put message in array (4x4) 
to encrypt message first add round key 0 with message
then make substitute from s_box 
then make shift rows 0,1,2,3 respect to message rows
then make mixcolumn with array mixcolumn (multible message with mixcolumn in GF(2^8) )
            [02 03 01 01]
            [01 02 03 01]
 mixcolumn =[01 01 02 03]
            [03 01 01 02]
then repeat previes steps 10 times but , last one without mixcolumn step
 */
package aes;



/**
 *
 * @author Ahmed_Martin
 */
public class AES_algorithm {
      private String key [] = new String[11];
      private String mixcolumn[][];
      private String s_box[][]=new String[16][16]; 
    public AES_algorithm() {
        
       mixcolumn = make_4x4_matrix_binary("02010103030201010103020101010302");
   
       // key round 
       key[0]="5468617473206D79204B756E67204675";
       key[1]="E232FCF191129188B159E4E6D679A293";
       key[2]="56082007C71AB18F76435569A03AF7FA";
       key[3]="D2600DE7157ABC686339E901C3031EFB";
       key[4]="A11202C9B468BEA1D75157A01452495B";
       key[5]="B1293B3305418592D210D232C6429B69";
       key[6]="BD3DC2B7B87C47156A6C9527AC2E0E4E";
       key[7]="CC96ED1674EAAA031E863F24B2A8316A";
       key[8]="8E51EF21FABB4522E43D7A0656954B6C";
       key[9]="BFE2BF904559FAB2A16480B4F7F1CBD8";
       key[10]="28FDDEF86DA4244ACCC0A4FE3B316F26";
       
       // S-box 
       String temp="637C777BF26B6FC53001672BFED7AB76";
       temp+="CA82C97DFA5947F0ADD4A2AF9CA472C0";
       temp+="B7FD9326363FF7CC34A5E5F171D83115";
       temp+="04C723C31896059A071280E2EB27B275";
       temp+="09832C1A1B6E5AA0523BD6B329E32F84";
       temp+="53D100ED20FCB15B6ACBBE394A4C58CF";
       temp+="D0EFAAFB434D338545F9027F503C9FA8";
       temp+="51A3408F929D38F5BCB6DA2110FFF3D2";
       temp+="CD0C13EC5F974417C4A77E3D645D1973";
       temp+="60814FDC222A908846EEB814DE5E0BDB";
       temp+="E0323A0A4906245CC2D3AC629195E479";
       temp+="E7C8376D8DD54EA96C56F4EA657AAE08";
       temp+="BA78252E1CA6B4C6E8DD741F4BBD8B8A";
       temp+="703EB5664803F60E613557B986C11D9E";
       temp+="E1F8981169D98E949B1E87E9CE5528DF";
       temp+="8CA1890DBFE6426841992D0FB054BB16";
       for(int i=0,index=0;i<16;i++){
         for(int j=0;j<16;j++,index+=2){
           s_box[i][j]= temp.substring(index,index+2);
         }
       }
       
    }
    
    String encrypt(String message){
        
        
        String message_4x4 [][]= make_4x4_matrix_binary(message);
        
        String key_4x4 [][] = make_4x4_matrix_binary(key[0]);
        message_4x4 = add(message_4x4,key_4x4);
        
        for(int i=1;i<=9;i++){
                       
          message_4x4=subistitution(message_4x4);
          
          message_4x4=shift_row(message_4x4);
          
          message_4x4=product(message_4x4, mixcolumn);
         
          key_4x4= make_4x4_matrix_binary(key[i]);
          message_4x4=add(message_4x4, key_4x4);
         
        }
        
        message_4x4=subistitution(message_4x4);
        message_4x4=shift_row(message_4x4);
        key_4x4= make_4x4_matrix_binary(key[10]);
        message_4x4=add(message_4x4, key_4x4);
        
        return convert_4x4_matrix_to_hex_string(message_4x4);
    }
    
    private void print_matrix(String [][]matrix){
        for(int row =0;row<4;row++){
          for(int column=0;column<4;column++){
              System.out.print(matrix[row][column]+" ");
          }
            System.out.println("");
       }
    }
    
    
    // subistitute every hex to another from s_box
    private String [][] subistitution(String[][] matrix){
        String hex=convert_4x4_matrix_to_hex_string(matrix); // convert matrix to hex string
        String temp ="";
         // take every 2 character and search in s_box and subistitute it
         for(int i=0;i<hex.length();i+=2){
             int a ,b; 
             // check if first char is (A,B,C,D,E,F) convert to (10,11,12,13,14,15) respect
            switch (hex.charAt(i)){
                 case 'A': a=10; break;
                 case 'B': a=11; break;
                 case 'C': a=12; break;
                 case 'D': a=13; break;
                 case 'E': a=14; break;
                 case 'F': a=15; break;
                 default: a=Integer.parseInt(hex.charAt(i)+""); break;
            } 
            // check if second char is (A,B,C,D,E,F) convert to (10,11,12,13,14,15) respect
            switch (hex.charAt(i+1)){
                 case 'A': b=10; break;
                 case 'B': b=11; break;
                 case 'C': b=12; break;
                 case 'D': b=13; break;
                 case 'E': b=14; break;
                 case 'F': b=15; break;
                 default: b=Integer.parseInt(hex.charAt(i+1)+""); break;
            } 
            temp+=s_box[a][b]; // put result in temp 
         }
         return make_4x4_matrix_binary(temp); // return binary matrix that convert hex(temp) to binary matrix
    }
    
    // shift every row shift left (0,1,2,3) respect
    private String [][] shift_row(String [][] matrix){
        String result[][] = new String[4][4];
        for(int i=0;i<4;i++){ // i is number of shifts in this row
           for(int j=i,index=0;j<4;j++,index++){ // put last elements in front
               result[i][index]=matrix[i][j];
           }
           for(int j=0,index=4-i;j<i;j++,index++){ // put front element in last
             result[i][index]=matrix[i][j];
           }
        }
        return result;
    }
    
    
    // add 2 matrices 
    private String[][] add(String [][]a,String[][]b){
        String [][] result=new String[4][4];
       for(int row =0;row<4;row++){
          for(int column=0;column<4;column++){
             String temp[] = new String[2];     //get element in place [row][column] for
             temp[0]=a[row][column];           //first and second mutrix then
             temp[1]=b[row][column];          //we call add to make (add binary 2 element)
             result[row][column]=add(temp);  // then return result put in result matrix [row][column]
          }
       }
       return result;
    }
    
    // add multible number binary
    private String add (String[] bits){
          String result="";
          for(int column=0;column<8;column++){
             char temp = bits[0].charAt(column);
              for(int row=1;row<bits.length;row++){
                 if(temp==bits[row].charAt(column))
                    temp='0';
                 else
                    temp='1';
              }
              result+=temp;
          }
          return result;
    }
    
    // product 2 marices
    private String[][] product(String[][]ms,String[][]mi){
        String ressult[][] = new String[4][4];
        String temp [] = new String[4];
        // product 2 matrices row * column and then add all values
        for(int i=0;i<4;i++){
            for(int k =0;k<4;k++){
                for(int j=0;j<4;j++){
                  temp[j]= product(ms[j][k],mi[i][j]);
                }
           ressult[i][k]=add(temp);
           temp =new String[4];
            }
        }
        return ressult;
    }
    
    //product 2 numbers
    private String product(String first , String second){
           String irreducable = "00011011";      // irreducable function for GF(2^8) 
           String []result = new String[8];      // array contain all result for product x in f(Xi)
           result[0] =first;
           // algorethm to product x in f(X)
           for(int i=1;i<8;i++){
               // sheft 1 character and put 0 at the end
               String sheft = result[i-1].substring(1, 8)+"0";
               result[i]="";
               // check if first character =0
               if(result[i-1].charAt(0)=='0')
                   result[i]=sheft;  // result i = sheft 
               else{
                  // add irreducable with sheft and put result in result i
                 String [] t ={sheft,irreducable};
                 result[i]= add(t);
               }
           }
           
           String temp ="";
           // if i found 1 in index i  put result[j] at temp and separate it by ","
           for(int i=0,j=7;i<8;i++,j--){
              if(second.charAt(i)=='1'){
                 temp+=result[j]+",";
              }
           }
           return add(temp.split(","));  // split data in add method to add all value for result [i]
    }
    
   // make matrix 4x4 convert from hex to binary
    private String [][] make_4x4_matrix_binary (String hexdecimal_str){
        String binary = convert_hex_to_bin(hexdecimal_str); // convert hex to binary 
        String result[][] = new String[4][4];
        // put every 8 bit from binary string  in result [row][column]  row=0....3 , column=0....3  
        for(int column =0,index=0 ; column <4;column++){
           for(int row=0;row<4;row++,index+=8){
              result[row][column]= binary.substring(index, index+8);
           }
        }
        return result;
    }
    
    // convert from matrix 4x4 binary to hexdecimal 
    private String  convert_4x4_matrix_to_hex_string (String[][] matrix){
        String result = "";
        // loop in matrix  [row] [column]
        for(int column =0; column <4;column++){
           for(int row=0;row<4;row++){
              result+= convert_binary_to_hex(matrix[row][column]);   //convert binary in matrix [row][column] to hex
           }
        }
        return result;
    }
    
    private String convert_hex_to_bin(String str) {
        String result = "";
        byte[] temp = hexToBytes(str);  // convert from hex to byte then convert byte to binary
        for (int i = 0; i < temp.length; i++) {
            byte b1 = temp[i];
            result += String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
        }
        return result;
    }
    private byte[] hexToBytes(String str) {
        if (str == null) {
            return null;
        } else if (str.length() < 2) {
            return null;
        } else {
            int len = str.length() / 2;
            byte[] buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(
                        str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }
    }
    
    // convert from binary to hexdecimal
    private String convert_binary_to_hex(String binary){
        int sum =0;
        String result ="";
        // loop for every char in String binary 
        for(int i=binary.length()-1,power=0;i>=0;i--,power++){
           if(binary.charAt(i)=='1'){          // if char = 1 
             sum+=(int) Math.pow(2, power);   // sum = sum + 2^ power  power (0 or 1 , 2 ,3)
           }
           
           if(i%4==0){  // if iterator   i%4 = 0 
               // check if sum = (10 or 11,12,13,14,15) supstitute with (A ,B,C,D,E,F) respectefly
               switch (sum){
                 case 10: result = "A"+result; break;
                 case 11: result = "B"+result; break;
                 case 12: result = "C"+result; break;
                 case 13: result = "D"+result; break;
                 case 14: result = "E"+result; break;
                 case 15: result = "F"+result; break;
                 default: result=sum+result; break;
               }      
             power=-1;  //make power = -1 becuase it increase every time 1 then next step will be 0
             sum=0;  // make sum =0 to calculate new hex number 
           }
           
        }
        return result;
    }
    
}
