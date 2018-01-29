package tv.guojiang.network;

import com.google.gson.annotations.SerializedName;

/**
 * @author leo
 */
public class Person {

    @SerializedName("user_id")
    public String uid;

    public String username;

    @Override
    public String toString() {
        return "Person{" +
            "uid='" + uid + '\'' +
            ", username='" + username + '\'' +
            '}';
    }
}
