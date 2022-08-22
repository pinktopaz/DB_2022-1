
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//DB�� �����Ͽ� �۾� ó���ϱ� ���� Ŭ����

public class DB2022_Team08_ProductDAO {
	private static Connection conn;

    private PreparedStatement pstmt;
    private CallableStatement cstmt;
    private ResultSet rs;


    //�⺻������
    public DB2022_Team08_ProductDAO() {

    }

    private void getConnection() throws ClassNotFoundException, SQLException{
        if(conn == null){
            //��������
        	String url = "jdbc:mysql://localhost:3306/DB2022Team08";
			String user = "DB2022Team08";
			String pw = "DB2022Team08";

            //JDBC����̹� �ε�
            Class.forName("com.mysql.cj.jdbc.Driver");

            //mysql�� �����Ͽ� Connection ��ü ���.
            conn = DriverManager.getConnection(url,user,pw);

        }
    }


    /**
     * ��ǰ ����ϱ�
     */
    public boolean insertProduct(DB2022_Team08_ProductDTO dto){

        boolean result = false;
        try {
            getConnection();

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO DB2022_��ǰ VALUES(?, ?, ?, ?, ?, ?, ?)");

            pstmt.setString(1,dto.getProduct_num());
            pstmt.setString(2,dto.getCategory());
            pstmt.setString(3,dto.getName());
            pstmt.setString(4,dto.getSize());
            pstmt.setInt(5,dto.getPrice());
            pstmt.setString(6,dto.getCompany());
            pstmt.setInt(7,dto.getStock());

            int r = pstmt.executeUpdate();

            if(r>0) result = true;

        } catch (Exception e) {
            System.out.println("���ܹ߻�:insertMember()=> "+e.getMessage());
        }finally{
            dbClose();
        }
        return result;
    }

    /**
     * ��ǰ��ȣ�� �ش��ϴ� ��ǰ���� ����
     */
    public DB2022_Team08_ProductDTO getProduct(String product_num){
        DB2022_Team08_ProductDTO dto =null;
        try {
            getConnection();

            String sql = "SELECT * FROM DB2022_��ǰ WHERE ��ǰ��ȣ = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, product_num);
            ResultSet r = pstmt.executeQuery();

            if(r.next()) {
                String category = r.getString("��ǰī�װ�");
                String name = r.getString("��ǰ��");
                String size = r.getString("��ǰ������");
                Integer price = r.getInt("�ǸŰ���");
                String company = r.getString("������ü");
                Integer stock = r.getInt("���");

                dto = new DB2022_Team08_ProductDTO(product_num, category, name, size,
                price, company, stock);
            }

        } catch (Exception e) {
            System.out.println("���ܹ߻�:deleteMember()=> "+e.getMessage());
        }finally{
            dbClose();
        }

        return dto;
    }

    /**
     * ����� ��ǰ ��� ����
     */
    public List<DB2022_Team08_ProductDTO> getProductList(){
        List<DB2022_Team08_ProductDTO> list = new ArrayList<DB2022_Team08_ProductDTO>();

        try {
            getConnection();

            String sql = "SELECT * FROM DB2022_��ǰ";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet r = pstmt.executeQuery();

            while(r.next()) {
                String product_num = r.getString("��ǰ��ȣ");
                String category = r.getString("��ǰī�װ�");
                String name = r.getString("��ǰ��");
                String size = r.getString("��ǰ������");
                int price = r.getInt("�ǸŰ���");
                String company = r.getString("������ü");
                int stock = r.getInt("���");
                list.add(new DB2022_Team08_ProductDTO(product_num,category,name,size,price,company,stock));
            }

        } catch (Exception e) {
            System.out.println("���ܹ߻�:getProductList()=> "+e.getMessage());
        }finally{
            dbClose();
        }

        return list;
    }


    /**
     * ��ǰ ����
     */
    public boolean updateProduct(DB2022_Team08_ProductDTO dto){

        boolean result = false;
        try {
            getConnection();

            String sql = "UPDATE DB2022_��ǰ SET ��ǰī�װ�=? ,��ǰ��=?, ��ǰ������=?, �ǸŰ���=?, ������ü=?, ���=? WHERE ��ǰ��ȣ=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,dto.getCategory());
            pstmt.setString(2,dto.getName());
            pstmt.setString(3,dto.getSize());
            pstmt.setInt(4,dto.getPrice());
            pstmt.setString(5,dto.getCompany());
            pstmt.setInt(6,dto.getStock());
            pstmt.setString(7,dto.getProduct_num());

            int r = pstmt.executeUpdate();

            if(r>0) result = true;

        } catch (Exception e) {
            System.out.println("���ܹ߻�:updateProduct()=> "+e.getMessage());
        }finally{
            dbClose();
        }
        return result;
    }


    /**
     * ��ǰ ����
     */
    public boolean deleteProduct(String productNum){
        boolean result = false;
        try {
            getConnection();

            String sql = "DELETE FROM DB2022_��ǰ WHERE ��ǰ��ȣ = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, productNum);
            int r = pstmt.executeUpdate();

            if(r>0) result = true;

        } catch (Exception e) {
            System.out.println("���ܹ߻�:deleteProduct()=> "+e.getMessage());
        }finally{
            dbClose();
        }
        return result;
    }//deleteMember()--------------


    /**DB���� ����(�ݱ�)*/
    public void dbClose(){

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("����:ResultSet��ü close():" + e.getMessage());
            }
        }

        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("����:PreparedStatement��ü close():" + e.getMessage());
            }
        }

        if (cstmt != null) {
            try {
                cstmt.close();
            } catch (SQLException e) {
                System.out.println("����:CallableStatement��ü close():" + e.getMessage());
            }
        }


        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("����:Connection��ü close():" + e.getMessage());
            }
        }

        conn = null;
    }//dbClose()---------

}
