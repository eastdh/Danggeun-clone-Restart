package io.github.restart.gmo_danggeun.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "\"user\"")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 30, nullable = false)
  private String email;

  @Column(length = 100, nullable = false)
  private String password;

  @Column(length = 8, nullable = false)
  private String nickname;

  @Column(name = "join_date", nullable = false)
  private LocalDateTime joinDate;

  @Column(length = 50)
  private String location;

  @Column(name = "manner_score")
  private double mannerScore;

  // OneToMany: 사용자 = 리뷰 구매자/판매자, 이미지 업로더, 글쓴이, 채팅 작성자 등


  public User() {
  }

  public User(Long id, String email, String password, String nickname, LocalDateTime joinDate, String location) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.joinDate = joinDate;
    this.location = location;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public LocalDateTime getJoinDate() {
    return joinDate;
  }

  public void setJoinDate(LocalDateTime joinDate) {
    this.joinDate = joinDate;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public double getMannerScore() {
    return mannerScore;
  }

  public void setMannerScore(double mannerScore) {
    this.mannerScore = mannerScore;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}