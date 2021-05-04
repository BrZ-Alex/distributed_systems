package nsu.akatov.third_task.dto;

import lombok.NonNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TagDto {
    @NonNull
    private String k;
    @NonNull
    private String v;
}
