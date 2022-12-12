import java.sql.*;

public class CallableStatement01 {
    /*
    Java'da method'lar return type sahibi olsa da olmasa da method olarak adlandirilir.
    SQL'de ise data return ediyorsa "fuction" denir, return yapmiyorsa "procedure" olarak adlandirilir.
     */

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/techproed","postgres","qz2wx1ec4rv3");
        Statement st = con.createStatement();

        // CallableStatement ile function cagirmayi parametrelendirecegiz.

        // 1. adim: Function kodunu yaz
        String sql1 = "create or replace function toplama(x numeric, y numeric)\n" +
                "returns numeric \n" +
                "language plpgsql\n" +
                "as\n" +
                "$$\n" +
                "begin\n" +
                "\n" +
                "return x+y;\n" +
                "\n" +
                "end\n" +
                "$$";

        // 2. adim: Function calistir.
        st.execute(sql1);

        // 3. adim: Function'i cagir.
        CallableStatement cst1 = con.prepareCall("{? = call toplama(?, ?)}");  // ilk paranetre return type

        // 4. adim: Return icin registerOurParameter() methodunu, parametreler icin ise set()... methodlarini uygula
         cst1.registerOutParameter(1,Types.NUMERIC);  // return type olarak hangi data tipini verir onu belirtiyoruz
         cst1.setInt(2,6);
         cst1.setInt(3,4);

         // 5. adim: execute() methodu ile CallableStatement'i calistir.
        cst1.execute();

        // 6. adim: Sonucu cagirmak icin return data type'ina gore
        System.out.println("toplam: " + cst1.getBigDecimal(1));



        //2. Örnek: Koninin hacmini hesaplayan bir function yazın.

        String sql2 = "create or replace function konininHacmiF(r numeric, h numeric)\n" +
                "returns numeric \n" +
                "language plpgsql\n" +
                "as\n" +
                "$$\n" +
                "begin\n" +
                "\n" +
                "return 3.14*r*r*h/3;\n" +
                "\n" +
                "end\n" +
                "$$";

        st.execute(sql2);

        CallableStatement cst2 = con.prepareCall("{? = call konininHacmiF(?, ?)}");

        cst2.registerOutParameter(1,Types.NUMERIC);
        cst2.setInt(2,1);
        cst2.setInt(3,6);

        cst2.execute();

        System.out.printf("konininHacmiF: " + "%.2f" ,cst2.getBigDecimal(1));

    }
}
