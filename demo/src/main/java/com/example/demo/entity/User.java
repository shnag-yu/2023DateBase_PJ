package com.example.demo.entity;

/**
 * @Description：
 * @Author ShangYu
 * @date 2023-12-09
 **/

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
        private Long id;
        private String name;
        private String password;
        private Short age;
        private String gender;
        private Long tel;

    // 构造函数、getter和setter等方法
    public User(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name)
                && Objects.equals(password, user.password) && Objects.equals(age, user.age)
                && Objects.equals(gender, user.gender) && Objects.equals(tel, user.tel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, age, gender, tel);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", tel=" + tel +
                '}';
    }
}

