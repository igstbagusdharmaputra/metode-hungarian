package risetoperasi_project_penugasan;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RisetOperasi_Project_Penugasan {
    static JFrame frame;
    private static JTextField input[][]; //field text untuk memasukan bobot matrix
    private static JTextField inputdata; //field text untuk memasukan jumlah data

    private static JTextField inputField; //field text 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
               tampil();
            }
        });
    }
    
    static void tampil(){ // fungsi yang pertama dijalankan untuk meminta jumlah baris dan kolom
        frame = new JFrame("Project Riset Operasi Metode Penugasan");
        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();
        label1.setText("Data");
       
        inputdata= new JTextField(5);
        
        Font ukuranText = inputdata.getFont().deriveFont(Font.PLAIN,20f);
        inputdata.setFont(ukuranText);
        
        label1.setFont(ukuranText);
        
        JButton TombolOK = new JButton("OK");
        TombolOK.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {  
		 int data=Integer.parseInt(inputdata.getText());
                 setNilai(data,data);
            }
        });
        
        frame.getContentPane().setLayout(new FlowLayout());
        frame.setPreferredSize(new Dimension(400, 100));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(label1);
        frame.add(inputdata);
        frame.add(TombolOK);
        frame.setVisible(true);
    }
    private static void setNilai(int baris,int kolom){ // fungsi untuk menset nilai matrix
       int i,j; //variabel iterasi untuk perulangan
       JPanel choosePanel [] = new JPanel [baris+3];
       choosePanel[0] = new JPanel();
       choosePanel[0].add(new Label("Metode Penugasan"));
       choosePanel[choosePanel.length-1] = new JPanel();
       choosePanel[choosePanel.length-1].add(new Label("Masukan Data !"));
       input  = new JTextField [baris][kolom];
       for (i=1; i <= baris; i++){
           choosePanel[i] = new JPanel();
           for (j=0; j < kolom ; j++){
               input [i-1][j] = new JTextField(4);
               choosePanel[i].add(input[i-1][j]);
               
           }
       }
       int hasil;
        hasil = JOptionPane.showConfirmDialog(null, choosePanel, 
             "INPUT", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (hasil == JOptionPane.OK_OPTION) {
            metodepenugasan(input);
        }
       
    }
    private static void pengurangan(int [][] m, int cek){
        int i,j;   //variabel iterasi dan m merupakan variabel matrix
        int min=0; // variabel untuk menentukan nilai minimum
        if(cek==0){ //pengurangan pada baris 
            for(i=0;i<m.length;i++){ //perulangan i berdasarkan jumlah baris 
                min = m[i][0]; // penentuan nilai awal minimum
                for (j=0;j<m[0].length;j++){ //perulangan j berdasarkan jumlah kolom
                    if(min > m[i][j]){ //pengecekan nilai terkecil pada matrix baris
                        min = m[i][j]; //pengisian nilai variabel min pada matrix
                    }
                }
                for (j=0; j < m[0].length;j++){ //perulangan pengurangan bobot matrix berdasarkan jumlah kolom
                    m[i][j] = m[i][j]-min;  // pengisian nilai matrix yang sudah dikurang dengan nilai minimum disetiap baris
                }
            
            }
        }
        if(cek==1){ //pengurangan pada kolom
            for (i=0;i<m[0].length;i++){ //perulangan i berdasarkan jumlah kolom 
                min = m[0][i];// penentuan nilai awal minimum
                for (j=0;j<m.length;j++){ //perulangan j berdasarkan jumlah baris
                    if(min > m[j][i]){//pengecekan nilai terkecil pada matrix kolom
                        min = m[j][i]; //pengisian nilai variabel min pada matrix
                    }
                }
                
                for (j=0;j<m.length;j++){ //perulangan pengurangan bobot matrix berdasarkan jumlah baris
                    m[j][i] = m[j][i]-min; // pengisian nilai matrix yang sudah dikurang dengan nilai minimum disetiap kolom
                }
            }
        }
        //test print matrix
//        for (i=0;i<m.length;i++){ 
//            for (j=0;j<m[0].length;j++){
//                System.out.print(" "+m[i][j]);
//            }
//            System.out.println();
//        }
        
    }
    private static void hitunggaris3(int [][] m,int [] cekbaris,int [] cekkolom ){
        /*proses ini dilakukan untuk melakukan mencari nilai matrix yang dimana
          ketika totalgaris kurang dari jumlah baris atau kolom
        */
        int min=9999; // inisialisasi nilai awal sebesar mungkin karena mencari nilai minimum
        for (int i=0; i< m.length;i++){
            for (int j=0; j<m[0].length;j++){
                //mencari nilai minimu dengan syarat nilai tersebut tidak terkena garis pada baris atau kolom
                if(min>m[i][j] && cekbaris[i]==0 && cekkolom[j]==0){ 
                    min = m[i][j];
                }
            }
            System.out.println("Minimum nilai adalah:"+min);
        }
        //melakukan proses pengurangan nilai matrix dengan nilai minimum yang sudah dicari
        for (int i=0;i<m.length;i++){
            for (int j=0;j<m[0].length;j++){
                    //jika baris belum ditandai atau coret maka lakukan proses pengurangan nilai bobot matrix 
                    if(cekbaris[i]==0){
                        m[i][j] = m[i][j] - min;
                    }
                    /*
                        1. pada kolom ketika terjadi pengurangan yang dilakukan pada cekbaris maka dilakukan
                           penambahan agar bisa kembali seperti nilai sebelumnya
                        2. pada kolom ketika terjadi intersection atau titik temu yang terkena 2 garis maka dilakukan
                           penambahan nilai dengan nilai min yang sudah dicari
                    */
                    if(cekkolom[j]==1){
                        m[i][j] = m[i][j] + min;
                    }
            }
        }
        hitunggaris1(m); //selanjutnya hitung kembali ke fungsi hitunggaris1
    }
    private static void hitunggaris1(int [][] m){
        /*
            fungsi ini digunakan untuk melakukan proses
            untuk mengecek matrix yang akan digaris pada baris atau kolom pada fungsi
            hitunggaris2 proses ini dilakukan sesuai jumlah baris atau kolom dan melakukan
            pengecekan kembali ketika totalgaris kurang dari
            baris atau kolom yang akan dilakukan pada fungsi hitunggaris3
           
        */
        int count=0; //variabel digunakan untuk menghitung iterasi
        int totalgaris=0; //jumlah garis 
        int cekbaris[] = new int [m.length]; // untuk menandai suatu baris yang sudah digaris
        int cekkolom[] = new int [m[0].length]; // untuk menandai suatu kolom yang sudah digaris
        while(count < m.length || count < m[0].length ){ // melakukan perulangan sebanyak jumlah baris atau kolom
            totalgaris = hitunggaris2(m,cekbaris,cekkolom,totalgaris); //variabel totalgaris akan menyimpan nilai yang sudah dilakukan pada fungsi hitunggaris2
            System.out.println();
            System.out.println("Total Garis : "+totalgaris); // menampilkan total garis selama iterasi
            for(int i=0; i<m.length;i++){
                System.out.println("Garis Baris["+i+"]:"+cekbaris[i]); //menampilkan baris yang sudah digaris atau belum
            }
            for(int i=0; i<m[0].length;i++){
               System.out.println("Garis Kolom["+i+"]:"+cekkolom[i]);//menampilkan kolom yang sudah digaris atau belum
            }
            count++; //proses increment
        }
        if(totalgaris<m.length){ //mengecek jika total garis kurang dari total baris atau kolom maka tabel tersebut dihitung kembali ke fungsi hitunggaris3
            hitunggaris3(m,cekbaris,cekkolom);
        }
    }
    private static int hitunggaris2(int [][] m,int [] cekbaris, int [] cekkolom,int totalgaris){
        int nol[]=new int [2*m.length]; 
        /*variabel nol[] ini digunakan untuk menampung jumlah angka 0 disetiap baris dan kolom
          total yang ditampung dalam array nol[] adalah 2*jumlah baris atau kolom.
          contoh totalbaris 2 maka nol[2*totalbaris]
          nol[0],nol[1],nol[2],nol[3] = digunakan untuk menampung total nilai nol untuk baris
          nol[4],nol[5],nol[6],nol[7] = digunakan untuk menampung total nilai nol untuk kolom
        */
        
        //proses mencari jumlah angkan nol di setiap baris dan kolom
        for (int i=0;i < m.length;i++){
            for (int j=0; j<m[0].length;j++){
                /* pada kondisi ini dimana nilai matrix atau m[i][j] harus bernilai 0
                   dan cekbaris[] atau cekkolom[] harus bernilai 0 karena kita menghitung
                   jumlah nol yang belum pernah digaris
                */
                    if(m[i][j]==0 && cekbaris[i]==0 && cekkolom[j]==0){ 
                    nol[i]++; //proses increment pada baris ketika kondisi terpenuhi
                    nol[j+m.length]++; //proses increment pada kolom ketika kondisi terpenuhi
                }
            }
        }
        //menampilkan semua nilai nol[] 
        for (int i=0; i< 2*m.length;i++){
            System.out.println("Total Max Nol["+i+"]:"+nol[i]);
        }
        //proses mencari nol terbanyak pada baris atau kolom
        int bmaxnol=nol[0]; //inisialisasi nilai awal untuk mencari nilai 0 terbanyak pada baris
        int kmaxnol=nol[m.length]; //inisialisasi nilai awal untuk mencari nilai 0 terbanyak pada kolom
        int baris=0,kolom=0; //menampung nilai index 
        for (int i = 0; i<m.length;i++){
            if(bmaxnol<nol[i]){ //proses untuk pencarian nilai 0 max pada baris
                bmaxnol=nol[i];
                baris =i;
            }
            if(kmaxnol<nol[m.length+i]){ //proses untuk pencarian nilai 0 max pada kolom
                kmaxnol=nol[m.length+i];
                kolom= i;
            }
        }
        //menampilkan nilai index dan total nol baris
        System.out.println("Index Baris : "+baris+" Total Nol Baris : "+bmaxnol);
        System.out.println("Index Kolom : "+kolom+" Total Nol Kolom : "+kmaxnol);
        
        //proses garis yang akan ditentukan sesuai jumlah max pada baris atau kolom
        if(bmaxnol > 0 || kmaxnol > 0){ // total nilai harus positif
               /*
                jika kondisi terpenuhi maka tandai atau garis pada
                kolom dan garis. Untuk totalgaris dilakukan penambahan
                */
               if(bmaxnol==kmaxnol && bmaxnol!=1 && bmaxnol!=2){ 
                   cekkolom[kolom]=1;
                   totalgaris++;
                   cekbaris[baris]=1;
                   totalgaris++;
                   
               }
               else if (bmaxnol > kmaxnol){
                   /*
                    jika total nol pada baris lebih besar dari total nol pada kolom
                    maka tandai atau garis pada baris dan melakukan penambahan totalgaris
                   */
                   cekbaris[baris]=1;
                   totalgaris++;
               }
               else if (kmaxnol > bmaxnol){
                    /*
                    jika total nol pada kolom lebih besar dari total nol pada baris
                    maka tandai atau garis pada kolom dan melakukan penambahan totalgaris
                   */
                   cekkolom[kolom]=1;
                   totalgaris++;
               }
               else if (bmaxnol==kmaxnol && (bmaxnol==1 || bmaxnol==2)){
                   /*
                    jika kondisi ini terpenuhi maka tandai atau garis pada 
                    baris dan melakukan penambahan totalgaris
                   */
                   cekbaris[baris]=1;
                   totalgaris++;
               }
               else{
                    /*
                    jika kondisi diatas tidak terpenuhi maka tandai atau garis pada 
                    kolom dan melakukan penambahan totalgaris
                   */
                   cekkolom[kolom]=1;
                   totalgaris++;
               }
        }
        return totalgaris; //mereturn nilai 
    }
    private static boolean cekposisi(int m [][],int baris,int kolom){
         if (m[baris][kolom]!=0){
            return false;
        }
         for (int i=0; i < kolom;i++){ //mengecek posisi dari masing-masing objek
             if (m[baris][i]==9999){
                 return false;
             }
         }
        return true;
    }
    private static boolean ceksolusi(int m [][],int kolom){ //mencari solusi penugasan
        if(kolom>=m.length){
            return true;
        }
        for (int i=0; i < m.length;i++){
            if(cekposisi(m,i,kolom)){
                System.out.println(i+" "+kolom+" ");
                m[i][kolom]=9999;
                
                if(ceksolusi(m,kolom+1)==true){
                    return true;
                }
                m[i][kolom]=0;
            }
        }
        return false;
    }
    private static void cekhasil(int m [][],int temp [][]){ //menghitung hasil
        int k=0,hasil=0;
        int arr1[] = new int [m.length];
        int arr2[] = new int [m[0].length];
        for (int i=0;i < m.length;i++){
            for (int j=0;j<m[0].length;j++){
                if(m[i][j]==9999){
                    hasil+=temp[i][j];
                    arr1[k]=i;
                    arr2[k]=j;
                    k++;
                }
            }
        }
        JFrame frame1 = new JFrame("Solusi Optimal");
	        String x= "";
	        JButton[] buttons = new JButton[k+1];
	        for(int i=0;i<k;i++)
	        { 
	        	x=x+arr1[i];
	        	x=x+","+arr2[i];
	        	buttons[i]=new JButton(x);
	        	x="";
	        }
	        x="";
	        x=x+hasil;
	        buttons[k]=new JButton(x);
	        frame1.getContentPane().setLayout(new FlowLayout());
	        for(int i=0;i<=k;i++){frame1.add(buttons[i]);}
	        frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	        frame1.setPreferredSize(new Dimension(300, 200));
	        frame1.pack();
	        frame1.setLocationRelativeTo(null);
	        frame1.setVisible(true);
    
    }
    private static void metodepenugasan(JTextField matrix [][]){
        int i,j;
       int bobot[][] = new int [matrix.length][matrix[0].length];
       int temp[][] = new int [matrix.length][matrix[0].length];
       for (i = 0; i < matrix.length; i++){
           for (j = 0; j < matrix[0].length; j++){
               bobot[i][j] = Integer.parseInt(matrix[i][j].getText());
               temp[i][j]  = bobot[i][j];
           }
       }
       // fungsi pengurangan jika 0 maka cek baris dan jika 1 maka cek kolom
       pengurangan(bobot,0);
       pengurangan(bobot,1);
       
       hitunggaris1(bobot);
       
       if(ceksolusi(bobot,0)==false){
           System.out.print("Tidak ada solusi");
       }
       cekhasil(bobot,temp);
    }
    
}
