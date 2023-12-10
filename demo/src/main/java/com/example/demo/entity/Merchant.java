package com.example.demo.entity;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Merchant {
    private Long id;
    private String name;
    private String password;
    private String addr;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Merchant)) return false;
        Merchant merchant = (Merchant) o;
        return Objects.equals(id, merchant.id) &&
                Objects.equals(name, merchant.name) &&
                Objects.equals(password, merchant.password) &&
                Objects.equals(addr, merchant.addr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, addr);
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }
}
