package bsu.radchuk.task.model;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
    @Expose(deserialize = false)
    int id;
    String login;
    String passwordHash;
}
