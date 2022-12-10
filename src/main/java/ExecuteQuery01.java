import java.sql.*;

public class ExecuteQuery01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");

        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/techproed","postgres","qz2wx1ec4rv3");

        Statement st = con.createStatement();


        //1. Örnek:  region id'si 1 olan "country name" değerlerini çağırın.

        String sql1 = "select country_name from countries where region_id=1";
        boolean r1 = st.execute(sql1);
        System.out.println("r1 = " + r1);

        // Recordlari gormek icin ExecuteQuery() methodunu kullanmaliyiz.

        ResultSet resultSet1 = st.executeQuery(sql1);

        while (resultSet1.next()){
            System.out.println(resultSet1.getString(1));
        }

        System.out.println("--------------------------");


        // 2.Örnek: "region_id"nin 2'den büyük olduğu "country_id" ve "country_name" değerlerini çağırın.

        String sql2 = "select country_name, country_id from countries where region_id>2";
        ResultSet resultSet2 = st.executeQuery(sql2);

        while (resultSet2.next()){
            System.out.println(resultSet2.getString("country_name")+"--"+resultSet2.getString("country_id"));
        }

        System.out.println("--------------------");

        //3.Örnek: "number_of_employees" değeri en düşük olan satırın tüm değerlerini çağırın.

        String sgl3 = "select * from companies where number_of_employees = (select min(number_of_employees) from companies)";
        ResultSet resultSet3 = st.executeQuery(sgl3);

        while (resultSet3.next()){
            System.out.println( resultSet3.getInt(1)+ "--"+
                    resultSet3.getString(2) + "--" +
                    resultSet3.getInt(3));
        }





    }
}
