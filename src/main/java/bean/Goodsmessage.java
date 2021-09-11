package bean;


public class Goodsmessage {

  private String goodsNum;
  private String name;
  private String type;
  private String purchasePrice;
  private String salePrice;
  private String date;
  private String period;
  private String photo;

  public Goodsmessage() {
  }

  public Goodsmessage(String goodsNum, String name, String type, String purchasePrice, String salePrice, String date, String period, String photo) {
    this.goodsNum = goodsNum;
    this.name = name;
    this.type = type;
    this.purchasePrice = purchasePrice;
    this.salePrice = salePrice;
    this.date = date;
    this.period = period;
    this.photo = photo;
  }

  public String getGoodsNum() {
    return goodsNum;
  }

  public void setGoodsNum(String goodsNum) {
    this.goodsNum = goodsNum;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public String getPurchasePrice() {
    return purchasePrice;
  }

  public void setPurchasePrice(String purchasePrice) {
    this.purchasePrice = purchasePrice;
  }


  public String getSalePrice() {
    return salePrice;
  }

  public void setSalePrice(String salePrice) {
    this.salePrice = salePrice;
  }


  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }


  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }


  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  @Override
  public String toString() {
    return "Goodsmessage{" +
            "goodsNum='" + goodsNum + '\'' +
            ", name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", purchasePrice='" + purchasePrice + '\'' +
            ", salePrice='" + salePrice + '\'' +
            ", date='" + date + '\'' +
            ", period='" + period + '\'' +
            ", photo='" + photo + '\'' +
            '}';
  }
}
