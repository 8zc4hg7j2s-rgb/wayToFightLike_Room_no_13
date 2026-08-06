package nitin.gupta.room.no13.coreJavaPrectise.validation;

import lombok.Getter;

@Getter

public class Testing {
    @ContactNumberConstraint
    @FieldsValueMatch(field = "password",
            fieldMatch = "verifyPassword",
            message = "Passwords do not match!")
    private String contractNo;
    @MobileValidation

    private Integer mobileNO;

    public static void main(String[] args) {
        Testing testing = new Testing();
        testing.setContractNo("hello How r u");
        System.out.println(testing.getContractNo());
    }

    public String getContractNo() {
        return contractNo;
    }

    @ContactNumberConstraint
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getMobileNO() {
        return mobileNO;
    }

    public void setMobileNO(Integer mobileNO) {
        this.mobileNO = mobileNO;
    }
}


