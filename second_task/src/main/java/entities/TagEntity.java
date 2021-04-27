package entities;

import generated.Node;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagEntity {
    private String k;
    private String v;
    private int nodeId;

    public TagEntity(int nodeId, Node.Tag tag) {
        this.nodeId = nodeId;
        k = tag.getK();
        v = tag.getV();
    }
}
