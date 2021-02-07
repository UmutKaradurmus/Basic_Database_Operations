package javaapplication4;

import static java.lang.System.out;
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class MsSqlConnection {

    public DefaultTableModel eListele(String sql) {
        try {
            MsSqlConnection msql = new MsSqlConnection();

            Statement stmt = msql.stmtver();

            ResultSet rs = stmt.executeQuery(sql);

            String row[] = new String[6];

            String dizim[][] = null;

            DefaultTableModel model = new DefaultTableModel(
                    dizim,
                    new String[]{"Id", "İsim", "Soy İsim", "Doğum Tarihi", "Maaş", "Yaşadığı Şehir"}
            );
            while (rs.next()) {
                row[0] = rs.getString("id");
                row[1] = rs.getString("adi");
                row[2] = rs.getString("soyadi");
                row[3] = rs.getString("dogTah");
                row[4] = rs.getString("maaş");
                row[5] = rs.getString("ySehir");
                model.addRow(row);
            }

            return model;
        } catch (SQLException ex) {
            Logger.getLogger(PersonelFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public void listele(String s) throws SQLException {
        MsSqlConnection msc = new MsSqlConnection();
        Statement stmt = msc.stmtver();
        ResultSet rs = stmt.executeQuery(s);
        System.out.println("Yazdırılıyor...");
        while (rs.next()) {

            System.out.println(rs.getInt("id") + " " + rs.getString("adi") + " "
                    + "" + rs.getString("soyadi") + " " + rs.getDate("dogTah") + ""
                    + " " + rs.getInt("maaş") + " " + rs.getString("ySehir") + " ");
        }

    }

    public void add(String ad, String Soyad, String dogTah, int maaş, String yŞehir, Statement stmt) throws SQLException {
        try {
            String tmp = "insert into Personel (adi,soyadi,dogTah,maaş,ySehir) values ('";               //girişte alınan değer burada sql komutuna dönüşüp bir komut olsun diye eklenerek ilerliyor
            tmp += ad + "','" + Soyad + "','" + dogTah + "'," + maaş + ",'" + yŞehir + "')";
            System.out.println("Eklendi");
            String sq2 = tmp;
            stmt.executeUpdate(sq2);

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public Statement stmtver() throws SQLException {
        String dbURL = "jdbc:sqlserver://localhost\\sqlexpress;user=umut;password=1234";                 //veritabanı bağlantısı yapılıyor

        Connection conn = DriverManager.getConnection(dbURL);

        Statement stmt = conn.createStatement();

        return stmt;
    }

    public void print(Statement stmt) throws SQLException {
        String sql = "select * from Personel ";
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("Yazdırılıyor...");
        while (rs.next()) {

            System.out.println(rs.getInt("id") + " " + rs.getString("adi") + " "
                    + "" + rs.getString("soyadi") + " " + rs.getDate("dogTah") + ""
                    + " " + rs.getInt("maaş") + " " + rs.getString("ySehir") + " ");
        }
    }

    public void delete(int id, Statement stmt) {
        String tmp = "delete from Personel where id= ";                                                  //girişte alınan değer burada sql komutuna dönüşüp bir komut olsun diye eklenerek ilerliyor
        tmp += id;
        System.out.println("Silindi");
        try {
            stmt.executeUpdate(tmp);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void menü(Statement stmt) throws SQLException {
        int a = 1;
        MsSqlConnection msc = new MsSqlConnection();
        while (a == 1) {
            Scanner s = new Scanner(System.in);
            System.out.println("Menüye hoş geldiniz \n"
                    + " ►Personel ekleme için 1\n"
                    + " ►Peersonel silme için 2\n"
                    + " ►Personel tablosunu yazdırmak için 3\n"
                    + " ►Personel güncellemek için 4\n"
                    + " ►Listeleme ve Gruplandırma için 5 \n"
                    + " ►Menüden çıkış yapmak için 6 giriniz");
            int choose = s.nextInt();                                                                    //Menü için anahtar işlevi göryor
            switch (choose) {
                case 1:
                    String dogTah;
                    System.out.println("Personel ekleme alanına hoş geldiniz"
                            + "Sırasıyla eklemek istenen personel için İsim ve soy isim giriniz");
                    String ad = s.next();
                    String soyad = s.next();
                    System.out.println("Doğum tarihini sırasıyla yıl ay gün şeklinde giriniz");
                    dogTah = "";
                    int yıl = s.nextInt();
                    dogTah += yıl + "-";
                    int ay = s.nextInt();
                    dogTah += ay + "-";
                    int gün = s.nextInt();
                    dogTah += gün;
                    System.out.println("Personelin maaşını giiniz");
                    int maaş = s.nextInt();
                    System.out.println("Yaşadığı şehri giriniz");
                    String ySehir = s.next();
                    msc.add(ad, soyad, dogTah, maaş, ySehir, stmt);

                    break;
                case 2:
                    System.out.println("Silek istenilen personelin id numarasını giriniz");
                    int id = s.nextInt();
                    msc.delete(id, stmt);
                    break;
                case 3:
                    msc.print(stmt);
                    break;
                case 4:
                    int b = 0;
                    while (b == 0) {
                        System.out.println("Güncelleme seçenekleri \n"
                                + " ►isim güncellemek için 1 \n"
                                + " ►soy isim güncellemek için 2 \n"
                                + " ►doğum tarihi güncellemek için 3\n"
                                + " ►maaş güncellemek için 4\n"
                                + " ►yaşanılan şehri güncellemek için 5\n"
                                + " ►üst menüye dönmeek için 6 giriniz");
                        int sc = s.nextInt();
                        switch (sc) {
                            case 1:
                                System.out.println("Güncellenecek Personelin id numarasını giriniz");
                                id = s.nextInt();
                                System.out.println("Güncel ismi giriniz");
                                ad = s.next();
                                String geçici = "Update Personel Set adi='" + ad + "' where id= " + id;
                                stmt.execute(geçici);
                                System.out.println("Güncellendi");
                                break;
                            case 2:
                                System.out.println("Güncellenecek Personelin id numarasını giriniz");
                                id = s.nextInt();
                                System.out.println("Güncel soyadını giriniz");
                                soyad = s.next();
                                geçici = "Update Personel Set soyadi='" + soyad + "' where id= " + id;
                                stmt.execute(geçici);
                                System.out.println("Güncellendi");
                                break;
                            case 3:
                                System.out.println("Güncellenecek Personelin id numarasını giriniz");
                                id = s.nextInt();
                                System.out.println("Güncel doğum tarihini sırasıyla yıl ay gün şeklinde giriniz");
                                dogTah = "";
                                yıl = s.nextInt();
                                dogTah += yıl + "-";
                                ay = s.nextInt();
                                dogTah += ay + "-";
                                gün = s.nextInt();
                                dogTah += gün + "";
                                geçici = "Update Personel Set dogTah='" + dogTah + "' where id= " + id;
                                stmt.execute(geçici);
                                System.out.println("Güncellendi");
                                break;

                            case 4:
                                System.out.println("Güncellemek istediğiniz Personelin id numarasını giriniz");
                                id = s.nextInt();
                                System.out.println("Güncel maaş değerini giriniz");
                                maaş = s.nextInt();
                                geçici = "Update Personel Set maaş='" + maaş + "' where id= " + id;
                                stmt.execute(geçici);
                                System.out.println("Güncellendi");
                                break;
                            case 5:
                                System.out.println("Güncellemek istediğiniz Personelin id numarasını giriniz");
                                id = s.nextInt();
                                System.out.println("Güncel Yaşanılan şehri giriniz");
                                ySehir = s.next();
                                geçici = "Update Personel Set ySehir='" + ySehir + "' where id= " + id;
                                stmt.execute(geçici);
                                System.out.println("Güncellendi");
                                break;

                            case 6:
                                b = 1;                                                                     //while döngüsü için sonlanma koşulu
                                break;

                        }

                    }
                    break;
                case 6:
                    a = 0;                                                                                 //while döngüsü için sonlanma koşulu
                    break;
                case 5:
                    int c = 2;
                    while (c == 2) {
                        System.out.println(" ►Listemek için 1\n"
                                + " ►gruplama için 2 \n"
                                + " ►üst menüye dönmek için 3 giriniz");
                        int sc5 = s.nextInt();
                        switch (sc5) {
                            case 1:
                                int f = 10;
                                while (f == 10) {

                                    System.out.println("Listeleme modelini seçiniz\n"
                                            + " ►Alfaetik isim sırası için 1\n"
                                            + " ►Alfabetik soyisim sırası için 2\n"
                                            + " ►Maaş büyükten küçüğe sırası için 3\n"
                                            + " ►Maaş küçükten büyüğe sırası için 4\n"
                                            + " ►Yaş büyükten küçüğe sırası için 5\n"
                                            + " ►Yaş küçükten büyüğesırası için 6\n"
                                            + " ►Üst menüye dnmek için 7 giriniz");
                                    int sc6 = s.nextInt();

                                    switch (sc6) {
                                        case 1:

                                            String sql = "select * from Personel order by adi  ";
                                            msc.listele(sql);
                                            break;
                                        case 2:
                                            sql = "select * from Personel order by soyadi ";
                                            msc.listele(sql);

                                            break;
                                        case 3:
                                            sql = "select * from Personel order by maaş desc";
                                            msc.listele(sql);

                                            break;

                                        case 4:
                                            sql = "select * from Personel order by maaş ";
                                            msc.listele(sql);

                                            break;
                                        case 5:
                                            sql = "select * from Personel order by dogTah ";
                                            msc.listele(sql);
                                            break;
                                        case 6:
                                            sql = "select * from Personel order by dogTah desc ";
                                            msc.listele(sql);
                                            break;
                                        case 7:
                                            f = 0;
                                            break;
                                    }
                                }
                                break;
                            case 2:
                                int h = 0;
                                while (h == 0) {
                                    System.out.println("Gruplama modelini seçiniz\n"
                                            + " ►Aynı şehirden olanlar için 1 i \n"
                                            + " ►Maaşı bazlı için 2 \n"
                                            + " ►Üst menüye dönmek için 3 giriniz");
                                    int sc9 = s.nextInt();
                                    switch (sc9) {
                                        case 1:
                                            System.out.println("Hangi şehirden personeller girmek istiyorsunuz lütfen ilk hafi büyük olacak şekilde türkçe karakterler kullanmadan yaıznız yazınız");
                                            String sql = "select * from Personel Where ySehir='";
                                            ySehir = s.next();
                                            sql += ySehir + "'";
                                            msc.listele(sql);
                                            break;
                                        case 2:
                                            System.out.println("Görmek istediğiniz maaş aralığını önce max sonra min değer olarak giriniz");
                                            int max = s.nextInt();
                                            int min = s.nextInt();
                                            sql = "select * from Personel Where maaş>";
                                            sql += min + " and maaş<" + max;
                                            msc.listele(sql);
                                            break;
                                        case 3:
                                            h = -1;
                                            break;
                                    }
                                }
                                break;
                            case 3:
                                c = 0;
                                break;
                        }
                    }
                    break;
            }

        }
    }

    public static void main(String[] args) throws SQLException {

        String dbURL = "jdbc:sqlserver://localhost\\sqlexpress;user=umut;password=1234";                 //veritabanı bağlantısı yapılıyor

        Connection conn = DriverManager.getConnection(dbURL);

        if (conn != null) {
            System.out.println("Connected");
        }
        Statement stmt = conn.createStatement();

        MsSqlConnection msc = new MsSqlConnection();
        PersonelFrame frame = new PersonelFrame();
//        frame.setVisible(true);
       msc.menü(stmt);
        conn.close();

    }
}
