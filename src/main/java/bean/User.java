package bean;


public class User {

  private String eno;
  private String username;
  private String password;
  private String position;
  public User() {
  }

  public User(String eno, String username, String password) {
    this.eno = eno;
    this.username = username;
    this.password = password;
  }

  public User(String eno, String username, String password, String position) {
    this.eno = eno;
    this.username = username;
    this.password = password;
    this.position = position;
  }

  public String getEno() {
    return eno;
  }

  public void setEno(String eno) {
    this.eno = eno;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  @Override
  public String toString() {
    return "User{" +
            "eno='" + eno + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", position='" + position + '\'' +
            '}';
  }
}
