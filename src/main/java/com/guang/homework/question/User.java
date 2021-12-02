package com.guang.homework.question;

import java.util.Objects;

/**
 * @author guangyong.deng
 * @date 2021-12-02 17:45
 */
public class User {

    private String roleRight;

    private String labRight;

    private String productRight;

    public User(String roleRight, String labRight, String productRight) {
        this.roleRight = roleRight;
        this.labRight = labRight;
        this.productRight = productRight;
    }

    public String getRoleRight() {
        return roleRight;
    }

    public User setRoleRight(String roleRight) {
        this.roleRight = roleRight;
        return this;
    }

    public String getLabRight() {
        return labRight;
    }

    public User setLabRight(String labRight) {
        this.labRight = labRight;
        return this;
    }

    public String getProductRight() {
        return productRight;
    }

    public User setProductRight(String productRight) {
        this.productRight = productRight;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(roleRight, user.roleRight) &&
                Objects.equals(labRight, user.labRight) &&
                Objects.equals(productRight, user.productRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleRight, labRight, productRight);
    }

    @Override
    public String toString() {
        return "User{" +
                "roleRight='" + roleRight + '\'' +
                ", labRight='" + labRight + '\'' +
                ", productRight='" + productRight + '\'' +
                '}';
    }
}
