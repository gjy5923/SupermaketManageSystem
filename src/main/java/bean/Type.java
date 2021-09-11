package bean;


public class Type {

  private String typeNum;
  private String type;

  public Type() {
  }

  public Type(String typeNum, String type) {
    this.typeNum = typeNum;
    this.type = type;
  }

  public String getTypeNum() {
    return typeNum;
  }

  public void setTypeNum(String typeNum) {
    this.typeNum = typeNum;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
