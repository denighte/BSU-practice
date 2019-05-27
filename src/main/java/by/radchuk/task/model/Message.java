package by.radchuk.task.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    private int status;
    private String data;
}
