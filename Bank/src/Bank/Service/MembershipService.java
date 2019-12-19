package Bank.Service;

import javafx.scene.Parent;

public interface MembershipService {
	// Membership fx:id 
	String fx_Name = "#joinName";
   String fx_Id = "#joinId";
   String fx_Pw = "#joinPw";
   String fx_PwOk = "#joinPwOk";
   String fx_IdNum1 = "#securityNum1";
   String fx_IdNum2 = "#securityNum2";
   String fx_AccNum = "#bankNum";
   String fx_PhoneNum1 = "#phoneNum1";
   String fx_PhoneNum2 = "#phoneNum2";
   String fx_PhoneNum3 = "#phoneNum3";
   String fx_Money = "#money";
	
	public void membershipProc(Parent membershipForm);
}

