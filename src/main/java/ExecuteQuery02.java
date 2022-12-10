import java.sql.*;

public class ExecuteQuery02 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/techproed","postgres","qz2wx1ec4rv3");
        Statement st = con.createStatement();


        // 1. Örnek: companies tablosundan en yüksek ikinci number_of_employees değeri olan
        // company ve number_of_employees değerlerini çağırın.

        // 1. yol; offset ve fetch next kullanarak
        String sql1 = "select company,number_of_employees from companies\n" +
                "order by number_of_employees desc \n" +
                "offset 1 row\n" +
                "fetch next 1 row only";

        ResultSet resultSet1 = st.executeQuery(sql1);

        while (resultSet1.next()){
            System.out.println(resultSet1.getString("company")+ "---"+ resultSet1.getInt("number_of_employees"));
        }

        System.out.println("***************");

        // 2. yol; Subquery kullanarak

        String sql2 = "select company,number_of_employees from companies\n" +
                "where number_of_employees = (select max(number_of_employees) \n" +
                "                             from companies\n" +
                "                             where number_of_employees < (select max(number_of_employees) \n" +
                "                                                          from companies))";

        ResultSet resultSet2 = st.executeQuery(sql2);


        while (resultSet2.next()){
            System.out.println(resultSet2.getString("company")+ "---"+ resultSet2.getInt("number_of_employees"));
        }

        con.close();
        st.close();
        resultSet1.close();
        resultSet2.close();  // bu resultset leri kapattin tekrar kullanamazsin demek


    }
}
