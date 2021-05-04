package nsu.akatov.third_task.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsu.akatov.third_task.generated.Node;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class TagEntity {
    @Column
    private String k;
    @Column
    private String v;
    @Id
    @Column(name = "node_id")
    private int nodeId;
    @ManyToOne
    @JoinColumn(name = "node_id", insertable = false, updatable = false)
    private NodeEntity node;
    public TagEntity(int nodeId, Node.Tag tag) {
        this.nodeId = nodeId;
        k = tag.getK();
        v = tag.getV();
    }
    public TagEntity(String k, String v, int nodeId) {
        this.nodeId = nodeId;
        this.k = k;
        this.v = v;
    }
}
