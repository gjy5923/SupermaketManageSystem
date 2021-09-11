package bean;


import DAO.ImageDAO;

import java.sql.SQLException;
import java.util.*;

public class Safetymonitoring {

  private String name;
  private String time;
  //定义的静态变量，用于存储所有的图片信息
  private static List<Safetymonitoring> temp=null;

  public Safetymonitoring(String name, String time) {
    this.name = name;
    this.time = time;
  }

  public Safetymonitoring() {
  }
static {
  if (new Safetymonitoring().getTemp() == null) {
    try {
      new Safetymonitoring().setTemp(ImageDAO.imageList());
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }
}
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public List<Safetymonitoring> getTemp() {
    return temp;
  }

  public void setTemp(List<Safetymonitoring> temp) {
    Safetymonitoring.temp = temp;
  }
//添加数据到定义的静态List<Safetymonitoring> temp 中
  public void addTemp(Safetymonitoring temp) {
    List<Safetymonitoring> t = new ArrayList<>();
    t.add(temp);
    t.addAll(Safetymonitoring.temp);
    Safetymonitoring.temp=t;
  }

  @Override
  public String toString() {
    return "Safetymonitoring{" +
            "name='" + name + '\'' +
            ", time='" + time + '\'' +
            '}';
  }
}
