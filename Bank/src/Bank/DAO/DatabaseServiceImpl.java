package Bank.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Bank.Member;
import Bank.Service.CommonService;
import Bank.Service.CommonServiceImpl;

public class DatabaseServiceImpl implements DatabaseService{
   // Driver Class
   final static String DRIVER = "oracle.jdbc.driver.OracleDriver";
   final static String URL = "jdbc:oracle:thin:@192.168.100.10:1521:xe";

   // SQL String
   final String INSERTSQL = "insert into bankmember(id,pw,name,phone,ppnum,banknum,balance)"
         + " values(?,?,?,?,?,?,?)";
   final String LOGINSQL = "select count(id) from bankmember where id=? AND pw=?";
   final String LOGIN = "select count(id) from bankmember where id=?";

   private Connection dbConn;

   static {
      try {
         Class.forName(DRIVER);
      }catch (Exception e) {
         e.printStackTrace();
      }
   }

   @Override
   public boolean Open() {
      try {
         String id = "kgitbank";
         String pw = "itbank";
         dbConn = DriverManager.getConnection(URL, id, pw);
      }catch (Exception e) {
         e.printStackTrace();
         return false;
      }
      return true;
   }

   @Override
   public void Insert(Member member) { 
      try {
         PreparedStatement prep = dbConn.prepareStatement(INSERTSQL);
         prep.setString(1, member.getId());
         prep.setString(2, member.getPw());
         prep.setString(3, member.getName());
         prep.setString(4, member.getPhoneNum());
         prep.setString(5, member.getIdNum());
         prep.setString(6, member.getAccNum());
         prep.setLong(7, member.getMoney()); 

         System.out.println("회원 등록 완료");
         prep.executeUpdate();
      } catch(SQLException e) {
         e.printStackTrace();
      }

   }
   
   // 로그인시 아이디 존재여부 확인 밑 로그인 기능
   public boolean Login(String id, String pw) {

      if (Select(id, pw) && IdEnter(id)) {
         return true;
      } else {
         return false;
      }
   }

   // 로그인 시 아이디와 패스워드 일치 확인
   public boolean Select(String id, String pw) {
      boolean result = true;

      CommonService commonSrv = new CommonServiceImpl();

      try {
         PreparedStatement prep = dbConn.prepareStatement(LOGINSQL);
         prep.setString(1, id);
         prep.setString(2, pw);

         ResultSet rs = prep.executeQuery();
         if(rs.next()) {
            if(rs.getInt(1)==0) {
               commonSrv.InfoMsg("입력하신 정보가 틀립니다.", "ID와 패스워드를 다시 확인해주세요.");
               result = false;
            }
            rs.close();
         } 
      } catch (SQLException e) {
         result = false;
         e.printStackTrace();
      }

      return result;
   }

   // 로그인 진행시 아이디 존재확인
   public boolean IdEnter(String id) {
      boolean result = true;
      CommonService commonSrv = new CommonServiceImpl();

      try {
         PreparedStatement prep = dbConn.prepareStatement(LOGIN);
         prep.setString(1, id);

         ResultSet rs = prep.executeQuery();
         if(rs.next()) {
            if(rs.getInt(1) == 0) {
               commonSrv.InfoMsg("해당 아이디는 존재하지 않습니다.", "다시한번 확인해주세요.");
               result = false;
            }
            rs.close();
         } 
      } catch (SQLException e) {
         result = false;
         e.printStackTrace();
      }

      return result;
   }

   // 아이디 중복 확인
   @Override
   public boolean IdCopy(String id) {
      boolean result = true;

      try {
         PreparedStatement prep = dbConn.prepareStatement(LOGIN);
         prep.setString(1, id);

         ResultSet rs = prep.executeQuery();
         if(rs.next()) {
            if(rs.getInt(1) != 0) {
               CommonService commonSrv = new CommonServiceImpl();
               commonSrv.InfoMsg("이미 등록된 아이디 입니다.", "아이디를 다시 입력하세요.");
               result = false;
            }
            rs.close();
         } 
      } catch (SQLException e) {
         result = false;
         e.printStackTrace();
      }

      return result;
   }
}
