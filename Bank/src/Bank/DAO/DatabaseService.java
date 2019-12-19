package Bank.DAO;

import Bank.Member;

public interface DatabaseService {
   public boolean Open();
   public void Insert(Member member);
   public boolean IdCopy(String id);
   public boolean Login(String id, String pw);

}
