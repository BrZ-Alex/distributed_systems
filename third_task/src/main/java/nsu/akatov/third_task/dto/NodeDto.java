package nsu.akatov.third_task.dto;

import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NodeDto {
    @NonNull
    private List<TagDto> tag;
    @NonNull
    private int id;
    @NonNull
    private int version;
    @NonNull
    private int uid;
    @NonNull
    private String user;
    @NonNull
    private int changeset;
    @NonNull
    private double lat;
    @NonNull
    private double lon;
}
