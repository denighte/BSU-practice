package bsu.radchuk.task.model;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhotoPost {
    @Expose(deserialize = false)
    private int id;
    private String description;
    private String author;
    private String date;
    @Expose(deserialize = false)
    private String src;
}
