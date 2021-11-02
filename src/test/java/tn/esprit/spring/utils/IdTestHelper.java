package tn.esprit.spring.utils;

import java.util.UUID;

public class IdTestHelper {
    public IdTestHelper() {
    }

    public String createUniqueId() {
        return UUID.randomUUID().toString();
    }

    public int createRandomInt() {
        return (int) Math.random() * 100;
    }

    public String createRandomString(int maxLength) {
        String id = this.createUniqueId();
        return id.length() <= maxLength ? id : id.substring(0, maxLength);
    }
}
